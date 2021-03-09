package com.internet.banking.external.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.internet.banking.external.account.entity.AccountNumberEntity;
import com.internet.banking.external.account.entity.ExternalAccountEntity;
import com.internet.banking.external.account.repository.ExternalAccountRepository;
import com.internet.banking.external.account.request.ExternalAccountRequest;
import com.internet.banking.external.account.request.GetAllExternalAccountsRequest;
import com.internet.banking.external.account.response.ExternalAccountResponse;
import com.internet.banking.external.account.response.GetAllAccountsResponse;
import com.internet.banking.external.account.response.StatusResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExternalAccountService {

	@Autowired
	private ExternalAccountRepository externalAccountRepository;

	/**
	 * addExternalAccount.
	 * 
	 * @param externalAccountRequest
	 * @return ExternalAccountResponse
	 */
	public ExternalAccountResponse addExternalAccount(ExternalAccountRequest externalAccountRequest) {
		log.info("Enter in addExternalAccount of ExternalAccountService");

		ExternalAccountResponse externalAccountResponse = new ExternalAccountResponse();
		StatusResponse statusResponse = new StatusResponse();

		try {

			// Pulling the All external Accounts
			ExternalAccountEntity externalAccountEntity = externalAccountRepository
					.findByCustomerId(externalAccountRequest.getCustomerId());

			// External accounts exists
			if (Objects.nonNull(externalAccountEntity)) {

				GetAllAccountsResponse getAllAccountsResponse = transformServiceResponse(externalAccountEntity);

				// Input account available in list
				if (!CollectionUtils.isEmpty(getAllAccountsResponse.getAccountNumbersList()) && getAllAccountsResponse
						.getAccountNumbersList().contains(externalAccountRequest.getAccountNumber())) {
					externalAccountResponse.setAccountNumber(externalAccountRequest.getAccountNumber());
					statusResponse.setStatusCode(422);
					statusResponse.setStatusMessage("Account already exixts in Database");
					externalAccountResponse.setStatusResponse(statusResponse);
				} else {

					AccountNumberEntity accountNumberEntity = new AccountNumberEntity();
					accountNumberEntity.setAccountNumber(externalAccountRequest.getAccountNumber());
					accountNumberEntity.setAccountNickName(externalAccountRequest.getAccountNickName() + " ending "
							+ getLastFourDigits(externalAccountRequest.getAccountNumber()));
					accountNumberEntity.setAccountType(externalAccountRequest.getAccountType());

					externalAccountEntity.getAccountNumberEntityList().add(accountNumberEntity);

					ExternalAccountEntity serviceResponse = externalAccountRepository.save(externalAccountEntity);

					transformAddExternalAccountServiceResponse(externalAccountRequest, externalAccountResponse,
							statusResponse, serviceResponse);
				}

			} else {
				ExternalAccountEntity serviceInput = prepareServiceInput(externalAccountRequest);

				ExternalAccountEntity serviceResponse = externalAccountRepository.save(serviceInput);

				transformAddExternalAccountServiceResponse(externalAccountRequest, externalAccountResponse,
						statusResponse, serviceResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while addExternalAccount");
			externalAccountResponse.setStatusResponse(statusResponse);
			log.error("Exception while addExternalAccount of ExternalAccountService {} ", e.getMessage());
		}

		log.info("Exit in addExternalAccount of ExternalAccountService");

		return externalAccountResponse;
	}

	/**
	 * getLastFourDigits
	 * 
	 * @param accountNumber
	 * @return String
	 */
	private String getLastFourDigits(String accountNumber) {
		return accountNumber.length() > 4 ? accountNumber.substring(accountNumber.length() - 4) : accountNumber;
	}

	private void transformAddExternalAccountServiceResponse(ExternalAccountRequest externalAccountRequest,
			ExternalAccountResponse externalAccountResponse, StatusResponse statusResponse,
			ExternalAccountEntity serviceResponse) {
		serviceResponse.getAccountNumberEntityList().stream().forEach(accountDetails -> {
			if (accountDetails.getAccountNumber().equalsIgnoreCase(externalAccountRequest.getAccountNumber())) {
				externalAccountResponse.setAccountNumber(accountDetails.getAccountNumber());
				externalAccountResponse.setAccountNickName(accountDetails.getAccountNickName());
				externalAccountResponse.setAccountType(accountDetails.getAccountType());
			}
		});

		statusResponse.setStatusCode(200);
		statusResponse.setStatusMessage("External Account Added Successfully");
		externalAccountResponse.setStatusResponse(statusResponse);
	}

	/**
	 * 
	 * @param externalAccountRequest
	 * @return
	 */
	public GetAllAccountsResponse getAllExternalAccounts(GetAllExternalAccountsRequest externalAccountRequest) {
		log.info("Enter in getAllExternalAccounts of ExternalAccountService");

		GetAllAccountsResponse getAllAccountsResponse = new GetAllAccountsResponse();
		StatusResponse statusResponse = new StatusResponse();

		try {

			ExternalAccountEntity externalAccountEntity = externalAccountRepository
					.findByCustomerId(externalAccountRequest.getCustomerId());

			if (Objects.nonNull(externalAccountEntity)) {
				getAllAccountsResponse = transformServiceResponse(externalAccountEntity);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("No External Accounts Available");
				getAllAccountsResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while getAllExternalAccounts");
			getAllAccountsResponse.setStatusResponse(statusResponse);
			log.error("Exception while getAllExternalAccounts of ExternalAccountService {} ", e.getMessage());
		}

		log.info("Exit in getAllExternalAccounts of ExternalAccountService");

		return getAllAccountsResponse;
	}

	/**
	 * transformServiceResponse.
	 * 
	 * @param externalAccountEntity
	 * @return GetAllAccountsResponse
	 */
	@SuppressWarnings("unchecked")
	private GetAllAccountsResponse transformServiceResponse(ExternalAccountEntity externalAccountEntity) {
		GetAllAccountsResponse getAllAccountsResponse = new GetAllAccountsResponse();

		if (externalAccountEntity.getAccountNumberEntityList() instanceof PersistentBag) {
			List<AccountNumberEntity> accountDetailsList = new ArrayList<>();
			PersistentBag persBag = (PersistentBag) externalAccountEntity.getAccountNumberEntityList();
			accountDetailsList.addAll(persBag);

			if (!CollectionUtils.isEmpty(accountDetailsList)) {

				List<String> accountNumbersList = new ArrayList<>();

				for (AccountNumberEntity accountDetails : accountDetailsList) {
					accountNumbersList.add(accountDetails.getAccountNickName());
				}

				getAllAccountsResponse.setAccountNumbersList(accountNumbersList);
				StatusResponse statusResponse = new StatusResponse();
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("External Accounts Fetched Successfully");
				getAllAccountsResponse.setStatusResponse(statusResponse);
			}

		}

		return getAllAccountsResponse;
	}

	/**
	 * prepareServiceInput.
	 * 
	 * @param externalAccountRequest
	 * @return ExternalAccountEntity
	 */
	private ExternalAccountEntity prepareServiceInput(ExternalAccountRequest externalAccountRequest) {
		ExternalAccountEntity externalAccountEntity = new ExternalAccountEntity();
		externalAccountEntity.setCustomerId(externalAccountRequest.getCustomerId());

		List<AccountNumberEntity> accountNumberEntitiesList = new ArrayList<>();

		AccountNumberEntity accountNumberEntity = new AccountNumberEntity();
		accountNumberEntity.setAccountNumber(externalAccountRequest.getAccountNumber());

		accountNumberEntitiesList.add(accountNumberEntity);

		externalAccountEntity.setAccountNumberEntityList(accountNumberEntitiesList);

		return externalAccountEntity;
	}

}
