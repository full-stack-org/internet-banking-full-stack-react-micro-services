package com.internet.banking.account.open.entity;

import java.io.Serializable;

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
@Table(name = "account_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5092164215240046755L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private int accountNumber;
	
	@NotNull
	private String accountType;
	
	@NotNull
	private int customerId;
	
}
