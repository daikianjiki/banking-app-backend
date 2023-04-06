package org.barp.backend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MoneyAccount {
    /**
     * This is used to create a list of predefined values that can be used
     * in the account type column.
     *
     * The corresponding field is annotated like this:
     *      @Enumerated(EnumType.STRING)
     *      public AccountType accountType;
     */
    public enum AccountType {
        Checking,
        Savings
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long accountId;
    public Double balance;

    @Enumerated(EnumType.STRING)
    public AccountType accountType;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "MoneyAccountToUser")
    public User user;

    /**
     * @OneToMany
     * This relationship creates a parent (MoneyAccount) and child (Transaction) relationship.
     *
     * The mappedBy tells Spring which field in the Transctions table points to a MoneyAccount.
     *
     * Cascade defines how records with foreign keys should be updated. Currently, the relationship
     * is configured as follows:
     *
     * CascadeType.REMOVE
     * Deleting a MoneyAccount will delete all associated transactions.
     *
     * CascadeType.DETACH
     * Deleting a transaction removes the reference to it in the MoneyAccount table.
     */
    @OneToMany(mappedBy = "moneyAccount", cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    //@JsonManagedReference(value = "MoneyAccountToTransaction")
    public List<Transaction> transactions;
}


