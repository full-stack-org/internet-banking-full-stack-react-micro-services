package com.internet.banking.payments.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internet.banking.payments.service.entity.PaymentEntity;

public interface PaymentsRepository extends JpaRepository<PaymentEntity, Long> {

	PaymentEntity findByCustomerId(int customerId);

}
