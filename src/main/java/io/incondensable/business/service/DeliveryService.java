package io.incondensable.business.service;

import io.incondensable.business.exceptions.delivery.DeliveryNotFoundWithId;
import io.incondensable.business.model.client.Customer;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.repository.DeliveryRepository;
import io.incondensable.global.security.vo.CustomerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final CustomerService customerService;

    public Delivery getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> {
            throw new DeliveryNotFoundWithId(deliveryId);
        });
    }

    public List<Delivery> getCustomerDeliveries() {
        CustomerDetails loggedInCustomer = (CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(loggedInCustomer.getId());

        return deliveryRepository.findAllByCustomer(customer);
    }

}
