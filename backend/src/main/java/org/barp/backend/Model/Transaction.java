package org.barp.backend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long transactionId;
    public Double amount;
    public Long timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    public MoneyAccount moneyAccount;
}
