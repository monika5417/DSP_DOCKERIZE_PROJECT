package com.mpcz.deposit_scheme.backend.api.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUP_ONLY_B_F_OYT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisionOnlyButNotForOyt extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "OYT_MATERIAL_CHARGES_SEQ", sequenceName = "OYT_MATERIAL_CHARGES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYT_MATERIAL_CHARGES_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
    private Long id;

    @Column(name = "PROJECT_ITEM_COST")
    private Double projectItemCost;

    @Column(name = "PO_LINE_ID")
    private Long poLineId;

    @Column(name = "PROJECT_DESC")
    private String projectDesc;

    @Column(name = "TECH_VIABILITY")
    private String techViability;

    @Column(name = "PRJ_OWNING_ORG")
    private String prjOwningOrg;

    @Column(name = "SCHEME_DESC")
    private String schemeDesc;

    @Column(name = "ESTIMATE_NO")
    private String estimateNo;

    @Column(name = "PROJECT_STATUS")
    private String projectStatus;

    @Column(name = "CONTRACTOR_NAME")
    private String contractorName;

    @Column(name = "PROJECT_UOM")
    private String projectUom;

    @Column(name = "ITEM_UOM")
    private String itemUom;

    @Column(name = "APPLICANT_CONSUMER_NAME")
    private String applicantConsumerName;

    @Column(name = "DC_NAME")
    private String dcName;

    @Column(name = "RESOURCE_ITEM")
    private String resourceItem;

    @Column(name = "SCHEME_CODE")
    private String schemeCode;

    @Column(name = "PROJ_COMPLETION_DATE")
    private String projCompletionDate;

    @Column(name = "CONTRACT_ORGANIZATION")
    private String contractOrganization;

    @Column(name = "FEEDER_ID")
    private String feederId;

    @Column(name = "CERTIFICATE")
    private String certificate;

    @Column(name = "ITEM_QTY")
    private Integer itemQty;

    @Column(name = "SCHEDULE_QTY")
    private Integer scheduleQty;

    @Column(name = "ITEM_DESC")
    private String itemDesc;

    @Column(name = "SCHEDULE_NO")
    private String scheduleNo;

    @Column(name = "CONTRACTOR_SUB_ID")
    private String contractorSubId;

    @Column(name = "LONG_NAME")
    private String longName;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "PROJECT_TOTAL_COST")
    private Double projectTotalCost;

    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @Column(name = "PROJ_CREATION_DATE")
    private String projCreationDate;

    @Column(name = "ITEM_COST")
    private String itemCost;

    @Column(name = "PROJ_START_DATE")
    private String projStartDate;

    @Column(name = "TASK_TOTAL_COST")
    private Double taskTotalCost;

    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "TOTAL_COST")
    private String totalCost;

    @Column(name = "CONTRACTOR_NUMBER")
    private String contractorNumber;

    @Column(name = "CENTAGES")
    private String centages;

    @Column(name = "PURCHASE_ORDER")
    private String purchaseOrder;

    @Column(name = "OA_DATE")
    private String oaDate;

    @Column(name = "LABOR_TRANS_CHG")
    private Double laborTransChg;

    @Column(name = "PROJECT_TYPE")
    private String projectType;

    @Column(name = "BASELINED_DATE")
    private String baselinedDate;

    @Column(name = "TASK_NUMBER")
    private String taskNumber;

    @Column(name = "PROJECT_NUMBER")
    private String projectNumber;

    @Column(name = "SCHEDULE_UOM")
    private String scheduleUom;

    @Column(name = "WORK_TYPE")
    private String workType;

    @Column(name = "TASK_ITEM_COST")
    private Double taskItemCost;

    @Column(name = "SB_PROJECT_ID")
    private Long sbProjectId;

    @Column(name = "CONSUMER_APPLICATION_NO")
    private String consumerApplicationNo;

    @Column(name = "ERP_NO")
    private String erpNo;
    
    @Column(name = "ITEM_CODE_FLAG")
    private Integer itemCodeFlag = 0;
    
    
    
    
	public Integer getItemCodeFlag() {
		return itemCodeFlag;
	}

	public void setItemCodeFlag(Integer itemCodeFlag) {
		this.itemCodeFlag = itemCodeFlag;
	}

	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getLaborTransChg() {
		return laborTransChg;
	}

	public void setLaborTransChg(Double laborTransChg) {
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

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    
}
