package com.mpcz.deposit_scheme.backend.api.dto;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OytProjectDetails1Dto {
	
	
	@JsonProperty("ID")
	private Long id;

	
    @JsonProperty("TASK_NAME")
    private String taskName;

    @JsonProperty("SCHEME_CODE")
    private String schemeCode;

    @JsonProperty("PROJ_COMPLETION_DATE")
    private Long projCompletionDate;

    @JsonProperty("LABOR_TRANS_CHG")
    private String laborTransChg;

    @JsonProperty("TOTAL_COST")
    private BigDecimal totalCost;

    @JsonProperty("CONTRACTOR_NAME")
    private String contractorName;

    @JsonProperty("ITEM_COST")
    private BigDecimal itemCost;

    @JsonProperty("PROJECT_STATUS")
    private String projectStatus;

    @JsonProperty("CERTIFICATE")
    private String certificate;

    @JsonProperty("RESOURCE_ITEM")
    private String resourceItem;

    @JsonProperty("APPLICANT_CONSUMER_NAME")
    private String applicantConsumerName;

    @JsonProperty("DC_NAME")
    private String dcName;

    @JsonProperty("SCHEDULE_NO")
    private String scheduleNo;

    @JsonProperty("TASK_ITEM_COST")
    private BigDecimal taskItemCost;

    @JsonProperty("ESTIMATE_NO")
    private String estimateNo;

    @JsonProperty("TASK_NUMBER")
    private String taskNumber;

    @JsonProperty("PROJECT_NUMBER")
    private String projectNumber;

    @JsonProperty("PROJ_START_DATE")
    private Long projStartDate;

    @JsonProperty("PRJ_OWNING_ORG")
    private String prjOwningOrg;

    @JsonProperty("PROJ_CREATION_DATE")
    private Long projCreationDate;

    @JsonProperty("WORK_TYPE")
    private String workType;

    @JsonProperty("SANCTIONED_BY")
    private String sanctionedBy;

    @JsonProperty("RATE")
    private BigDecimal rate;

    @JsonProperty("PROJECT_ITEM_COST")
    private BigDecimal projectItemCost;

    @JsonProperty("BASELINED_DATE")
    private Long baselinedDate;

    @JsonProperty("PROJECT_TYPE")
    private String projectType;

    @JsonProperty("TASK_TOTAL_COST")
    private BigDecimal taskTotalCost;

    @JsonProperty("SCHEME_DESC")
    private String schemeDesc;

    @JsonProperty("ITEM_UOM")
    private String itemUom;

    @JsonProperty("PROJECT_TOTAL_COST")
    private BigDecimal projectTotalCost;

    @JsonProperty("VERSION")
    private Integer version;

    @JsonProperty("ITEM_DESC")
    private String itemDesc;

    @JsonProperty("CONTRACTOR_NUMBER")
    private String contractorNumber;

    @JsonProperty("CENTAGES")
    private String centages;

    @JsonProperty("CONTRACT_ORGANIZATION")
    private String contractOrganization;

    @JsonProperty("OA_DATE")
    private String oaDate;

    @JsonProperty("SCHEDULE_QTY")
    private String scheduleQty;

    @JsonProperty("EXP_TYPE")
    private String expType;

    @JsonProperty("CPY_PROV_FLAG")
    private String cpyProvFlag;

    @JsonProperty("CONTRACT_NUMBER")
    private String contractNumber;

    @JsonProperty("PURCHASE_ORDER")
    private String purchaseOrder;

    @JsonProperty("SCHEDULE_UOM")
    private String scheduleUom;

    @JsonProperty("LONG_NAME")
    private String longName;

    @JsonProperty("REMARKS")
    private String remarks;

    @JsonProperty("TECH_VIABILITY")
    private String techViability;

    @JsonProperty("ITEM_QTY")
    private BigDecimal itemQty;

    @JsonProperty("PROJECT_DESC")
    private String projectDesc;

    @JsonProperty("PROJECT_ID")
    private Long projectId;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public Long getProjCompletionDate() {
		return projCompletionDate;
	}

	public void setProjCompletionDate(Long projCompletionDate) {
		this.projCompletionDate = projCompletionDate;
	}

	public String getLaborTransChg() {
		return laborTransChg;
	}

	public void setLaborTransChg(String laborTransChg) {
		this.laborTransChg = laborTransChg;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public BigDecimal getItemCost() {
		return itemCost;
	}

	public void setItemCost(BigDecimal itemCost) {
		this.itemCost = itemCost;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getResourceItem() {
		return resourceItem;
	}

	public void setResourceItem(String resourceItem) {
		this.resourceItem = resourceItem;
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

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public BigDecimal getTaskItemCost() {
		return taskItemCost;
	}

	public void setTaskItemCost(BigDecimal taskItemCost) {
		this.taskItemCost = taskItemCost;
	}

	public String getEstimateNo() {
		return estimateNo;
	}

	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
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

	public Long getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(Long projStartDate) {
		this.projStartDate = projStartDate;
	}

	public String getPrjOwningOrg() {
		return prjOwningOrg;
	}

	public void setPrjOwningOrg(String prjOwningOrg) {
		this.prjOwningOrg = prjOwningOrg;
	}

	public Long getProjCreationDate() {
		return projCreationDate;
	}

	public void setProjCreationDate(Long projCreationDate) {
		this.projCreationDate = projCreationDate;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getSanctionedBy() {
		return sanctionedBy;
	}

	public void setSanctionedBy(String sanctionedBy) {
		this.sanctionedBy = sanctionedBy;
	}

	public BigDecimal getRate() {
		return rate;
	}



	
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getProjectItemCost() {
		return projectItemCost;
	}

	public void setProjectItemCost(BigDecimal projectItemCost) {
		this.projectItemCost = projectItemCost;
	}

	public Long getBaselinedDate() {
		return baselinedDate;
	}

	public void setBaselinedDate(Long baselinedDate) {
		this.baselinedDate = baselinedDate;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public BigDecimal getTaskTotalCost() {
		return taskTotalCost;
	}

	public void setTaskTotalCost(BigDecimal taskTotalCost) {
		this.taskTotalCost = taskTotalCost;
	}

	public String getSchemeDesc() {
		return schemeDesc;
	}

	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}

	public String getItemUom() {
		return itemUom;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	public BigDecimal getProjectTotalCost() {
		return projectTotalCost;
	}

	public void setProjectTotalCost(BigDecimal projectTotalCost) {
		this.projectTotalCost = projectTotalCost;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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

	public String getContractOrganization() {
		return contractOrganization;
	}

	public void setContractOrganization(String contractOrganization) {
		this.contractOrganization = contractOrganization;
	}

	public String getOaDate() {
		return oaDate;
	}

	public void setOaDate(String oaDate) {
		this.oaDate = oaDate;
	}

	public String getScheduleQty() {
		return scheduleQty;
	}

	public void setScheduleQty(String scheduleQty) {
		this.scheduleQty = scheduleQty;
	}

	public String getExpType() {
		return expType;
	}

	public void setExpType(String expType) {
		this.expType = expType;
	}

	public String getCpyProvFlag() {
		return cpyProvFlag;
	}

	public void setCpyProvFlag(String cpyProvFlag) {
		this.cpyProvFlag = cpyProvFlag;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getScheduleUom() {
		return scheduleUom;
	}

	public void setScheduleUom(String scheduleUom) {
		this.scheduleUom = scheduleUom;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTechViability() {
		return techViability;
	}

	public void setTechViability(String techViability) {
		this.techViability = techViability;
	}



	public BigDecimal getItemQty() {
		return itemQty;
	}

	public void setItemQty(BigDecimal itemQty) {
		this.itemQty = itemQty;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    // Getters and setters (you can generate via IDE like IntelliJ or Lombok)
    
    
}
