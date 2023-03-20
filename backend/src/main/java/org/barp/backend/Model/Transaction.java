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

    public enum TransactionType {
        Deposit,
        Withdraw
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long transactionId;
    public Double amount;
    public Long timestamp;
    public String description;
    public Double settledBalance;

    @Enumerated(EnumType.STRING)
    public TransactionType transactionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    public MoneyAccount moneyAccount;
}
