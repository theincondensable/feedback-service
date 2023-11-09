package io.incondensable.web.dto.user.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class AddressResponseDto {
    private String country;
    private String city;
    private String street;
    private String zipcode;

    @Override
    public String toString() {
        return "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'';
    }
}
