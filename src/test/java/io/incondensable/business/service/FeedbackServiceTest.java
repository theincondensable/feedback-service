package io.incondensable.business.service;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.FeedbackRepository;
import io.incondensable.business.service.model.feedback.FeedbackDummy;
import io.incondensable.util.FeedbackTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@DisplayName("Feedback Service Unit Tests")
class FeedbackServiceTest {

    private final DeliveryService deliveryService = mock(DeliveryService.class);
    private final FeedbackRepository feedbackRepository = mock(FeedbackRepository.class);
    private final NotificationService notificationService = mock(NotificationService.class);

    private final FeedbackService SUT = new FeedbackService(deliveryService, feedbackRepository, notificationService);

    @Test
    @DisplayName("GIVEN Biker ID WHEN Feedbacks of them is going to be given THEN A List of Feedbacks will be returned.")
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

}