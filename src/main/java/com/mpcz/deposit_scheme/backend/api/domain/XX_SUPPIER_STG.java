package com.mpcz.deposit_scheme.backend.api.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "XX_SUPPIER_STG")
public class XX_SUPPIER_STG {

    @Id
    @Column(name = "VENDOR_ID", nullable = false)
    private Long vendorId; // Manually set karna hoga

    @Column(name = "ATTRIBUTE14", length = 150)
    private String attribute14;

    @Column(name = "ATTRIBUTE15", length = 150)
    private String attribute15;

    @Column(name = "PAY_GROUP_LOOKUP_CODE", length = 100)
    private String payGroupLookupCode;

    @Column(name = "INVOICE_CURRENCY_CODE", length = 100)
    private String invoiceCurrencyCode;

    @Column(name = "PAYMENT_CURRENCY_CODE", length = 100)
    private String paymentCurrencyCode;

    @Column(name = "SUMMARY_FLAG", length = 10)
    private String summaryFlag;

    @Column(name = "ENABLED_FLAG", length = 10)
    private String enabledFlag;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "ERROR_CODE", length = 100)
    private String errorCode;

    @Column(name = "ERROR_MESSAGE", length = 100)
    private String errorMessage;

    @Column(name = "PROCESS_FLAG", length = 1)
    private String processFlag;

    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;

    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private Long lastUpdateLogin;

    @Column(name = "VENDOR_NAME", length = 2000)
    private String vendorName;

    @Column(name = "VENDOR_TYPE_LOOKUP_CODE", length = 100)
    private String vendorTypeLookupCode;

    @Column(name = "SHIP_TO_LOCATION_CODE", length = 100)
    private String shipToLocationCode;

    @Column(name = "BILL_TO_LOCATION_CODE", length = 100)
    private String billToLocationCode;

    @Column(name = "TERMS_NAME", length = 100)
    private String termsName;

    @Column(name = "TAX_VERIFICATION_DATE", length = 100)
    private String taxVerificationDate;

    @Column(name = "VAT_REGISTRATION_NUM", length = 100)
    private String vatRegistrationNum;

    @Column(name = "ATTRIBUTE1", length = 150)
    private String attribute1;

    @Column(name = "ATTRIBUTE2", length = 150)
    private String attribute2;

    @Column(name = "ATTRIBUTE3", length = 150)
    private String attribute3;

    @Column(name = "ATTRIBUTE4", length = 150)
    private String attribute4;

    @Column(name = "ATTRIBUTE5", length = 150)
    private String attribute5;

    @Column(name = "ATTRIBUTE6", length = 150)
    private String attribute6;

    @Column(name = "ATTRIBUTE7", length = 150)
    private String attribute7;

    @Column(name = "ATTRIBUTE8", length = 150)
    private String attribute8;

    @Column(name = "ATTRIBUTE9", length = 150)
    private String attribute9;

    @Column(name = "ATTRIBUTE10", length = 150)
    private String attribute10;

    @Column(name = "ATTRIBUTE11", length = 150)
    private String attribute11;

    @Column(name = "ATTRIBUTE12", length = 150)
    private String attribute12;

    @Column(name = "ATTRIBUTE13", length = 150)
    private String attribute13;

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	public String getAttribute15() {
		return attribute15;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	public String getPayGroupLookupCode() {
		return payGroupLookupCode;
	}

	public void setPayGroupLookupCode(String payGroupLookupCode) {
		this.payGroupLookupCode = payGroupLookupCode;
	}

	public String getInvoiceCurrencyCode() {
		return invoiceCurrencyCode;
	}

	public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
		this.invoiceCurrencyCode = invoiceCurrencyCode;
	}

	public String getPaymentCurrencyCode() {
		return paymentCurrencyCode;
	}

	public void setPaymentCurrencyCode(String paymentCurrencyCode) {
		this.paymentCurrencyCode = paymentCurrencyCode;
	}

	public String getSummaryFlag() {
		return summaryFlag;
	}

	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorTypeLookupCode() {
		return vendorTypeLookupCode;
	}

	public void setVendorTypeLookupCode(String vendorTypeLookupCode) {
		this.vendorTypeLookupCode = vendorTypeLookupCode;
	}

	public String getShipToLocationCode() {
		return shipToLocationCode;
	}

	public void setShipToLocationCode(String shipToLocationCode) {
		this.shipToLocationCode = shipToLocationCode;
	}

	public String getBillToLocationCode() {
		return billToLocationCode;
	}

	public void setBillToLocationCode(String billToLocationCode) {
		this.billToLocationCode = billToLocationCode;
	}

	public String getTermsName() {
		return termsName;
	}

	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}

	public String getTaxVerificationDate() {
		return taxVerificationDate;
	}

	public void setTaxVerificationDate(String taxVerificationDate) {
		this.taxVerificationDate = taxVerificationDate;
	}

	public String getVatRegistrationNum() {
		return vatRegistrationNum;
	}

	public void setVatRegistrationNum(String vatRegistrationNum) {
		this.vatRegistrationNum = vatRegistrationNum;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}
    
    
    
}
