package io.incondensable.business.service;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.repository.DeliveryRepository;
import io.incondensable.business.service.model.Delivery.DeliveryDummy;
import io.incondensable.global.exception.BusinessException;
import io.incondensable.util.FeedbackTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author abbas
 */
@DisplayName("Delivery Service Unit Tests")
public class DeliveryServiceTest {

    private final UserService userService = mock(UserService.class);
    private final DeliveryRepository deliveryRepository = mock(DeliveryRepository.class);

    private final DeliveryService SUT = new DeliveryService(userService, deliveryRepository);

    @Test
    @DisplayName("GIVEN DeliveryId WHEN Finding Delivery with the Id NOT FOUND THEN an Exception is thrown.")
    public void testDeliveryWithIdNotFound() {
        //GIVEN
        Long deliveryId = 1L;
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());

        //WHEN
        BusinessException actual = assertThrows(BusinessException.class, () -> SUT.getDeliveryById(deliveryId));

        //THEN
        assertThat(actual.getErrorDetails().getMessage(), is("delivery.not.found.with.given.id"));
    }

    @Test
    @DisplayName("GIVEN DeliveryId WHEN Finding Delivery with the Id is found THEN the Delivery is Returned.")
    public void testDeliveryIsReturnedByGivenId() {
        //GIVEN
        Long deliveryId = 1L;
        Delivery expectedDelivery = DeliveryDummy.create(deliveryId, FeedbackTestUtil.CUSTOMER_USER);
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(expectedDelivery));

        //WHEN
        Delivery actualDelivery = SUT.getDeliveryById(deliveryId);

        //THEN
        assertThat(actualDelivery.getId(), is(expectedDelivery.getId()));
        assertThat(actualDelivery.getDeliveree(), is(expectedDelivery.getDeliveree()));
    }

    @Test
    @DisplayName("WHEN Finding Deliveries of the Logged-in Customer THEN the List of Deliveris is returned.")
    public void testGetDeliveriesToTheDeliveree() {
        User loggedInCustomer = FeedbackTestUtil.CUSTOMER_USER;
        FeedbackTestUtil.beforeSetup(loggedInCustomer.getId());
        //GIVEN
        Long delivereeId = loggedInCustomer.getId();
        List<Delivery> expectedDeliveries = DeliveryDummy.createDeliveriesWithDelivereeId(delivereeId);
        expectedDeliveries = expectedDeliveries.stream().filter(
                d -> d.getDeliveree().getId().equals(delivereeId)
        ).toList();

        when(userService.getUserById(delivereeId)).thenReturn(loggedInCustomer);
        when(deliveryRepository.findAllByDeliveree(loggedInCustomer)).thenReturn(expectedDeliveries);

        //WHEN
        List<Delivery> actualDeliveries = SUT.getCustomerDeliveries();

        //THEN
        assertThat(actualDeliveries.size(), is(expectedDeliveries.size()));
        assertThat(actualDeliveries.containsAll(expectedDeliveries), is(Boolean.TRUE));
    }

}
