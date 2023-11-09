package io.incondensable.web.dto.delivery.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@AllArgsConstructor
public class CustomerDeliveryResponseDto {

    private Long deliveryId;
    private Long userId;

    @Override
    public String toString() {
        return "deliveryId=" + deliveryId +
                ", userId=" + userId;
    }
}
