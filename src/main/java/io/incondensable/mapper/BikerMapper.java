package io.incondensable.mapper;

import io.incondensable.business.model.domain.Biker;
import io.incondensable.web.dto.biker.response.BikerResponseDto;

/**
 * @author abbas
 */
@Mapper
public class BikerMapper {

    public BikerResponseDto entityToDto(Biker biker) {
        return new BikerResponseDto(
                biker.getId(),
                biker.getUser().getFirstname() + " " + biker.getUser().getLastname(),
                biker.getUser().getPhoneNumber(),
                biker.getUser().getEmail()
        );
    }

}
