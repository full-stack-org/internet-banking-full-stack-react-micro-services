package com.internet.banking.routing.number.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.banking.routing.number.entity.RoutingNumberEntity;
import com.internet.banking.routing.number.repository.RoutingNumberRepository;
import com.internet.banking.routing.number.request.AddRoutingNumberRequest;
import com.internet.banking.routing.number.request.ValidateRoutingNumberRequest;
import com.internet.banking.routing.number.response.AddRoutingNumberResponse;
import com.internet.banking.routing.number.response.StatusResponse;
import com.internet.banking.routing.number.response.ValidateRoutingNumberResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoutingNumberService {

	@Autowired
	private RoutingNumberRepository routingNumberRepository;

	/**
	 * addRoutingNumber.
	 * 
	 * @param addRoutingNumberRequest
	 * @return AddRoutingNumberResponse
	 */
	public AddRoutingNumberResponse addRoutingNumber(AddRoutingNumberRequest addRoutingNumberRequest) {
		log.info("Enter addRoutingNumber of  RoutingNumberService");
		AddRoutingNumberResponse addRoutingNumberResponse = new AddRoutingNumberResponse();
		StatusResponse statusResponse = new StatusResponse();
		try {

			// Check the Routing Number Existence Before Adding.
			RoutingNumberEntity routingNumberData = routingNumberRepository
					.findByRoutingNumber(addRoutingNumberRequest.getRoutingNumber());

			if (Objects.nonNull(routingNumberData)) {
				addRoutingNumberResponse.setBankName(routingNumberData.getBankName());
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("Routing Number Data Available in Database.");
				addRoutingNumberResponse.setStatusResponse(statusResponse);
			} else {
				RoutingNumberEntity serviceInput = prepareServiceInput(addRoutingNumberRequest);
				RoutingNumberEntity serviceResponse = routingNumberRepository.save(serviceInput);

				addRoutingNumberResponse.setBankAdded(true);
				addRoutingNumberResponse.setBankName(serviceResponse.getBankName());
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("New Routing Number Added Successfully.");
				addRoutingNumberResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while Adding New Routing Number");
			addRoutingNumberResponse.setStatusResponse(statusResponse);
			log.error("Exception while openNewAccount of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit addRoutingNumber of  RoutingNumberService");
		return addRoutingNumberResponse;
	}

	/**
	 * validateRoutingNumber.
	 * 
	 * @param validateRoutingNumberRequest
	 * @return ValidateRoutingNumberResponse
	 */
	public ValidateRoutingNumberResponse validateRoutingNumber(
			ValidateRoutingNumberRequest validateRoutingNumberRequest) {
		log.info("Enter validateRoutingNumber of  RoutingNumberService");

		ValidateRoutingNumberResponse validateRoutingNumberResponse = new ValidateRoutingNumberResponse();
		StatusResponse statusResponse = new StatusResponse();

		try {

			RoutingNumberEntity serviceResponse = routingNumberRepository
					.findByRoutingNumber(validateRoutingNumberRequest.getRoutingNumber());

			if (Objects.nonNull(serviceResponse)) {
				validateRoutingNumberResponse.setBankName(serviceResponse.getBankName());
				statusResponse.setStatusCode(200);
				statusResponse.setStatusMessage("Routing Number Details Fetched Successfully.");
				validateRoutingNumberResponse.setStatusResponse(statusResponse);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("Routing Number Not Exists in Database.");
				validateRoutingNumberResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while Validating Routing Number");
			validateRoutingNumberResponse.setStatusResponse(statusResponse);
			log.error("Exception while validateRoutingNumber of AccountOpenService {} ", e.getMessage());
		}

		log.info("Exit validateRoutingNumber of  RoutingNumberService");

		return validateRoutingNumberResponse;
	}

	/**
	 * prepareServiceInput.
	 * 
	 * @param addRoutingNumberRequest
	 * @return RoutingNumberEntity
	 */
	private RoutingNumberEntity prepareServiceInput(AddRoutingNumberRequest addRoutingNumberRequest) {
		RoutingNumberEntity routingNumberEntity = new RoutingNumberEntity();
		routingNumberEntity.setRoutingNumber(addRoutingNumberRequest.getRoutingNumber());
		routingNumberEntity.setBankName(addRoutingNumberRequest.getBankName());
		return routingNumberEntity;
	}

}
