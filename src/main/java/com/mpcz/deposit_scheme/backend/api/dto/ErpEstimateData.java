package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

public @Data class ErpEstimateData implements Serializable {

	
	private String estimateNo;
	private String pendingOn;
	private String status;
	private String notificationOnDate;
	private String actionPerformedDate;
	private String actionPerformed;
}
