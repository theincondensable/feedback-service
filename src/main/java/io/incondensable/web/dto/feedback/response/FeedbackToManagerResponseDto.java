package io.incondensable.web.dto.feedback.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

/**
 * @author abbas
 */
@Getter
@AllArgsConstructor
public class FeedbackToManagerResponseDto {

    private Long feedbackId;
    private Long deliveryId;
    private Long bikerId;
    private String customerName;
    private String bikerName;
    private Byte rating;
    private String comment;
    private Date feedbackCreatedTime;

    @Override
    public String toString() {
        return "feedbackId=" + feedbackId +
                ", deliveryId=" + deliveryId +
                ", bikerId=" + bikerId +
                ", customerName='" + customerName + '\'' +
                ", bikerName='" + bikerName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", feedbackCreatedTime=" + feedbackCreatedTime;
    }
}
