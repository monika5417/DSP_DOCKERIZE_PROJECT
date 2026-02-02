package com.mpcz.deposit_scheme.backend.api.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mpcz.deposit_scheme.backend.api.jwt.security.filter.AuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private AuthenticationTokenFilter authenticationTokenFilter;

	private static final String[] PUBLIC_MATCHERS = { "/images/*", "/resources/static/*", "/v2/api-docs",
			"/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**",
			"/CorrectionOfDuplicateApplicationNumber/updateApplicationNumber", "/ReturnMaterialDataController/save",
			"/api/consumer/signup", "/api/user/erp/get_erp_estimate_doc/{applicationNo}", "/api/user/erp/downloadpdf",
			"/api/user/erp/downloadpdf1", "/api/consumer/login/verification",
			"/api/consumer/qc-portal/conforbid/{applicationNo}", "/api/consumer/login/verificationCaptcha",
			"/api/consumer/login/getCaptcha", "/api/consumer/login/changePassword", "/api/consumer/otp/getOtp/**",
			"/api/consumer/otp/getLoginOtp/**", "/api/consumer/otp/verifyLoginOtp/**",
			"/api/consumer/login/forgotPassword/**", "/api/consumer-acc/signup", "/api/user/signup",
			"/api/user/login/verification", "/api/user/login/verificationCaptcha", "/api/user/login/getCaptcha",
			"/api/user/login/changePassword", "/api/user/otp/getOtp/**", "/api/user/otp/getLoginOtp/**",
			"/api/user/otp/verifyLoginOtp/**", "/api/user/login/forgotPassword/**", "/api/user/work-status/get",
			"/api/user/work-status/get/{ConsumerApplicationNumber}", "/api/user/region/getAll", "/mis_details/mis",
			"/api/user/consumer-application/getAllPaginate/", "/ReSampling/post-resample-data",
			"/ReSampling/post-resample-data1", "/ReSampling/safling-data-by-autenotication-ID/{authenticationId}",
			"/ReSampling/safling-data-by-autenotication-ID1/{authenticationId}/{userID}",
			"/ReSampling/safling-data-by-autenotication-ID1/{authenticationId}/{userID}/{circleId}/{divisionId}",
//			"/ReSampling/getSampleDataForReSamplingTable/{flagNo}",
			"/oyt14/save",
			"/oyt14//getbyapplication-no/{applicationNo}",
			"/ReSampling/getSampleDataForReSamplingTable/{flagNo}/{circleId}/{divisionId}/{regionId}",
			"/ReSampling/getSampleDataForConsuemrAppliationDetailTable/{flagNo}", 
			"/ReSampling/getSampleDataForConsuemrAppliationDetailTable1/{dcid}",
			"/nistha-lab/testing-report-upload",
			"/GatePassChallan/save", "/ReSampling/uploadPhotoDtr",
			"/ReSampling/getSampleDataForConsuemrAppliationDetailTable2",
			"/ReSampling/getSampleDataForConsuemrAppliationDetailTableByConsumerApplicaionNo/{consumerApplicaionNO}",
//			charitra
			"/api/user/dtr/25kvadtr/{dtrQuentity}",
			"/api/consumer/consumer-application/changeMobileNOByApplicationisGOV1",
			"/api/consumer/consumer-application/Vijlance",
			"/api/consumer/consumer-application/changeMobileNOByApplicationisGOV/{oldMobileNo}/{newMobileNo}",
			"/api/consumer/consumer-application/checkConsumerHaveAnyGovermentApplicationOrNot/{mobileNo}",
			"/api/user/login/getAll",
			"/api/consumer/consumer-application/jePutErpNumberAndChangeApplicationStatusAndSchemeCode/{consumerAppNo}/{erpNumber}/{SchemeCode}",
			"/api/consumer/consumer-application/changeConsumerMobileNumber1",
			"/ReSampling/saveDtrLiftingDAte/{authenticationId}/{dtrLiftingDate}",
			"/ReSampling/saveDtrLiftingDAte1/{authenticationId}/{dtrLiftingDate}",
			"/api/consumer/qc-portal/listSelectContractor/{consumerapplicationNumber}",
			"/api/consumer/consumer-application/sortContratoreListByKvLine",
			"/GatePassChallan/saveReversiveGetPassPdfaAndtrfUploadFile", "/GatePassChallan/nistha-lab-TA-submit-data",
			"/ReSampling/getReSampleData/{consumerApplicationNo}", "/ReSampling/getDtrValue/{consumerApplicationNo}",
			"/api/refundAmount/saveDataForRefundMaterialZeroOneCase", "/api/refundAmount/upload-pdf-for-refund-data",
			"/GatePassChallan/saveGetPassPdfaAndtrfUploadFile",
			"/GatePassChallan/getPassChallanDetailsByconsumerApplicationNumber/{consuemrApplicationNumber}",
			"/api/adminChangable/user/updataAddUserTable", "/api/consumer/bill-desk/Billdesk_payment_retrive_api",
//			"/api/consumer/bill-desk/Billdesk_payment_refundDemandAmount",
			"/api/consumer/dc/get/{id}", "/GatePassChallan/saveGetPassPdf",
			"/api/user/estimatedependOonthischeckBox/savedataerpEstimateTable",
			"/api/user/erp/getamount/{consumerApplicationid}",
			"/api/consumer/consumer-application/consumerApplicationDetailsBasedOnCityCricleBhopalAndGwaliorOANDM/{circleid}",
			"/api/consumer/consumer-application/consumerApplicationDetailsBasedOnCityCricleBhopalAndGwaliorBasedONLtNullValueHTM/{circleid}",
			"/api/consumer/bill-desk/Billdesk_payment_retrive_apis", "/api/updateUser/password",
			"/procedure/sendDataToErpProject", "/api/adminChangable/user/getUserDcAndDivion/{userId}",
			"/api/adminChangable/user/userDeActiveAccount/{oldLoginId}/{oneactive2Deactive}", "/procedure/dataToErp",
			"/api/consumer/consumer-application/getByDcId/{dcId}",
			"/api/adminChangable/user/changeConsumerMobileNo/{oldLoginId}/{newMobileNo}",
			"/api/user/erp/getoytMaterialdataByConsumerapplicationNumber/{consumerApplicationNo}",
			"/api/consumer/consumer-application/getConsumerApplicationDetailsByApplicationNumber/{consumerApplicationNo}",
			// end
			"/ReSampling/getSampleDataForConsuemrAppliationDetailTable/{flagNo}/{circleid}",
			"/ReSampling/getSampleDataForConsuemrAppliationDetailTableNotNsc/{flagNo}",
			"/api/user/erp/erpEstimateAmountDataGetByerp/{consumerApplicationNo}", "/GatePassChallan/save",
			"/ReSampling/getsempleSelectedAndTestingDoneData", "/ReSampling/getselectTestingData",
			"/api/user/erp//getoytMaterial/{consumerApplicationNo}",
			"/ReSampling/safling-data-by-Vendor-table-autenotication-ID/{authenticationId}", "/user-tkoen/token",
			"/api/consumer/payment/response_payment", "/api/consumer/payment/sendReciept",
			"/api/user/qc-portal/conforbid/{applicationNo}", "/api/master/**",
			"/api/consumer/payment/initial_pay_success", "/api/user/geo-location/add", "/api/user/save-work-order/save",
			"/api/user/nsc-data/findall", "/api/user/nsc-data/nsc/{userid}", "/api/user/nsc-data/nscdata/{nscid}",
			"/api/user/nsc-data/nscdatadetails/{dccode}", "/api/user/nsc-data/add", "/api/user/work-status/save",
			"/api/user/work-status/add", "/api/user/work-status/update",
			"/api/user/save-work-order/workorders/{applicationNo}", "/api/user/nsc-data/save/nscdata",
			"/api/user/nsc-data/nscdetail/{consumerno}", " /api/user/savejason/get", "/api/user/savejason/get",
			"/api/user/work-status/update", "/fileDetails/getFileDetailsByConsumerApplicationId/{ApplicationId}",
			"/api/user/document-type/add", "/api/user/consumer-type/add", "/api/user/application-type/add",
			"/api/user/application-type/get/{id}", "/api/user/application-type/getAll", "/api/qc-portal/**",
			"/api/user/work-order/generateWorkOderNo", "/deposit_scheme/workorder/save",
			"/api/consumer/bill-desk/vidhitSir/{id}", "/api/urjas/**",
			"/api/consumer/bill-desk/billPaymentsProcess/{id}",
			"/api/consumer/dc/findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId/{id}",
			"/api/consumer/signup", "/api/consumer/work-nature/add", "/api/consumer/work-nature/getAll",
			"/api/consumer/work-nature/get/{id}", "/api/master/getAllNatureOfWork",
			"/api/user/dc/getAllDcByDistrict/{id}", "/api/master/getAllDistrict", "/api/master/getAllLoadRequested",
			"/api/consumer/consumer-application/add", "/api/consumer/district/get/{id}",
			"/api/consumer/supervision-consumer-estimation/getAll", "/api/user/geo-location/getAll",
			"/api/user/feeder/get/{id}", "/api/user/{id}", "/api/user/consumer-application/getUserOtp/{userId}",
			"/api/consumer/otp/getOtp", "api/consumer/jasper-pdf/download/{consumer_Id}", "/api/user/substation/getAll",
			"/api/user/substation/get/{id}", "/api/user/substation/getAllSubstaionByDc/{id}",
			"/api/user/feeder/getAllFeederBySubstation/{id}", "/api/consumer/feeder/jasper-pdf/download/{erp_no}",
			"/api/consumer/getAllConsumerDetail", "/api/consumer/qc-portal/acceptanceOfConsumer",
			"/api/user/nsc-data/add", "/api/user/getsp/{circleId}", "/api/user/dgm-htc", "/api/user/dgm-htc/addDtr",
			"/api/user/dgm-htc/addPtr", "/api/user/dgm-htc/addLt", "/api/user/dgm-htc/add11Kv",
			"/api/user/dgm-htc/add33Kv", "/api/user/dgm-htc/add132Kv",
			"/api/user/erp/oytMaterialCharges1/{erpNo}/{consumerApplicationNo}",
			"/api/user/erp/getErpEstimate/{erpNumber}/{consumerAppId}",
			"/api/user/erp/getErpEstimateAmount/{erpNumber}/{consumerAppId}", "/api/user/erp/update/{consumerAppId}",
			"/api/user/erp/erpEstimateCalculations/{consumerAppId}",
			"/api/consumer/qc-portal/getQcConsumerbid/{consumerAppNo}", "/api/consumer/qc-portal/saveForQcPortal",
			"/api/consumer/jasper-pdf/download", "/api/user/login/changeUserStaus/{id}",
			"/api/user/jasper-pdf/download/{erp_no}", "/api/consumer/jasper-pdf/download/{erp_no}",
			"/api/user/otp/getLoginOtp", "/api/consumer/bill-desk-response/save", "/api/user/handoverToDgmHtc/add",
			"/api/user/change-status/get",
			"/api/consumer/consumer-application/jePutErpNumberAndChangeApplicationStatus/{consumerAppNo}/{erpNumber}",
			"/api/user/user-type/signup", "/api/master/getAllWorkType", "/api/user/work-type/add",
			"/api/user/geo-locaion/add", "/api/user/vendor/getAllConsumerApplicationByVendor/{id}",
			"/api/user/vendor/addConsumerApplicationDetails", "/api/user/work-status/conforbids-get/{applicationNo}",
			"/api/consumer/bill-desk/billPaymentProcess", "/api/consumer/bill-desk/save__payment_response",
			"/api/consumer/bill-desk/sendReciept", "/api/user/work-status/conforbid/{applicationNo}",
			"/api/consumer/bill-desk-response/sendReciept", "/ReSampling/getSampleData", "/api/user/dtr/add",
			"/api/user/ptr/add", "/api/user/ht/add", "/api/user/lt/add", "/api/user/lt_11/add",
			"/api/user/dtr/get/{id}", "/api/user/ptr/get/{id}", "/api/user/ht/get/{id}", "/api/user/lt/get/{id}",
			"/api/user/lt_11/get/{id}", "/api/user/change-status/add", "/api/user/change-status/get/{id}",
			"/api/user/login/signup", "/ReSampling/getReSamplingDataByContractorAuthentication/{authenticationId}",
			"/api/consumer/consumer-application/getReSamplingDataByContractorAuthentication/{authenticationId}",
			"/api/consumer/bill-desk/fees_genreted_recipt/{consumerApplicationNumber}/{slipGenretatedId}",
			"/api/consumer/bill-desk/fees_genreted_recipt/{consumerApplicationNumber}/{slipGenretatedId}/sendReciept",
			"/api/consumer/consumer-application/rejectApplicationByGM", "/api/consumer/consumer-application/jeLoad",
			"/api/consumer/consumer-application/getChalan",
			"/api/consumer/consumer-application/getConsumerApplicationDetailsBySamagraId/{id}",
			"/api/consumer/consumer-application/consumerApplicationUpdateByMKMY",
			"/api/ssp/saveivrs/{ivrs}/{sspPortalApplicautionNuber}",
			"/api/consumer/consumer-application/getSampleData1", "/api/consumer/login/consumer_login",
			"/api/user/login/user_login", "/samagra/SamgraDataBySamgraId", "/samagra/genrateToken",
			"/api/consumer/consumer-application/consumerApplicationUpdateByMKMY",
			"/api/consumer/consumer-application/getConsumerApplicationDetailsByDistrictId/{id}",
			"/api/consumer/consumer-application/getConsumerApplicationDetailsByConsumerApplicationNo/{consumerApplicationNo}",
			"/api/consumer/consumer-application/consumerApplicationUpdateByApplication",
			"/api/consumer/dc/getAllDcByDistrict/{id}",
			"/api/consumer/consumer-application/getAllConsumerApplicationByNatureOfwork8/{dcCode}",

			"/api/user/login/getUserById/{userId}", "/api/user/work-status/updatee",
			"/api/consumer/qc-portal/contractor_selection_old_dsp_save",
			"/api/consumer/login/getConsumerByMobileNo/{mobileNo}", "/api/updateConsumer/password",
			"/api/consumer/bill-desk/save_payment_response", "/api/master/getAllGovDepartment",

			"/api/consumer/consumer-application/aaaaaa", "/scheduler/demandFees", "/scheduler/RegistrationFees",
			"/scheduler/RavindraSendDataForContractorSelectinon",
			"/ReturnMaterialDataController/saveReturnMaterialData1", "/ReSampling/getSampleData/{flagNo}",
			"/api/consumer/consumer-application/sendDataMeterReadervigilanceGet1/{ByNgbDcCode}",
			"/api/consumer/consumer-application/sendDataMeterReadervigilanceGet2/{ByNgbDcCode}",
			"/api/consumer/consumer-application/sendDataMeterReaderVijlance",

			"/api/consumer/consumer-application/punchNamaDoneApi/{consumerApplicatonNO}/{punchNamaReferemceMo}/{punchNamaReferenceDate}",
			"/api/consumer/consumer-application/changCalulationForOyt/{applicationNumber}/{OytTepOrParma}",
			"/api/user/erp/saveForSupervisionOnlyButNotForOyt/{erpNo}/{consumerApplicationNo}",
			// Monika API Start

			"/api/consumer/consumer-application/rejectProposalApplication",
			"/api/consumer/consumer-application/getConsumerApplicationNumber/{consumerApplicationNumber}",
			"/vendor_add_materials/addMaterial", "/api/user/application-status/getAll",
			"/api/user/erp/update/{consumerAppId}/{scheduleSurveyDate}/{scheduleSurveyTime}/{surveyorName}/{surveyorMobile}",
			"/api/consumer/dc/getAllDcBySubdivision/{id}",
			"/api/consumer/consumer-application/oytSamgraDataApplication",
			"/api/consumer/consumer-application/jeReturnAmount", "/api/mmky/saveMMKYParentChild",
			"/mis_details/bill_desk_mis/{toDate}/{fromDate}", "/mis_details/bill_desk/{toDate}/{fromDate}",
			"/api/consumer/consumer-application/jeReturnAmount", "/api/mmky/saveMMKYParentChild",
			"/mis_details/bill_desk_mis/{toDate}/{fromDate}", "/mis_details/bill_desk/{toDate}/{fromDate}",
			"/api/consumer/consumer-application/jeReturnAmount", "/api/mmky/saveMMKYParentChild",
			"/mis_details/bill_desk_mis/{toDate}/{fromDate}", "/mis_details/bill_desk/{toDate}/{fromDate}",
			"/api/mmky/getamount/{consumerApplicationid}",
			"/api/consumer/consumer-application/getMkmyConsumerDetailsByApplicationNo/{consumerApplicationNo}",
			"/api/consumer/consumer-application/updateMkmyConsumerDetails",
			"/api/consumer/consumer-application/stringToJsonConvert",
			"/api/mmky/getMkmyAmountByConsumerApplicationNo/{consumerAppNo}",
			"/api/user/erp/getconsumerApplicatioNo/{consumerApplicatioNo}", "/api/mmky/deActiveAppAfter30Days",
			"/api/user/erp/getconsumerApplicatioNo/{consumerApplicatioNo}", "/api/mmky/deActiveAppAfter30Days",
			"/api/user/erp/getconsumerApplicatioNo/{consumerApplicatioNo}", "/api/mmky/deActiveAppAfter30Days",
			"/api/consumer/consumer-application/getDcByDistrictId/{districtId}",
			"/api/consumer/bill-desk/getPaymentDetailsByApplicationNo/{consumerApplicationNo}",
			"/api/consumer/bill-desk/getPaymentDetailsByApplicationNo1/{consumerApplicationNo}",
			"/api/consumer/consumer-application/getMkmySamagraListDataByApplicationNo/{consumerApplicationNo}",
			"/api/consumer/qc-portal/postDataProd/{consApp}", "/api/consumer/qc-portal/postDataUat/{consApp}",
			"/mis_details/getCraData/{date}", "/api/mmky/mkmyMsgAfter25Days",
			"/api/consumer/qc-portal/postDataProd/{consApp}", "/api/consumer/qc-portal/postDataUat/{consApp}",
			"/mis_details/getCraData/{date}", "/api/mmky/mkmyMsgAfter25Days",
			"/api/consumer/qc-portal/postDataProd/{consApp}", "/api/consumer/qc-portal/postDataUat/{consApp}",
			"/mis_details/getCraData/{date}", "/api/mmky/mkmyMsgAfter25Days",
			"/ErpRev/getConsumerApplication/{consumerApp}",
			"/api/consumer/consumer-application/rejectContractorApplicationByDgmHtc",
			"/api/dgm_stc_remark/saveDgmStcRemark", "/vendor_add_materials/getAllVendorMaterial/{consAppNo}",
			"/vendor_add_materials/getAllVendorMaterial1/{consAppNo}", "/Proposed-Format-Non-Oyt/save",
			"/api/dgm_stc_remark/dgmStcRemark", "/api/contractor_remark/addContractorRemark",
			"/api/dgm_stc_remark/saveDgmStcRemark", "/vendor_add_materials/getAllVendorMaterial/{consAppNo}",
			"/api/dgm_stc_remark/dgmStcRemark", "/api/contractor_remark/addContractorRemark",
			"/api/dgm_stc_remark/saveDgmStcRemark", "/vendor_add_materials/getAllVendorMaterial/{consAppNo}",
			"/api/dgm_stc_remark/dgmStcRemark", "/api/contractor_remark/addContractorRemark",
			"/api/contractor_remark/getAllConRemark/{consumerAppNo}",
			"/api/dgm_stc_remark/getAllDgmStcRemark/{consumerAppNo}",
			"/api/consumer/consumer-application/deactivateApplication/{consumerApp}",
			"/api/consumer/consumer-application/getApplicationDetail", "/api/connection_praday/saveConnectionPraday",
			"/api/connection_praday/getConnectionByIvrs/{ivrs}", "/api/manualpayments/getAllManualPayments",
			"/api/manualpayments/getPaymentByApplication/{consumerAppNo}",
			"/api/connection_praday/getConnectionDetailsByConsAppNo/{consAppNo}",
			"/api/manualpayments/createManualPayment", "/api/consumer/qc-portal/getQcPortalData",
			"/api/consumer/consumer-application/getConsumerDetailsfullDetailsByApplicationNo1/{consumerApplicationNo}",
			"/api/consumer/consumer-application/getConsumerDetailsForTrackApplication",
			"/api/consumer/bill-desk/getAllOytPaymentDatePendingApplication",
			"/scheduler/RavindraSendDataForContractorSelectinon/{date}", "/ErpRev/erpReviseFileSave",
			"/api/consumer/consumer-application/saveMkmyDtrForIndividual",
			"/api/manualpayments/getManualPaymentByBillRefNo/{billRefNo}", "/api/consumer/qc-portal/postDataProdAll",
			"/api/consumer/consumer-application/saveOAndMLoadDetailsForRevise",
			"/api/consumer/consumer-application/revertAppToDemandNote/{consumerAppNo}",
			"/scheduler/RegistrationFeesNotDoneRevertApp",
//			"/api/consumer/consumer-application/updateJERejectedDocument",
			"/api/tds/save", "/api/tds/tdsDemandCalculation/{consumerAppNo}", "/api/getErpData/{erpNo}", "/api/main",
			"/api/consumer/consumer-application/changeNatureOfWorkOfLegalAndIllegal/{consumerApplicationNo}/{newNatureOfWorkId}/{schemeTypeId}",
			"/api/erpEstimate", "/ErpRev/negativeRev/{erpNo}/{applicationNo}/{value}",
			"/api/consumer/consumer-application/changeSchemeType/{consumerApplicationNo}/{schemeTypeId}",
			"/NgbStagingData/getNgbData/{consumerAppNo}", "/printPaymentDetails/{consumerApplicationNo}",
			"/NgbStagingData/saveNgbStagingData", "/NgbStagingData/getToken",
			"/NgbStagingData/fetchDataByLocationCode/{locationCode}", "/NgbStagingData/fetchDataWithGRNo/{GrNo}",
			"/NgbStagingData/sendDataToNgb", "/NgbStagingData/getFeederDataWithIsamparkCode/{iSamparkCode}",
			"/NgbStagingData/getDataWithFeederCode/{feederCode}",
			"/api/user/erp/getErpEstimateAmount/{erpNumber}/{consumerAppId}/{savedataId}",
			"/api/user/erp/newErpResponse/{erpNo}", "/NgbStagingData/getDataWithLocationCode",
			"/NgbStagingData/getConsumerIdByNgbId",
//			 "/api/adminChangable/user/changeNatureOfWorkOfLegalAndIllegal/{consumerApplicationNo}/{newNatureOfWorkId}/{schemeTypeId}",
//			 "/api/adminChangable/user/getConsumerByLoginId/{loginId}",
//			 "/api/adminChangable/user/changeConsumerMobileNo/{oldLoginId}/{newMobileNo}",
//			 "/api/adminChangable/user/changeConsumerNameAndAddress/{consumerApplicationNo}/{newConsumerName}/{newAddress}",
//			 "/api/adminChangable/user/changeIndividualOrGroup/{consumerApplicationNo}/{individualOrGroupId}",
//			"/api/adminChangable/user/changeConsumerNameAndAddressByConsumerMobileNo/{consumerMobileNo}/{newConsumerName}/{newAddress}",
			"/api/adminChangable/user/changeApplicationStatus",
			"/api/consumer/consumer-application/saveContractorExpectedDates", "/scheduler/saveContractorExpectedDates",
			"/api/consumer/consumer-application/updateConsumerApplicationDetailToChangeScheme/{id}",
			"/api/consumer/consumer-application/revertApplcaicationForNatureOfWork/{consumerApplicationNo}/{natrueOfWorkChange}",
//		"/api/adminChangable/user/changeSchemeType/{consumerApplicationNo}/{schemeTypeId}",
//		"/api/adminChangable/user/saveJeRemarkForApplicationUpdation",
			"/api/consumer/consumer-application/getAllJeRemarkForUpdation/{consumerAppNo}",
			"/NgbStagingData/getNgbDataForSSP/{consumerAppNo}", "/NgbStagingData/saveDataToSsp",
			"/NgbStagingData/putConsumerNoFromSSP", "/NgbStagingData/getGroupNoByLocationCode/{locationCode}",
			"/NgbStagingData/getDiaryNoByGroupNo/{groupNo}",
			"/NgbStagingData/getNgbDataByApplicationNo/{consumerAppNo}", "/scheduler/applicationStatusUpdate",
			"/api/consumer/consumer-application/saveApplicantFeedback",
			"/api/consumer/consumer-application/saveApplicantWorkIssues",
			"/api/consumer/consumer-application/getAllApplicantFeedback/{consumerApplicationNo}",
			"/api/consumer/consumer-application/getAllApplicantWorkIssues/{consumerApplicationNo}",
			"/api/consumer/bill-desk/saveBillRequest", "/api/consumer/bill-desk/settlementDate",
			"/api/user/erp/getErpData/{consumerApplicationNo}/{value}",
			"/api/user/refund_process/saveRefundProcessData",
			"/api/consumer/consumer-application/user/reselectionOfContractor",
			"/api/user/refund_process/refundApplicationByGM",
			"/api/user/refund_process/getRefundProcessDataByApplicationNo/{consumerApplicationNo}",
			"/api/user/erp/checkDemandOrReviseDemandRefund/{consumerApplicationNo}", "/api/mmky/sendSms",
			"/api/user/refund_process/processFinanceMdResponse/{consumerApplicationNo}/{amountRefundedOrNot}",
			"/api/user/pagination/getAllUserWiseConsumerApplicationPagination1",
			"/poseMachine/getDemandFeesPaymentDetails/{consumerApplicationNo}",
			"/api/consumer/consumer-application/getBilldeskDemandPaymentExistOrNot/{consumerApplicationNo}",
			"/api/ssp/sspSignUp", "/api/user/geo-location/getCaptcheredDataGstNumber/{mobileNumber}",
			"/api/user/sendDataToSSp", "/mis_details/getAllDataFromView",
//			  "/api/user/pagination/getAllApplicationByStatusCount",
//			  "/api/user/pagination/getApplicationData/{applicationStatusId}",
			"/api/user/pagination/getAllDataFromViewViaAccessLevel/{userId}",
			"/api/user/pagination/getAllDataFromViewViaAccessLevelAndDc/{userId}/{dcId}/{divisionId}",
			"/api/user/pagination/getAllApplicationByApplicationStatusByUserId/{applicationStatusId}/{userId}",
//			  "/api/adminChangable/user/updateMKMYDocument",
//			  "/api/refundAmount/user/saveRefundDetails",
//			  "/api/refundAmount/user/getRefundApplication/{consumerApplicationNo}",
//			  "/api/refundAmount/user/getAllData",
//			  "/api/refundAmount/consumer/saveApplicationCancelletion",
//			  "/api/refundAmount/user/dgmApprovalForRefund",
//			  "/api/refundAmount/user/gmApprovalForRefund",
			"/api/refundAmount/getAllApplicationByConsumerId/{consumerId}",
			"/api/refundAmount/getAllJeReturnAmountApp/{consumerId}", "/api/refundAmount/user/dgmStcRefundApproval",
			"/api/refundAmount/getPaymentRefundDetails/{consumerApplicationNo}/{value}",
			"/api/refundAmount/consumer/saveReturnAmntApplication",
			"/api/refundAmount/getSchemeAndNatureByApplicationNo/{consumerApplicationNo}",
			"/api/consumer/consumer-application/consumerApplicationSearch",
			"/api/connection_praday/saveIvrsConnectionByJE",
			"/api/consumer/qc-portal/oytApplicationGreaterFiveOnSameContractor",
			"/scheduler/getAllOytPaymentDatePendingApplication", "/api/user/saveContractorActualWorkCompletionDates",
			"/api/user/dbQueryCheck", "/api/user/save-work-order/uploadWorkOrderFile",
			"/api/user/save-work-order/uploadDemandNoteFile",
			"/api/consumer/qc-portal/oytContractorSelectionByDGM/{consumerApplicationNo}/{discomUserId}/{userType}/{contractorUserId}",
			"/api/consumer_account_deatils/saveConsumerAccountDetails",
			"/api/consumer_account_deatils/consumer/getConsumerApplicationDuplicateRefundDetails/{consumerApplicationNo}",
			"/api/ssp/sendDataToSspAfterWorkOrder/{consumerApplicationNo}/{applicationStatusId}",
			"/api/consumer_account_deatils/getConsumerAccountDetails/{consumerApplicationNo}",
			"/api/consumer_account_deatils/consumer/saveDuplicateRefundDetails",
//			"/api/refundAmount/user/changeRefundApplicationStatusToFinalStage/{consumerApplicationNo}",
			"/mis_details/erp_bill_desk_mis/{toDate}/{fromDate}", "/mis_details/ErpStagingData",
			"/api/consumer/consumer-application/getUserIdApplicationCountPendency/{userId}",
			"/api/consumer/consumer-application//getDSPApplicationPendencyOnDiscomUser/{userId}",
			"/api/consumer/consumer-application/isAvedakReallyGovernment",
			"/api/consumer_account_deatils/getAllDuplicateRefundApplicationByApplicationNo/{consumerApplicationNo}",
			"/api/refundAmount/user/getRefundApplication/{consumerApplicationNo}",
			"/api/consumer_account_deatils/getAllDuplicateRefundApplication",
			"/api/consumer/bill-desk/Billdesk_payment_retrive_api1",
			"/api/consumer/qc-portal/oytContractorPendency/{authenticationId}",
			"/api/refundAmount/consumer/saveAddressProofData", "/api/user/redirect-to-frontend",
			"/poseMachine/sanchayPaymentData", "/poseMachine/rejectApplicationByAO", "/api/user/refNoGenerator",
			"/api/user/erp/refNoGenerator", "/mis_details/refundAmountMIS",
			"/api/consumer/consumer-application/getConsumerApplicationDataFromView/{consumerApplicationNo}",
			"/api/user/erp/posePaymentReceipt/{consumerApplicatioNo}",
			"/api/user/erp/oytMaterialCharges/{erpNo}/{consumerApplicationNo}",
			"/ReSampling/getPtrValue/{consumerApplicationNo}",
			"/vendor_add_materials/consumer/updateVendorMaterialData", "/Db_Link_Erp_Controller/saveSupplierData",
			"/Db_Link_Erp_Controller/saveInvoiceDataERP",
			"/api/user/work-status/forwardApplicationToAEStc/{consumerApplicationNo}",
			"/api/consumer/consumer-application/getAllPurposeType",
			"/api/consumer/consumer-application/uploadInspectionFile",
			"/api/consumer/consumer-application/rejectLoadChangeApplication/{consumerApplicationNo}",
			"/api/user/login/getUserDetailsById/{userId}",
			"/api/consumer/consumer-application/getUserDetailsById/{consumerApplicationNo}",
			"/api/user/login/getUserByUserId/{userId}", "/api/user/save-work-order/uploadDraftAgreement",
			"/api/refundAmount/user/submitEFileNoByDGM",
			"/api/consumer/qc-portal/getListOfActualWorkCompletionApplicationForQC",
			"/api/refundAmount/user/financeAoAcceptanceAfterErpCompletion",
			"/NgbStagingData/getOytDataForTemporaryConnection/{consumerAppNo}",
			"/NgbStagingData/pdc//{ivrsNo}",
			"/api/consumer/dc/getDcDataByNgbDCCode/{ngbDcCode}",
			"/mis_details/execute-query",
			"/api/erp-estimate/save",
			"/api/erp-estimate/getEstimateQty/{consumerApplicationNo}",
//			procedure call api
			"/procedure/createInvoiceProcedure", "/mis_details/getFinancePaymentDataForGSTApplication/{yearMonth}",
			"/api/consumer/consumer-application/demandNoteApprovalByGM",
			// Monika API End
			"/ErpRev/RevErpDataNew/{erpNo}/{applicationNo}/{value}", "/api/consumer/bill-desk/billPaymentProcess/{id}",
			"/api/user/consumer-application/get/{id}", "/poseMachine/savedata",
			"/api/master/getAllApplicationDocument/{id}",
//			bhanu sir
			"/api/user/application-dc-change/add", "/AmountMatch/supervision", "/poseMachine/savedata",
			"/api/consumer/consumer-application/getAllNewPaginate/", "/stream/contractorMobileNo",
			"/api/user/application-survey/get/{id}", "/procedure/send/{appNo}",
			"/oyt14/updatby",

			"/api/consumer/consumer-application/getAllNewPaginate/",
			"/api/refundAmount/consumer/saveApplicationCancelletion",
			"/api/user/consumer-application/getAllNewPaginate/", "/api/user/login/getUserRoleByCricleId/{circleId}",
			"/api/mmky/erpMkmypApi/{erpNumber}/{applicationNumber}", "/natureOfWorkIdSettlementController/Settlement",
			"/api/consumer/consumer-application/getConsumerDetailsfullDetailsByApplicationNo/{consumerApplicationNo}" };

//		{ "/api/login/signup", "/api/login/verification",
//			"/api/login/verificationCaptcha", "/api/login/getCaptcha", "/api/login/forgotPassword/**", "/v2/api-docs",
//			"/configuration/ui", "/swagger-resources/*", "/configuration/security", "/swagger-ui.html", "/webjars/*",
//			"/api/otp/getOtp/*", "/api/otp/getLoginOtp/", "/api/otp/verifyLoginOtp/*",
//			"/api/history/downloadCcnbPdf/*", "/htportal/download_file/*",
//			"/api/email-sms/sendBillOnMailByConsumer/*", "/api/masters/getConsumerMeter/*",
//			"/api/consumerTdsPunching/*", "/api/masters/getConsumerInfoTDSCertificateByConsumerId/*" };

	// ,"/api/amr_xml/addAmrXmlData"

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() {
		return new AuthenticationTokenFilter();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers(PUBLIC_MATCHERS)
				// .permitAll().antMatchers("/**")
				.permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		httpSecurity.headers().cacheControl();		
//		httpSecurity.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000);		  
//	
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

//		 httpSecurity.authorizeRequests()
//		.antMatchers("/delete").access("hasRole('ROLE_ADMIN')");

	}

}