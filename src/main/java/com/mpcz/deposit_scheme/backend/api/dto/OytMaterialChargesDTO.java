package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class OytMaterialChargesDTO {

	@JsonProperty("ID")
	private Long id;

	@JsonProperty("PROJECT_ITEM_COST")
	private Double projectItemCost;

	@JsonProperty("PO_LINE_ID")
	private Long poLineId;

	@JsonProperty("PROJECT_DESC")
	private String projectDesc;

	@JsonProperty("TECH_VIABILITY")
	private String techViability;

	@JsonProperty("PRJ_OWNING_ORG")
	private String prjOwningOrg;

	@JsonProperty("SCHEME_DESC")
	private String schemeDesc;

	@JsonProperty("ESTIMATE_NO")
	private String estimateNo;

	@JsonProperty("PROJECT_STATUS")
	private String projectStatus;

	@JsonProperty("CONTRACTOR_NAME")
	private String contractorName;

	@JsonProperty("PROJECT_UOM")
	private String projectUom;

	@JsonProperty("ITEM_UOM")
	private String itemUom;

	@JsonProperty("APPLICANT_CONSUMER_NAME")
	private String applicantConsumerName;

	@JsonProperty("DC_NAME")
	private String dcName;

	@JsonProperty("RESOURCE_ITEM")
	private String resourceItem;

	@JsonProperty("SCHEME_CODE")
	private String schemeCode;

	@JsonProperty("PROJ_COMPLETION_DATE")
	private String projCompletionDate;

	@JsonProperty("CONTRACT_ORGANIZATION")
	private String contractOrganization;

	@JsonProperty("FEEDER_ID")
	private String feederId;

	@JsonProperty("CERTIFICATE")
	private String certificate;

	@JsonProperty("ITEM_QTY")
	private Integer itemQty;

	@JsonProperty("SCHEDULE_QTY")
	private Integer scheduleQty;

	@JsonProperty("ITEM_DESC")
	private String itemDesc;

	@JsonProperty("SCHEDULE_NO")
	private String scheduleNo;

	@JsonProperty("CONTRACTOR_SUB_ID")
	private String contractorSubId;

	@JsonProperty("LONG_NAME")
	private String longName;

	@JsonProperty("TASK_NAME")
	private String taskName;

	@JsonProperty("PROJECT_TOTAL_COST")
	private Double projectTotalCost;

	@JsonProperty("PROJECT_ID")
	private Long projectId;

	@JsonProperty("CONTRACT_NUMBER")
	private String contractNumber;

	@JsonProperty("PROJ_CREATION_DATE")
	private String projCreationDate;

	@JsonProperty("ITEM_COST")
	private String itemCost;

	@JsonProperty("PROJ_START_DATE")
	private String projStartDate;

	@JsonProperty("TASK_TOTAL_COST")
	private Double taskTotalCost;

	@JsonProperty("VERSION")
	private Integer version;

	@JsonProperty("TOTAL_COST")
	private String totalCost;

	@JsonProperty("CONTRACTOR_NUMBER")
	private String contractorNumber;

	@JsonProperty("CENTAGES")
	private String centages;

	@JsonProperty("PURCHASE_ORDER")
	private String purchaseOrder;

	@JsonProperty("OA_DATE")
	private String oaDate;

	@JsonProperty("LABOR_TRANS_CHG")
	private Integer laborTransChg;

	@JsonProperty("PROJECT_TYPE")
	private String projectType;

	@JsonProperty("BASELINED_DATE")
	private String baselinedDate;

	@JsonProperty("TASK_NUMBER")
	private String taskNumber;

	@JsonProperty("PROJECT_NUMBER")
	private String projectNumber;

	@JsonProperty("SCHEDULE_UOM")
	private String scheduleUom;

	@JsonProperty("WORK_TYPE")
	private String workType;

	@JsonProperty("TASK_ITEM_COST")
	private Double taskItemCost;

	@JsonProperty("SB_PROJECT_ID")
	private Long sbProjectId;

	@JsonProperty("CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public Double getProjectItemCost() {
		return projectItemCost;
	}

	public void setProjectItemCost(Double projectItemCost) {
		this.projectItemCost = projectItemCost;
	}

	public Long getPoLineId() {
		return poLineId;
	}

	public void setPoLineId(Long poLineId) {
		this.poLineId = poLineId;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getTechViability() {
		return techViability;
	}

	public void setTechViability(String techViability) {
		this.techViability = techViability;
	}

	public String getPrjOwningOrg() {
		return prjOwningOrg;
	}

	public void setPrjOwningOrg(String prjOwningOrg) {
		this.prjOwningOrg = prjOwningOrg;
	}

	public String getSchemeDesc() {
		return schemeDesc;
	}

	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}

	public String getEstimateNo() {
		return estimateNo;
	}

	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getProjectUom() {
		return projectUom;
	}

	public void setProjectUom(String projectUom) {
		this.projectUom = projectUom;
	}

	public String getItemUom() {
		return itemUom;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	public String getApplicantConsumerName() {
		return applicantConsumerName;
	}

	public void setApplicantConsumerName(String applicantConsumerName) {
		this.applicantConsumerName = applicantConsumerName;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	public String getResourceItem() {
		return resourceItem;
	}

	public void setResourceItem(String resourceItem) {
		this.resourceItem = resourceItem;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getProjCompletionDate() {
		return projCompletionDate;
	}

	public void setProjCompletionDate(String projCompletionDate) {
		this.projCompletionDate = projCompletionDate;
	}

	public String getContractOrganization() {
		return contractOrganization;
	}

	public void setContractOrganization(String contractOrganization) {
		this.contractOrganization = contractOrganization;
	}

	public String getFeederId() {
		return feederId;
	}

	public void setFeederId(String feederId) {
		this.feederId = feederId;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Integer getItemQty() {
		return itemQty;
	}

	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}

	public Integer getScheduleQty() {
		return scheduleQty;
	}

	public void setScheduleQty(Integer scheduleQty) {
		this.scheduleQty = scheduleQty;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public String getContractorSubId() {
		return contractorSubId;
	}

	public void setContractorSubId(String contractorSubId) {
		this.contractorSubId = contractorSubId;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Double getProjectTotalCost() {
		return projectTotalCost;
	}

	public void setProjectTotalCost(Double projectTotalCost) {
		this.projectTotalCost = projectTotalCost;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getProjCreationDate() {
		return projCreationDate;
	}

	public void setProjCreationDate(String projCreationDate) {
		this.projCreationDate = projCreationDate;
	}

	public String getItemCost() {
		return itemCost;
	}

	public void setItemCost(String itemCost) {
		this.itemCost = itemCost;
	}

	public String getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(String projStartDate) {
		this.projStartDate = projStartDate;
	}

	public Double getTaskTotalCost() {
		return taskTotalCost;
	}

	public void setTaskTotalCost(Double taskTotalCost) {
		this.taskTotalCost = taskTotalCost;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getContractorNumber() {
		return contractorNumber;
	}

	public void setContractorNumber(String contractorNumber) {
		this.contractorNumber = contractorNumber;
	}

	public String getCentages() {
		return centages;
	}

	public void setCentages(String centages) {
		this.centages = centages;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getOaDate() {
		return oaDate;
	}

	public void setOaDate(String oaDate) {
		this.oaDate = oaDate;
	}

	public Integer getLaborTransChg() {
		return laborTransChg;
	}

	public void setLaborTransChg(Integer laborTransChg) {
		this.laborTransChg = laborTransChg;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getBaselinedDate() {
		return baselinedDate;
	}

	public void setBaselinedDate(String baselinedDate) {
		this.baselinedDate = baselinedDate;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getScheduleUom() {
		return scheduleUom;
	}

	public void setScheduleUom(String scheduleUom) {
		this.scheduleUom = scheduleUom;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Double getTaskItemCost() {
		return taskItemCost;
	}

	public void setTaskItemCost(Double taskItemCost) {
		this.taskItemCost = taskItemCost;
	}

	public Long getSbProjectId() {
		return sbProjectId;
	}

	public void setSbProjectId(Long sbProjectId) {
		this.sbProjectId = sbProjectId;
	}

}
