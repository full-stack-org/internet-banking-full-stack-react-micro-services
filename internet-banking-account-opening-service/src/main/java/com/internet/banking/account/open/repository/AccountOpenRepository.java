package com.internet.banking.account.open.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internet.banking.account.open.entity.AccountOpenEntity;

public interface AccountOpenRepository extends JpaRepository<AccountOpenEntity, Long> {

	AccountOpenEntity findByCustomerIdAndPassword(int customerId, String password);
	
	AccountOpenEntity findByCustomerId(int customerId);
	
	AccountOpenEntity findByAadharNumberAndPanNumber(String aadharNumber,String panNumber);

}
