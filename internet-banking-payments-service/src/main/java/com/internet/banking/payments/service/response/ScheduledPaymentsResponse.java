package com.internet.banking.payments.service.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.internet.banking.payments.service.bean.ScheduledPaymentsBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ScheduledPaymentsResponse {
	
	private List<ScheduledPaymentsBean> scheduledPaymentsBeansList;
	
	private StatusResponse statusResponse;

}
