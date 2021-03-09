package com.internet.banking.payments.service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scheduled_payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String fromAccountNumber;

	@NotNull
	private String toAccountNumber;

	@NotNull
	private String referenceNumber;

	@NotNull
	private String paymentDate;
	
	@NotNull
	private double paymentAmount;
}
