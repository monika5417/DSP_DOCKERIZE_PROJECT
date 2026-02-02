package com.mpcz.deposit_scheme.backend.api.constant;

/*
 * @author SandeepNamdeo
 * @version 1.0
 * @since 1.0
 * 
 * Interface to Manage Rest API Request URL's. It content all swagger REST API Documentation tags and URL.
 */
public interface RestApiUrl {
	
	/*
	 * User Base URL for REST API's If User Logged..
	 */
	public static final String USER_BASE_URL = "/api/user";

	/*
	 * Consumer Base URL for REST API's If Consumer Logged..
	 */
	public static final String CONSUMER_BASE_URL = "/api/consumer";
	public static final String CONSUMER_ACC_BASE_URL = "/api/consumer-acc";

	/*
	 * Master Base URL for REST API's If Consumer Logged..
	 */
	public static final String MASTER_BASE_URL = "/api";

	/*
	 * URL to add new record.
	 */
	public static final String ADD_URL = "/add";

	/*
	 * URL to delete new record.
	 */
	public static final String DELETE_URL = "/delete/{id}";

	/*
	 * URL to update existing record.
	 */
	public static final String UPDATE_URL = "/update/{id}";

	/*
	 * URL to retrieve specific record.
	 */
	public static final String GET_URL = "/get/{id}";

	/*
	 * URL to Retrieve all record's.
	 */
	public static final String GET_ALL_URL = "/getAll";

	/*
	 * URL to Retrieve all record's by specific parameter.
	 */
	public static final String GET_BY_PARAM = "/getByParam";

	/*
	 * URL to Retrieve all record's with Pagination.
	 */
	public static final String GET_ALL_PAGINATE_URL = "/getAllPaginate";

	/*
	 * API for Controllers
	 */

	/*
	 * Consumer Type Controller.
	 */
	public static final String CONSUMER_TYPE_TAGS = "consumer-type";
	public static final String CONSUMER_TYPE_API = USER_BASE_URL + "/" + CONSUMER_TYPE_TAGS;

	/*
	 * Document Type Controller.
	 */
	public static final String DOCUMENT_TYPE_TAGS = "document-type";
	public static final String DOCUMENT_TYPE_API = USER_BASE_URL + "/" + DOCUMENT_TYPE_TAGS;

	/*
	 * For Sign-up.
	 */
	public static final String SIGNUP_TAGS = "signup";

	/*
	 * URL for sign up process.
	 */
	public static final String SIGNUP_URL = "/signup";

	/*
	 * URL for Consumer Sign-up controller
	 */
//	public static final String CONSUMER_SIGNUP_API = CONSUMER_BASE_URL + "/" + SIGNUP_TAGS;
	public static final String CONSUMER_SIGNUP_API = CONSUMER_BASE_URL;
//	public static final String CONSUMER_ACC_BASE_API = CONSUMER_ACC_BASE_URL;

	/*
	 * URL for User Sign-up controller
	 */
//	public static final String USER_SIGNUP_API = USER_BASE_URL + "/" + SIGNUP_TAGS;
	public static final String USER_SIGNUP_API = USER_BASE_URL;

//	***********************sandeep, 19-09-2022, starts****************
	/*
	 * Login tag use for swagger REST API Documentation.
	 */
	// For Login.
	public static final String LOGIN_TAGS = "login";

	/*
	 * URL for Consumer Login controller
	 */
	public static final String CONSUMER_LOGIN_API = CONSUMER_BASE_URL + "/" + LOGIN_TAGS;

	/*
	 * URL for User Login controller
	 */
	public static final String USER_LOGIN_API = USER_BASE_URL + "/" + LOGIN_TAGS;

	/*
	 * URL for change login password
	 */
	public static final String FORGOT_PASSWORD_URL = "/forgotPassword";

	/*
	 * URL for update user password.
	 */
	public static final String CHANGE_PASSWORD_URL = "/changePassword";

	/*
	 * URL for Consumer Login process.
	 */
	public static final String LOGIN_VERIFICATION_URL = "/verification";
	public static final String LOGIN_VERIFICATION_CAPTCHA_URL = "/verificationCaptcha";
	public static final String LOGIN_GET_CAPTCHA_URL = "/getCaptcha";

	/*
	 * Consumer Application Detail Controller for manage Consumer Application
	 */
	public static final String CONSUMER_APPLICATION_DETAIL_TAGS = "consumer-application";
	public static final String CONSUMER_APPLICATION_DETAIL_API = CONSUMER_BASE_URL + "/"
			+ CONSUMER_APPLICATION_DETAIL_TAGS;

	public static final String USER_APPLICATION_DETAIL_API = USER_BASE_URL + "/" + CONSUMER_APPLICATION_DETAIL_TAGS;

	/*
	 * Scheme Type Controller for manage Scheme Type
	 */
	public static final String SCHEME_TYPE_TAGS = "scheme-type";
	public static final String SCHEME_TYPE_API = USER_BASE_URL + "/" + SCHEME_TYPE_TAGS;

//	***********************sandeep, 19-09-2022, ends****************

//	***********************charitra, 21-09-2022, start****************
	/*
	 * Application Status Controller for manage Application Status
	 */
	public static final String APPLICATION_STATUS_TAGS = "application-status";
	public static final String APPLICATION_STATUS_API = USER_BASE_URL + "/" + APPLICATION_STATUS_TAGS;

	/*
	 * Master Controller for manage Master's API.
	 */
	public static final String MASTER_TAGS = "master";
	public static final String MASTER_API = MASTER_BASE_URL + "/" + MASTER_TAGS;

	/*
	 * QC Portal Controller for manage QC Portal's API.
	 */
	public static final String QC_PORTAL_TAGS = "qc-portal";
	public static final String QC_PORTAL_API = MASTER_BASE_URL + "/" + QC_PORTAL_TAGS;

	/*
	 * URL for retrieve all Scheme Type.
	 */
	public static final String GET_ALL_SCHEME_TYPE_URL = "/getAllSchemeType";

	/*
	 * URL for retrieve all Document Type.
	 */
	public static final String GET_ALL_DOCUMENT_TYPE_URL = "/getAllDocumentType";

	/*
	 * URL for retrieve all Contractor.
	 */
	public static final String GET_ALL_CONTRACTOR_URL = "/getAllContractor/{id}";

	/*
	 * URL for retrieve all Discom.
	 */
	public static final String GET_ALL_DISCOM_URL = "/getAllDiscom";

	/*
	 * URL for retrieve all Work Type.
	 */
	public static final String GET_ALL_WORK_TYPE_URL = "/getAllWorkType";

	/*
	 * URL for retrieve all Distribution Center.
	 */
	public static final String GET_ALL_DISTRIBUTION_CENTER_URL = "/getAllDistributionCenter";

	/*
	 * URL for retrieve all Substation.
	 */
	public static final String GET_ALL_SUBSTATION_URL = "/getAllSubstation";
	public static final String GET_ALL_SUBSTATION_BY_DC_URL = "/getAllSubstationByDC/{id}";

	/*
	 * URL for retrieve all Feeder.
	 */
	public static final String GET_ALL_FEEDER_URL = "/getAllFeeder";
	public static final String GET_ALL_FEEDER_BY_SUBSTATION_URL = "/getAllFeederBySubstation/{id}";

	/*
	 * URL for retrieve all user designation's.
	 */
	public static final String GET_ALL_DESIGNATIONL_URL = "/getAllDesignation";
	/*
	 * URL for retrieve all circle's.
	 */
	public static final String GET_ALL_CIRCLE_URL = "/getAllCircle";
	/*
	 * URL for retrieve all region's.
	 */
	public static final String GET_ALL_REGION_URL = "/getAllRegion";
	/*
	 * URL for retrieve all divison's.
	 */
	public static final String GET_ALL_DIVISION_URL = "/getAllDivision";
	/*
	 * URL for retrieve all role's for user account.
	 */
	public static final String GET_ALL_ROLE_URL = "/getAllRole";

	/*
	 * URL for retrieve all access level for user account privileges(area wise).
	 */
	public static final String GET_ALL_ACCESS_LEVEL_URL = "/getAllAccessLevel";

	public static final String GET_ALL_CIRCLE_BY_REGION_URL = "/getAllCircleByRegion/{id}";

	public static final String GET_FILE = "/getFile/{id}";

	/*
	 * 
	 * 
	 * 
	 * 
	 * /* Discom Controller for manage Discom.
	 */
//	public static final String DISCOM_TAGS = "discom";
//	public static final String DISCOM_API = USER_BASE_URL + "/" + DISCOM_TAGS;

	/*
	 * Region Controller for manage Region.
	 */
//	public static final String REGION_TAGS = "Region";
//	public static final String REGION_API = USER_BASE_URL + "/" + REGION_TAGS;

	/* pdtc team starts */

	/*
	 * // ******************Ashish Tiwari code strat ************* Region Controller
	 */
	public static final String REGION_TAGS = "region";
	public static final String REGION_API = USER_BASE_URL + "/" + REGION_TAGS;

	/*
	 * Get All Region's by DiscomId
	 */
	public static final String GET_ALL_REGION_BY_DISCOM_URL = "/getAllRegionByDiscom/{id}";

	/*
	 * Get All Discom
	 */
	public static final String DISCOM_STATUS_TAGS = "discom-type";

	public static final String DISCOM_TYPE_API = USER_BASE_URL + "/" + DISCOM_STATUS_TAGS;

	public static final String CIRCLE_TAGS = "circle";

	public static final String CIRCLE_API = USER_BASE_URL + "/" + CIRCLE_TAGS;

	public static final String DIVISION_TAGS = "division";

	public static final String DIVISION_API = USER_BASE_URL + "/" + DIVISION_TAGS;

	public static final String SUB_DIVISION_TAGS = "subdivision";

	public static final String SUB_DIVISION_API = USER_BASE_URL + "/" + SUB_DIVISION_TAGS;

	// *************** Code end Ashish Tiwari ******************//

	/*
	 * Distribution Center Controller
	 */
	public static final String DC_TAGS = "dc";
	public static final String DC_API = CONSUMER_BASE_URL + "/" + DC_TAGS;

	public static final String SUB_STATION_TAGS = "substation";

	public static final String SUB_STATION_API = USER_BASE_URL + "/" + SUB_STATION_TAGS;

	public static final String FEEDER_TAGS = "feeder";
	public static final String FEEDER_API = USER_BASE_URL + "/" + FEEDER_TAGS;

	public static final String USER_TYPE_TAGS = "user-type";
	public static final String USER_TYPE_API = USER_BASE_URL + "/" + USER_TYPE_TAGS;

	public static final String _TAGS = "circle";

	
	/*
	 * Master API URL for data is already exists or not.
	 */

	public static final String IS_DATA_ALREADY_EXISTS = "/isDataAlreadyExists";

	public static final String UPDATE_USER_URL = "/updateUser/{id}";

	/*
	 * URL for view user sign up process form.
	 */
	public static final String VIEW_SIGNUP_URL = "/view_signup";

//	 CHARITRA STAT CODE 

	public static final String GEO_TYPE_TAGS = "geo-location";

	public static final String GEO_TYPE_API = USER_BASE_URL + "/" + GEO_TYPE_TAGS;

//	CHARITRA END CODE

	/*
	 * Division Controller
	 */
	public static final String GET_ALL_DIVISION_BY_CIRCLE_URL = "/getAllDivisionByCircle/{id}";

	/*
	 * Vendor Controller for manage Consumer's Application.
	 */
	public static final String VENDOR_TAGS = "vendor";
	public static final String VENDOR_API = USER_BASE_URL + "/" + VENDOR_TAGS;

	public static final String GET_ALL_CONSUMERS_APPLICATION_BY_VENDOR_ID = "/getAllConsumerApplicationByVendor/{id}";

	public static final String ADD_CONSUMER_APPLICATION_DETAILS = "/addConsumerApplicationDetails";

	/*
	 * Sub Division Controller
	 */
	public static final String GET_ALL_SUB_DIVISION_BY_DIVISION_URL = "/getAllSubDivisionByDivision/{id}";

	/*
	 * Distribution Center Controller
	 */
	public static final String GET_ALL_DC_BY_SUBDIVISION_URL = "/getAllDcBySubdivision/{id}";
	public static final String GET_ALL_DC_BY_DISTRICT_URL = "/getAllDcByDistrict/{id}";

	/*
	 * Demand Type Controller.
	 */
	public static final String DEMAND_TYPE_TAGS = "demand";
	public static final String DEMAND_TYPE_API = USER_BASE_URL + "/" + DEMAND_TYPE_TAGS;
	public static final String UPDATE_DEMAND_URL = "/updateDemand/{id}";

	public static final String SUBMIT_SURVEY_URL = "/submitSurvey";
	public static final String UPDATE_SURVEY_URL = "/updateSurvey/{id}";

	public static final String USER_APPLICATION_SURVEY_TAGS = "application-survey";
	public static final String USER_APPLICATION_SURVEY_DETAIL_API = USER_BASE_URL + "/" + USER_APPLICATION_SURVEY_TAGS;

	public static final String INVOICE_TAPE_TAGS = "invoice";

	public static final String INVOICE_API = CONSUMER_BASE_URL + "/" + INVOICE_TAPE_TAGS;

	public static final String PAYMENT_API = CONSUMER_BASE_URL + "/" + "payment";

	public static final String INITIA_PAY_SUCCESS = "/initial_pay_success";

	public static final String RESPONSE_PAYMENTS = "/response_payment";

	public static final String PAYMENT_HISTORY = "paymenthistory";
	public static final String PAYMENT_HISTORY_API = CONSUMER_BASE_URL + "/" + PAYMENT_HISTORY;

	public static final String PREVIOUS_STAGE_DETAIL_TAGS = "previous-stage";
	public static final String PREVIOUS_STAGE_DETAIL_API = USER_BASE_URL + "/" + PREVIOUS_STAGE_DETAIL_TAGS;

	public static final String SAVE_BACK_TO_PREVIOUS_STAGE_URL = "/saveBackToPreviousStage";

	/*
	 * Vendor Controller for manage Consumer's Application.
	 */
	public static final String CONTRACTOR_TAGS = "contractor";
	public static final String CONTRACTOR_API = CONSUMER_BASE_URL + "/" + CONTRACTOR_TAGS;

	/*
	 * Application Survey for manage Consumer's Application.
	 */
	public static final String GET_APPLICATION_SURVEY_BY_APPLICATION_ID_URL = "/getApplicationSurvey/{id}";

	/*
	 * Application Demand for manage Consumer's Application.
	 */
	public static final String GET_APPLICATION_DEMAND_BY_APPLICATION_ID_URL = "/getApplicationdDemand/{id}";

	public static final String USER_APPLICATION_DC_CHANGE_TAGS = "application-dc-change";
	public static final String USER_APPLICATION_DC_CHANGE_API = USER_BASE_URL + "/" + USER_APPLICATION_DC_CHANGE_TAGS;

	/*
	 * Demand Approval.
	 */

	public static final String DEMAND_APPROVAL_TAGS = "demand-approval";
	public static final String DEMAND_APPROVAL_API = USER_BASE_URL + "/" + DEMAND_APPROVAL_TAGS;

	/*
	 * Work Order Controller.
	 */
	public static final String WORK_ORDER_TYPE_TAGS = "work-order";
	public static final String WORK_ORDER_TYPE_API = USER_BASE_URL + "/" + WORK_ORDER_TYPE_TAGS;

	/*
	 * URL to retrieve specific User/Consumer record.
	 */
	public static final String GET_LOGIN_URL = "/getLoginAccountDetail/{loginId}";

	/*
	 * PaymentRecieptGenerateController for generating pdf .
	 */
	public static final String PAYMENT_RECEIPT_PDF_TAGS = "payment-pdf";
	public static final String PAYMENT_RECEIPT_PDF_API = CONSUMER_BASE_URL + "/" + PAYMENT_RECEIPT_PDF_TAGS;

	public static final String GET_PAYMENT_PDF = "/getPaymentPdf/{consumer_Id}";

	public static final String ADDD_URL = "/getFile/{consumer_Id}";

	public static final String GET_GEO_lOCATION_DETAILS = "/getGeolocationDetails/{consumerApplicationNumber}";

	/*
	 * Appliation Type Controller for manage Application Type
	 */
	public static final String APPLICATION_TYPE_TAGS = "application-type";
	public static final String APPLICATION_TYPE_API = USER_BASE_URL + "/" + APPLICATION_TYPE_TAGS;

	/*
	 * URL for retrieve all Application Type.
	 */
	public static final String GET_ALL_APPLICATION_TYPE_URL = "/getAllApplicationType";

	/*
	 * Appliation Type Wise Doc Controller for manage Application Type Wise Doc
	 */
	public static final String APPLICATION_TYPE_WISE_DOC_TAGS = "application-type-wise-doc";
	public static final String APPLICATION_TYPE_WISE_DOC_API = USER_BASE_URL + "/" + APPLICATION_TYPE_WISE_DOC_TAGS;

	/*
	 * URL for retrieve all Application Type.
	 */
	public static final String GET_ALL_APPLICATION_CHARGES_BY_APPLICATIONID_PAYMENTTYPE_URL = "/getAllApplicationHeadWiseCharges";

	public static final String GET_APPLICATION_DETAIL_BY_APPLICATION_NUMBER_URL = "/getApplicationdDetailByNumber/{appNumber}";

	/*
	 * URL for Get all Application Document On the basis Consumer Application
	 * Details Id.
	 */
	public static final String GET_ALL_APPLICATION_DOCUMENT_BY_CONSUMER_APPLICATION_ID_URL = "/getAllApplicationDocument/{id}";

	/*
	 * Supply Voltage Controller for manage Supply Voltage
	 */
	public static final String SUPPLY_VOLTAGE_TAGS = "supply-voltage";
	public static final String SUPPLY_VOLTAGE_API = USER_BASE_URL + "/" + SUPPLY_VOLTAGE_TAGS;

	/*
	 * URL for retrieve all Supply Voltage.
	 */
	public static final String GET_ALL_SUPPLY_VOLTAGE_URL = "/getAllSupplyVoltage";

//	/*
//	 * URL for retrieve Consumer's Application Demand details by Consumer
//	 * Application Id.
//	 */
//	public static final String GET_DEMAND_DETAIL_BY_CONSUMER_APPLICATION_ID_URL = "/getDemandDetailsByConsumerApplicationId/{id}";

//	charitra 
	public static final String GET_CONSUMER_APPLICATION_DC_DETAILS_BY_APPLICATION_NO_URL = "/getConsumerApplicationDcDetailsByApplicationNo/{ConsumerApplicationNumber}";

	public static final String GET_CONSUMER_APPLICATION_DC_DETAILS_BY_DC_CODE_URL = "/getCosumerApplicationDcDetailsByDcCode/{dcCode}";

	/*
	 * Tariff Category Controller for manage Tariff Category.
	 */
	public static final String TARIFF_CATEGORY_TAGS = "Tariff-Category";
	public static final String TARIFF_CATEGORY_API = USER_BASE_URL + "/" + TARIFF_CATEGORY_TAGS;

	/*
	 * URL for retrieve all Tariff Category.
	 */
	public static final String GET_ALL_TARIFF_CATEGORY_URL = "/getAllTariffCategory";

	/*
	 * URL to retrieve specific User by their Login Id.
	 */
	public static final String GET_USER_BY_LOGIN_ID_URL = "/getUserByLoginId/{id}";

	/*
	 * URL to retrieve User List by their Circle.
	 */
	public static final String GET_USERS_BY_CIRCLE_ID_URL = "/getUsersByCircleId/{id}";

	/*
	 * URL for fetch specific user details.
	 */
	public static final String VIEW_USER_URL = "/getUser/{id}";

	/*
	 * Task Type Controller for manage Task Type's API.
	 */
	public static final String TASK_TYPE_TAGS = "task-type";
	public static final String TASK_TYPE_API = USER_BASE_URL + "/" + TASK_TYPE_TAGS;

	/*
	 * URL for retrieve all Task Type.
	 */
	public static final String GET_ALL_TASK_TYPE_URL = "/getAllTaskType";

	/*
	 * URL for retrieve all Application Status.
	 */
	public static final String GET_ALL_APPLICATION_STATUS_URL = "/getAllApplicationStatus";

	/*
	 * misExcel data details.
	 */
	public static final String MIS_EXCEL_DETAIL_TAGS = "consumer-details";
	public static final String MIS_EXCEL_DETAIL_API = CONSUMER_BASE_URL + "/" + MIS_EXCEL_DETAIL_TAGS;

	public static final String GET_PDF_URL = "/getPDf";

	public static final String GET_HEADER_URL = "/getExcelPdf";

	public static final String MIS_EXCEL_DETAIL_TAG = "consumer-detail";

	public static final String NATURE_OF_WORK_TAGS = "work-nature";
	public static final String NATURE_OF_WORK_API = CONSUMER_BASE_URL + "/" + NATURE_OF_WORK_TAGS;

	/*
	 * URL for retrieve all Nature Of Work.
	 */
	public static final String GET_ALL_NATURE_OF_WORK_STATUS_URL = "/getAllNatureOfWork";

	/*
	 * URL for retrieve all District.
	 */
	public static final String GET_ALL_DISTRICT_URL = "/getAllDistrict";
	public static final String DISTRICT_TAGS = "district";
	public static final String DISTRICT_API = CONSUMER_BASE_URL + "/" + DISTRICT_TAGS;

	public static final String CONSUMER_ESTIMATION_TAGS = "supervision-consumer-estimation";
	public static final String COSNUMER_ESTIMATION_API = CONSUMER_BASE_URL + "/" + CONSUMER_ESTIMATION_TAGS;

	/*
	 * URL to retrieve specific record.
	 */
//	public static final String GET_GEO_URL = "/pendingForGeoLocation/get/{mobileNumber}";

	/*
	 * Document Type Controller.
	 */
	public static final String LOAD_REQUESTED_TAGS = "load-requested";
	public static final String LOAD_REQUESTED_API = CONSUMER_BASE_URL + "/" + LOAD_REQUESTED_TAGS;
	
	public static final String GET_ALL_LOAD_REQUESTED_URL = "/getAllLoadRequested";
	
	public static final String GET_lOAD_REQUESTED_BY_ID_URL = "/getRequestedByLongId/{id}";

	public static final String GET_ALL_LAND_AERA_UNIT_URL = "/getAllLandAeraUnit";

	public static final String GET_ALL_LAND_AERA_UNIT_HE_URL = "/getAllLandAeraUnitHe";

	public static final String GET_ALL_INDIVIDUAL_OR_GROUP_URL = "/getAllIndividualOrGroup";

	/*********************
	 * sandeep, 22-02-2023, start
	 ********************/
	/*
	 * QC Portal Controller for manage QC Portal's API.
	 */

	public static final String URJAS_PORTAL_TAGS = "erp";
	public static final String URJAS_PORTAL_API = USER_BASE_URL + "/" + URJAS_PORTAL_TAGS;

	/*
	 * URL for retrieve all Contractor.
	 */
	public static final String GET_ERP_ESTIMATE_URL = "/getErpEstimate/{erpNumber}/{consumerAppId}";

	/*********************
	 * sandeep, 22-02-2023, end
	 ********************/
	
	public static final String GET_ERP_ESTIMATE_AMOUNT_URL = "/getErpEstimateAmount/{erpNumber}/{consumerAppId}/{savedataId}";
	
	public static final String UPDATE_ESTIMATE_STATUS_URL = "/update/{consumerAppId}";
	
	public static final String GET_ERP_ESTIMATE_CALCULATIONS_URL = "/erpEstimateCalculations/{consumerAppId}";
	
	public static final String GET_CONTRACTOR_STATUS_URL = "/contractorStatus";
	
	
	
	
	public static final String CONTRACTOR_DETAIL_TAG = "qc-portal";
	public static final String CONTRACTOR_DETAIL_API = CONSUMER_BASE_URL + "/" + CONTRACTOR_DETAIL_TAG;
	
	public static final String JASPER_PDF_TAG = "jasper-pdf";
	public static final String JASPER_PDF_API = USER_BASE_URL + "/" + JASPER_PDF_TAG;
	
	public static final String DOWNLOAD_PDF="/download/{erp_no}";
	
	public static final String UPDATE_USER_STATUS_URL="/changeUserStaus/{id}";
	//http://localhost:8083/deposit_scheme/api/user/login/changeUserStaus

	public static final String GET_ALL_SUBSTAION_BY_DC_URL = "/getAllSubstaionByDc/{id}";
	
	public static final String CONSUMER_FETCHING_API = CONSUMER_BASE_URL;
	
	public static final String GET_ALL_CONSUMER_URL = "/getAllConsumerDetail";
	
	public static final String ACCEPTANCE_FOR_CONTRACTORFORQC = "/acceptanceOfConsumer";
	


	public static final String WORK_ORDER_GENERATE_TAGS = "work-order";
	public static final String WORK_ORDEER_GENERATOR_API = USER_BASE_URL + "/" + WORK_ORDER_GENERATE_TAGS;

	


	public static final String NSC_DATA_TAGS = "nsc-data";
	public static final String NSC_DATA_API = USER_BASE_URL + "/" + NSC_DATA_TAGS;

	public static final String WORK_TYPE_TAGS = "work-type";
	public static final String WORK_TYPE_API = USER_BASE_URL + "/" + WORK_TYPE_TAGS;
	
	/*
	 * Work Type Controller for manage Work Type's API.
	 */
	public static final String WORK_TYPEE_TAGS = "work-typee";
	public static final String WORK_TYPEE_API = USER_BASE_URL + "/" + WORK_TYPEE_TAGS;
	
	public static final String CONTRACTOR_BID_WORK_STATUS_TAGS = "work-status";
	public static final String CONTRACTOR_BID_WORK_STATUS_API = USER_BASE_URL + "/" + CONTRACTOR_BID_WORK_STATUS_TAGS;


	
	public static final String WORK_ORDER_SAVE_TAGS = "save-work-order";
	public static final String WORK_ORDEER_SAVE_API = USER_BASE_URL + "/" + WORK_ORDER_SAVE_TAGS;
	
	
	public static final String GET_USER_URL = "/getUserOtp/{userId}";
	
	//public static final String TEST_DATA_API = "/api/user/jason";

	public static final String TEST_DATA_API_TAGS = "savejason";
	public static final String TEST_DATA_API = USER_BASE_URL + "/" + TEST_DATA_API_TAGS;
	
	public static final String BILL_DESK_TYPE_TAGS = "bill-desk";
	public static final String BILL_DESK_TYPE_API = CONSUMER_BASE_URL + "/" + BILL_DESK_TYPE_TAGS;

	public static final String HANDOVER_DGMHTC_TAG = "dgm-htc";
	public static final String HANDOVER_DGMHTC_TAG_API = USER_BASE_URL + "/" + HANDOVER_DGMHTC_TAG;

	public static final String ADD_URL_DTR = "/addDtr";
	
	public static final String ADD_URL_PTR = "/addPtr";
	
	public static final String ADD_URL_Lt = "/addLt";
	
	public static final String ADD_URL_11Kv = "/add11Kv";
	
	public static final String ADD_URL_33Kv = "/add33Kv";
	
	public static final String ADD_URL_132Kv = "/add132Kv";
	
	public static final String BILLDESK_PAYMENT_RESPONSE_TAGS = "bill_desk_response";
	public static final String BILLDESK_PAYMENT_RESPONSE_API = CONSUMER_BASE_URL + "/" + BILLDESK_PAYMENT_RESPONSE_TAGS;
	public static final String ADD_PAYMENT_URL = "/save";
	
	
	public static final String HAND_OVER_TO_DGMHTC_TAG = "handoverToDgmHtc";
	public static final String  HAND_OVER_TO_DGMHTC_API = USER_BASE_URL + "/" + HAND_OVER_TO_DGMHTC_TAG;

	public static final String CHANGE_STATUS_TAGS = "change-status";
	public static final String CHANGE_STATUS_API = USER_BASE_URL + "/" + CHANGE_STATUS_TAGS;
	
	public static final String DTR_TAGS ="dtr";
	public static final String  DTR_API = USER_BASE_URL +"/" + DTR_TAGS ;
	
	public static final String PTR_TAGS ="ptr";
	public static final String  PTR_API = USER_BASE_URL +"/" + PTR_TAGS ;
	
	public static final String HT_TAGS ="ht";
	public static final String  HT_API = USER_BASE_URL +"/" + HT_TAGS ;
	
	public static final String LT_TAGS ="lt";
	public static final String  LT_API = USER_BASE_URL +"/" + LT_TAGS ;
	
	public static final String LT_11_TAGS ="lt_11";
	public static final String  LT_11_API = USER_BASE_URL +"/" + LT_11_TAGS ;
	
	public static final String WORK_COMPLECATION_CHANGE_BY_O_AND_M ="change-status";
	public static final String  WORK_COMPLECATION_API = USER_BASE_URL +"/" + WORK_COMPLECATION_CHANGE_BY_O_AND_M ;
	
//	charitra start code 
//	public static final String BILLDESK_PAYMENT_RETRIVE_TAGS ="Billdesk_payment_retrive_api";
//	public static final String BILLDESK_PAYMENT_RETRIVE_API = BILL_DESK_TYPE_API + "/" + BILLDESK_PAYMENT_RETRIVE_TAGS;
	
	public static final String  CONSUMER_APPLICATION_DETAILS_BASED_ON_CITY_CRICLE_BHOPAL_AND_GWALIOR_O_AND_M ="/consumerApplicationDetailsBasedOnCityCricleBhopalAndGwaliorOANDM/{circleid}";
	
	public static final String  CONSUMER_APPLICATION_DETAILS_BASED_ON_CITY_CRICLE_BHOPAL_AND_GWALIOR_BASED_ON_LT_NULL_VALVE_htm ="/consumerApplicationDetailsBasedOnCityCricleBhopalAndGwaliorBasedONLtNullValueHTM/{circleid}";
	
//charitra end  code	
	
//	bhanu sir code 
	public static final String GET_ALL_PAGINATE_NEW_URL = "/getAllNewPaginate";
}