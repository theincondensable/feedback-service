package io.incondensable.web.dto.biker.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author abbas
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BikerAverageRatingResponseDto {
    private BikerResponseDto bikerResponseDto;
    private Double averageRating;

    @Override
    public String toString() {
        return "biker=" + bikerResponseDto.toString() +
                ", averageRating=" + averageRating;
    }
}
