package io.incondensable.web.controller;

import io.incondensable.business.service.FeedbackService;
import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.mapper.FeedbackMapper;
import io.incondensable.web.dto.feedback.request.FeedbackRequestDto;
import io.incondensable.web.dto.feedback.response.SubmittedFeedbackResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackMapper feedbackMapper;
    private final FeedbackService feedbackService;

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
                    @ApiResponse(responseCode = "404", description = "If the Delivery ID is not Found.")
            }
    )
    public ResponseEntity<SubmittedFeedbackResponseDto> submit(@Valid @RequestBody FeedbackRequestDto request) {
        return ResponseEntity.ok(
                feedbackMapper.entityToDto(
                        feedbackService.submitFeedback(request)
                )
        );
    }

}
