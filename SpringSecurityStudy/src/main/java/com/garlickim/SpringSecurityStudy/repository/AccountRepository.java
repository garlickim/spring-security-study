package com.garlickim.SpringSecurityStudy.repository;

import com.garlickim.SpringSecurityStudy.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String username);
}
