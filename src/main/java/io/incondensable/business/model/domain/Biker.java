package io.incondensable.business.model.domain;

import io.incondensable.business.model.client.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Biker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "biker",
            orphanRemoval = true
    )
    private Set<Delivery> deliveries;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;

}
