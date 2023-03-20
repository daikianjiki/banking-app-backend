package org.barp.backend.Repository;

import org.barp.backend.Model.MoneyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyAccountRepository  extends JpaRepository<MoneyAccount, Long> {
}
