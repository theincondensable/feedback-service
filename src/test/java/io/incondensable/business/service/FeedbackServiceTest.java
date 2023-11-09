package io.incondensable.business.service;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.FeedbackRepository;
import io.incondensable.business.service.model.Delivery.DeliveryDummy;
import io.incondensable.business.service.model.biker.BikerDummy;
import io.incondensable.business.service.model.feedback.FeedbackDummy;
import io.incondensable.global.exception.BusinessException;
import io.incondensable.util.FeedbackTestUtil;
import io.incondensable.web.dto.feedback.request.FeedbackRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Feedback Service Unit Tests")
class FeedbackServiceTest {

    private final DeliveryService deliveryService = mock(DeliveryService.class);
    private final FeedbackRepository feedbackRepository = mock(FeedbackRepository.class);
    private final NotificationService notificationService = mock(NotificationService.class);

    private final FeedbackService SUT = new FeedbackService(deliveryService, feedbackRepository, notificationService);

    @Test
    @DisplayName("GIVEN the Delivery ID, Rating and comment for it WHEN there is already a Feedback submitted on the Delivery THEN an Exception is thrown")
    public void testFeedbackIsSubmittedOnDelivery() {
        //GIVEN
        Long expectedDeliveryId = 1L;
        Byte expectedRating = 5;
        FeedbackRequestDto req = new FeedbackRequestDto(expectedDeliveryId, expectedRating, "");

        when(feedbackRepository.findFeedbackByDelivery_Id(req.getDeliveryId())).thenReturn(Optional.of(new Feedback()));

        //WHEN
        BusinessException expectedException = assertThrows(BusinessException.class, () -> SUT.submitFeedback(req));

        //THEN
        assertThat(expectedException.getErrorDetails().getMessage(), is("feedback.already.submitted.for.delivery"));
    }

    @Test
    @DisplayName("GIVEN submit feedback request WHEN The Customer that is submitting Feedback on a certain Delivery is NOT the Deliveree THEN an Exception is thrown.")
    public void testFeedbackSubmitterIsNotTheDeliveree() {
        User customerSubmittingFeedback = FeedbackTestUtil.CUSTOMER_USER;
        FeedbackTestUtil.beforeSetup(customerSubmittingFeedback.getId());

        //GIVEN
        Long expectedDeliveryId = 1L;

        User deliveree = FeedbackTestUtil.CUSTOMER_USER;

        // So that now the Deliveree Customer differs from the Submitting Customer.
        deliveree.setId(customerSubmittingFeedback.getId() + 1);

        Delivery expectedDelivery = DeliveryDummy.create(expectedDeliveryId, deliveree);

        Byte expectedRating = 5;
        FeedbackRequestDto req = new FeedbackRequestDto(expectedDeliveryId, expectedRating, "");

        when(feedbackRepository.findFeedbackByDelivery_Id(req.getDeliveryId())).thenReturn(Optional.empty());
        when(deliveryService.getDeliveryById(req.getDeliveryId())).thenReturn(expectedDelivery);

        //WHEN
        BusinessException expectedException = assertThrows(BusinessException.class, () -> SUT.submitFeedback(req));

        //THEN
        assertThat(expectedException.getErrorDetails().getMessage(), is("feedback.must.be.submitted.by.proper.user"));
    }

    @Test
    @DisplayName("GIVEN submit feedback request WHEN Customer Submitting Feedback is the Deliveree THEN Notification is sent to Biker's PhoneNumber and Feedback is Persisted and Returned.")
    public void testFeedbackIsSubmittingByTheLoggedInCustomer() {
        User deliveree = FeedbackTestUtil.CUSTOMER_USER;
        FeedbackTestUtil.beforeSetup(deliveree.getId());

        //GIVEN
        Long expectedDeliveryId = 1L;
        Delivery expectedDelivery = DeliveryDummy.create(expectedDeliveryId, deliveree);
        User biker = FeedbackTestUtil.BIKER_USER;
        expectedDelivery.setBiker(BikerDummy.createBiker(biker));

        Byte expectedRating = 5;
        FeedbackRequestDto req = new FeedbackRequestDto(expectedDeliveryId, expectedRating, "");

        when(feedbackRepository.findFeedbackByDelivery_Id(req.getDeliveryId())).thenReturn(Optional.empty());
        when(deliveryService.getDeliveryById(req.getDeliveryId())).thenReturn(expectedDelivery);

        //WHEN
        Feedback actualFeedback = SUT.submitFeedback(req);

        //THEN
        verify(feedbackRepository, times(1)).save(any());
        verify(notificationService, times(1)).notifyByPhoneNumber(biker.getPhoneNumber());
        verify(notificationService, times(0)).notifyByPhoneNumber(deliveree.getPhoneNumber());
        assertThat(actualFeedback.getDelivery().getDeliveree(), is(expectedDelivery.getDeliveree()));
        assertThat(actualFeedback.getRating(), is(expectedRating));
    }

    @Test
    @DisplayName("WHEN all Feedbacks is going to be returned sorted by Delivery Date THEN a List of Feedbacks are returned.")
    public void testGettingAllFeedbacksSortedByDeliveryDate() {
        //GIVEN
        User bikerUser = FeedbackTestUtil.BIKER_USER;
        List<Feedback> expected = FeedbackDummy.createList(bikerUser);
        expected = expected.stream()
                .sorted(Comparator.comparing(feedback -> feedback.getDelivery().getCreatedOn()))
                .toList();
        when(feedbackRepository.findAllSortedByDeliveryCreatedDate()).thenReturn(expected);

        //WHEN
        List<Feedback> actual = SUT.getAllFeedbacksSortedByDate();

        //THEN
        assertThat(actual.size(), is(expected.size()));
        assertThat(actual.containsAll(expected), is(Boolean.TRUE));
    }

    @Test
    @DisplayName("GIVEN Biker ID WHEN Feedbacks of the Biker is going to be given THEN A List of Feedbacks will be returned.")
    public void testGettingAllFeedbacksOfBiker() {
        //GIVEN
        User bikerUser = FeedbackTestUtil.BIKER_USER;
        List<Feedback> expected = FeedbackDummy.createList(bikerUser);
        when(feedbackRepository.findAllByBiker_Id(bikerUser.getId())).thenReturn(expected);

        //WHEN
        List<Feedback> actual = SUT.getAllFeedbacksOfBiker(bikerUser.getId());

        //THEN
        assertThat(actual.size(), is(expected.size()));
        assertThat(actual.containsAll(expected), is(Boolean.TRUE));
    }

    @Test
    @DisplayName("GIVEN Rating WHEN Feedbacks with the Rating is needed THEN the List of Feedbacks with the given Rating is returned.")
    public void testGettingAllFeedbacksOfRating() {
        //GIVEN
        Byte rating = 4;
        User bikerUser = FeedbackTestUtil.BIKER_USER;
        List<Feedback> expectedFeedbacks = FeedbackDummy.createList(bikerUser);
        expectedFeedbacks = expectedFeedbacks.stream()
                .filter(f -> f.getRating().equals(rating))
                .toList();
        when(feedbackRepository.findAllByRating(rating)).thenReturn(expectedFeedbacks);

        //WHEN
        List<Feedback> actual = SUT.getAllFeedbacksOfRating((byte) 4);

        //THEN
        assertThat(actual.size(), is(expectedFeedbacks.size()));
        assertThat(actual.containsAll(expectedFeedbacks), is(Boolean.TRUE));
    }

}