package io.incondensable.business.model.auth;

import io.incondensable.business.model.client.Customer;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author abbas
 */
@Entity
@Table(name = "customer_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String token;

    private Instant activeTime;
}
