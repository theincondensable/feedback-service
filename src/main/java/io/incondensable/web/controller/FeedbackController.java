package io.incondensable.web.controller;

import io.incondensable.business.service.FeedbackService;
import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.mapper.FeedbackMapper;
import io.incondensable.web.dto.feedback.request.FeedbackRequestDto;
import io.incondensable.web.dto.feedback.response.FeedbackToManagerResponseDto;
import io.incondensable.web.dto.feedback.response.SubmittedFeedbackResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackMapper mapper;
    private final FeedbackService service;

    @ControllerLog
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "To Submit a Feedback to a Certain Delivery.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "FeedbackRequestDto",
                                    summary = "A Simple Feedback Request",
                                    value = """
                                            {
                                              "deliveryId": 1,
                                              "rating": "5",
                                              "comment": ""
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the preview of your Feedback"),
                    @ApiResponse(responseCode = "403", description = "If the User is not the one who the Delivery is delivered to."),
                    @ApiResponse(responseCode = "404", description = "If the Delivery ID is not Found."),
                    @ApiResponse(responseCode = "409", description = "If the Feedback is already submitted.")
            }
    )
    public ResponseEntity<SubmittedFeedbackResponseDto> submit(@Valid @RequestBody FeedbackRequestDto request) {
        return ResponseEntity.ok(
                mapper.entityToDto(
                        service.submitFeedback(request)
                )
        );
    }

    @ControllerLog
    @GetMapping("/sortedByDate")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @Operation(summary = "To get all feedbacks sorted by their Delivery Date from the most recent to the oldest.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the List of Feedbacks.")
            }
    )
    public ResponseEntity<List<FeedbackToManagerResponseDto>> listFeedbacksSortedByDate() {
        return ResponseEntity.ok(
                service.getAllFeedbacksSortedByDate().stream()
                        .map(mapper::entityToManagerDto)
                        .toList()
        );
    }

    @ControllerLog
    @GetMapping("/biker/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @Operation(summary = "To get all feedbacks of a Biker.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the List of Feedbacks of a Biker.")
            }
    )
    public ResponseEntity<List<FeedbackToManagerResponseDto>> listFeedbacksOfBiker(@PathVariable Long id) {
        return ResponseEntity.ok(
                service.getAllFeedbacksOfBiker(id).stream()
                        .map(mapper::entityToManagerDto)
                        .toList()
        );
    }

    @ControllerLog
    @GetMapping("/rated/{rating}")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @Operation(summary = "To get all feedbacks with a certain rating.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the List of Feedbacks with a certain Rating.")
            }
    )
    public ResponseEntity<List<FeedbackToManagerResponseDto>> listFeedbacksOfRating(@PathVariable Byte rating) {
        return ResponseEntity.ok(
                service.getAllFeedbacksOfRating(rating).stream()
                        .map(mapper::entityToManagerDto)
                        .toList()
        );
    }

}
