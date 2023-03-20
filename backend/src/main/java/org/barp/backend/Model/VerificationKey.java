package org.barp.backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class VerificationKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long keyId;
    public String secretKey;

    @OneToOne(fetch = FetchType.EAGER)
    public User user;
}
