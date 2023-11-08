package io.incondensable.business.model.client;

import io.incondensable.business.model.auth.CustomerRole;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Embedded
    private Address address;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<CustomerRole> customerRoles;

    public String fullName() {
        return firstname + lastname;
    }

}
