package com.internet.banking.account.open.entity;

import java.io.Serializable;
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
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOpenEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 168914437405084833L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String password;

	@NotNull
	private String aadharNumber;

	@NotNull
	private String panNumber;

	@NotNull
	private String dateOfBirth;

	@NotNull
	private String gender;

	@NotNull
	private int customerId;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = AccountDetails.class)
	private List<AccountDetails> accountDetails;

}
