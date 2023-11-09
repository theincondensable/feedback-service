package io.incondensable.web.controller;

import io.incondensable.business.service.DeliveryService;
import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.mapper.DeliveryMapper;
import io.incondensable.web.dto.delivery.response.CustomerDeliveryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;

    @ControllerLog
    @GetMapping("/customerDeliveries")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "To Find Logged in Customer User Deliveries so that they can submit a Feedback.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the Customer User Deliveries"),
                    @ApiResponse(responseCode = "401", description = "If the Customer is not logged in, or its Role is not met")
            }
    )
    public ResponseEntity<List<CustomerDeliveryResponseDto>> getCustomerDeliveries() {
        return ResponseEntity.ok(
                deliveryService.getCustomerDeliveries().stream()
                        .map(deliveryMapper::entityToDto)
                        .toList()
        );
    }

}
