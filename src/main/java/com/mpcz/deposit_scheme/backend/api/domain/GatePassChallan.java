package com.mpcz.deposit_scheme.backend.api.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GATE_PASS_CHALLAN")
public class GatePassChallan {

    @Id
    @SequenceGenerator(name = "GATE_PASS_CHALLAN_SEQ", sequenceName = "GATE_PASS_CHALLAN_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GATE_PASS_CHALLAN_SEQ")
    @Column(name = "GATE_PASS_CHALLAN_ID")
    private Long id;

    @Column(name = "OUTWARD_QUANTITY")
    private Long outwardQuantity;

    @Column(name = "MANUAL_DI_NO")
    private String manualDINo;

    @Column(name = "NAME_OF_ENTITY")
    private String nameOfEntity;

    @Column(name = "LOA_ORDER_NO")
    private String loaOrderNo;

    @Column(name = "LOA_ORDER_DATE")
    @Temporal(TemporalType.DATE)
    private Date loaOrderDate;

    
    
//    ...................................
    @Column(name = "ITEM_NAME")
    private String itemnName;
    
    @Column(name = "ITEM_CODE")
    private String itemCode;
    
    
    @Column(name = "MATERIAL_TYPE")
    private String materialType;
    
    @Column(name = "SERIAL_NO")
    private String serialNo;
   
    @Column(name = "DTR_CAPACITY")
    private String dtrCapacity;
    
    @Column(name = "INVOICE_NO")
    private String invoiceNo;
    
    @Column(name = "Y_O_M")
    private String yearOfMenufacture;
    
    @Column(name = "WORK_ORDER_DATE")
    private String workOrderDate;
    
    
//    ?......................................
    
    public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleNNumber() {
		return vehicleNNumber;
	}

	public void setVehicleNNumber(String vehicleNNumber) {
		this.vehicleNNumber = vehicleNNumber;
	}

	@Column(name = "MANUFACTURER_FIRM_NAME")
    private String manufacturerFirmName;

    @Column(name = "DESCRIPTION_OF_ITEM")
    private String descriptionOfItem;

    @Column(name = "VEHICLE_NAME")
    private String vehicleName;
    
    @Column(name = "VEHICLE_NUMER")
    private String vehicleNNumber;

    @Column(name = "DRIVER_NAME")
    private String driverName;

    @Column(name = "DI_NO")
    private String diNo;

    @Column(name = "DRIVER_CONTACT_NO")
    private String driverContactNo;

    @Column(name = "ISSUED_TO")
    private String issuedTo;

    @Column(name = "ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Column(name = "RECEIVER_ENTITY_NAME")
    private String receiverEntityName;

    @Column(name = "RECEIVER_DETAILS")
    private String receiverDetails;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    @Column(name = "CONTACT_NO")
    private String contactNo;

    @Column(name = "CONSUMER_APPLICATION_NUMBER")
    private String consumerApplicationNumber;
//    .............................................
    
    @Column(name = "DIVISION_NAME", columnDefinition = "divisionName", updatable = true)
	private String divisionName;
	
	@Column(name = "DC_NAME", columnDefinition = "DC_NAME", updatable = true)
	private String DC_NAME;
	
	
	@Column(name = "CONSUMER_NAME", columnDefinition = "consumerName", updatable = true)
	private String consumerName;
	
	
	@Column(name = "ADDRESS", columnDefinition = "address", updatable = true)
	private String address;
	
	@Column(name = "CON_AUTHEN_NO", columnDefinition = "contractorAuthenticationNo", updatable = true)
	private String contractorAuthenticationNo;
	
	@Column(name = "WORK_ORDER_NO", columnDefinition = "workOrderNumber", updatable = true)
	private String workOrderNumber;
    
	
	@Column(name = "CIRCLE", columnDefinition = "circleName", updatable = true)
	private String circleName ;
	
	
	@Column(name = "VENDOR_NAME", columnDefinition = "vendorName", updatable = true)
	private String vendorName ;
	
	@Column(name = "NISTHA_LAB", columnDefinition = "nisthaLab", updatable = true)
	private String nisthaLab ;
	
	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
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

	public Long getOutwardQuantity() {
		return outwardQuantity;
	}

	public void setOutwardQuantity(Long outwardQuantity) {
		this.outwardQuantity = outwardQuantity;
	}

	public String getManualDINo() {
		return manualDINo;
	}

	public void setManualDINo(String manualDINo) {
		this.manualDINo = manualDINo;
	}

	public String getNameOfEntity() {
		return nameOfEntity;
	}

	public void setNameOfEntity(String nameOfEntity) {
		this.nameOfEntity = nameOfEntity;
	}

	public String getLoaOrderNo() {
		return loaOrderNo;
	}

	public void setLoaOrderNo(String loaOrderNo) {
		this.loaOrderNo = loaOrderNo;
	}

	public Date getLoaOrderDate() {
		return loaOrderDate;
	}

	public void setLoaOrderDate(Date loaOrderDate) {
		this.loaOrderDate = loaOrderDate;
	}



	public String getManufacturerFirmName() {
		return manufacturerFirmName;
	}

	public void setManufacturerFirmName(String manufacturerFirmName) {
		this.manufacturerFirmName = manufacturerFirmName;
	}

	public String getDescriptionOfItem() {
		return descriptionOfItem;
	}

	public void setDescriptionOfItem(String descriptionOfItem) {
		this.descriptionOfItem = descriptionOfItem;
	}



	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDiNo() {
		return diNo;
	}

	public void setDiNo(String diNo) {
		this.diNo = diNo;
	}

	public String getDriverContactNo() {
		return driverContactNo;
	}

	public void setDriverContactNo(String driverContactNo) {
		this.driverContactNo = driverContactNo;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getReceiverEntityName() {
		return receiverEntityName;
	}

	public void setReceiverEntityName(String receiverEntityName) {
		this.receiverEntityName = receiverEntityName;
	}

	public String getReceiverDetails() {
		return receiverDetails;
	}

	public void setReceiverDetails(String receiverDetails) {
		this.receiverDetails = receiverDetails;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getItemnName() {
		return itemnName;
	}

	public void setItemnName(String itemnName) {
		this.itemnName = itemnName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDtrCapacity() {
		return dtrCapacity;
	}

	public void setDtrCapacity(String dtrCapacity) {
		this.dtrCapacity = dtrCapacity;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getYearOfMenufacture() {
		return yearOfMenufacture;
	}

	public void setYearOfMenufacture(String yearOfMenufacture) {
		this.yearOfMenufacture = yearOfMenufacture;
	}

	public String getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getNisthaLab() {
		return nisthaLab;
	}

	public void setNisthaLab(String nisthaLab) {
		this.nisthaLab = nisthaLab;
	}

    // Getters and Setters ...
    
    
	 @Column(name = "GATE_PASS_OR_REV")
	private String gatePassOrReverseGatePass;


	public String getGatePassOrReverseGatePass() {
		return gatePassOrReverseGatePass;
	}

	public void setGatePassOrReverseGatePass(String gatePassOrReverseGatePass) {
		this.gatePassOrReverseGatePass = gatePassOrReverseGatePass;
	}
	
	
}
