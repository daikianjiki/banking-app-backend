package org.barp.backend.Repository;

import org.barp.backend.Model.MoneyAccount;
import org.barp.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMoneyAccount(MoneyAccount moneyAccount);

    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
