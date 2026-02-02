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
@Table(name = "XXAP_DSP_INV_HDR_STG") // yaha synonym ka naam dena hai jo db link se linked hai
public class XXAP_DSP_INV_HDR_STG {

    @Id
    @SequenceGenerator(name = "XXAP_DSP_INV_HDR_STG_SEQ", sequenceName = "XXAP_DSP_INV_HDR_STG_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXAP_DSP_INV_HDR_STG_SEQ")
    @Column(name = "INVOICE_ID") // Assuming INVOICE_ID is primary key
    private Long invoiceId;

    @Column(name = "DESCRIPTION", length = 240)
    private String description;

    @Column(name = "VOUCHER_NUM", length = 20)
    private String voucherNum;

    @Column(name = "SOURCE", length = 240)
    private String source;

    @Column(name = "PROCESSED_FLAG", length = 1)
    private String processedFlag;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "CALC_TAX_DURING_IMPORT_FLAG", length = 1)
    private String calcTaxDuringImportFlag;

    @Column(name = "ADD_TAX_TO_INV_AMT_FLAG", length = 1)
    private String addTaxToInvAmtFlag;

    @Column(name = "OPERATING_UNIT", length = 255, nullable = false)
    private String operatingUnit;

    @Column(name = "TERMS_NAME", length = 240, nullable = false)
    private String termsName;

    @Column(name = "INVOICE_NUM", length = 240, nullable = false)
    private String invoiceNum;

    @Column(name = "INVOICE_TYPE_LOOKUP_CODE", length = 240)
    private String invoiceTypeLookupCode;

    @Column(name = "VENDOR_ID")
    private Long vendorId;

    @Column(name = "VENDOR_SITE_ID")
    private Long vendorSiteId;

    @Column(name = "INVOICE_AMOUNT")
    private Long invoiceAmount;

    @Column(name = "INVOICE_DATE")
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @Column(name = "INVOICE_CURRENCY_CODE", length = 3)
    private String invoiceCurrencyCode;

    // ✅ Getters and Setters
    public Long getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getVoucherNum() {
        return voucherNum;
    }
    public void setVoucherNum(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getProcessedFlag() {
        return processedFlag;
    }
    public void setProcessedFlag(String processedFlag) {
        this.processedFlag = processedFlag;
    }

    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getCalcTaxDuringImportFlag() {
        return calcTaxDuringImportFlag;
    }
    public void setCalcTaxDuringImportFlag(String calcTaxDuringImportFlag) {
        this.calcTaxDuringImportFlag = calcTaxDuringImportFlag;
    }

    public String getAddTaxToInvAmtFlag() {
        return addTaxToInvAmtFlag;
    }
    public void setAddTaxToInvAmtFlag(String addTaxToInvAmtFlag) {
        this.addTaxToInvAmtFlag = addTaxToInvAmtFlag;
    }

    public String getOperatingUnit() {
        return operatingUnit;
    }
    public void setOperatingUnit(String operatingUnit) {
        this.operatingUnit = operatingUnit;
    }

    public String getTermsName() {
        return termsName;
    }
    public void setTermsName(String termsName) {
        this.termsName = termsName;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }
    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceTypeLookupCode() {
        return invoiceTypeLookupCode;
    }
    public void setInvoiceTypeLookupCode(String invoiceTypeLookupCode) {
        this.invoiceTypeLookupCode = invoiceTypeLookupCode;
    }

    public Long getVendorId() {
        return vendorId;
    }
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorSiteId() {
        return vendorSiteId;
    }
    public void setVendorSiteId(Long vendorSiteId) {
        this.vendorSiteId = vendorSiteId;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }
    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }
    public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }
}
