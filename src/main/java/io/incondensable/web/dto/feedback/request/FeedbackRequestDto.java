package io.incondensable.web.dto.feedback.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author abbas
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDto {

    @NotNull(message = "{delivery.id.is.null}")
    private Long deliveryId;

    @Range(min = 1, max = 5, message = "{invalid.rating}")
    private Byte rating;
    private String comment;

    @Override
    public String toString() {
        return "deliveryId=" + deliveryId +
                ", rating=" + rating +
                ", comment='" + comment + '\'';
    }
}
