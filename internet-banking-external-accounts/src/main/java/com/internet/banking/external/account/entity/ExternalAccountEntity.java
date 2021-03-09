package com.internet.banking.external.account.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "external_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalAccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int customerId;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = AccountNumberEntity.class)
	private List<AccountNumberEntity> accountNumberEntityList;

}
