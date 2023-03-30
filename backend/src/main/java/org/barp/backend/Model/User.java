package org.barp.backend.Model;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId;
    public String username;
    public String password;
    @Column(unique=true)
    public String email;
    public Long phoneNumber;
    public String streetAddress1;
    public String streetAddress2;
    public String city;
    public String state;
    public String country;
    public String zipCode;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    //@JsonManagedReference
    public List<MoneyAccount> moneyAccount;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    //@JsonManagedReference
    public List<Transaction> transactions;
    @OneToOne(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    public VerificationKey verificationKey;
}
