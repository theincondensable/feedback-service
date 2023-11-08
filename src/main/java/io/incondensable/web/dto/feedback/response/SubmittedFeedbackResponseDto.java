package io.incondensable.web.dto.feedback.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@AllArgsConstructor
public class SubmittedFeedbackResponseDto {

    private String customerName;
    private String bikerName;
    private Byte rating;
    private String comment;

    @Override
    public String toString() {
        return "customerName='" + customerName + '\'' +
                ", bikerName='" + bikerName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'';
    }
}
