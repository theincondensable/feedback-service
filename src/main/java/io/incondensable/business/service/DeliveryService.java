package io.incondensable.business.service;

import io.incondensable.business.exceptions.delivery.DeliveryNotFoundWithId;
import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.repository.DeliveryRepository;
import io.incondensable.global.security.vo.FeedbackUserDetails;
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

    private final UserService userService;
    private final DeliveryRepository deliveryRepository;

    public Delivery getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> {
            throw new DeliveryNotFoundWithId(deliveryId);
        });
    }

    public List<Delivery> getCustomerDeliveries() {
        FeedbackUserDetails loggedInUser = (FeedbackUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User deliveree = userService.getUserById(loggedInUser.getId());

        return deliveryRepository.findAllByDeliveree(deliveree);
    }

}
