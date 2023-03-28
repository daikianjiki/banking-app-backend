package org.barp.backend.Repository;

import org.barp.backend.Model.MoneyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MoneyAccountRepository  extends JpaRepository<MoneyAccount, Long> {
    Optional<List<MoneyAccount>> findMoneyAccountByUserUserId(Long userId);
}
