package org.barp.backend.Repository;

import org.barp.backend.Model.VerificationKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationKeyRepository extends JpaRepository<VerificationKey, Long> {
}
