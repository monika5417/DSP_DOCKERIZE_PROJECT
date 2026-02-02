package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.GatePassChallan;
import com.mpcz.deposit_scheme.backend.api.domain.MaterialDetail;
import com.mpcz.deposit_scheme.backend.api.domain.VerifierBy;
import com.mpcz.deposit_scheme.backend.api.domain.VerifierGatekeeper;
import com.mpcz.deposit_scheme.backend.api.domain.VerifierIssuingAuthority;

public class GatePassChallanDTO {
	
	
	private GatePassChallan gatePassChallan;
	
	private List<MaterialDetail> materialDetail;
	
	private VerifierGatekeeper verifierGatekeeper;
	
	private VerifierIssuingAuthority verifierIssuingAuthority;
	
	private VerifierBy verifierBy;
	

	public GatePassChallan getGatePassChallan() {
		return gatePassChallan;
	}

	public void setGatePassChallan(GatePassChallan gatePassChallan) {
		this.gatePassChallan = gatePassChallan;
	}

	

	public List<MaterialDetail> getMaterialDetail() {
		return materialDetail;
	}

	public void setMaterialDetail(List<MaterialDetail> materialDetail) {
		this.materialDetail = materialDetail;
	}

	public VerifierGatekeeper getVerifierGatekeeper() {
		return verifierGatekeeper;
	}

	public void setVerifierGatekeeper(VerifierGatekeeper verifierGatekeeper) {
		this.verifierGatekeeper = verifierGatekeeper;
	}

	public VerifierIssuingAuthority getVerifierIssuingAuthority() {
		return verifierIssuingAuthority;
	}

	public void setVerifierIssuingAuthority(VerifierIssuingAuthority verifierIssuingAuthority) {
		this.verifierIssuingAuthority = verifierIssuingAuthority;
	}

	public VerifierBy getVerifierBy() {
		return verifierBy;
	}

	public void setVerifierBy(VerifierBy verifierBy) {
		this.verifierBy = verifierBy;
	}
	
	

//    private Long id;
//    private int outwardQuantity;
//    private String manualDINo;
//    private String nameOfEntity;
//    private String loaOrderNo;
//    private Date loaOrderDate;
//    private String nameOfItem;
//    private String manufacturerFirmName;
//    private String descriptionOfItem;
//    private String vehicleNameNumber;
//    private String driverName;
//    private String diNo;
//    private String driverContactNo;
//    private String issuedTo;
//    private Date issueDate;
//
//    // Related Details
//    private List<MaterialDetailDTO> materialDetails;
//    private VerifierByDTO verifiedBy;
//    private VerifierGatekeeperDTO gatekeeper;
//    private VerifierIssuingAuthorityDTO issuingAuthority;
//
//    // Receiver Details
//    private String receiverEntityName;
//    private String receiverDetails;
//    private String contactPerson;
//    private String contactNo;
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public int getOutwardQuantity() {
//		return outwardQuantity;
//	}
//	public void setOutwardQuantity(int outwardQuantity) {
//		this.outwardQuantity = outwardQuantity;
//	}
//	public String getManualDINo() {
//		return manualDINo;
//	}
//	public void setManualDINo(String manualDINo) {
//		this.manualDINo = manualDINo;
//	}
//	public String getNameOfEntity() {
//		return nameOfEntity;
//	}
//	public void setNameOfEntity(String nameOfEntity) {
//		this.nameOfEntity = nameOfEntity;
//	}
//	public String getLoaOrderNo() {
//		return loaOrderNo;
//	}
//	public void setLoaOrderNo(String loaOrderNo) {
//		this.loaOrderNo = loaOrderNo;
//	}
//	public Date getLoaOrderDate() {
//		return loaOrderDate;
//	}
//	public void setLoaOrderDate(Date loaOrderDate) {
//		this.loaOrderDate = loaOrderDate;
//	}
//	public String getNameOfItem() {
//		return nameOfItem;
//	}
//	public void setNameOfItem(String nameOfItem) {
//		this.nameOfItem = nameOfItem;
//	}
//	public String getManufacturerFirmName() {
//		return manufacturerFirmName;
//	}
//	public void setManufacturerFirmName(String manufacturerFirmName) {
//		this.manufacturerFirmName = manufacturerFirmName;
//	}
//	public String getDescriptionOfItem() {
//		return descriptionOfItem;
//	}
//	public void setDescriptionOfItem(String descriptionOfItem) {
//		this.descriptionOfItem = descriptionOfItem;
//	}
//	public String getVehicleNameNumber() {
//		return vehicleNameNumber;
//	}
//	public void setVehicleNameNumber(String vehicleNameNumber) {
//		this.vehicleNameNumber = vehicleNameNumber;
//	}
//	public String getDriverName() {
//		return driverName;
//	}
//	public void setDriverName(String driverName) {
//		this.driverName = driverName;
//	}
//	public String getDiNo() {
//		return diNo;
//	}
//	public void setDiNo(String diNo) {
//		this.diNo = diNo;
//	}
//	public String getDriverContactNo() {
//		return driverContactNo;
//	}
//	public void setDriverContactNo(String driverContactNo) {
//		this.driverContactNo = driverContactNo;
//	}
//	public String getIssuedTo() {
//		return issuedTo;
//	}
//	public void setIssuedTo(String issuedTo) {
//		this.issuedTo = issuedTo;
//	}
//	public Date getIssueDate() {
//		return issueDate;
//	}
//	public void setIssueDate(Date issueDate) {
//		this.issueDate = issueDate;
//	}
//	public List<MaterialDetailDTO> getMaterialDetails() {
//		return materialDetails;
//	}
//	public void setMaterialDetails(List<MaterialDetailDTO> materialDetails) {
//		this.materialDetails = materialDetails;
//	}
//	public VerifierByDTO getVerifiedBy() {
//		return verifiedBy;
//	}
//	public void setVerifiedBy(VerifierByDTO verifiedBy) {
//		this.verifiedBy = verifiedBy;
//	}
//	public VerifierGatekeeperDTO getGatekeeper() {
//		return gatekeeper;
//	}
//	public void setGatekeeper(VerifierGatekeeperDTO gatekeeper) {
//		this.gatekeeper = gatekeeper;
//	}
//	public VerifierIssuingAuthorityDTO getIssuingAuthority() {
//		return issuingAuthority;
//	}
//	public void setIssuingAuthority(VerifierIssuingAuthorityDTO issuingAuthority) {
//		this.issuingAuthority = issuingAuthority;
//	}
//	public String getReceiverEntityName() {
//		return receiverEntityName;
//	}
//	public void setReceiverEntityName(String receiverEntityName) {
//		this.receiverEntityName = receiverEntityName;
//	}
//	public String getReceiverDetails() {
//		return receiverDetails;
//	}
//	public void setReceiverDetails(String receiverDetails) {
//		this.receiverDetails = receiverDetails;
//	}
//	public String getContactPerson() {
//		return contactPerson;
//	}
//	public void setContactPerson(String contactPerson) {
//		this.contactPerson = contactPerson;
//	}
//	public String getContactNo() {
//		return contactNo;
//	}
//	public void setContactNo(String contactNo) {
//		this.contactNo = contactNo;
//	}
//	@Override
//	public String toString() {
//		return "GatePassChallanDTO [id=" + id + ", outwardQuantity=" + outwardQuantity + ", manualDINo=" + manualDINo
//				+ ", nameOfEntity=" + nameOfEntity + ", loaOrderNo=" + loaOrderNo + ", loaOrderDate=" + loaOrderDate
//				+ ", nameOfItem=" + nameOfItem + ", manufacturerFirmName=" + manufacturerFirmName
//				+ ", descriptionOfItem=" + descriptionOfItem + ", vehicleNameNumber=" + vehicleNameNumber
//				+ ", driverName=" + driverName + ", diNo=" + diNo + ", driverContactNo=" + driverContactNo
//				+ ", issuedTo=" + issuedTo + ", issueDate=" + issueDate + ", materialDetails=" + materialDetails
//				+ ", verifiedBy=" + verifiedBy + ", gatekeeper=" + gatekeeper + ", issuingAuthority=" + issuingAuthority
//				+ ", receiverEntityName=" + receiverEntityName + ", receiverDetails=" + receiverDetails
//				+ ", contactPerson=" + contactPerson + ", contactNo=" + contactNo + ", getId()=" + getId()
//				+ ", getOutwardQuantity()=" + getOutwardQuantity() + ", getManualDINo()=" + getManualDINo()
//				+ ", getNameOfEntity()=" + getNameOfEntity() + ", getLoaOrderNo()=" + getLoaOrderNo()
//				+ ", getLoaOrderDate()=" + getLoaOrderDate() + ", getNameOfItem()=" + getNameOfItem()
//				+ ", getManufacturerFirmName()=" + getManufacturerFirmName() + ", getDescriptionOfItem()="
//				+ getDescriptionOfItem() + ", getVehicleNameNumber()=" + getVehicleNameNumber() + ", getDriverName()="
//				+ getDriverName() + ", getDiNo()=" + getDiNo() + ", getDriverContactNo()=" + getDriverContactNo()
//				+ ", getIssuedTo()=" + getIssuedTo() + ", getIssueDate()=" + getIssueDate() + ", getMaterialDetails()="
//				+ getMaterialDetails() + ", getVerifiedBy()=" + getVerifiedBy() + ", getGatekeeper()=" + getGatekeeper()
//				+ ", getIssuingAuthority()=" + getIssuingAuthority() + ", getReceiverEntityName()="
//				+ getReceiverEntityName() + ", getReceiverDetails()=" + getReceiverDetails() + ", getContactPerson()="
//				+ getContactPerson() + ", getContactNo()=" + getContactNo() + ", getClass()=" + getClass()
//				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
//	}
//
//    // Getters and Setters
//    // OR Use Lombok @Data if you prefer
//    
    
    
	
}
