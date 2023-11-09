package io.incondensable.business.model.auth;

import io.incondensable.business.model.client.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author abbas
 */
@Entity
@Table(name = "user_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String token;
    private Instant activeTime;
}
