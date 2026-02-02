package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "ConsumerApplicationDetail")
@Table(name = "CONSUMER_APPLICATION_DETAIL")
public @Data class ConsumerApplicationDetail extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_APPLICATION_DETAIL_S", sequenceName = "CONSUMER_APPLICATION_DETAIL_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_APPLICATION_DETAIL_S")
	@Column(name = "CONSUMER_APPLICATION_ID", columnDefinition = "serial", updatable = false)
	private Long consumerApplicationId;

	@ManyToOne()
	@JoinColumn(name = "SCHEME_TYPE_ID", referencedColumnName = "SCHEME_TYPE_ID")
	private SchemeType schemeType;

	@ManyToOne()
	@JoinColumn(name = "TASK_TYPE_ID", referencedColumnName = "TASK_TYPE_ID")
	private TaskType taskType;

//	@ManyToOne
//	@JoinColumn(name = "CONSUMER_TYPE_ID", referencedColumnName = "CONSUMER_TYPE_ID")
//	private ConsumerType consumerTypes;

	@ManyToOne()
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID")
	private Consumer consumers;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "GUARDIAN_NAME")
	private String guardianName;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "AREA")
	private Double area;

	@Column(name = "BILL_NO")
	private String billNo;

	@Column(name = "DTR_CODE")
	private Long dtrCode;

	@Column(name = "POLE_NO")
	private String poleNo;

	@Column(name = "AADHAR_NO")
	private String aadharNo;

	@Column(name = "PAN_NO")
	private String panNo;

	@Column(name = "IS_REJECTED")
	private Boolean isRejected;

	@Column(name = "REJECTED_REMARK")
	private String rejectedRemark;

//	@Column(name = "APPLICATION_STATUS")
//	private Long applicationStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_STATUS", referencedColumnName = "APPLICATION_STATUS_ID")
	private ApplicationStatus applicationStatus;

//	@OneToOne
//	@JoinColumn(name = "DOC_AADHAR_ID", referencedColumnName = "UPLOAD_ID")
//	private Upload docAadhar;

//	@OneToOne
//	@JoinColumn(name = "DOC_PAN_ID", referencedColumnName = "UPLOAD_ID")
//	private Upload docPan;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_ENERGY_BILL_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docEnergyBill;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_REGISTORY_OR_LEASE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docRegistoryOrLease;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_BULLIDING_PERMISSON_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docResidentialProof;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_TNCP_APPROVAL_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docBullidingPermissonOrTncpApproval;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_REJECTION_PROPOSAL_FILE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docRejectProposalFilePath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WORK_TYPE_ID", referencedColumnName = "WORK_TYPE_ID")
	private WorkType workType;

	@Column(name = "VENDOR")
	private String vendor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DC_ID", referencedColumnName = "DC_ID")
	private DistributionCenter distributionCenter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBSTATION_ID", referencedColumnName = "SUBSTATION_ID")
	private Substation substation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEEDER_ID", referencedColumnName = "FEEDER_ID")
	private Feeder feeder;

	@Column(name = "CONSUMER_APPLICATION_NUMBER")
	private String consumerApplicationNo;

	@Column(name = "PREVIOUS_STAGE")
	private Boolean previousStage = Boolean.FALSE;

	@Column(name = "PREVIOUS_STAGE_REMARK")
	private String previousStageRemark;

//	@Column(name = "CONTRACTOR_ID")
//	private Long contractorId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACTOR_ID", referencedColumnName = "CONTRACTOR_ID")
	private ContractorDetail contractorId;

	@Column(name = "CONTRACTOR_NAME")
	private String contractorName;

	@Column(name = "SHORT_DESCRIPTION_OF_WORK")
	private String shortDescriptionOfWork;

	@Column(name = "VENDOR_ID")
	private Long contractorUserId;

//	@ManyToOne
//	@JoinColumn(name = "APPLICATION_TYPE_ID", referencedColumnName = "APPLICATION_TYPE_ID")
//	private ApplicationType applicationType;

//	@ManyToOne
//	@JoinColumn(name = "SUPPLY_VOLTAGE_ID", referencedColumnName = "SUPPLY_VOLTAGE_ID")
//	private SupplyVoltage supplyVoltage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TARIFF_CATEGORY_ID", referencedColumnName = "TARIFF_CATEGORY_ID")
	private TariffCategory tariffCategory;

	@Column(name = "PIN_CODE")
	private String pincode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NATURE_OF_WORK_ID", referencedColumnName = "NATURE_OF_WORK_ID")
	private NatureOfWork natureOfWorkType;

	@Column(name = "DTR")
	private Long dtr;

	@Column(name = "PTR")
	private Long ptr;

	@Column(name = "LT")
	private Long lt;

	@Column(name = "HT_11_KV")
	private Long ht11Kv;

	@Column(name = "HT_33_KV")
	private Long ht33Kv;

	@Column(name = "HT_132_KV")
	private Long ht132Kv;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID", referencedColumnName = "DISTRICT_ID")
	private District district;

	@Column(name = "IVRSNO")
	private String ivrsNo;

	@Column(name = "WORK_ALLOCATION_ADDRESS")
	private String workAllocationAddress;

	@Column(name = "work_location_Address_cb")
	private Boolean workLocationAddresscb = false;

	@Column(name = "LOAD_REQUESTED_PER_UNIT")
	private String loadRequested;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOAD_REQUESTED_ID", referencedColumnName = "LOAD_REQUESTED_ID")
	private LoadRequested loadRequestedId;

	@Column(name = "LAND_AREA_UNIT_NAME")
	private String landAreaUnitName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAND_AREA_UNIT_ID", referencedColumnName = "LAND_AREA_UNIT_ID")
	private LandAreaUnit landAreaUnit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INDIVIDUAL_OR_GROUPWISE_ID", referencedColumnName = "INDIVIDUAL_OR_GROUPWISE_ID")
	private IndividualOrGroup individualOrGroup;

	@Column(name = "NO_OF_PLOT")
	private String noOfPlot;

	@Column(name = "KHASRA")
	private String khasra;

	@Column(name = "KHATONI")
	private String khatoni;

	@Column(name = "ERP_WORKFLOW_NUMBER")
	private String erpWorkFlowNumber;

//	@Column(name = "ERP_WORKFLOW_NUMBER")
	@Transient
	private String erpWorkFlowSacntion;

	@Column(name = "GST_NUMBER")
	private String gstNumber;

	@Column(name = "UPDATED_DATE_FOR_CONTRACTOR")
	private String updatedDateForContractor;

	@Column(name = "DGM_OANDN_ID")
	private String dgmOandId;

	@Column(name = "PAYMENT_DATE")
	private String paymentDate;

	@Column(name = "WORK_COMPLETION_DATE")
	private String workCompletionDate;

	@Column(name = "DEMAND_CALCULATED_BY_CHECKBOX")
	private String demandCalculateBycheckbox;

	@Transient
	private List<Map<String, String>> listOfTransactionDate;

	@Column(name = "APPLICATION_REJECTED_DATE")
	private String applicationRejectedDate;

	@Column(name = "REJECT_APPLICATION_GM_NAME")
	private String rejectApplicationGMName;

	@Column(name = "BUILDING_TYPE")
	private String colonyLegalSelectionType; // Multi and Individual

	@Column(name = "COLONY_TYPE")
	private String colonyIllegalSelectionType; // 1=Declared and 2= Undeclared

	@Column(name = "JE_LOAD")
	private String jeLoad;

	@Column(name = "JE_LOAD_UNIT_KW_KVA")
	private String jeLoadUnitKwYaKva;

	@Column(name = "samagra_id")
	private String samagraId;

	@Column(name = "CAST_CATEGORY")
	private String castCategory;

	@Column(name = "Phone_Number")
	private String consumerphonNumber;

	@Column(name = "REJECTION_PROPOSAL")
	private String rejectionProposal;

	@Column(name = "DGM_STC_DATE")
	private String dgmStcDate;

	@Column(name = "DATE_OF_DGM_O_M")
	private String dateOfDgmOandM;

	@Column(name = "REJECTION_REMARK_BY_DGM_O_M")
	private String rejectionRemarkByDGMOAndM;

	@Transient
	private String rejectionProrposalFilePath;

	@Column(name = "GM_REJECTED_OR_ACCEPTED")
	private String gmRejectedOrAccepted;

	@Column(name = "JE_RETURN_AMOUNT")
	private BigDecimal jeReturnAmount;

	@Column(name = "ERP_VER")
	private String erpVersion;

	@Column(name = "REV_ERP")
	private String revisedErpNumber;

	@Column(name = "MKMY_DTR_CAPACITY")
	private String mkmyDtrCapacity;

	@Column(name = "MKMY_CUT_POINT")
	private String mkmyCutPoint;

	@Column(name = "MKMY_DISTANCE")
	private String mkmyDistance;

	@Column(name = "O_AND_M_LOAD")
	private BigDecimal oAndMLoad;

	@Column(name = "O_AND_M_LOAD_UNIT")
	private String oAndMLoadUnit;

	@Column(name = "O_AND_M_RETURN_AMOUNT")
	private BigDecimal oAndMReturnAmount;

	@Column(name = "PAYMENT_MODE_MANUAL")
	private String paymentModeManual;

	@Column(name = "AVEDAK_KA_PRAKAR")
	private String avedakKaPrakar;

	@Column(name = "AVEDAK_REMARK")
	private String avedakRemark;

	@Column(name = "RETURN_MATERIAL_DATA")
	private String returnMaterialData;

	@OneToOne
	@JoinColumn(name = "FEEDBACK_ID")
	private ApplicantFeedbackDSP applicantFeedbackDSP;

	@Column(name = "REFUND_APP_GM_DATE")
	private String refundAppGmDate;
	@Column(name = "REFUND_GM_REMARK")
	private String refundGmRemark;
	@Column(name = "REFUND_APP_GM_NAME")
	private String refundAppGMName;
	@Column(name = "GM_REFUND_ACCEPT_OR_REJECT")
	private String gmRefundAcceptedOrRejected;

//	coloum added for contractor reselection on 16- august -2024 by monika
	@Column(name = "CONT_RESELECTION_DATE")
	private String conReselectionDate;

	@Column(name = "CONT_RESELECTION_REMARK")
	private String conReselectionRemark;

	@Column(name = "CONT_RESELECTION_DGM_NAME")
	private String conReselectionDGMOANDMName;

	@Column(name = "CONT_RESELECTION_DGM_ACCEPTED")
	private String conReselectionDgmAccepted;

	@Column(name = "VOLTAGE_LEVEL")
	private String voltageLevel;

//	23-September-2024

	@Column(name = "SSP_APPLICATION_NO")
	private String nscApplicationNo;

	@Column(name = "SSP_PORTAL_NAME")
	private String sspPortalName;

//	23-September-2024

//	24-September-2024
	@Column(name = "GST_NUMBER_PROVIDE")
	private String gstNumberProvide;

//	24-September-2024

//	25-september-2024
	@Column(name = "SSP_APPLICATION_COMPLETED")
	private String sspApplicationCompleted;

	@Column(name = "SSP_COMPLETED_DATE")
	private String sspApplicationCompleteDate;
//	25-SEPTEMBER-2024

//	22-NOV-2024
	@Column(name = "ADMIN_ID")
	private String adminId;

	@Column(name = "ADMIN_NAME")
	private String adminName;

	@Column(name = "ADMIN_UPDATE_DATE")
	private String adminUpdateDate;

	@Column(name = "ADMIN_REMARK")
	private String adminRemark;
//	22-NOV-2024

//	23-jan-2025 start
	@Column(name = "OYT_CONTRACTOR_SELECTION")
	private String oytContractorSelection;
//	end

//	7-march-2025
	@Column(name = "IS_AVEDAK_GOVERNMENT_ERP")
	private String isAvedakGovernmentErp;

	@Column(name = "IS_AVEDAK_GOVERNMENT_REVISE")
	private String isAvedakGovernmentRevise;

//	monika code end
// 0 matlb cscrap material (bekar material)
	@Column(name = "GOOD_MATERAIL_OR_NOT")
	private Integer goodMaterialOrnot = 0;

	@Column(name = "CONNECTION_TYPE")
	private String connectionType;

	@Column(name = "RESAMPLING_FLAG")
	private Long reSamplingFlag = 0L;

//	09-09-2025

	@Column(name = "SSP_REG_CHARGE", updatable = false)
	private BigDecimal sspRegistrationCharge;

	@Column(name = "SSP_METER_COST", updatable = false)
	private BigDecimal sspMeterCost;

	@Column(name = "SSP_SD", updatable = false)
	private BigDecimal sspSecurityDeposit;

	@Column(name = "SSP_SAC", updatable = false)
	private BigDecimal sspSupplyAffordingCharges;

	@Column(name = "SSPTOTALAMOUNT", updatable = false)
	private BigDecimal sspTotalAmount;

	@Column(name = "PURPOSE_OF_INSTALLATION")
	private String purposeOfInstallation;

	@Column(name = "PHASE")
	private String phase;

	@Column(name = "REJECTION_REMARK_JE")
	private String rejectionRemarkByJE;

//	=============OYT wal part

	@Column(name = "OYT_TEMP_OR_PARMA")
	private String oytTepOrParma;

	@Column(name = "PREMISE_AREA_TYPE")
	private String premiseAreaType;

	@Column(name = "OYT_TEMP_APPLICATION_NO")
	private String oytTempApplicationNo;

//	=========BIGILANCE WALA PART====================

	@Column(name = "PUNCH_NAMA_REFERENCE_NO")
	private String punchNamaReferenceNo;

	@Column(name = "PUNCH_NAMA_CREATE_DATE")
	private String punchNamaCreatedDate;

// 19-11-2025
	@Column(name = "METERING_STATUS")
	private String meteringStatus;

	@Column(name = "CONNECTION_CATEGORY")
	private String connectionCategory;

	@Column(name = "TEMPORARY_PUSHED")
	private String temporaryPushed;

	@Column(name = "TEMPORARY_PUSHED_DATE")
	private String temporaryPushedDate;

	@Column(name = "TEMP_IVRS_NO")
	private String tempIvrsNo;

	@Column(name = "GM_DEMAND_APPROVED")
	private String gmDemandApproved;

	@Column(name = "GM_DEMAND_APPOVED_DATE")
	private String gmDemandAppDate;

	@Column(name = "GM_DEMAND_REMARK")
	private String gmDemandRemark;

	@Column(name = "GM_DEMAND_USER_ID")
	private String gmDemandUserId;
	
	@Column(name = "ADMIN_STATUS_CHANGED_TO")
	private Long adminStatusChangedTo;
	
	

	public Long getAdminStatusChangedTo() {
		return adminStatusChangedTo;
	}

	public void setAdminStatusChangedTo(Long adminStatusChangedTo) {
		this.adminStatusChangedTo = adminStatusChangedTo;
	}

	public String getGmDemandApproved() {
		return gmDemandApproved;
	}

	public void setGmDemandApproved(String gmDemandApproved) {
		this.gmDemandApproved = gmDemandApproved;
	}

	public String getGmDemandAppDate() {
		return gmDemandAppDate;
	}

	public void setGmDemandAppDate(String gmDemandAppDate) {
		this.gmDemandAppDate = gmDemandAppDate;
	}

	public String getGmDemandRemark() {
		return gmDemandRemark;
	}

	public void setGmDemandRemark(String gmDemandRemark) {
		this.gmDemandRemark = gmDemandRemark;
	}

	

	public String getGmDemandUserId() {
		return gmDemandUserId;
	}

	public void setGmDemandUserId(String gmDemandUserId) {
		this.gmDemandUserId = gmDemandUserId;
	}

	public String getTempIvrsNo() {
		return tempIvrsNo;
	}

	public void setTempIvrsNo(String tempIvrsNo) {
		this.tempIvrsNo = tempIvrsNo;
	}

	public String getOytTempApplicationNo() {
		return oytTempApplicationNo;
	}

	public void setOytTempApplicationNo(String oytTempApplicationNo) {
		this.oytTempApplicationNo = oytTempApplicationNo;
	}

	public String getTemporaryPushed() {
		return temporaryPushed;
	}

	public void setTemporaryPushed(String temporaryPushed) {
		this.temporaryPushed = temporaryPushed;
	}

	public String getTemporaryPushedDate() {
		return temporaryPushedDate;
	}

	public void setTemporaryPushedDate(String temporaryPushedDate) {
		this.temporaryPushedDate = temporaryPushedDate;
	}

	public String getMeteringStatus() {
		return meteringStatus;
	}

	public void setMeteringStatus(String meteringStatus) {
		this.meteringStatus = meteringStatus;
	}

	public String getConnectionCategory() {
		return connectionCategory;
	}

	public void setConnectionCategory(String connectionCategory) {
		this.connectionCategory = connectionCategory;
	}

	public String getOytTepOrParma() {
		return oytTepOrParma;
	}

	public void setOytTepOrParma(String oytTepOrParma) {
		this.oytTepOrParma = oytTepOrParma;
	}

	public String getPremiseAreaType() {
		return premiseAreaType;
	}

	public void setPremiseAreaType(String premiseAreaType) {
		this.premiseAreaType = premiseAreaType;
	}

	public String getPunchNamaReferenceNo() {
		return punchNamaReferenceNo;
	}

	public void setPunchNamaReferenceNo(String punchNamaReferenceNo) {
		this.punchNamaReferenceNo = punchNamaReferenceNo;
	}

	public String getPunchNamaCreatedDate() {
		return punchNamaCreatedDate;
	}

	public void setPunchNamaCreatedDate(String punchNamaCreatedDate) {
		this.punchNamaCreatedDate = punchNamaCreatedDate;
	}

	public String getRejectionRemarkByJE() {
		return rejectionRemarkByJE;
	}

	public void setRejectionRemarkByJE(String rejectionRemarkByJE) {
		this.rejectionRemarkByJE = rejectionRemarkByJE;
	}

	public String getPurposeOfInstallation() {
		return purposeOfInstallation;
	}

	public void setPurposeOfInstallation(String purposeOfInstallation) {
		this.purposeOfInstallation = purposeOfInstallation;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public BigDecimal getSspRegistrationCharge() {
		return sspRegistrationCharge;
	}

	public void setSspRegistrationCharge(BigDecimal sspRegistrationCharge) {
		this.sspRegistrationCharge = sspRegistrationCharge;
	}

	public BigDecimal getSspMeterCost() {
		return sspMeterCost;
	}

	public void setSspMeterCost(BigDecimal sspMeterCost) {
		this.sspMeterCost = sspMeterCost;
	}

	public BigDecimal getSspSecurityDeposit() {
		return sspSecurityDeposit;
	}

	public void setSspSecurityDeposit(BigDecimal sspSecurityDeposit) {
		this.sspSecurityDeposit = sspSecurityDeposit;
	}

	public BigDecimal getSspSupplyAffordingCharges() {
		return sspSupplyAffordingCharges;
	}

	public void setSspSupplyAffordingCharges(BigDecimal sspSupplyAffordingCharges) {
		this.sspSupplyAffordingCharges = sspSupplyAffordingCharges;
	}

	public BigDecimal getSspTotalAmount() {
		return sspTotalAmount;
	}

	public void setSspTotalAmount(BigDecimal sspTotalAmount) {
		this.sspTotalAmount = sspTotalAmount;
	}

	public Long getReSamplingFlag() {
		return reSamplingFlag;
	}

	public void setReSamplingFlag(Long reSamplingFlag) {
		this.reSamplingFlag = reSamplingFlag;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public Integer getGoodMaterialOrnot() {
		return goodMaterialOrnot;
	}

	public void setGoodMaterialOrnot(Integer goodMaterialOrnot) {
		this.goodMaterialOrnot = goodMaterialOrnot;
	}

	public String getConReselectionDate() {
		return conReselectionDate;
	}

	public String getIsAvedakGovernmentErp() {
		return isAvedakGovernmentErp;
	}

	public void setIsAvedakGovernmentErp(String isAvedakGovernmentErp) {
		this.isAvedakGovernmentErp = isAvedakGovernmentErp;
	}

	public String getIsAvedakGovernmentRevise() {
		return isAvedakGovernmentRevise;
	}

	public void setIsAvedakGovernmentRevise(String isAvedakGovernmentRevise) {
		this.isAvedakGovernmentRevise = isAvedakGovernmentRevise;
	}

	public String getOytContractorSelection() {
		return oytContractorSelection;
	}

	public void setOytContractorSelection(String oytContractorSelection) {
		this.oytContractorSelection = oytContractorSelection;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminUpdateDate() {
		return adminUpdateDate;
	}

	public void setAdminUpdateDate(String adminUpdateDate) {
		this.adminUpdateDate = adminUpdateDate;
	}

	public String getAdminRemark() {
		return adminRemark;
	}

	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}

	public String getSspApplicationCompleted() {
		return sspApplicationCompleted;
	}

	public void setSspApplicationCompleted(String sspApplicationCompleted) {
		this.sspApplicationCompleted = sspApplicationCompleted;
	}

	public String getSspApplicationCompleteDate() {
		return sspApplicationCompleteDate;
	}

	public void setSspApplicationCompleteDate(String sspApplicationCompleteDate) {
		this.sspApplicationCompleteDate = sspApplicationCompleteDate;
	}

	public String getGstNumberProvide() {
		return gstNumberProvide;
	}

	public void setGstNumberProvide(String gstNumberProvide) {
		this.gstNumberProvide = gstNumberProvide;
	}

	public String getNscApplicationNo() {
		return nscApplicationNo;
	}

	public void setNscApplicationNo(String nscApplicationNo) {
		this.nscApplicationNo = nscApplicationNo;
	}

	public String getSspPortalName() {
		return sspPortalName;
	}

	public void setSspPortalName(String sspPortalName) {
		this.sspPortalName = sspPortalName;
	}

	public String getVoltageLevel() {
		return voltageLevel;
	}

	public void setVoltageLevel(String voltageLevel) {
		this.voltageLevel = voltageLevel;
	}

	public void setConReselectionDate(String conReselectionDate) {
		this.conReselectionDate = conReselectionDate;
	}

	public String getConReselectionRemark() {
		return conReselectionRemark;
	}

	public void setConReselectionRemark(String conReselectionRemark) {
		this.conReselectionRemark = conReselectionRemark;
	}

	public String getConReselectionDGMOANDMName() {
		return conReselectionDGMOANDMName;
	}

	public void setConReselectionDGMOANDMName(String conReselectionDGMOANDMName) {
		this.conReselectionDGMOANDMName = conReselectionDGMOANDMName;
	}

	public String getConReselectionDgmAccepted() {
		return conReselectionDgmAccepted;
	}

	public void setConReselectionDgmAccepted(String conReselectionDgmAccepted) {
		this.conReselectionDgmAccepted = conReselectionDgmAccepted;
	}

	public String getRefundAppGmDate() {
		return refundAppGmDate;
	}

	public void setRefundAppGmDate(String refundAppGmDate) {
		this.refundAppGmDate = refundAppGmDate;
	}

	public String getRefundGmRemark() {
		return refundGmRemark;
	}

	public void setRefundGmRemark(String refundGmRemark) {
		this.refundGmRemark = refundGmRemark;
	}

	public String getRefundAppGMName() {
		return refundAppGMName;
	}

	public void setRefundAppGMName(String refundAppGMName) {
		this.refundAppGMName = refundAppGMName;
	}

	public String getGmRefundAcceptedOrRejected() {
		return gmRefundAcceptedOrRejected;
	}

	public void setGmRefundAcceptedOrRejected(String gmRefundAcceptedOrRejected) {
		this.gmRefundAcceptedOrRejected = gmRefundAcceptedOrRejected;
	}

	public ApplicantFeedbackDSP getApplicantFeedbackDSP() {
		return applicantFeedbackDSP;
	}

	public void setApplicantFeedbackDSP(ApplicantFeedbackDSP applicantFeedbackDSP) {
		this.applicantFeedbackDSP = applicantFeedbackDSP;
	}

	public String getReturnMaterialData() {
		return returnMaterialData;
	}

	public void setReturnMaterialData(String returnMaterialData) {
		this.returnMaterialData = returnMaterialData;
	}

	public String getAvedakKaPrakar() {
		return avedakKaPrakar;
	}

	public void setAvedakKaPrakar(String avedakKaPrakar) {
		this.avedakKaPrakar = avedakKaPrakar;
	}

	public String getAvedakRemark() {
		return avedakRemark;
	}

	public void setAvedakRemark(String avedakRemark) {
		this.avedakRemark = avedakRemark;
	}

	public String getPaymentModeManual() {
		return paymentModeManual;
	}

	public void setPaymentModeManual(String paymentModeManual) {
		this.paymentModeManual = paymentModeManual;
	}

	public BigDecimal getoAndMLoad() {
		return oAndMLoad;
	}

	public void setoAndMLoad(BigDecimal oAndMLoad) {
		this.oAndMLoad = oAndMLoad;
	}

	public String getoAndMLoadUnit() {
		return oAndMLoadUnit;
	}

	public void setoAndMLoadUnit(String oAndMLoadUnit) {
		this.oAndMLoadUnit = oAndMLoadUnit;
	}

	public BigDecimal getoAndMReturnAmount() {
		return oAndMReturnAmount;
	}

	public void setoAndMReturnAmount(BigDecimal oAndMReturnAmount) {
		this.oAndMReturnAmount = oAndMReturnAmount;
	}

	public String getMkmyDtrCapacity() {
		return mkmyDtrCapacity;
	}

	public void setMkmyDtrCapacity(String mkmyDtrCapacity) {
		this.mkmyDtrCapacity = mkmyDtrCapacity;
	}

	public String getMkmyCutPoint() {
		return mkmyCutPoint;
	}

	public void setMkmyCutPoint(String mkmyCutPoint) {
		this.mkmyCutPoint = mkmyCutPoint;
	}

	public String getMkmyDistance() {
		return mkmyDistance;
	}

	public void setMkmyDistance(String mkmyDistance) {
		this.mkmyDistance = mkmyDistance;
	}

	public String getRevisedErpNumber() {
		return revisedErpNumber;
	}

	public void setRevisedErpNumber(String revisedErpNumber) {
		this.revisedErpNumber = revisedErpNumber;
	}

	public String getErpVersion() {
		return erpVersion;
	}

	public void setErpVersion(String erpVersion) {
		this.erpVersion = erpVersion;
	}

	public BigDecimal getJeReturnAmount() {
		return jeReturnAmount;
	}

	public void setJeReturnAmount(BigDecimal jeReturnAmount) {
		this.jeReturnAmount = jeReturnAmount;
	}

	public String getGmRejectedOrAccepted() {
		return gmRejectedOrAccepted;
	}

	public void setGmRejectedOrAccepted(String gmRejectedOrAccepted) {
		this.gmRejectedOrAccepted = gmRejectedOrAccepted;
	}

	public String getRejectionProrposalFilePath() {
		return rejectionProrposalFilePath;
	}

	public void setRejectionProrposalFilePath(String rejectionProrposalFilePath) {
		this.rejectionProrposalFilePath = rejectionProrposalFilePath;
	}

	public Upload getDocRejectProposalFilePath() {
		return docRejectProposalFilePath;
	}

	public void setDocRejectProposalFilePath(Upload docRejectProposalFilePath) {
		this.docRejectProposalFilePath = docRejectProposalFilePath;
	}

	public String getDgmStcDate() {
		return dgmStcDate;
	}

	public void setDgmStcDate(String dgmStcDate) {
		this.dgmStcDate = dgmStcDate;
	}

	public String getRejectionRemarkByDGMOAndM() {
		return rejectionRemarkByDGMOAndM;
	}

	public void setRejectionRemarkByDGMOAndM(String rejectionRemarkByDGMOAndM) {
		this.rejectionRemarkByDGMOAndM = rejectionRemarkByDGMOAndM;
	}

	public String getDateOfDgmOandM() {
		return dateOfDgmOandM;
	}

	public void setDateOfDgmOandM(String dateOfDgmOandM) {
		this.dateOfDgmOandM = dateOfDgmOandM;
	}

	public String getWorkCompletionDate() {
		return workCompletionDate;
	}

	public void setWorkCompletionDate(String workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}

	public String getRejectionProposal() {
		return rejectionProposal;
	}

	public void setRejectionProposal(String rejectionProposal) {
		this.rejectionProposal = rejectionProposal;
	}

	public String getConsumerphonNumber() {
		return consumerphonNumber;
	}

	public void setConsumerphonNumber(String consumerphonNumber) {
		this.consumerphonNumber = consumerphonNumber;
	}

	public String getSamagraId() {
		return samagraId;
	}

	public void setSamagraId(String samagraId) {
		this.samagraId = samagraId;
	}

	public String getCastCategory() {
		return castCategory;
	}

	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}

	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

	public SchemeType getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Consumer getConsumers() {
		return consumers;
	}

	public void setConsumers(Consumer consumers) {
		this.consumers = consumers;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Long getDtrCode() {
		return dtrCode;
	}

	public void setDtrCode(Long dtrCode) {
		this.dtrCode = dtrCode;
	}

	public String getPoleNo() {
		return poleNo;
	}

	public void setPoleNo(String poleNo) {
		this.poleNo = poleNo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public String getRejectedRemark() {
		return rejectedRemark;
	}

	public void setRejectedRemark(String rejectedRemark) {
		this.rejectedRemark = rejectedRemark;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Upload getDocEnergyBill() {
		return docEnergyBill;
	}

	public void setDocEnergyBill(Upload docEnergyBill) {
		this.docEnergyBill = docEnergyBill;
	}

	public Upload getDocRegistoryOrLease() {
		return docRegistoryOrLease;
	}

	public void setDocRegistoryOrLease(Upload docRegistoryOrLease) {
		this.docRegistoryOrLease = docRegistoryOrLease;
	}

	public Upload getDocResidentialProof() {
		return docResidentialProof;
	}

	public void setDocResidentialProof(Upload docResidentialProof) {
		this.docResidentialProof = docResidentialProof;
	}

	public Upload getDocBullidingPermissonOrTncpApproval() {
		return docBullidingPermissonOrTncpApproval;
	}

	public void setDocBullidingPermissonOrTncpApproval(Upload docBullidingPermissonOrTncpApproval) {
		this.docBullidingPermissonOrTncpApproval = docBullidingPermissonOrTncpApproval;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public DistributionCenter getDistributionCenter() {
		return distributionCenter;
	}

	public void setDistributionCenter(DistributionCenter distributionCenter) {
		this.distributionCenter = distributionCenter;
	}

	public Substation getSubstation() {
		return substation;
	}

	public void setSubstation(Substation substation) {
		this.substation = substation;
	}

	public Feeder getFeeder() {
		return feeder;
	}

	public void setFeeder(Feeder feeder) {
		this.feeder = feeder;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public Boolean getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(Boolean previousStage) {
		this.previousStage = previousStage;
	}

	public String getPreviousStageRemark() {
		return previousStageRemark;
	}

	public void setPreviousStageRemark(String previousStageRemark) {
		this.previousStageRemark = previousStageRemark;
	}

	public ContractorDetail getContractorId() {
		return contractorId;
	}

	public void setContractorId(ContractorDetail contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getShortDescriptionOfWork() {
		return shortDescriptionOfWork;
	}

	public void setShortDescriptionOfWork(String shortDescriptionOfWork) {
		this.shortDescriptionOfWork = shortDescriptionOfWork;
	}

	public Long getContractorUserId() {
		return contractorUserId;
	}

	public void setContractorUserId(Long contractorUserId) {
		this.contractorUserId = contractorUserId;
	}

	public TariffCategory getTariffCategory() {
		return tariffCategory;
	}

	public void setTariffCategory(TariffCategory tariffCategory) {
		this.tariffCategory = tariffCategory;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public NatureOfWork getNatureOfWorkType() {
		return natureOfWorkType;
	}

	public void setNatureOfWorkType(NatureOfWork natureOfWorkType) {
		this.natureOfWorkType = natureOfWorkType;
	}

	public Long getDtr() {
		return dtr;
	}

	public void setDtr(Long dtr) {
		this.dtr = dtr;
	}

	public Long getPtr() {
		return ptr;
	}

	public void setPtr(Long ptr) {
		this.ptr = ptr;
	}

	public Long getLt() {
		return lt;
	}

	public void setLt(Long lt) {
		this.lt = lt;
	}

	public Long getHt11Kv() {
		return ht11Kv;
	}

	public void setHt11Kv(Long ht11Kv) {
		this.ht11Kv = ht11Kv;
	}

	public Long getHt33Kv() {
		return ht33Kv;
	}

	public void setHt33Kv(Long ht33Kv) {
		this.ht33Kv = ht33Kv;
	}

	public Long getHt132Kv() {
		return ht132Kv;
	}

	public void setHt132Kv(Long ht132Kv) {
		this.ht132Kv = ht132Kv;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public String getIvrsNo() {
		return ivrsNo;
	}

	public void setIvrsNo(String ivrsNo) {
		this.ivrsNo = ivrsNo;
	}

	public String getWorkAllocationAddress() {
		return workAllocationAddress;
	}

	public void setWorkAllocationAddress(String workAllocationAddress) {
		this.workAllocationAddress = workAllocationAddress;
	}

	public Boolean getWorkLocationAddresscb() {
		return workLocationAddresscb;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public void setWorkLocationAddresscb(Boolean workLocationAddresscb) {
		this.workLocationAddresscb = workLocationAddresscb;
	}

	public String getLoadRequested() {
		return loadRequested;
	}

	public void setLoadRequested(String loadRequested) {
		this.loadRequested = loadRequested;
	}

	public LoadRequested getLoadRequestedId() {
		return loadRequestedId;
	}

	public void setLoadRequestedId(LoadRequested loadRequestedId) {
		this.loadRequestedId = loadRequestedId;
	}

	public String getLandAreaUnitName() {
		return landAreaUnitName;
	}

	public void setLandAreaUnitName(String landAreaUnitName) {
		this.landAreaUnitName = landAreaUnitName;
	}

	public LandAreaUnit getLandAreaUnit() {
		return landAreaUnit;
	}

	public void setLandAreaUnit(LandAreaUnit landAreaUnit) {
		this.landAreaUnit = landAreaUnit;
	}

	public IndividualOrGroup getIndividualOrGroup() {
		return individualOrGroup;
	}

	public void setIndividualOrGroup(IndividualOrGroup individualOrGroup) {
		this.individualOrGroup = individualOrGroup;
	}

	public String getNoOfPlot() {
		return noOfPlot;
	}

	public void setNoOfPlot(String noOfPlot) {
		this.noOfPlot = noOfPlot;
	}

	public String getKhasra() {
		return khasra;
	}

	public void setKhasra(String khasra) {
		this.khasra = khasra;
	}

	public String getKhatoni() {
		return khatoni;
	}

	public void setKhatoni(String khatoni) {
		this.khatoni = khatoni;
	}

	public String getErpWorkFlowNumber() {
		return erpWorkFlowNumber;
	}

	public void setErpWorkFlowNumber(String erpWorkFlowNumber) {
		this.erpWorkFlowNumber = erpWorkFlowNumber;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getUpdatedDateForContractor() {
		return updatedDateForContractor;
	}

	public void setUpdatedDateForContractor(String updatedDateForContractor) {
		this.updatedDateForContractor = updatedDateForContractor;
	}

	public String getDgmOandId() {
		return dgmOandId;
	}

	public void setDgmOandId(String dgmOandId) {
		this.dgmOandId = dgmOandId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getDemandCalculateBycheckbox() {
		return demandCalculateBycheckbox;
	}

	public void setDemandCalculateBycheckbox(String demandCalculateBycheckbox) {
		this.demandCalculateBycheckbox = demandCalculateBycheckbox;
	}

	public List<Map<String, String>> getListOfTransactionDate() {
		return listOfTransactionDate;
	}

	public void setListOfTransactionDate(List<Map<String, String>> listOfTransactionDate) {
		this.listOfTransactionDate = listOfTransactionDate;
	}

	public String getApplicationRejectedDate() {
		return applicationRejectedDate;
	}

	public void setApplicationRejectedDate(String applicationRejectedDate) {
		this.applicationRejectedDate = applicationRejectedDate;
	}

	public String getRejectApplicationGMName() {
		return rejectApplicationGMName;
	}

	public void setRejectApplicationGMName(String rejectApplicationGMName) {
		this.rejectApplicationGMName = rejectApplicationGMName;
	}

	public String getColonyLegalSelectionType() {
		return colonyLegalSelectionType;
	}

	public void setColonyLegalSelectionType(String colonyLegalSelectionType) {
		this.colonyLegalSelectionType = colonyLegalSelectionType;
	}

	public String getColonyIllegalSelectionType() {
		return colonyIllegalSelectionType;
	}

	public void setColonyIllegalSelectionType(String colonyIllegalSelectionType) {
		this.colonyIllegalSelectionType = colonyIllegalSelectionType;
	}

	public String getJeLoad() {
		return jeLoad;
	}

	public void setJeLoad(String jeLoad) {
		this.jeLoad = jeLoad;
	}

	public String getJeLoadUnitKwYaKva() {
		return jeLoadUnitKwYaKva;
	}

	public void setJeLoadUnitKwYaKva(String jeLoadUnitKwYaKva) {
		this.jeLoadUnitKwYaKva = jeLoadUnitKwYaKva;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getErpWorkFlowSacntion() {
		return erpWorkFlowSacntion;
	}

	public void setErpWorkFlowSacntion(String erpWorkFlowSacntion) {
		this.erpWorkFlowSacntion = erpWorkFlowSacntion;
	}

	@Override
	public String toString() {
		return "ConsumerApplicationDetail [consumerApplicationId=" + consumerApplicationId + ", schemeType="
				+ schemeType + ", taskType=" + taskType + ", consumers=" + consumers + ", consumerName=" + consumerName
				+ ", guardianName=" + guardianName + ", address=" + address + ", area=" + area + ", billNo=" + billNo
				+ ", dtrCode=" + dtrCode + ", poleNo=" + poleNo + ", aadharNo=" + aadharNo + ", panNo=" + panNo
				+ ", isRejected=" + isRejected + ", rejectedRemark=" + rejectedRemark + ", applicationStatus="
				+ applicationStatus + ", docEnergyBill=" + docEnergyBill + ", docRegistoryOrLease="
				+ docRegistoryOrLease + ", docResidentialProof=" + docResidentialProof
				+ ", docBullidingPermissonOrTncpApproval=" + docBullidingPermissonOrTncpApproval
				+ ", docRejectProposalFilePath=" + docRejectProposalFilePath + ", workType=" + workType + ", vendor="
				+ vendor + ", distributionCenter=" + distributionCenter + ", substation=" + substation + ", feeder="
				+ feeder + ", consumerApplicationNo=" + consumerApplicationNo + ", previousStage=" + previousStage
				+ ", previousStageRemark=" + previousStageRemark + ", contractorId=" + contractorId
				+ ", contractorName=" + contractorName + ", shortDescriptionOfWork=" + shortDescriptionOfWork
				+ ", contractorUserId=" + contractorUserId + ", tariffCategory=" + tariffCategory + ", pincode="
				+ pincode + ", natureOfWorkType=" + natureOfWorkType + ", dtr=" + dtr + ", ptr=" + ptr + ", lt=" + lt
				+ ", ht11Kv=" + ht11Kv + ", ht33Kv=" + ht33Kv + ", ht132Kv=" + ht132Kv + ", district=" + district
				+ ", ivrsNo=" + ivrsNo + ", workAllocationAddress=" + workAllocationAddress + ", workLocationAddresscb="
				+ workLocationAddresscb + ", loadRequested=" + loadRequested + ", loadRequestedId=" + loadRequestedId
				+ ", landAreaUnitName=" + landAreaUnitName + ", landAreaUnit=" + landAreaUnit + ", individualOrGroup="
				+ individualOrGroup + ", noOfPlot=" + noOfPlot + ", khasra=" + khasra + ", khatoni=" + khatoni
				+ ", erpWorkFlowNumber=" + erpWorkFlowNumber + ", erpWorkFlowSacntion=" + erpWorkFlowSacntion
				+ ", gstNumber=" + gstNumber + ", updatedDateForContractor=" + updatedDateForContractor + ", dgmOandId="
				+ dgmOandId + ", paymentDate=" + paymentDate + ", workCompletionDate=" + workCompletionDate
				+ ", demandCalculateBycheckbox=" + demandCalculateBycheckbox + ", listOfTransactionDate="
				+ listOfTransactionDate + ", applicationRejectedDate=" + applicationRejectedDate
				+ ", rejectApplicationGMName=" + rejectApplicationGMName + ", colonyLegalSelectionType="
				+ colonyLegalSelectionType + ", colonyIllegalSelectionType=" + colonyIllegalSelectionType + ", jeLoad="
				+ jeLoad + ", jeLoadUnitKwYaKva=" + jeLoadUnitKwYaKva + ", samagraId=" + samagraId + ", castCategory="
				+ castCategory + ", consumerphonNumber=" + consumerphonNumber + ", rejectionProposal="
				+ rejectionProposal + ", dgmStcDate=" + dgmStcDate + ", dateOfDgmOandM=" + dateOfDgmOandM
				+ ", rejectionRemarkByDGMOAndM=" + rejectionRemarkByDGMOAndM + ", rejectionProrposalFilePath="
				+ rejectionProrposalFilePath + ", gmRejectedOrAccepted=" + gmRejectedOrAccepted + ", jeReturnAmount="
				+ jeReturnAmount + ", erpVersion=" + erpVersion + ", revisedErpNumber=" + revisedErpNumber
				+ ", mkmyDtrCapacity=" + mkmyDtrCapacity + ", mkmyCutPoint=" + mkmyCutPoint + ", mkmyDistance="
				+ mkmyDistance + ", oAndMLoad=" + oAndMLoad + ", oAndMLoadUnit=" + oAndMLoadUnit
				+ ", oAndMReturnAmount=" + oAndMReturnAmount + ", paymentModeManual=" + paymentModeManual
				+ ", avedakKaPrakar=" + avedakKaPrakar + ", avedakRemark=" + avedakRemark + ", returnMaterialData="
				+ returnMaterialData + ", applicantFeedbackDSP=" + applicantFeedbackDSP + ", refundAppGmDate="
				+ refundAppGmDate + ", refundGmRemark=" + refundGmRemark + ", refundAppGMName=" + refundAppGMName
				+ ", gmRefundAcceptedOrRejected=" + gmRefundAcceptedOrRejected + ", conReselectionDate="
				+ conReselectionDate + ", conReselectionRemark=" + conReselectionRemark
				+ ", conReselectionDGMOANDMName=" + conReselectionDGMOANDMName + ", conReselectionDgmAccepted="
				+ conReselectionDgmAccepted + ", voltageLevel=" + voltageLevel + ", nscApplicationNo="
				+ nscApplicationNo + ", sspPortalName=" + sspPortalName + ", gstNumberProvide=" + gstNumberProvide
				+ ", sspApplicationCompleted=" + sspApplicationCompleted + ", sspApplicationCompleteDate="
				+ sspApplicationCompleteDate + ", adminId=" + adminId + ", adminName=" + adminName
				+ ", adminUpdateDate=" + adminUpdateDate + ", adminRemark=" + adminRemark + ", oytContractorSelection="
				+ oytContractorSelection + ", isAvedakGovernmentErp=" + isAvedakGovernmentErp
				+ ", isAvedakGovernmentRevise=" + isAvedakGovernmentRevise + ", goodMaterialOrnot=" + goodMaterialOrnot
				+ ", connectionType=" + connectionType + ", reSamplingFlag=" + reSamplingFlag
				+ ", sspRegistrationCharge=" + sspRegistrationCharge + ", sspMeterCost=" + sspMeterCost
				+ ", sspSecurityDeposit=" + sspSecurityDeposit + ", sspSupplyAffordingCharges="
				+ sspSupplyAffordingCharges + ", sspTotalAmount=" + sspTotalAmount + ", purposeOfInstallation="
				+ purposeOfInstallation + ", phase=" + phase + "]";
	}

}
