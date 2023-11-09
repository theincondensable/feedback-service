package io.incondensable.business.service.model.user;

import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.User;

import java.util.Set;

/**
 * @author abbas
 */
public class UserDummy {
    private UserDummy() {

    }

    public static User create(Long id, String email, Role role) {
        return User.builder()
                .id(id)
                .firstname("first")
                .lastname("last")
                .email(email)
                .password("$e0801$1D+IXQhry0JKEAgQRb+6U19Ub6CuJVtQno7VF0jHOEsiccz/MYRBy8ZURMJZ1JFiEcS4e5AnrulLX4fG/DPeAw==$jBBpBngcMEbWOX23GLrd3Xg52VvKP+oI1VP0M9zs/J0=")
                .roles(Set.of(role))
                .build();
    }

    /**
     * <p>to use in tests.</p>
     * @return the encoded of P@ssw0rd
     */
    public static String encodedPassword() {
        return "$e0801$1D+IXQhry0JKEAgQRb+6U19Ub6CuJVtQno7VF0jHOEsiccz/MYRBy8ZURMJZ1JFiEcS4e5AnrulLX4fG/DPeAw==$jBBpBngcMEbWOX23GLrd3Xg52VvKP+oI1VP0M9zs/J0=";
    }

    /**
     * <p>to use in tests.</p>
     * @return the decoded of encoded-P@ssw0rd :|
     */
    public static String decodedPassword() {
        return "P@ssw0rd";
    }

}
