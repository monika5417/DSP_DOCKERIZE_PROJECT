package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "ReSampling")
@Table(name = "RE_SAMPLING")
public class ReSampling {

	@Id
	@SequenceGenerator(name = "RE_SAMPLING_SEQ", sequenceName = "RE_SAMPLING_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RE_SAMPLING_SEQ")
	@Column(name = "ID",columnDefinition = "ID",updatable = true)
	private Long id;

	@Column(name = "CON_APP_NO", columnDefinition = "conAppNo", updatable = true)
	private String conAppNo;

	@Column(name = "TOTAL_NO_OF_DTR", columnDefinition = "totalNoOfDtr", updatable = true)
	private int totalNoOfDtr;

	@Column(name = "VENDOR_NAME", columnDefinition = "vendorName", updatable = false)
	private String vendorName;

	@Column(name = "SERIAL_NO", columnDefinition = "serialNo", updatable = true)
	private String serialNo;

	@Column(name = "INVOICE_NO", columnDefinition = "invoiceNO", updatable = true)
	private String invoiceNO;

	@Column(name = "YEAR_OF_MANUFACTURE", columnDefinition = "yearOfManufacture", updatable = true)
	private String yearOfManufacture;

	@Column(name = "CAPACITY_OF_DTR", columnDefinition = "capacityOfDtr", updatable = true)
	private Long capacityOfDtr;

	@Column(name = "AUTICATION_ID", columnDefinition = "auticationId", updatable = true)
	private String auticationId;
	               
	@Column(name = "SHUFFLING", columnDefinition = "shuffling", updatable = true)
	private  String shuffling ;

	@Column(name = "CIRCLE_NAME", columnDefinition = "circleName", updatable = true)
	private String circleName;
	
	
	

	@Column(name = "PARANT_APPLICATION_NO", columnDefinition = "PARANT_APPLICATION_NO", updatable = true)
	private  String ParantApplicationNo ;

	@Column(name = "CHILD_APPLICATION_NO", columnDefinition = "childApplicationNo", updatable = true)
	private String childApplicationNo;

	@Column(name = "SHUFFLING_FLAG", columnDefinition = "childApplicationNo", updatable = true)
	private Long shufflingFlag;

	@Column(name = "ITEM_NO", columnDefinition = "childApplicationNo", updatable = true)
	private String itemNo;
	
	
	@Column(name = "MATERIAL_NAME", columnDefinition = "materialName", updatable = true)
	private String materialName;

	@Column(name = "MATERIAL_SPECIFICATION", columnDefinition = "MeterialSpecification", updatable = true)
	private String meterialSpecification;
	
	@Column(name = "CAPACITY_OF_PTR", columnDefinition = "capacityOfPtr", updatable = true)
	private String capacityOfPtr;
	
	@Column(name = "TOTAL_NO_OF_PTR", columnDefinition = "totalNoOfDtr", updatable = true)
	private int totalNoOfPtr;
//............................................................................
	
	@Column(name = "DIVISION_NAME", columnDefinition = "divisionName", updatable = true)
	private String divisionName;
	
	@Column(name = "DC_NAME", columnDefinition = "DC_NAME", updatable = true)
	private String DC_NAME;
	
	
	@Column(name = "CONSUMER_NAME")
	private String consumerName;
	
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "CON_AUTHEN_NO")
	private String contractorAuthenticationNo;
	
	@Column(name = "WORK_ORDER_NO")
	private String workOrderNumber;
	
	
	@Column(name = "MATERIAL_RECIVED_IN_LAB")
	private String materialRecivedInLab;
	
	@Column(name = "CIRCLE_ID")
	private Long circleId;

	@Column(name = "DTR_PASS_OR_FAIL")
	private String dtrPassOrFail;

	@Column(name = "APPLICAION_ID")
	private Long applicationId;
	

	
	@Column(name = "INSTALATION_DATE")
	private String instalationDate;
	
	@Column(name = "REGION_ID")
	private Long regionId;
	
	@Column(name = "REGION_NAME")
	private String regionName;
	
	
	@Column (name = "TRF_GEN_DATE")
	private String Date;
	
	
	@Column(name="DTR_ACCEPTED_OR")
	private String dtrAcceptOrNot;
	
	@Column(name="TA_ACCEPET_DATE")
	private String TaAcceptDtrOrNotDate;
	
	
	@Column(name="REJECT_REMARK")
	private String remark;
	
	@Column(name="REJECT_REMARK_DGM")
	private String remarkDGM;
	
	@Column(name="CONTRACTOR_NAME")
	private String contractorName;
	
	
	@Column (name = "RIV_GATE_PASS_DATE")
	private String gatPassDate;
	
	
	@Column (name = "DTR_LEFTING_DATE")
	private String dtrLeftingDate;
	
	@Column(name = "DIVISION_ID")
	private Long divisionId;
	
	

	
	
	public Long getDivisionId() {
		return divisionId;
	}



	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}



	public String getDtrLeftingDate() {
		return dtrLeftingDate;
	}



	public void setDtrLeftingDate(String dtrLeftingDate) {
		this.dtrLeftingDate = dtrLeftingDate;
	}



	public String getContractorName() {
		return contractorName;
	}



	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}



	public String getGatPassDate() {
		return gatPassDate;
	}



	public void setGatPassDate(String gatPassDate) {
		this.gatPassDate = gatPassDate;
	}



	public String getRemarkDGM() {
		return remarkDGM;
	}



	public void setRemarkDGM(String remarkDGM) {
		this.remarkDGM = remarkDGM;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getTaAcceptDtrOrNotDate() {
		return TaAcceptDtrOrNotDate;
	}



	public void setTaAcceptDtrOrNotDate(String taAcceptDtrOrNotDate) {
		TaAcceptDtrOrNotDate = taAcceptDtrOrNotDate;
	}



	public String getDtrAcceptOrNot() {
		return dtrAcceptOrNot;
	}



	public void setDtrAcceptOrNot(String dtrAcceptOrNot) {
		this.dtrAcceptOrNot = dtrAcceptOrNot;
	}



	public String getDate() {
		return Date;
	}



	public void setDate(String date) {
		Date = date;
	}



	public Long getRegionId() {
		return regionId;
	}



	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}



	public String getInstalationDate() {
		return instalationDate;
	}



	public void setInstalationDate(String instalationDate) {
		this.instalationDate = instalationDate;
	}



	public Long getCircleId() {
		return circleId;
	}



	public Long getApplicationId() {
		return applicationId;
	}



	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}



	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}



	public String getMaterialRecivedInLab() {
		return materialRecivedInLab;
	}



	public void setMaterialRecivedInLab(String materialRecivedInLab) {
		this.materialRecivedInLab = materialRecivedInLab;
	}



	public String getDtrPassOrFail() {
		return dtrPassOrFail;
	}



	public void setDtrPassOrFail(String dtrPassOrFail) {
		this.dtrPassOrFail = dtrPassOrFail;
	}






	public String getDivisionName() {
		return divisionName;
	}



	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}



	public String getDC_NAME() {
		return DC_NAME;
	}



	public void setDC_NAME(String dC_NAME) {
		DC_NAME = dC_NAME;
	}



	public String getConsumerName() {
		return consumerName;
	}



	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getContractorAuthenticationNo() {
		return contractorAuthenticationNo;
	}



	public void setContractorAuthenticationNo(String contractorAuthenticationNo) {
		this.contractorAuthenticationNo = contractorAuthenticationNo;
	}



	public String getWorkOrderNumber() {
		return workOrderNumber;
	}



	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getConAppNo() {
		return conAppNo;
	}



	public void setConAppNo(String conAppNo) {
		this.conAppNo = conAppNo;
	}



	public int getTotalNoOfDtr() {
		return totalNoOfDtr;
	}



	public void setTotalNoOfDtr(int totalNoOfDtr) {
		this.totalNoOfDtr = totalNoOfDtr;
	}



	public String getVendorName() {
		return vendorName;
	}



	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}



	public String getSerialNo() {
		return serialNo;
	}



	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}



	public String getInvoiceNO() {
		return invoiceNO;
	}



	public void setInvoiceNO(String invoiceNO) {
		this.invoiceNO = invoiceNO;
	}



	public String getYearOfManufacture() {
		return yearOfManufacture;
	}



	public void setYearOfManufacture(String yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}



	public Long getCapacityOfDtr() {
		return capacityOfDtr;
	}



	public void setCapacityOfDtr(Long capacityOfDtr) {
		this.capacityOfDtr = capacityOfDtr;
	}



	public String getAuticationId() {
		return auticationId;
	}



	public void setAuticationId(String auticationId) {
		this.auticationId = auticationId;
	}



	public String getShuffling() {
		return shuffling;
	}



	public void setShuffling(String shuffling) {
		this.shuffling = shuffling;
	}



	public String getCircleName() {
		return circleName;
	}



	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}



	public String getParantApplicationNo() {
		return ParantApplicationNo;
	}



	public void setParantApplicationNo(String parantApplicationNo) {
		ParantApplicationNo = parantApplicationNo;
	}



	public String getChildApplicationNo() {
		return childApplicationNo;
	}



	public void setChildApplicationNo(String childApplicationNo) {
		this.childApplicationNo = childApplicationNo;
	}



	public Long getShufflingFlag() {
		return shufflingFlag;
	}



	public void setShufflingFlag(Long shufflingFlag) {
		this.shufflingFlag = shufflingFlag;
	}



	public String getItemNo() {
		return itemNo;
	}



	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}



	



	public String getMaterialName() {
		return materialName;
	}



	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}






	public String getMeterialSpecification() {
		return meterialSpecification;
	}



	public void setMeterialSpecification(String meterialSpecification) {
		this.meterialSpecification = meterialSpecification;
	}



	public String getCapacityOfPtr() {
		return capacityOfPtr;
	}



	public void setCapacityOfPtr(String capacityOfPtr) {
		this.capacityOfPtr = capacityOfPtr;
	}



	public int getTotalNoOfPtr() {
		return totalNoOfPtr;
	}



	public void setTotalNoOfPtr(int totalNoOfPtr) {
		this.totalNoOfPtr = totalNoOfPtr;
	}



	@Override
	public String toString() {
		return "ReSampling [id=" + id + ", conAppNo=" + conAppNo + ", totalNoOfDtr=" + totalNoOfDtr + ", vendorName="
				+ vendorName + ", serialNo=" + serialNo + ", invoiceNO=" + invoiceNO + ", yearOfManufacture="
				+ yearOfManufacture + ", capacityOfDtr=" + capacityOfDtr + ", auticationId=" + auticationId
				+ ", shuffling=" + shuffling + ", circleName=" + circleName + ", ParantApplicationNo="
				+ ParantApplicationNo + ", childApplicationNo=" + childApplicationNo + ", shufflingFlag="
				+ shufflingFlag + ", itemNo=" + itemNo + ", materialName=" + materialName + ", meterialSpecification="
				+ meterialSpecification + ", capacityOfPtr=" + capacityOfPtr + ", totalNoOfPtr=" + totalNoOfPtr
				+ ", divisionName=" + divisionName + ", DC_NAME=" + DC_NAME + ", consumerName=" + consumerName
				+ ", address=" + address + ", contractorAuthenticationNo=" + contractorAuthenticationNo
				+ ", workOrderNumber=" + workOrderNumber + ", materialRecivedInLab=" + materialRecivedInLab
				+ ", circleId=" + circleId + ", dtrPassOrFail=" + dtrPassOrFail + ", applicationId=" + applicationId
				+ "]";
	}



	public String getRegionName() {
		return regionName;
	}



	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	
	
	
}
