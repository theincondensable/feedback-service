package io.incondensable.business.model.client;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * @author abbas
 */
@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String country;
    private String city;
    private String street;
    private String zipcode;

}
