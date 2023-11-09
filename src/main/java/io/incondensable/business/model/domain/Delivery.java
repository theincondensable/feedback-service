package io.incondensable.business.model.domain;

import io.incondensable.business.model.client.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biker_id", nullable = false)
    private Biker biker;

    @ManyToOne(fetch = FetchType.LAZY)
    private User deliveree;

    @CreationTimestamp
    private Instant createdOn;

}
