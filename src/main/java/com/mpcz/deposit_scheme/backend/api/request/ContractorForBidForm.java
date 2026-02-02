package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;

	@ApiModel(description = "ContractorForBidForm.")
	public @Data class ContractorForBidForm implements Serializable {

		private static final long serialVersionUID = 1L;

		//@JsonProperty(value="userId")
		private String userId;
		
		private String consumers;
		
		private Long consumerApplicationId;
		
		private String consumerTsak;
		
		private String consumerName;
		
		private String contractorAuthenticationId;

		
		

		public String getContractorAuthenticationId() {
			return contractorAuthenticationId;
		}

		public void setContractorAuthenticationId(String contractorAuthenticationId) {
			this.contractorAuthenticationId = contractorAuthenticationId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getConsumers() {
			return consumers;
		}

		public void setConsumers(String consumers) {
			this.consumers = consumers;
		}

		public Long getConsumerApplicationId() {
			return consumerApplicationId;
		}

		public void setConsumerApplicationId(Long consumerApplicationId) {
			this.consumerApplicationId = consumerApplicationId;
		}

		public String getConsumerTsak() {
			return consumerTsak;
		}

		public void setConsumerTsak(String consumerTsak) {
			this.consumerTsak = consumerTsak;
		}

		public String getConsumerName() {
			return consumerName;
		}

		public void setConsumerName(String consumerName) {
			this.consumerName = consumerName;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
	}

