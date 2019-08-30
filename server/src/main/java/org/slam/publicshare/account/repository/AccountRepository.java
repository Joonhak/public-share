package org.slam.publicshare.account.repository;

import org.slam.publicshare.account.domain.Account;
import org.slam.publicshare.account.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(Email email);

    boolean existsByEmail(Email email);

}