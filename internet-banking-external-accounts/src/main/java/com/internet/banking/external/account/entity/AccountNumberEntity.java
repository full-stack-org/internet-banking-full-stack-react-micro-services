package com.internet.banking.external.account.entity;

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
@Table(name = "external_account_numbers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountNumberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String accountNumber;
	
	@NotNull
	private String accountNickName;
	
	@NotNull
	private String accountType;


}
