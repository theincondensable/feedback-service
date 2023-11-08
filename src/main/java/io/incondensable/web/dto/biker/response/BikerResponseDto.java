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
public class BikerResponseDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private Integer age;

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age;
    }
}
