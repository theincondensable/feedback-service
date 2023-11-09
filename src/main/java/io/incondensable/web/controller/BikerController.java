package io.incondensable.web.controller;

import io.incondensable.business.service.BikerService;
import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.mapper.BikerMapper;
import io.incondensable.web.dto.biker.response.BikerAverageRatingResponseDto;
import io.incondensable.web.dto.biker.response.BikerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/biker")
@RequiredArgsConstructor
public class BikerController {

    private final BikerService service;
    private final BikerMapper mapper;

    @ControllerLog
    @GetMapping("/averageRating")
    @PreAuthorize("hasAnyAuthority('BIKER')")
    @Operation(summary = "To get Average Rating of the Logged-in Biker.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns The Biker Profile and its Average Rating.")
            }
    )
    public ResponseEntity<BikerAverageRatingResponseDto> showBikerAverageRating() {
        BikerResponseDto bikerProfile = mapper.entityToDto(service.getBikerByLoggedInUser());
        return ResponseEntity.ok(
                new BikerAverageRatingResponseDto(
                        bikerProfile,
                        service.calculateAverageRatingOfBiker(bikerProfile.getId())
                )
        );
    }

}
