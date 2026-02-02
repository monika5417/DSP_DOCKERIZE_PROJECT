package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "OYT_PROJECT_DETAILS")
public class OytProjectDetails extends Auditable<Long> {

	@Id
	@SequenceGenerator(name = "OYT_PROJECT_DETAILS_SEQ", sequenceName = "OYT_PROJECT_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYT_PROJECT_DETAILS_SEQ")
    private Long id;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "SCHEME_CODE")
    private String schemeCode;

    @Column(name = "PROJ_COMPLETION_DATE")
    private Long projCompletionDate;

    @Column(name = "LABOR_TRANS_CHG")
    private String laborTransChg;

    @Column(name = "TOTAL_COST")
    private BigDecimal totalCost;

    @Column(name = "CONTRACTOR_NAME")
    private String contractorName;

    @Column(name = "ITEM_COST")
    private BigDecimal itemCost;

    @Column(name = "PROJECT_STATUS")
    private String projectStatus;

    @Column(name = "CERTIFICATE")
    private String certificate;

    @Column(name = "RESOURCE_ITEM")
    private String resourceItem;

    @Column(name = "APPLICANT_CONSUMER_NAME")
    private String applicantConsumerName;

    @Column(name = "DC_NAME")
    private String dcName;

    @Column(name = "SCHEDULE_NO")
    private String scheduleNo;

    @Column(name = "TASK_ITEM_COST")
    private BigDecimal taskItemCost;

    @Column(name = "ESTIMATE_NO")
    private String estimateNo;

    @Column(name = "TASK_NUMBER")
    private String taskNumber;

    @Column(name = "PROJECT_NUMBER")
    private String projectNumber;

    @Column(name = "PROJ_START_DATE")
    private Long projStartDate;

    @Column(name = "PRJ_OWNING_ORG")
    private String prjOwningOrg;

    @Column(name = "PROJ_CREATION_DATE")
    private Long projCreationDate;

    @Column(name = "WORK_TYPE")
    private String workType;

    @Column(name = "SANCTIONED_BY")
    private String sanctionedBy;

    @Column(name = "RATE")
    private String rate;

    @Column(name = "PROJECT_ITEM_COST")
    private BigDecimal projectItemCost;

    @Column(name = "BASELINED_DATE")
    private Long baselinedDate;

    @Column(name = "PROJECT_TYPE")
    private String projectType;

    @Column(name = "TASK_TOTAL_COST")
    private BigDecimal taskTotalCost;

    @Column(name = "SCHEME_DESC")
    private String schemeDesc;

    @Column(name = "ITEM_UOM")
    private String itemUom;

    @Column(name = "PROJECT_TOTAL_COST")
    private BigDecimal projectTotalCost;

    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "ITEM_DESC")
    private String itemDesc;

    @Column(name = "CONTRACTOR_NUMBER")
    private String contractorNumber;

    @Column(name = "CENTAGES")
    private String centages;

    @Column(name = "CONTRACT_ORGANIZATION")
    private String contractOrganization;

    @Column(name = "OA_DATE")
    private String oaDate;

    @Column(name = "SCHEDULE_QTY")
    private String scheduleQty;

    @Column(name = "EXP_TYPE")
    private String expType;

    @Column(name = "CPY_PROV_FLAG")
    private String cpyProvFlag;

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @Column(name = "PURCHASE_ORDER")
    private String purchaseOrder;

    @Column(name = "SCHEDULE_UOM")
    private String scheduleUom;

    @Column(name = "LONG_NAME", length = 1000)
    private String longName;

    @Column(name = "REMARKS", length = 1000)
    private String remarks;

    @Column(name = "TECH_VIABILITY")
    private String techViability;

    @Column(name = "ITEM_QTY")
    private Integer itemQty;

    @Column(name = "PROJECT_DESC", length = 1000)
    private String projectDesc;

    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "CONSUMER_APPLICATION_NO")
    private String consumerApplicationNo;

    @Column(name = "ERP_NO")
    private String erpNo;
    
    @Column(name = "ITEM_CODE_FLAG")
    private Integer itemCodeFlag = 0;
    // 👉 You can generate getters/setters via IDE or add Lombok annotations
    
    @Column(name = "OYT_M_T_C_W_C_S")
	private BigDecimal oytMaterialTotalCostWithCgstAndSgst;
    
    @Column(name = "OYT_M_C_W_CGST")
   	private BigDecimal oytMaterialCostWithCgst;
    
    @Column(name = "OYT_M_C_W_SGST")
   	private BigDecimal oytMaterialCostWithSgst;
    
    @Column(name = "RATE_WITH_T_C_S")
   	private BigDecimal rateWithTotalCgstAndSgst;
    
    

	public BigDecimal getRateWithTotalCgstAndSgst() {
		return rateWithTotalCgstAndSgst;
	}


	public void setRateWithTotalCgstAndSgst(BigDecimal rateWithTotalCgstAndSgst) {
		this.rateWithTotalCgstAndSgst = rateWithTotalCgstAndSgst;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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


	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
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


	public Integer getItemQty() {
		return itemQty;
	}


	public void setItemQty(Integer itemQty) {
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


	public Integer getItemCodeFlag() {
		return itemCodeFlag;
	}


	public void setItemCodeFlag(Integer itemCodeFlag) {
		this.itemCodeFlag = itemCodeFlag;
	}


	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}


	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}


	public String getErpNo() {
		return erpNo;
	}


	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}


	
	
	
	
	public BigDecimal getOytMaterialTotalCostWithCgstAndSgst() {
		return oytMaterialTotalCostWithCgstAndSgst;
	}


	public void setOytMaterialTotalCostWithCgstAndSgst(BigDecimal oytMaterialTotalCostWithCgstAndSgst) {
		this.oytMaterialTotalCostWithCgstAndSgst = oytMaterialTotalCostWithCgstAndSgst;
	}


	public BigDecimal getOytMaterialCostWithCgst() {
		return oytMaterialCostWithCgst;
	}


	public void setOytMaterialCostWithCgst(BigDecimal oytMaterialCostWithCgst) {
		this.oytMaterialCostWithCgst = oytMaterialCostWithCgst;
	}


	public BigDecimal getOytMaterialCostWithSgst() {
		return oytMaterialCostWithSgst;
	}


	public void setOytMaterialCostWithSgst(BigDecimal oytMaterialCostWithSgst) {
		this.oytMaterialCostWithSgst = oytMaterialCostWithSgst;
	}


	@Override
	public String toString() {
		return "OytProjectDetails [id=" + id + ", taskName=" + taskName + ", schemeCode=" + schemeCode
				+ ", projCompletionDate=" + projCompletionDate + ", laborTransChg=" + laborTransChg + ", totalCost="
				+ totalCost + ", contractorName=" + contractorName + ", itemCost=" + itemCost + ", projectStatus="
				+ projectStatus + ", certificate=" + certificate + ", resourceItem=" + resourceItem
				+ ", applicantConsumerName=" + applicantConsumerName + ", dcName=" + dcName + ", scheduleNo="
				+ scheduleNo + ", taskItemCost=" + taskItemCost + ", estimateNo=" + estimateNo + ", taskNumber="
				+ taskNumber + ", projectNumber=" + projectNumber + ", projStartDate=" + projStartDate
				+ ", prjOwningOrg=" + prjOwningOrg + ", projCreationDate=" + projCreationDate + ", workType=" + workType
				+ ", sanctionedBy=" + sanctionedBy + ", rate=" + rate + ", projectItemCost=" + projectItemCost
				+ ", baselinedDate=" + baselinedDate + ", projectType=" + projectType + ", taskTotalCost="
				+ taskTotalCost + ", schemeDesc=" + schemeDesc + ", itemUom=" + itemUom + ", projectTotalCost="
				+ projectTotalCost + ", version=" + version + ", itemDesc=" + itemDesc + ", contractorNumber="
				+ contractorNumber + ", centages=" + centages + ", contractOrganization=" + contractOrganization
				+ ", oaDate=" + oaDate + ", scheduleQty=" + scheduleQty + ", expType=" + expType + ", cpyProvFlag="
				+ cpyProvFlag + ", contractNumber=" + contractNumber + ", purchaseOrder=" + purchaseOrder
				+ ", scheduleUom=" + scheduleUom + ", longName=" + longName + ", remarks=" + remarks
				+ ", techViability=" + techViability + ", itemQty=" + itemQty + ", projectDesc=" + projectDesc
				+ ", projectId=" + projectId + ", consumerApplicationNo=" + consumerApplicationNo + ", erpNo=" + erpNo
				+ ", itemCodeFlag=" + itemCodeFlag + "]";
	}
    
	
    
}
