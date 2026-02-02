package com.mpcz.deposit_scheme.backend.api.constant;

/*
 * 
 * @author Sandeep Namdeo
 * @version 1.0
 * 
 * Response Message interface content all the message(i.e. success/error etc) return to each request.
 * final static fields for all response messages.
 */
public interface ResponseMessage {

	/*
	 * Content-Type for JSON
	 */
	String APPLICATION_TYPE_JSON = "Content-Type, application/json";

	/*
	 * if object having null value
	 */
	String NULL_OBJECT_MESSAGE = "Object having null value";

	/*
	 * CRUD operation status messages
	 */
	// insert operation
	String RECORD_INSERTED_MESSAGE = "Record Inserted Successfully";
	String LOGIN_RECORD_INSERTED_MESSAGE = "Login Detail Inserted Successfully";
	String LOGOUT_MESSAGE = "Logout Successfully";
	String RECORD_INSERTION_FAILED_MESSAGE = "Record Insertion Failed";

	// update operation
	String RECORD_UPDATED_MESSAGE = "Record Updated Successfully";
	String RECORD_UPDATION_FAILED_MESSAGE = "Record Updated Failed";

	// fetch operation
	String RECORD_FETCH_BY_ID_MESSAGE = "Record Retrived Successfully";
	String RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Record retrival failed";

	// fetch all operation
	String RECORD_FETCH_ALL_MESSAGE = "All Record Retrived Successfully";
	String RECORD_FETCH_ALL_FAILED_MESSAGE = "All Record's retrival failed";

	// delete operation
	String RECORD_DELETION_MESSAGE = "Record Deleted Successfully";
	String RECORD_DELETION_FAILED_MESSAGE = "Record deletion failed";

	/*
	 * HTTP Response status messages
	 */
	String SUCCESS = "Success";
	String FORM_VALIDATION_ERROR = "Form validation errors";
	String FORBIDDEN = "Forbidden";
	String NOT_FOUND = "Not Found";
	String UNSUPPORTED_MEDIA_TYPE = "Unsupported media type";
	String BAD_REQUEST = "Bad request";
	String UNAUTHORIZED = "Unauthorized!!";
	String INTERNAL_SERVER_ERROR = "Internal serevr error..!!";

	/*
	 * Custom messages for sending with response.
	 */
	String LOGIN_SUCCESSFULLY = "Login Successfully";
	String INVALID_USER_ID = "Invalid User Id";
	String ACCOUNT_LOCKED_MESSAGE = "Your account has been locked";
	String SIGNUP_FORM_VIEW_SUCCESS = "Registration form view Successfully";
	String USER_RECORD_ERROR_MESSAGE = "Object having null value in user created and updated date and id ";
	String CREATE_USER_RECORD_ERROR_MESSAGE = "Please try again. User not created";
	String AUTHENTICATION_FAIL = "Authentication fail due to wrong credential ";
	String ACCOUNT_LOCKED = "Your Account has been locked. Please contact with admin.";
	String ACCOUNT_EXPIRED = "Your Account has been expired. Please contact with admin.";
	String USER_DETAILS_NOT_FOUND = "No details found..!!";
	String PASSWORD_MATCH_ERROR = "Old password authentication failed.";
	String MOBILE_NO_NOT_FOUND = "Mobile number not present in system.";

	// Masters
	String ROLE_RECORD_ERROR_MESSAGE = "Object having null value in Role created and updated date and id ";
	String DESIGNATION_RECORD_ERROR_MESSAGE = "Object having null value in Designation created and updated date and id ";
	String REGION_RECORD_ERROR_MESSAGE = "Object having null value in Region created and updated date and id ";
	String CIRCLE_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String NAME_TYPE_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String CUSTOMERCLASS_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String REVENUE_CATEGORY_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String PURPOSEGMC_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String CONNECTION_TYPE_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String GOVERNMENT_TYPE_RECORD_ERROR_MESSAGE = "Object having null value in Circle created and updated date and id ";
	String DIVISION_RECORD_ERROR_MESSAGE = "Object having null value in Division created and updated date and id ";
	String DEPARTMENT_RECORD_ERROR_MESSAGE = "Object having null value in Department created and updated date and id ";
	String PREMISE_TYPE_RECORD_ERROR_MESSAGE = "Object having null value in Premise Type created and updated date and id ";
	String LOCATION_CODE_RECORD_ERROR_MESSAGE = "Object having null value in Location Code created and updated date and id ";
	String SUBDIVISION_RECORD_ERROR_MESSAGE = "Object having null value in Subdivision created and updated date and id ";
	String DISTRICT_RECORD_ERROR_MESSAGE = "Object having null value in District created and updated date and id ";
	String BLOCK_RECORD_ERROR_MESSAGE = "Object having null value in  Block created and updated date and id ";
	String TEHSIL_RECORD_ERROR_MESSAGE = "Object having null value in Tehsil created and updated date and id ";
	String CITY_RECORD_ERROR_MESSAGE = "Object having null value in City created and updated date and id ";

	String NSC_RECORD_ERROR_MESSAGE = "Object having null value in NSC created and updated date and id ";
	String CONSUMER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumer record not found by consumer id OR is active is not true";
	String CONSUMER_RECORD_FETCH_BY_METER_ID_FAILED_MESSAGE = "Consumer record not found by meter id ";
	String PDC_CONSUMER_NETPAYABLE_AMOUNT_ZERO = "PDC consumer has no payable amount ";

	String PDC_CONSUMER_NOT_FOUND = "PDC consumer not found ! ";

	String CONSUMER_RECORD_FETCH_BY_METER_NO_FAILED_MESSAGE = "Consumer record not found by meter number ";

	String DEVELOPER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Developer record not found by developer id ";

	String ADJUSTMENT_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Adjust type record not found by adjustment type id ";

	String DEVELOPER_RECORD_ERROR_MESSAGE = "Object having null value in Developer created and updated date and id ";
	String HOLIDAY_MASTER_RECORD_ERROR_MESSAGE = "Object having null value in Designation created and updated date and id ";

	String DC_RECORD_ERROR_MESSAGE = "Object having null value in Distribution Center created and updated date and id ";
	String SUBSTATION_RECORD_ERROR_MESSAGE = "Object having null value in Sub-station created and updated date and id ";
	String FEEDER_RECORD_ERROR_MESSAGE = "Object having null value in Feeder created and updated date and id ";

	String RECORD_ALREADY_EXISTS_MESSAGE = "Already exits!";
	String NEW_RECORD_MESSAGE = "Unique Record found!";

	String HT_ADJUSTMENT_RECORD_ERROR_MESSAGE = "Object having null value in Adjustment process perform created and updated date and id ";
	String HT_ADJUSTMENT_CONSUMER_RECORD_FETCH_BY_ADJUSTMENT_TYPE_AND_CONSUMER_ID_FAILED_MESSAGE = "Consumer record not found by adjustment type & consumer id";
	String HT_ADJUSTMENT_CONSUMER_BILL_DETERMINANT_READ_ENDDATE_IS_NULL = "Consumer Bill Determinant read end date is null";
	String HT_ADJUSTMENT_CONSUMER_METER_INSTALLATION_DATE_IS_NULL = "Consumer meter installation date is null";

	String TARIFF_CATEGORY_RECORD_ERROR_MESSAGE = "Object having null value in Tariff Catgory created and updated date and id ";

	String TARIFF_SUB_CATEGORY_RECORD_ERROR_MESSAGE = "Object having null value in Tariff Sub-Catgory created and updated date and id ";

	String TARIFF_SCHEDULE_RECORD_ERROR_MESSAGE = "Object having null value in Tariff Schedule created and updated date and id ";
	String TARIFF_SCHEDULE_RECORD_FOR_NSC_ERROR_MESSAGE = "Tariff Schedule not mapped with this purpose";

	String TARIFF_CATEGORY_RECORD_FOR_NSC_ERROR_MESSAGE = "Tariff Category not mapped with this purpose";

	String TARIFF_SUB_CATEGORY_RECORD_FOR_NSC_ERROR_MESSAGE = "Tariff Sub-Category not mapped with this purpose";

	String PURPOSE_AND_REVENUE_CATEGORY_RECORD_ERROR_MESSAGE = "Object having null value in Purpose & Revenue Catgory created and updated date and id ";

	String CONSUMER_METER_DETAILS_ERROR_MESSAGE = "Object having null value in  Consumer meter details created and updated date and id ";

	String CONSUMER_AVG_ASSESSMENT_ERROR_MESSAGE = "Object having null value in  Consumer avg assessment consumption details created and updated date and id ";
	String CONSUMER_AVG_ASSESSMENT_FETCH_ERROR_MESSAGE = "Object having null value in  Consumer avg assessment consumption details fetch ";
	String CONSUMER_CAPTIVE_FETCH_ERROR_MESSAGE = "Object having null value in  Consumer Captive details fetch ";
	String CONSUMER_OPEN_ACCESS_FETCH_ERROR_MESSAGE = "Object having null value in  Consumer Open Access details fetch ";

	String BILL_DETERMINANT_MESSAGE = "Record of Bill Determinant Saved Successfully";
	String BILL_DETERMINANT_ERROR_MESSAGE = "No Record of Bill Determinant Saved from AMR";

	String BILL_CALCULATION_MESSAGE = "Bill Calculation Saved Successfully";
	String BILL_CALCULATION_ERROR_MESSAGE = "No Record of Bill Calculation Saved Successfully";
	String BILL_DETERMINANT_MANUAL_ENTRY_MESSAGE = "Record of Manual Entry Bill Determinant Saved Successfully";
	String BILL_DETERMINANT_MANUAL_ENTRY_ERROR_MESSAGE = "No Record of Manual Entry Bill Determinant Saved Successfully.";

	String VIEW_BILL_MESSAGE = "Consumer Bill Found Successfully.";
	String VIEW_BILL_ERROR_MESSAGE = "No Consumer Bill Found.";

	String CONSUMER_LOCATION_RECORD_ERROR_MESSAGE = "Object having null value in Consumer Location created and updated date and id ";
	String CONSUMER_LOCATION_RECORD_FETCH_CONSUMER_ID_ERROR_MESSAGE = "Object having null value in Consumer Location fetch by consumer id ";

	String CONSUMER_TARIFF_RECORD_ERROR_MESSAGE = "Object having null value in Consumer Tariff created and updated date and id ";

	// -------------------------------Exception Message

	// consumer data exist or not message
	String RECORD_FETCH_BY_METER_NO_MESSAGE = "Consumer Record exist with meter number";
	String RECORD_FETCH_BY_CONSUMER_ID_MESSAGE = "Consumer Record exist with consumer id";

	// DISCOM
	String DISCOM_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Discom record not found by discom id";
	String DISCOM_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Discom record found";

	// Region
	String REGION_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Region record not found by region id";
	String REGION_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Region record found";
	String REGION_RECORD_FETCH_ALL_BY_DISCOM_ID_FAILED_MESSAGE = "Region record not found by discom id";

	// Division
	String DIVISION_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Division record not found by division id";
	String DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Division record found";
	String DIVISION_RECORD_FETCH_ALL_BY_CIRCLE_ID_FAILED_MESSAGE = "Division record not found by circle id";
	String GROUP_RECORD_FETCH_ALL_BY_DIVISION_ID_FAILED_MESSAGE = "No Group found by division id";

	// Sub-Division
	String SUB_DIVISION_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Sub-Division record not found by sub-division id";
	String SUB_DIVISION_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Sub-Division record found";
	String SUB_DIVISION_RECORD_FETCH_ALL_BY_DIVISION_ID_FAILED_MESSAGE = "Sub-Division record not found by division id";

	// DC
	String DC_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Distribution Center record not found by dc id";
	String DC_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Distribution Center record found";
	String DC_RECORD_FETCH_ALL_BY_SUB_DIVISION_ID_FAILED_MESSAGE = "Distribution Center record not found by sub-division id";

	// Sub-Station
	String SUB_STATION_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Sub-Station record not found by sub-station id";
	String SUB_STATION_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Sub-Station record found";
	String SUB_STATION_RECORD_FETCH_ALL_BY_DC_ID_FAILED_MESSAGE = "Sub-Station record not found by dc id";

	// Feeder
	String FEEDER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Feeder record not found by feeder id";
	String FEEDER_RECORD_FETCH_ALL_FAILED_MESSAGE = "Record not found in database";
	String FEEDER_RECORD_FETCH_ALL_BY_SUB_STATION_ID_FAILED_MESSAGE = "Feeder record not found by sub-station id";

	// State
	String STATE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "State record not found by State id";
	String STATE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO State record found";

	// District
	String DISTRICT_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "District record not found by district id";
	String DISTRICT_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO District record found";
	String DISTRICT_RECORD_FETCH_ALL_BY_STATE_ID_FAILED_MESSAGE = "District record not found by state id";

	// ToDo
	String TO_DO_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "ToDo record not found by district id";
	String TO_DO_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO ToDo record found";
	String TO_DO_RECORD_FETCH_ALL_BY_STATE_ID_FAILED_MESSAGE = "ToDo record not found by state id";

	// Tehsil
	String TEHSIL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Tehsil record not found by district id";
	String TEHSIL_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Tehsil record found";
	String TEHSIL_RECORD_FETCH_ALL_BY_DISTRICT_ID_FAILED_MESSAGE = "Tehsil record not found by district id";

	// City
	String CITY_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "City record not found by city id";
	String CITY_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO City record found";
	String CITY_RECORD_FETCH_ALL_BY_TEHSIL_ID_FAILED_MESSAGE = "City record not found by tehsil id";

	// cda
	String CDA_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO disputed arrear record found";

	// Circle
	String CIRCLE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Circle record not found by circle id";
	String CIRCLE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Circle record found";
	String CIRCLE_RECORD_FETCH_ALL_BY_REGION_ID_FAILED_MESSAGE = "Circle record not found by region id";

	// CONSUMER_TDS_PUNCHING
	String CONSUMER_TDS_PUNCHING_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumer Tds Punching record not found ";
	String CONSUMER_TDS_PUNCHING_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Consumer Tds Punching  record found";
	// String CONSUMER_TDS_PUNCHING_RECORD_FETCH_ALL_BY_REGION_ID_FAILED_MESSAGE =
	// "Consumer Tds Punching record not found by region id";

	// Consumer Disputed Arrear

	String CONSUMER_DISPUTED_ARREAR_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumer Disputed Arrear record not found by Disputed Arrear id";

	// Connection type
	String CONNECTION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Connection Type record not found by connection type id";
	String CONNECTION_TYPE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Connection Type record found";

	// Tariff Schedule
	String TARIFF_SCHEDULE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Tariff Schedule record not found by tariff schedule id";
	String TARIFF_SCHEDULE_RECORD_FETCH_BY_CODE_FAILED_MESSAGE = "Tariff Schedule record not found by tariff schedule code";
	String TARIFF_SCHEDULE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Tariff Schedule record found";

	// Tariff Category
	String TARIFF_CATEGORY_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Tariff Category record not found by tariff category id";
	String TARIFF_CATEGORY_RECORD_FETCH_BY_TARIFF_CATEGORY_CODE_FAILED_MESSAGE = "Tariff Category record not found by tariff category code";
	String TARIFF_CATEGORY_RECORD_FETCH_BY_TARIFF_SCHEDULE_CODE_FAILED_MESSAGE = "Tariff Category record not found by tariff schedule code";
	String TARIFF_CATEGORY_RECORD_FETCH_ALL_FAILED_MESSAGE = "No Tariff Category record found";

	// Tariff Sub-Category
	String TARIFF_SUB_CATEGORY_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Tariff Sub-Category record not found by tariff sub-category id";
	String TARIFF_SUB_CATEGORY_RECORD_FETCH_BY_TARIFF_SUB_CATEGORY_CODE_FAILED_MESSAGE = "Tariff Sub-Category record not found by tariff sub-category code";
	String TARIFF_SUB_CATEGORY_RECORD_FETCH_BY_TARIFF_CATEGORY_CODE_FAILED_MESSAGE = "Tariff Sub-Category record not found by tariff category code";
	String TARIFF_SUB_CATEGORY_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Tariff Sub-Category record found";

	String ISSUE_IN_TARIFF_UPDATE = " issue in tariff update ";

	// Purpose & Revenue Category
//	String PURPOSE_AND_REVENUE_CATEGORY_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Purpose & Revenue Category record not found by id";
//	String PURPOSE_AND_REVENUE_CATEGORY_RECORD_ALL_FAILED_MESSAGE = "No Purpose & Revenue Category record found";
//	String PURPOSE_AND_REVENUE_CATEGORY_RECORD_FETCH_ALL_BY_TARIFF_SUB_CATEGORY_CODE_FAILED_MESSAGE = "Purpose & Revenue Category record not found by tariff sub-category code";

	// Purpose
	String PURPOSE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Purpose record not found by id";
	String PURPOSE_RECORD_ALL_FAILED_MESSAGE = "No Purpose record found";
	String PURPOSE_RECORD_FETCH_ALL_BY_TARIFF_SUB_CATEGORY_CODE_FAILED_MESSAGE = "Purpose record not found by tariff sub-category code";

	// Government Type
	String GOVT_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Government Type record not found by id";
	String GOVT_TYPE_RECORD_FETCH_ALL_FAILED_MESSAGE = "No Government Type record found";

	// Meter Type
	String METER_TYEP_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Meter type record not found by id";
	String METER_TYEP_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Meter type record found";

	// Configuration
	String CONFIGURATION_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Configuration record not found by id";
	String CONFIGURATION_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Configuration record found";

	// Meter Rent Type
	String METER_RENT_TYEP_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Meter Rent type record not found by id";
	String METER_RENT_TYEP_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Meter Rent type record found";

	// Meter Manufacturer
	String METER_MANUFACTURERE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Meter Manufacturer record not found by id";
	String METER_MANUFACTURERE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Meter Manufacturer record found";

	// Meter Model
	String METER_MODEL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Meter Model record not found by id";
	String METER_MODEL_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Meter Model record found";
	String METER_MODEL_RECORD_FETCH_BY_ID_METER_MANUFACTURERE_FAILED_MESSAGE = "Meter Model record not found by Meter Manufacturer id";

	// Meter CT Ratio
	String METER_CT_RATIO_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Meter ct ratio record not found by id";
	String METER_CT_RATIO_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Meter ct ratio record found";

	// Meter PT Ratio
	String METER_PT_RATIO_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Meter pt ratio record not found by id";
	String METER_PT_RATIO_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Meter pt ratio record found";

	// Current Transformer parameter
	String CURRENT_TRANSFORMER__PARAMETER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Current Transformer paramter record not found by id";
	String CURRENT_TRANSFORMER__PARAMETER_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Current Transformer paramter record found";

	// Current Transformer parameter value
	String CURRENT_TRANSFORMER__PARAMETER__VALUE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Current Transformer paramter value record not found by id";
	String CURRENT_TRANSFORMER__PARAMETER_VALUE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Current Transformer paramter value record found";
	String CURRENT_TRANSFORMER__PARAMETER_VALUE_BY_PARAMETER_ID_RECORD_FETCH_ALL_FAILED_MESSAGE = "Current Transformer paramter value record not found by Current Transformer paramter id";

	// Potential Transformer parameter
	String POTENTIAL_TRANSFORMER__PARAMETER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Potential Transformer paramter record not found by id";
	String POTENTIAL_TRANSFORMER__PARAMETER_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Potential Transformer paramter record found";

	// Potential Transformer parameter value
	String POTENTIAL_TRANSFORMER__PARAMETER__VALUE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Potential Transformer paramter value record not found by id";
	String POTENTIAL_TRANSFORMER__PARAMETER_VALUE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Potential Transformer paramter value record found";
	String POTENTIAL_TRANSFORMER__PARAMETER_VALUE_BY_PARAMETER_ID_RECORD_FETCH_ALL_FAILED_MESSAGE = "Potential Transformer paramter value record not found by Potential Transformer paramter id";

	// Current Transformer Manufacturer
	String CURRENT_TRANSFORMER__MANUFACTURERE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Current Transformer Manufacturer record not found by id";
	String CURRENT_TRANSFORMER__MANUFACTURERE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Current Transformer Manufacturer record found";

	// Current Transformer model
	String CURRENT_TRANSFORMER__MODEL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Current Transformer model record not found by id";
	String CURRENT_TRANSFORMER__MODEL_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Current Transformer model record found";
	String CURRENT_TRANSFORMER__MODEL_BY_MANUFACTURERE_ID_RECORD_FETCH_ALL_FAILED_MESSAGE = "Current Transformer model record not found by Current Transformer Manufacturer id";

	// Potential Transformer Manufacturer
	String POTENTIAL_TRANSFORMER__MANUFACTURERE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Potential Transformer Manufacturer record not found by id";
	String POTENTIAL_TRANSFORMER__MANUFACTURERE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Potential Transformer Manufacturer record found";

	// Potential Transformer model
	String POTENTIAL_TRANSFORMER__MODEL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Potential Transformer model record not found by id";
	String POTENTIAL_TRANSFORMER__MODEL_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Potential Transformer model record found";
	String POTENTIAL_TRANSFORMER__MODEL_BY_MANUFACTURERE_ID_RECORD_FETCH_ALL_FAILED_MESSAGE = "Potential Transformer model record not found by Potential Transformer Manufacturer id";

	// QuantityContractTypes
	String QUANTITY_CONTRACT_TYPES_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Quantity Contract Types record found";

	// SIMServiceProvider
	String SIM_SERVICE_PROVIDER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "SIM Service Provider record not found by id";
	String SIM_SERVICE_PROVIDER_RECORD_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO SIM Service Provider record found";

	// Modem Manufacturer
	String MODEM_MANUFACTURERE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Modem Manufacturer record not found by id";
	String MODEM_MANUFACTURERE_RECORD_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Modem Manufacturer record found";

	// Modem Model
	String MODEM_MODEL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Modem Model record not found by id";
	String MODEM_MODEL_RECORD_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Modem Model record found";
	String MODEM_MODEL_RECORD_FETCH_BY_MODEM_MANUFACTURERE_ID_FAILED_MESSAGE = "Modem Model record not found by Modem Manufacturer id";

	// Tariff
	String TARIFF_RECORD_FETCH_BY_ID_WITH_ACTIVE_STATUS_FAILED_MESSAGE = "No Tariff Record found with active status as true";

	// Load
	String LOAD_RECORD_FETCH_BY_ID_WITH_ACTIVE_STATUS_FAILED_MESSAGE = "No Load Record found with active status as true";

	// Meter
	String METER_RECORD_FETCH_BY_ID_WITH_ACTIVE_STATUS_FAILED_MESSAGE = "No Meter Record found with active status as true";

	// ME
	String ME_RECORD_FETCH_BY_ID_WITH_ACTIVE_STATUS_FAILED_MESSAGE = "No ME Record found with active status as true";

	// Login history
	String LOGIN_HISTORY_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Login history record not found by Login history id";
	String LOGIN_HISTORY_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Login history record found";
	String LOGIN_HISTORY_RECORD_FETCH_ALL_BY_USER_ID_FAILED_MESSAGE = "Login history record not found by user id";

	String CONSUMER_DISCONNECTION_RECORD_ERROR_MESSAGE = "Object having null value in Consumer Disconnection created and updated date and id ";

	String AREA_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Area Type not found..!!";

	String REVENUE_RECORD_FETCH_ALL_FAILED_MESSAGE = "Revenue Category code retrival failed";

	String BILLING_CYCLE_SCHEDULING_RECORD_ERROR_MESSAGE = "Object having null value in BillingCycleScheduling created and updated date and id ";

	String DUE_DATE_MESSAGE = "Due date for cash and cheque is generated Successfully";
	String DUE_DATE_ERROR_MESSAGE = "Due date for cash and cheque is not generated";
	String BILL_DATE_ERROR_MESSAGE = "Bill date must be greater than Meter Reading Date.";
	String BILL_DATE_ISNULL_DUE_DATE_ERROR_MESSAGE = "Bill Date should not be null to generate Due date for cash and cheque.";

	String BILL_SCHEDULE_GENERATED_MESSAGE = "Bill Schedule is generating  for next month.";
	String BILL_SCHEDULE_ALREADY_GENERATED_MESSAGE = "Bill Schedule is already generated.";
	String BILL_SCHEDULE_IS_NOT_FOUND_MESSAGE = "Bill Schedule is not found. ";
	String BILL_MONTH_SHOULD_NOT_BE_NULL_MESSAGE = "Bill month should not be null. ";
	String BILL_SCHEDULE_ERROR_MESSAGE = "Bill Schedule is not generated for this location. BillingCycleSchedulingInput.getMeterReadingDate() should not be null.";
	String BILL_SCHEDULE_FOUND_MESSAGE = "Bill Schedule is found for this location";
	String BILL_DETERMINANT_EXECUTED_FOR_THIS_BILL_SCHEDULE_MESSAGE = "Bill Determinant executed for this Bill Schedule.";
	String BILL_DETERMINANT_INPUT_ISSUE_MESSAGE = "Bill determinant input issue.";
	String AMR_NOT_FOUND_MESSAGE = "AMR is not found in bill determinant.";
	String AMR_NOT_FOUND_FOR_CONSUMER_MESSAGE = "AMR is not found for consumer.";
	String BILL_DATE_SHOULD_NOT_GREATER_THAN_CURRENT_DATE_MESSAGE = "Bill Date should not greater than current date.";

	String DISCOM_GROUP_IS_ALREADY_GENERATED_MESSAGE = "Discom Group is already generated.";
	String DISCOM_GROUP_SHOULD_NOT_BE_NULL_MESSAGE = "Discom Group not found.";

	// Eduty
	String EDUTY_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Eduty record not found by eduty id";
	String EDUTY_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Eduty record found";

	String MANUAL_ENTRY_ADJUSTMENT_NOT_FOUND = "Manual Entry Adjustment not found";

	String AVERAGE_ADJUSTMENT_NOT_FOUND = "Average Adjustment not found";

	String MANUAL_ENTRY_AVERAGE_ADJUSTMENT_NOT_FOUND = "Manual Entry and Average Adjustment not found";

	String MANUAL_ENTRY_AVERAGE_TMM_ADJUSTMENT_NOT_FOUND = "Manual Entry, Average or TMM Adjustment not found";

	String HT_ADJUSTMENT_NOT_FOUND = "HT Adjustment not found for this bill month";

	String BILL_DETERMINANT_MANUAL_ENTRY_AVERAGE_ADJUSTMENT_MESSAGE = "Record of Bill Determinant Saved Successfully from Manual Entry and Average";
	String BILL_DETERMINANT_MANUAL_ENTRY_AVERAGE_ADJUSTMENT_ERROR_MESSAGE = "No Record of Bill Determinant Saved from Manual Entry and Average";

	String BILL_DETERMINANT_MANUAL_ENTRY_AVERAGE_TMM_ADJUSTMENT_MESSAGE = "Bill Determinant Saved Successfully from Manual Entry, Average or TMM Adjustment";
	String BILL_DETERMINANT_MANUAL_ENTRY_AVERAGE_TMM_ADJUSTMENT_ERROR_MESSAGE = "No Bill Determinant Saved from Manual Entry, Average or TMM Adjustment";
	String BILL_DETERMINANT_PREVIOUS_BILL_MONTH_NOT_FOUND = "Previous Bill Month Bill Determinant Not Found";
	String BILL_MONTH_ISSUE_FOUND = "Bill Month Issue Found";

	String CONSUMER_LOCATION_IS_NOT_FOUND_MESSAGE = "Consumer Loction is not found. ";

	String MIS_HT_CONSUMER_RECORD_FETCH_ALL_FAILED_MESSAGE = "Mis ht consumer data not found.";

	String ASD_BILL_CONSUMER_RECORD_FETCH_ALL_FAILED_MESSAGE = "ASD Bill consumer Determinant list is null.";

	String RECORD_NOT_FOUND_BY_SAME_BILL_MONTH = "Record Retrived Not Found";

	String NO_RECORD_FOUND = "Data not found for given inputs";

	String PAYMENT_ALREADY_EXIST = "Payment already exits!";

	String SUCCESS_BD_NOT_FOUND = "Success Record of Bill Determinant not found for consumer.";

	// Disconnection Update Detail
	String ARREAR_NOT_FOUND_DISCONNECTION_UPDATE_DETAIL = "Arrear not found in disconnection Update Detail ";
	String MULTIPLE_DISCONNECTION_RECORD_FOUND_FOR_CONSUMER = "Multiple Disconnection Record  Found  for Consumer ";
	String MULTIPLE_FINAL_BILL_RECORD_FOUND_FOR_CONSUMER = "Multiple Final Bill Record  Found  for Consumer ";
	String MULTIPLE_BILL_DISCONNECTION_ENTRIES_IN_DISCONNECTION_UPDATE_DETAIL_CONSUMER = "Multiple bill or disconnection Entries in disconnection update detail for consumer ";
	String MULTIPLE_ENTRIES_FOR_CONSUMER_CONNECTION_DETAIL = "Multiple entries for consumer in Consumer connection detail ";
	String MULTIPLE_ENTRIES_FOR_CONSUMER_LOCATION_DETAIL = "Multiple entries for consumer location detail found for Consumer ";

	String MULTIPLE_ENTRIES_FOR_CONSUMER_LOAD_DETAIL = "Multiple entries for consumer load detail found for Consumer ";

	String MULTIPLE_ENTRIES_FOR_CONSUMER_METER_DETAIL = "Multiple entries for consumer meter detail found for Consumer ";

	String MULTIPLE_ENTRIES_FOR_CONSUMER_TARIFF_DETAIL = "Multiple entries for consumer tariff detail found for Consumer ";
	String TARIFF_RECORD_NOT_FOUND_FOR_CONSUMER = "Tariff record not found for  Consumer ";
	String LOCATION_NOT_FOUND_FOR_CONSUMER = "Location  not found for  Consumer ";

	String PAYMENT_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_ARREAR_AMOUNT = "Payment amount should not be greater than Arrear amount ! ";

	// cancel or approve payment and adjustments
	// adjustment messages
	String CANNOT_CANCEL_ADJUSTMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot cancel Adjustments of Previous  Bill ! ";
	String CANNOT_APPROVE_ADJUSTMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot approve Adjustments of Previous Bill ! ";
	String CANNOT_REJECT_ADJUSTMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot reject Adjustments of Previous Bill ! ";
	String JE_APPROVAL_REJECT_FUNCTIONALITY_NOT_EXIST = "JE approval reject functionality  not exist ! ";
	String ADJUSTMENT_AMOUNT_GREATER_THAN_SD_AMOUNT = "Error: Adjustment amount is greater than SD amount ! ";
	String ADJUSTMENT_AMOUNT_GREATER_THAN_ARREAR_AMOUNT = "Error: Adjustment amount is greater than Arrear amount ! ";

	String ARREAR_AMOUNT_IS_NEGATIVE_NEGATIVE_ADJUSTMENT_IS_INVALID = "Error: Arrear amount is Negative . Negative Adjustment is invalid  ! ";

	// unit adjustment message
	// payment messages
	String CANNOT_DELETE_ADJUSTMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot delete Unit Adjustments of Previous  Bill ! ";
	String CANNOT_ADD_ADJUSTMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot add  Adjustments of Previous  Bill ! ";

	String CANNOT_APPROVE_PAYMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot approve Payment of Previous  Bill ! ";
	String CANNOT_REJECT_PAYMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot reject Payment of Previous  Bill ! ";
	String CANNOT_CANCEL_PAYMENT_PREVIOUS_SCHEDULE_BILLS = "Cannot cancel Payment of Previous Bill ! ";

	// bill messages
	String BILL_MONTH_NOT_FOUND_IN_CONSUMER_BILL = "Bill month not found in Consumer Bill for Consumer  ! ";
	String FINAL_BILL_MONTH_NOT_FOUND_IN_CONSUMER_BILL = "Final Bill month not found in Consumer Bill for Consumer  ! ";

	// disconnection messages

	String BILL_MONTH_NOT_FOUND_IN_CONSUMER_DISCONNECTION = "Bill month not found in Consumer Disconnection for Consumer ! ";

	String GROUP_NUMBER_NOT_FOUND_FOR_CONSUMER = "Group Number not found for consumer  ! ";

	String TARIFF_SUB_CATEGORY_NOT_FOUND_FOR_CONSUMER = "Tariff sub category not found for consumer  ! ";
	String ERROR_IN_UPDATING_SECURITY_DEPOSIT = "Error: some error in updating security Deposit ! ";

	String FUTURE_DATES_NOT_ALLOWED_FOR_PAYMENTS = "Future date not allowed for payments ! ";

	String CANCEL_BILL_TO_ADD_ADJUSTMENT = "Cancel bill to add adjustment !";
	String CANCEL_BILL_TO_DELETE_ADJUSTMENT = "Cancel bill to delete adjustment !";

	String BILL_SCHEDULE_IS_NOT_FOUND_FOR_GROUP_MESSAGE = "Bill Schedule is not found for Group : ";

	/// MiscellaneousBillingRate messages
	String PLEASE_ADD_END_DATE_FOR_PREVIOUS_ENTRIES_OF_THIS_CHARGE = " First add end date for previous entries of this charge ! ";

	String THIS_CHARGE_ALREADY_EXIST_IN_DATE_RANGE = "This Charge  already exist in  date range !";

	String ERROR_IN_SAVING_MISCELLANEOUS_BILLING_RATE = "Error in saving Miscellaneous Billing Rate !";

	String MISCELLANEOUS_BILLING_RATE_DATA_NOT_FOUND = "Miscellaneous Billing Rate Data not found !";
	String END_DATE_SHOULD_NOT_BE_SMALLER_THAN_START_DATE = "End Date should not be smaller than start date  !";
	String START_DATE_SHOULD_NOT_BE_NULL = "Start date should not be null  !";
	String MISCELLANEOUS_BILLING_RAT_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Miscellaneous billing rate record found";
	String ERROR_FETCHING_PAYMENT_ADJUSTMENT = "Error fetching amount adjustment !";

	String ERROR_SAVING_AMOUNT_ADJUSTMENT = "Error in saving amount adjustment !";

	// tds punching

	String TDS_DETAIL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "No Tds details found by consumer id";
	String RECORD_ALREADY_PUNCHED_FOR_BILL_MONTH = "One Record already punched for same bill month: ";
	// ConsumerSubmeterDetials
	String CONSUMER_SUBMETER_DETAILS_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumer submeter details record not found by consumer submeter detials id";
	String CONSUMER_SUBMETER_DETAILS_RECORD_FETCH_ALL_BY_REGION_ID_FAILED_MESSAGE = "Consumer submeter details record not found by consumer id";

	// ConsumerDetailsByConsumerId ************Asif************
	String CONSUMER_RECORD_SEARCH_FETCH_BY_CONSUMER_ID_FAILED_MESSAGE = "Consumer record search not found by consumer id";
	String CONSUMER_RECORD_SEARCH_FETCHED_BY_CONSUMER_ID_MESSAGE = "All Record Retrived Successfully";

	// SubmeterManualLf
	String SUBMETERMANUAL_LF_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "SubmeterManualLf record not found by SubmeterManualLf id";
	String SUBMETERMANUAL_LF_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO SubmeterManualLf record found";
	String SUBMETERMANUAL_LF_RECORD_ALREADY_EXISTS_MESSAGE = "This Consumer Already Exits for the same bill month!";
	String SUBMETERMANUAL_LF_FETCH_BY_CONSUMER_ID_AND_BILLMONTH_FAILED_MESSAGE = "SubmeterManualLf record not found by consumer id and bill month";
	String SUBMETERMANUAL_LF_FETCH_BY_CONSUMER_ID_FAILED_MESSAGE = "Submeter Manual Lf record not found by consumer id";

	// CmHtBillHold
	String CMHTBILLHOLD_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "CmHtBillHold record not found by circle id";
	String CMHTBILLHOLD_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO CmHtBillHold record found";
	String CMHTBILLHOLD_RECORD_FETCH_ALL_BY_REGION_ID_FAILED_MESSAGE = "CmHtBillHold record not found by region id";

	/*
	 * Sandeep Namdeo Date:- 07 July, 2022 start
	 */
	// Fca Charge Master
	String FCA_CHARGE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Fca Charge record not found by fca id";
	String FCA_CHARGE_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Fca Charge record found";

	/*
	 * Sandeep Namdeo Date:- 07 July, 2022 start
	 */

	/*
	 * Sandeep Namdeo Date:- 26 July, 2022 start
	 */
	// Fca Charge Master
	String POTENTIA_TRANSFOR_MERMANUFACTURER_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Potentia Transfor Mermanufacturer record not found by fca id";
	String POTENTIA_TRANSFOR_MERMANUFACTURER_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Potentia Transfor Mermanufacturer record found";

	/*
	 * Sandeep Namdeo Date:- 26 July, 2022 start
	 */

	/*
	 * ******************** Sandeep, Date: - 28 - 07 - 2022 - Starts *****
	 *********************/
	String RECORD_IS_ALREADY_EXIST_MESSAGE = "This Record is already exist in Records !";
	String TODO_DETAIL_RECORD_FETCH_BY_SEARCH_PERAMETER_FAILED_MESSAGE = "No Todo details found by search perameter";
	/*
	 * ******************** Sandeep, Date: - 28 - 07 - 2022 - Ends ******
	 ********************/

	/*
	 * ******************** Sandeep, Date: - 28 - 07 - 2022 - Starts *****
	 *********************/
	// Consumer Generator Detail
	String CONSUMER_GENERATOR_DETAIL_RECORD_IS_ALREADY_EXIST_MESSAGE = "This Record is already exist in Records !";
	String CONSUMER_GENERATOR_DETAIL_RECORD_FETCH_BY_SEARCH_PERAMETER_FAILED_MESSAGE = "No Consumer Generator Detail found by search perameter";
	String CONSUMER_GENERATOR_DETAIL_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumer Generator Detail record not found by consumerGeneratorDetail id";
	String CONSUMER_GENERATOR_DETAIL_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Consumer Generator Detail record found";
	/*
	 * ******************** Sandeep, Date: - 28 - 07 - 2022 - Ends ******
	 ********************/

	// Consumer Type
	/*
	 * ******************** Sandeep, Date: - 16 - 09 - 2022 - Starts *****
	 *********************/
	String CONSUMER_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumer Type record not found by Consumer Type Id";
	String APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Application Type record not found by Application Type Id";
	String CONSUMER_APPLICATION_DETAIL_RECORDS_WITH_PAGINATION_FAILED_MESSAGE = "No Consumer application details with pagination";
	String WORK_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Work Type record not found by Work Type Id";
	/*
	 * ******************** Sandeep, Date: - 16 - 09 - 2022 - Ends ******
	 ********************/

	String INVALID_PHONE_NUMBER = "Invalid CONSUMER PHONE NUMBER";

//	******************gourav, 22-09-2022, starts**************************
	// Document Type
	String DOCUMENT_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Document record not found by id";
	String DOCUMENT_RECORD_FETCH_ALL_FAILED_MESSAGE = "NO Document record found";
	String DOCUMENT_RECORD_FETCH_ALL_BY_GOVT_ID_FAILED_MESSAGE = "Document record found by Government type id";
//		******************gourav, 22-09-2022, ends**************************	

	String CONSUMER_DETAILS_NOT_FOUND = "Consumer not found !!!";
	
//	charitra start 
	String CONSUMER_APPLICATION_NOT_FOUND = "Consumer application not found";
	String DUPLICATE_TRANSACTION_MESSAGE = "duplicate transaction message";
//	charitra end here
	
	// Demand Details
		String CONSUMERS_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Consumers record not found";
		
	String 	DEMAND_RECORD_FETCH_BY_ID_FAILED_MESSAGE="Demand Record Not found";
		
		
//		charitra start 
		String PAYMENT_ALREADY_DONE="payment already done";
//		charitra end here
		
		String CONTRACTOR_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE = "Contractor record not found by Work Type Id";
		
		
}

