package com.internet.banking.external.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internet.banking.external.account.entity.ExternalAccountEntity;

public interface ExternalAccountRepository extends JpaRepository<ExternalAccountEntity, Long> {

	ExternalAccountEntity findByCustomerId(int customerId);

}
