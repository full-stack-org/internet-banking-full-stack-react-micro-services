package com.internet.banking.account.open.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.banking.account.open.entity.AccountDetails;
import com.internet.banking.account.open.entity.AccountOpenEntity;
import com.internet.banking.account.open.repository.AccountOpenRepository;
import com.internet.banking.account.open.request.AccountOpenRequest;
import com.internet.banking.account.open.request.AuthenticationRequest;
import com.internet.banking.account.open.request.CustomerProfileRequest;
import com.internet.banking.account.open.request.FindByCustomerIdRequest;
import com.internet.banking.account.open.request.InternalAccountListRequest;
import com.internet.banking.account.open.request.UpdatePasswordRequest;
import com.internet.banking.account.open.request.UpdateProfileRequest;
import com.internet.banking.account.open.response.AccountOpenResponse;
import com.internet.banking.account.open.response.AuthenticationResponse;
import com.internet.banking.account.open.response.CustomerProfileResponse;
import com.internet.banking.account.open.response.FindByCustomerIdResponse;
import com.internet.banking.account.open.response.InternalAccountListResponse;
import com.internet.banking.account.open.response.StatusResponse;
import com.internet.banking.account.open.response.UpdatePasswordResponse;
import com.internet.banking.account.open.response.UpdateProfileResponse;
import com.internet.banking.account.open.util.AccountOpenUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountOpenService {

	@Autowired
	private AccountOpenRepository accountOpenRepository;

	/**
	 * openNewAccount.
	 * 
	 * @param accountOpenRequest
	 * @return AccountOpenResponse
	 */
	public AccountOpenResponse openNewAccount(AccountOpenRequest accountOpenRequest) {
		log.info("Enter in openNewAccount of AccountOpenService");

		AccountOpenResponse accountOpenResponse = new AccountOpenResponse();
		StatusResponse statusResponse = new StatusResponse();
		AccountOpenEntity serviceInputRequest = null;
		int generatedAccountNumber = 0;

		try {

			serviceInputRequest = accountOpenRepository.findByAadharNumberAndPanNumber(
					accountOpenRequest.getAadharNumber(), accountOpenRequest.getPanNumber());

			// If customer already exists adding the newly requested Account number
			if (Objects.nonNull(serviceInputRequest)) {

				generatedAccountNumber = AccountOpenUtils.generateAccountNumber();

				AccountDetails accountDetails = new AccountDetails();
				accountDetails.setAccountNumber(generatedAccountNumber);
				accountDetails.setAccountType(accountOpenRequest.getAccountType());
				accountDetails.setCustomerId(serviceInputRequest.getCustomerId());

				serviceInputRequest.getAccountDetails().add(accountDetails);

			} else {
				serviceInputRequest = prepareServiceRequest(accountOpenRequest);
			}

			AccountOpenEntity serviceResponse = accountOpenRepository.save(serviceInputRequest);

			// Setting Up the Response
			accountOpenResponse.setAccountNumber(generatedAccountNumber > 0 ? generatedAccountNumber
					: serviceResponse.getAccountDetails().get(0).getAccountNumber());
			accountOpenResponse.setCustomerId(serviceResponse.getCustomerId());
			statusResponse.setStatusCode(200);
			statusResponse.setStatusMessage("Account Opened Successfully");
			accountOpenResponse.setStatusResponse(statusResponse);

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while openNewAccount");
			accountOpenResponse.setStatusResponse(statusResponse);
			log.error("Exception while openNewAccount of AccountOpenService {} ", e.getMessage());
		}

		log.info("Enter in openNewAccount of AccountOpenService");

		return accountOpenResponse;
	}

	/**
	 * authenticate.
	 * 
	 * @param authenticationRequest
	 * @return AuthenticationResponse
	 */
	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		log.info("Enter in authenticate of AccountOpenService");
		StatusResponse statusResponse = new StatusResponse();

		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		try {
			AccountOpenEntity serviceResponse = accountOpenRepository.findByCustomerIdAndPassword(
					authenticationRequest.getCustomerId(), authenticationRequest.getPassword());

			if (Objects.nonNull(serviceResponse)) {
				authenticationResponse.setAuthenticatedSuccessfully(true);
				authenticationResponse.setCustomerId(serviceResponse.getCustomerId());

				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("Customer Authenticated Successfully");
				authenticationResponse.setStatusResponse(statusResponse);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Customer Not Available in Database");
				authenticationResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while authentication");
			authenticationResponse.setStatusResponse(statusResponse);
			log.error("Exception while authenticate of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit in authenticate of AccountOpenService");

		return authenticationResponse;
	}

	/**
	 * getInternalAccountsList.
	 * 
	 * @param internalAccountListRequest
	 * @return InternalAccountListResponse
	 */
	public InternalAccountListResponse getInternalAccountsList(InternalAccountListRequest internalAccountListRequest) {
		log.info("Enter in getInternalAccountsList of AccountOpenService");
		
		StatusResponse statusResponse = new StatusResponse();
		InternalAccountListResponse internalAccountsListResponse = new InternalAccountListResponse();

		try {
			AccountOpenEntity serviceResponse = accountOpenRepository
					.findByCustomerId(internalAccountListRequest.getCustomerId());

			if (Objects.nonNull(serviceResponse)) {
				internalAccountsListResponse = transformInternalAccountsServiceResponse(serviceResponse);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Internal Accounts Not Exists in Database");
				internalAccountsListResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while getting Internal Accounts List");
			internalAccountsListResponse.setStatusResponse(statusResponse);
			log.error("Exception while getInternalAccountsList of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit in getInternalAccountsList of AccountOpenService");

		return internalAccountsListResponse;
	}

	/**
	 * getCustomeDataByCutomerId.
	 * 
	 * @param findByCustomerIdRequest
	 * @return FindByCustomerIdResponse
	 */
	public FindByCustomerIdResponse getCustomeDataByCutomerId(FindByCustomerIdRequest findByCustomerIdRequest) {
		log.info("Enter in getCustomeDataByCutomerId of AccountOpenService");
		StatusResponse statusResponse = new StatusResponse();

		FindByCustomerIdResponse findByCustomerIdResponse = new FindByCustomerIdResponse();

		try {
			AccountOpenEntity serviceResponse = accountOpenRepository
					.findByCustomerId(findByCustomerIdRequest.getCustomerId());

			if (Objects.nonNull(serviceResponse)) {
				findByCustomerIdResponse.setCustomerId(serviceResponse.getCustomerId());
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("Customer Data Fetched Successfully");
				findByCustomerIdResponse.setStatusResponse(statusResponse);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Customer Id Not Exists in Database");
				findByCustomerIdResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while getting Customer Data By Customer Id");
			findByCustomerIdResponse.setStatusResponse(statusResponse);
			log.error("Exception while getCustomeDataByCutomerId of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit in getCustomeDataByCutomerId of AccountOpenService");

		return findByCustomerIdResponse;
	}
	
	/**
	 * getCustomerProfileData.
	 * 
	 * @param customerProfileRequest
	 * @return CustomerProfileResponse
	 */
	public CustomerProfileResponse getCustomerProfileData(CustomerProfileRequest customerProfileRequest) {
		log.info("Enter in getCustomerProfileData of AccountOpenService");
		StatusResponse statusResponse = new StatusResponse();

		CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse();

		try {
			AccountOpenEntity serviceResponse = accountOpenRepository
					.findByCustomerId(customerProfileRequest.getCustomerId());

			if (Objects.nonNull(serviceResponse)) {
				customerProfileResponse = transformCustomerProfileResponse(serviceResponse);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Customer Data Not Exists in Database");
				customerProfileResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while getting Customer Data By Customer Id");
			customerProfileResponse.setStatusResponse(statusResponse);
			log.error("Exception while getCustomeDataByCutomerId of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit in getCustomerProfileData of AccountOpenService");

		return customerProfileResponse;
	}

	
	/**
	 * updatePassword.
	 * 
	 * @param updatePasswordRequest
	 * @return UpdatePasswordResponse
	 */
	public UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		log.info("Enter in updatePassword of AccountOpenService");
		StatusResponse statusResponse = new StatusResponse();

		UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse();

		try {
			AccountOpenEntity serviceResponse = accountOpenRepository
					.findByCustomerId(updatePasswordRequest.getCustomerId());

			if (Objects.nonNull(serviceResponse)) {
				serviceResponse.setPassword(updatePasswordRequest.getPassword());
				AccountOpenEntity passwordUpdateResponse = accountOpenRepository.save(serviceResponse);

				// Setting up the Response.
				updatePasswordResponse.setCustomerId(passwordUpdateResponse.getCustomerId());
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("Password Updated Successfully");
				updatePasswordResponse.setStatusResponse(statusResponse);

			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Customer Id Not Exists in Database");
				updatePasswordResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while Updating the Password");
			updatePasswordResponse.setStatusResponse(statusResponse);
			log.error("Exception while updatePassword of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit in updatePassword of AccountOpenService");

		return updatePasswordResponse;
	}

	/**
	 * updateProfile.
	 * 
	 * @param updateProfileRequest
	 * @return UpdateProfileResponse
	 */
	public UpdateProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest) {
		log.info("Enter in updateProfile of AccountOpenService");

		StatusResponse statusResponse = new StatusResponse();
		UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse();

		try {
			Optional<AccountOpenEntity> profileData = accountOpenRepository.findById(updateProfileRequest.getId());

			if (profileData.isPresent()) {
				profileData.get().setAadharNumber(updateProfileRequest.getAadharNumber());
				profileData.get().setDateOfBirth(updateProfileRequest.getDateOfBirth());
				profileData.get().setFirstName(updateProfileRequest.getFirstName());
				profileData.get().setLastName(updateProfileRequest.getLastName());
				profileData.get().setGender(updateProfileRequest.getGender());
				profileData.get().setPanNumber(updateProfileRequest.getPanNumber());

				AccountOpenEntity serviceResponse = accountOpenRepository.save(profileData.get());

				updateProfileResponse.setFirstName(serviceResponse.getFirstName());
				updateProfileResponse.setLastName(serviceResponse.getLastName());
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("Profile Updated Successfully");
				updateProfileResponse.setStatusResponse(statusResponse);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Profile Data Not Available in Database");
				updateProfileResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while Updating the Profile");
			updateProfileResponse.setStatusResponse(statusResponse);
			log.error("Exception while updateProfile of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit in updateProfile of AccountOpenService");
		return updateProfileResponse;
	}

	/**
	 * transformCustomerProfileResponse.
	 * 
	 * @param serviceResponse
	 * @return CustomerProfileResponse
	 */
	private CustomerProfileResponse transformCustomerProfileResponse(AccountOpenEntity serviceResponse) {
		CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse();
		
		customerProfileResponse.setAadharNumber(serviceResponse.getAadharNumber());
		customerProfileResponse.setCustomerId(serviceResponse.getCustomerId());
		customerProfileResponse.setDateOfBirth(serviceResponse.getDateOfBirth());
		customerProfileResponse.setFirstName(serviceResponse.getFirstName());
		customerProfileResponse.setLastName(serviceResponse.getLastName());
		customerProfileResponse.setId(serviceResponse.getId());
		customerProfileResponse.setPanNumber(serviceResponse.getPanNumber());
		customerProfileResponse.setGender(serviceResponse.getGender());

		StatusResponse statusResponse = new StatusResponse();
		statusResponse.setStatusCode(200);
		statusResponse.setStatusMessage("Customer Data Fetched Successfully");
		customerProfileResponse.setStatusResponse(statusResponse);
		
		return customerProfileResponse;
	}

	
	/**
	 * transformInternalAccountsServiceResponse.
	 * 
	 * @param serviceResponse
	 * @return InternalAccountListResponse
	 */
	@SuppressWarnings("unchecked")
	private InternalAccountListResponse transformInternalAccountsServiceResponse(AccountOpenEntity serviceResponse) {
		InternalAccountListResponse internalAccountListResponse = null;

		if (serviceResponse.getAccountDetails() instanceof PersistentBag) {
			internalAccountListResponse = new InternalAccountListResponse();
			List<AccountDetails> accountDetailsList = new ArrayList<>();
			PersistentBag persBag = (PersistentBag) serviceResponse.getAccountDetails();
			accountDetailsList.addAll(persBag);

			List<Integer> accountsList = new ArrayList<>();

			for (AccountDetails accountDetails : accountDetailsList) {
				accountsList.add(accountDetails.getAccountNumber());
			}

			log.info("Account List {} ", accountsList);
			internalAccountListResponse.setAccountsList(accountsList);
			StatusResponse statusResponse = new StatusResponse();
			statusResponse.setStatusCode(200);
			statusResponse.setStatusMessage("Account Numbers Fetched Successfully");
			internalAccountListResponse.setStatusResponse(statusResponse);
		}
		return internalAccountListResponse;
	}


	/**
	 * prepareServiceRequest.
	 * 
	 * @param accountOpenRequest
	 * @return AccountOpenEntity
	 */
	private AccountOpenEntity prepareServiceRequest(AccountOpenRequest accountOpenRequest) {
		AccountOpenEntity accountOpenEntity = new AccountOpenEntity();
		accountOpenEntity.setAadharNumber(accountOpenRequest.getAadharNumber());
		accountOpenEntity.setDateOfBirth(accountOpenRequest.getDateOfBirth());
		accountOpenEntity.setGender(accountOpenRequest.getGender());
		accountOpenEntity.setFirstName(accountOpenRequest.getFirstName());
		accountOpenEntity.setLastName(accountOpenRequest.getLastName());
		accountOpenEntity.setPanNumber(accountOpenRequest.getPanNumber());
		accountOpenEntity.setPassword(accountOpenRequest.getPassword());
		accountOpenEntity.setCustomerId(AccountOpenUtils.generateCustomerId());

		int generatedAccountNumber = AccountOpenUtils.generateAccountNumber();

		List<AccountDetails> accountDetailsSet = new ArrayList<>();
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccountNumber(generatedAccountNumber);
		accountDetails.setAccountType(accountOpenRequest.getAccountType());
		accountDetails.setCustomerId(accountOpenEntity.getCustomerId());

		accountDetailsSet.add(accountDetails);
		accountOpenEntity.setAccountDetails(accountDetailsSet);

		return accountOpenEntity;
	}

}
