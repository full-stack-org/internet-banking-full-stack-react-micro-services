package com.internet.banking.payments.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.banking.payments.service.bean.ScheduledPaymentsBean;
import com.internet.banking.payments.service.entity.PaymentEntity;
import com.internet.banking.payments.service.entity.SchedulesEntity;
import com.internet.banking.payments.service.repository.PaymentsRepository;
import com.internet.banking.payments.service.request.CreatePaymentRequest;
import com.internet.banking.payments.service.request.ScheduledPaymentsRequest;
import com.internet.banking.payments.service.response.CreatePaymentResponse;
import com.internet.banking.payments.service.response.ScheduledPaymentsResponse;
import com.internet.banking.payments.service.response.StatusResponse;
import com.internet.banking.payments.service.util.PaymentsUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentsService {

	@Autowired
	private PaymentsRepository paymentsRepository;

	/**
	 * createPayment.
	 * 
	 * @param createPaymentRequest
	 * @return CreatePaymentResponse
	 */
	public CreatePaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) {
		log.info("Enter in createPayment of PaymentsService");

		CreatePaymentResponse createPaymentResponse = new CreatePaymentResponse();
		StatusResponse statusResponse = new StatusResponse();

		try {

			PaymentEntity paymentEntity = paymentsRepository.findByCustomerId(createPaymentRequest.getCustomerId());

			if (Objects.nonNull(paymentEntity)) {

				SchedulesEntity schedulesEntity = new SchedulesEntity();
				schedulesEntity.setFromAccountNumber(createPaymentRequest.getFromAccount());
				schedulesEntity.setPaymentAmount(createPaymentRequest.getPaymentAmount());
				schedulesEntity.setPaymentDate(createPaymentRequest.getPaymentDate());
				schedulesEntity.setToAccountNumber(createPaymentRequest.getToAccount());
				schedulesEntity.setReferenceNumber(String.valueOf(PaymentsUtil.generateConfirmationNumber()));
				paymentEntity.getSchedulesEntity().add(schedulesEntity);

				PaymentEntity serviceResponse = paymentsRepository.save(paymentEntity);

				createPaymentResponse = trasnfromCreatePaymentServiceResponse(serviceResponse,
						schedulesEntity.getReferenceNumber());

			} else {
				PaymentEntity serviceInput = prepareServiceInput(createPaymentRequest);

				PaymentEntity serviceResponse = paymentsRepository.save(serviceInput);

				createPaymentResponse = trasnfromCreatePaymentServiceResponse(serviceResponse,
						serviceInput.getSchedulesEntity().get(0).getReferenceNumber());
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while Creating Payment");
			createPaymentResponse.setStatusResponse(statusResponse);
			log.error("Exception while createPayment of PaymentsService {} ", e.getMessage());
		}

		log.info("Exit in createPayment of PaymentsService");

		return createPaymentResponse;
	}

	/**
	 * getScheduledPayments.
	 * 
	 * @param scheduledPaymentsRequest
	 * @return ScheduledPaymentsResponse
	 */
	public ScheduledPaymentsResponse getScheduledPayments(ScheduledPaymentsRequest scheduledPaymentsRequest) {
		log.info("Enter in getScheduledPayments of PaymentsService");

		ScheduledPaymentsResponse scheduledPaymentsResponse = new ScheduledPaymentsResponse();
		StatusResponse statusResponse = new StatusResponse();

		try {
			PaymentEntity paymentEntity = paymentsRepository.findByCustomerId(scheduledPaymentsRequest.getCustomerId());

			if (Objects.nonNull(paymentEntity)) {
				scheduledPaymentsResponse = trasnfromScheduledPaymentServiceResponse(paymentEntity);
			} else {
				statusResponse.setStatusCode(422);
				statusResponse.setStatusMessage("No scheduled Payments");
				scheduledPaymentsResponse.setStatusResponse(statusResponse);
			}

		} catch (Exception e) {
			statusResponse.setStatusCode(500);
			statusResponse.setStatusMessage("Exception while Getting Scheduled Payments");
			scheduledPaymentsResponse.setStatusResponse(statusResponse);
			log.error("Exception while getScheduledPayments of PaymentsService {} ", e.getMessage());
		}

		log.info("Exit in getScheduledPayments of PaymentsService");

		return scheduledPaymentsResponse;
	}

	/**
	 * trasnfromScheduledPaymentServiceResponse.
	 * 
	 * @param paymentEntity
	 * @return ScheduledPaymentsResponse
	 */
	@SuppressWarnings("unchecked")
	private ScheduledPaymentsResponse trasnfromScheduledPaymentServiceResponse(PaymentEntity paymentEntity) {
		ScheduledPaymentsResponse scheduledPaymentsResponse = new ScheduledPaymentsResponse();

		if (paymentEntity.getSchedulesEntity() instanceof PersistentBag) {
			List<SchedulesEntity> accountDetailsList = new ArrayList<>();
			PersistentBag persBag = (PersistentBag) paymentEntity.getSchedulesEntity();
			accountDetailsList.addAll(persBag);

			List<ScheduledPaymentsBean> scheduledPaymentsBeansList = new ArrayList<>();

			accountDetailsList.stream().forEach(schedulePayment -> {
				ScheduledPaymentsBean scheduledPaymentsBean = new ScheduledPaymentsBean();
				scheduledPaymentsBean.setFromAccountNumber(schedulePayment.getFromAccountNumber());
				scheduledPaymentsBean.setPaymentAmount(schedulePayment.getPaymentAmount());
				scheduledPaymentsBean.setPaymentDate(schedulePayment.getPaymentDate());
				scheduledPaymentsBean.setReferenceNumber(schedulePayment.getReferenceNumber());
				scheduledPaymentsBean.setToAccountNumber(schedulePayment.getToAccountNumber());
				scheduledPaymentsBeansList.add(scheduledPaymentsBean);
			});

			scheduledPaymentsResponse.setScheduledPaymentsBeansList(scheduledPaymentsBeansList);
			StatusResponse statusResponse = new StatusResponse();
			statusResponse.setStatusCode(200);
			statusResponse.setStatusMessage("Scheduled Payments Fetched Successfully");
			scheduledPaymentsResponse.setStatusResponse(statusResponse);
		}
		return scheduledPaymentsResponse;
	}

	/**
	 * trasnfromCreatePaymentServiceResponse.
	 * 
	 * @param serviceResponse
	 * @param string
	 * @return CreatePaymentResponse
	 */
	private CreatePaymentResponse trasnfromCreatePaymentServiceResponse(PaymentEntity serviceResponse,
			String refereNumber) {

		CreatePaymentResponse createPaymentResponse = new CreatePaymentResponse();

		createPaymentResponse.setCustomerId(serviceResponse.getCustomerId());
		createPaymentResponse.setConfirmationNumber(refereNumber);
		StatusResponse statusResponse = new StatusResponse();
		statusResponse.setStatusCode(200);
		statusResponse.setStatusMessage("Payment Created Successfully");
		createPaymentResponse.setStatusResponse(statusResponse);

		return createPaymentResponse;
	}

	/**
	 * prepareServiceInput.
	 * 
	 * @param createPaymentRequest
	 * @return PaymentEntity
	 */
	private PaymentEntity prepareServiceInput(CreatePaymentRequest createPaymentRequest) {
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setCustomerId(createPaymentRequest.getCustomerId());

		List<SchedulesEntity> schedulesEntityList = new ArrayList<>();
		SchedulesEntity schedulesEntity = new SchedulesEntity();
		schedulesEntity.setFromAccountNumber(createPaymentRequest.getFromAccount());
		schedulesEntity.setPaymentAmount(createPaymentRequest.getPaymentAmount());
		schedulesEntity.setPaymentDate(createPaymentRequest.getPaymentDate());
		schedulesEntity.setToAccountNumber(createPaymentRequest.getToAccount());
		schedulesEntity.setReferenceNumber(String.valueOf(PaymentsUtil.generateConfirmationNumber()));

		schedulesEntityList.add(schedulesEntity);

		paymentEntity.setSchedulesEntity(schedulesEntityList);

		return paymentEntity;
	}

}
