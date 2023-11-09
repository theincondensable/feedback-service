package io.incondensable.mapper;

import io.incondensable.business.model.domain.Delivery;
import io.incondensable.web.dto.delivery.response.CustomerDeliveryResponseDto;

/**
 * @author abbas
 */
@Mapper
public class DeliveryMapper {

    public CustomerDeliveryResponseDto entityToDto(Delivery delivery) {
        return new CustomerDeliveryResponseDto(
                delivery.getId(),
                delivery.getDeliveree().getId()
        );
    }

}
