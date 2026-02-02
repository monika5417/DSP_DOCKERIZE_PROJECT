package com.mpcz.deposit_scheme.backend.api.enums;

public enum ApplicationStatusEnum {

	UNPAID_APPLICATION_PENDING_FOR_VENDOR_APPROVAL(2L, "Unpaid application pending for vendor approval"),
	UNPAID_APPLICATION_VENDOR_REJECTED(3L, "Unpaid application vendor rejected"),
	PENDING_FOR_GIS_LOCATION(4L, "Unpaid application pending for GIS location"),
	PENDING_FOR_REGISTRATION_FEES(5L, "Pending for Registration Fees"),
	ACCEPTANCE_OF_APPLICATION_AT_DC(6L, "Acceptance of application at DC"),
	PENDING_FOR_SURVEY_AT_DC(7L, "Pending for survey at DC"),
//	PENDING_FOR_DEMAND_GENERATION_AT_DE(8L, "Pending for demand generation at DE"),
	PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_DGM(9L, "Pending for demand note approval at DGM"),
	PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_GM(10L, "Pending for demand note approval at GM"),
	PENDING_FOR_DEMAND_NOTE_APPROVAL_AT_CGM(11L, "Pending for demand note approval at CGM"),
	DEMAND_PAYMENT_PENDING_BY_CONSUMER(12L, "Demand payment pending by consumer "),
	PENDING_FOR_WORK_ORDER_BY_DE(14L, "Pending for work order by DE"),
	WAITING_FOR_72_HOURS(20L, "Waiting for Contractor Response for 72 hours"),
	PENDING_FOR_SELECTING_CONTRACTOR(21L, "Pending for selecting contractor"),
	PENDING_FOR_ACCEPTANCE_CONTRACTOR(22L, "Pending for Acceptance of contractor"),
	PENDING_FOR_WORK_ORDER(23L, "Pending for generation of work order"),
	PENDING_FOR_WORK_COMPLETION(24L, "Pending for generation of work completion"),
	WORK_COMPLETION_DONE(25L, "Work Completion Done"),
	HAND_OVER_TO_DGM(26L, "HandOver to Dgm O&M"),
	PENDING_FOR_CONFIRMATION_OF_WORK_COMPLETION_BY_DGM_STC(27L,
			"Pending for Confirmation of Work Completion by DGM STC"),
	WORK_FINISED(28L, "work finised"), 
	APPLICATION_REJECT_BY_GM(29L, "application rejected"),
	REMAING_DEMAND_PAYMENT_PENDING_BY_CONSUMER(30L, "Remaining Demand Payment Pending By Consumer"),
	NOTICE_TO_CONTRACTOR_FOR_SHORTCOMING_BY_DGM_STC(31L, "Notice To Contractor By Shortcoming Dgm Stc"),
	WORK_FINISHED_NOW_PENDING_FOR_CONNECTION_PUNCHING(32L, "Work Finished Now Pending For Connection Punching"),
	CONNECTION_GRANTED_SUCCESSFULLY(33L, "Connection Granted Successfully"),
	PENDING_FOR_CONTRACTOR_SELECTION_AFTER_TENDER_BY_DGM_STC(34L,
			"Pending for Contractor Selection After Tender By DGM-STC"),
	APPLICATION_REJECTED_DEMAND_NOTE_PAYMENT_NOT_DONE_WITHIN_30_DAYS(35L,
			"Application Rejected, Demand Note Payment Not Done WIthin 30 Days."),
	APPLICATION_REJECTED_BY_DC_USER_DUE_TO_UPLOADED_WRONG_DOCUMENT_BY_CONSUMER(36L,
			"Application Rejected By DC User Due To Uploaded Wrong Document By Consumer"),
	REJECTION_PROPOSAL_BY_DGM(37L, "Rejection Proposal By DGM"),
	APPLIACATION_BACK_TO_CONSUMER_END_FOR_UPDATION(38L, "Application Back To Consumer End For Updation"),
	APPLICATION_PENDING_AT_DGM_FOR_REFUND(39L, "Application Pending At DGM(O&M) For Refund"),
	APPLICATION_PENDING_AT_GM_FOR_REFUND(40L, "Application Pending At GM(O&M) For Refund"),
	APPLICATION_PENDING_AT_FINANCE_AO_FOR_REFUND(41L, "Application Pending At Finance AO For Refund"),
	CANCELLATION_AMOUNT_REFUNDED_TO_APPLICANT_SUCCESSFULLY(42L,
			"Cancellation Amount Refunded To Applicant Successfully"),
	APPLICATION_PENDING_AT_DGM_STC_FOR_REFUND(43L, "Application Pending At DGM STC For Refund"),
	REJECTION_PROPOSAL_BY_JE(44L, "Rejection Proposal By JE/AE O&M"),
	DGM_OR_DGM_STC_REJECTED_APPLICATION_AT_CONSUMER_END(45L, "Dgm(O&M) AND DGM(STC) Rejected Application At Consumer End"),
	DGM_OR_DGM_STC_REJECTED_RETURN_AMOUNT_APPLICATION_AT_CONSUMER_END(46L, "Dgm(O&M) AND DGM(STC) Rejected Return Amount Application At Consumer End"),
	PENDING_AT_AO_END_FOR_PAYMENT_VERIFICATION(47L, "Pending At AO End For Payment Verification"),
	PENDING_FOR_INSPECTION_OF_WORK_BY_AE_STC(48L,"Pending for Inspection of Work by AE STC"),
	PENDING_AT_ERP_END_FOR_INVOICE_CREATION(49L,
			"Pending at ERP end for Invoice Creation"),
	PENDING_AT_GM_END_FOR_DEMAND_NOTE_VERIFICATION(50L,
			"Pending at GM end for Demand Note Verification");

	private Long id;
	private String value;

	private ApplicationStatusEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static ApplicationStatusEnum getEnumByName(String name) {
		ApplicationStatusEnum[] modes = ApplicationStatusEnum.values();
		if (modes == null) {
			return null;
		}
		for (ApplicationStatusEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static ApplicationStatusEnum getEnumByValue(Long value) {
		ApplicationStatusEnum[] modes = ApplicationStatusEnum.values();
		if (modes == null) {
			return null;
		}
		for (ApplicationStatusEnum mode : modes) {
			if (mode.getId() == value) {
				return mode;
			}
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

}
