package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "ApplicationDocument")
@Table(name = "APPLICATION_DOCUMENT")
public class ApplicationDocument extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPLICATION_DOCUMENT_SEQ", sequenceName = "APPLICATION_DOCUMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_DOCUMENT_SEQ")
	@Column(name = "APPLICATION_DOCUMENT_ID", columnDefinition = "serial", updatable = false)
	private Long applicationDocumentId;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumerApplicationDetail;

	@ManyToOne
	@JoinColumn(name = "DOC_ROW_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docRow;

	@ManyToOne
	@JoinColumn(name = "DOC_REGISTRY_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docRegistry;

	@ManyToOne
	@JoinColumn(name = "DOC_NAGAR_NIGAM_PERMISSION_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docNagarNigamPermission;

	@ManyToOne
	@JoinColumn(name = "DOC_DIC_OR_GUMASTA_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docDicOrGumasta;

	@ManyToOne
	@JoinColumn(name = "DOC_SHAPATH_PATRA_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docShapathPatra;

	@ManyToOne
	@JoinColumn(name = "DOC_TEST_REPORT_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docTestReport;

	@ManyToOne
	@JoinColumn(name = "DOC_T$CP_PERMISSION_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docT$cpPermission;

	@ManyToOne
	@JoinColumn(name = "DOC_RERA_PERMISSION_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docReraPermission;

	@ManyToOne
	@JoinColumn(name = "DOC_DRAWING_NOTARIZED_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docDrawingNotarized;

	@ManyToOne
	@JoinColumn(name = "DOC_COLONY_PRAKOSHTH_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docColonyPrakoshth;

	@ManyToOne
	@JoinColumn(name = "DOC_COLONY_LICENCE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docColonyLicence;

	@ManyToOne
	@JoinColumn(name = "DOC_LOAD_SHEET_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docLoadSheet;

	@ManyToOne
	@JoinColumn(name = "DOC_NOC_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docNoc;

	@ManyToOne
	@JoinColumn(name = "DOC_ALL_PAPER_NOTARIZED_SET_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docAllPaperNotarizedSet;

	@ManyToOne
	@JoinColumn(name = "DOC_COMMITTEE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docCommittee;

	@ManyToOne
	@JoinColumn(name = "DOC_DIVERSION_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docDiversion;

	@ManyToOne
	@JoinColumn(name = "DOC_NAZUL_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docNazul;

	@ManyToOne
	@JoinColumn(name = "DOC_MAP_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docMap;

	@ManyToOne
	@JoinColumn(name = "DOC_KHASRA_KHATONI_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docKhasraKhatoni;

	@ManyToOne
	@JoinColumn(name = "DOC_MAP_CIVIL_ENGINEER_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docMapCivilEngineer;

	@ManyToOne
	@JoinColumn(name = "DOC_APPLICATION_CONSENT_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docApplicationConsent;

	@ManyToOne
	@JoinColumn(name = "DOC_PERFORMA_5A_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docPerforma5a;

	@ManyToOne
	@JoinColumn(name = "DOC_PERFORMA_5B_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docPerforma5b;

	@ManyToOne
	@JoinColumn(name = "DOC_PLOT_AREA_DETAILS_OWNER_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docPlotAreaDetailsOwner;

	@ManyToOne
	@JoinColumn(name = "DOC_ADMINISTRATIVE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docAdministrative;

	@ManyToOne
	@JoinColumn(name = "DOC_GST_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docGst;

	@ManyToOne
	@JoinColumn(name = "DOC_ESTIMATE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docEstimate;

	@ManyToOne
	@JoinColumn(name = "DOC_GROUP_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docGroup;

	@ManyToOne
	@JoinColumn(name = "DOC_REJECTION_PROPOSAL_FILE_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docRejectProposalFilePath;

	@ManyToOne
	@JoinColumn(name = "DOC_GROUP_PEOPLE_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docGroupPeopleCommonFile;

	@ManyToOne
	@JoinColumn(name = "DOC_SAMAGRA_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docSamagraFile;

	@ManyToOne
	@JoinColumn(name = "DOC_ERP_REVISE_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docErpReviseFile;

	@ManyToOne
	@JoinColumn(name = "DOC_START_GEO_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docStartGeoFile;

	@ManyToOne
	@JoinColumn(name = "DOC_END_GEO_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docEndGeoFile;

	@ManyToOne
	@JoinColumn(name = "DOC_ENERGY_BILL_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docEnergyBillFile;

//	monika code regarding refund amount start  Date (20-july-2024)
	@ManyToOne
	@JoinColumn(name = "DOC_REFUND_LETTER_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docRefundLetterFile;

	@ManyToOne
	@JoinColumn(name = "DOC_CHECK_OR_PASSBOOK_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docCheckOrPassBookFile;

//	28-AUGUST-2024 added DOC_CONSUMER_REF_LETTER_FILE
	@ManyToOne
	@JoinColumn(name = "DOC_CONSUMER_REF_LETTER_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docConsumerRefundLetterFile;

//	30-August-2024 added DOC_CONTRACTOR_RES_FILE
	@ManyToOne
	@JoinColumn(name = "DOC_CONTRACTOR_RES_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docContractorReselctionFile;

//	end

//	17-jan-2025 added work order file and demand note file code start 
	@ManyToOne
	@JoinColumn(name = "DOC_WORK_ORDER_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docWorkOrderFile;

	@ManyToOne
	@JoinColumn(name = "DOC_DEMAND_NOTE_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docDemandNoteFile;

	@ManyToOne
	@JoinColumn(name = "DOC_PAN_NO_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docPanNoFile;

	@ManyToOne
	@JoinColumn(name = "DOC_ADDRESS_PROOF_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docAddressProofFile;

	@ManyToOne
	@JoinColumn(name = "DOC_NOTRY_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docNotry;

	@ManyToOne
	@JoinColumn(name = "DOC_GOV_AUTHORIZED_LETTER_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docGovAuthorizedLetterFile;

	@ManyToOne
	@JoinColumn(name = "DOC_MRA_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docMraFile;

	@ManyToOne
	@JoinColumn(name = "DOC_REQUEST_LETTER", referencedColumnName = "UPLOAD_ID")
	private Upload docRequestLetter;

//	04-Aug-2025
	@ManyToOne
	@JoinColumn(name = "DOC_DEBIT_SLIP_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docDebitSlipFile;

//	13-08-2025

	@ManyToOne
	@JoinColumn(name = "DOC_INSPECTION_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload docInspectionFile;
//	end

	@ManyToOne
	@JoinColumn(name = "DOC_DTR_FILE1", referencedColumnName = "UPLOAD_ID")
	private Upload docDtrFile1;

	@ManyToOne
	@JoinColumn(name = "DOC_DTR_FILE2", referencedColumnName = "UPLOAD_ID")
	private Upload docDtrFile2;

	@ManyToOne
	@JoinColumn(name = "GATE_PASS_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload getPassfile;

	@ManyToOne
	@JoinColumn(name = "TRF_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload trffile;

	@ManyToOne
	@JoinColumn(name = "TEST_REPORT", referencedColumnName = "UPLOAD_ID")
	private Upload testReportFile;

	@ManyToOne
	@JoinColumn(name = "CON_UPLOAD_PDF_FOR_REFUND_T", referencedColumnName = "UPLOAD_ID")
	private Upload consumerUploadPdfForRefundTime;

	@ManyToOne
	@JoinColumn(name = "DGM_UPLOAD_PDF_FOR_REFUND_T", referencedColumnName = "UPLOAD_ID")
	private Upload DgmUploadPdfForRefundTime;

	@ManyToOne
	@JoinColumn(name = "STC_UPLOAD_PDF_FOR_REFUND_T", referencedColumnName = "UPLOAD_ID")
	private Upload StcUploadPdfForRefundTime;

	@ManyToOne
	@JoinColumn(name = "GM_UPLOAD_PDF_FOR_REFUND_T", referencedColumnName = "UPLOAD_ID")
	private Upload gMUploadPdfForRefundTime;

	@ManyToOne
	@JoinColumn(name = "FIN_UPLOAD_PDF_FOR_REFUND_T", referencedColumnName = "UPLOAD_ID")
	private Upload financeUploadPdfForRefundTime;

	@ManyToOne
	@JoinColumn(name = "REV_GATE_PASS_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload revGetPassfile;

	@ManyToOne
	@JoinColumn(name = "DOC_DRAFT_AGREEMENT", referencedColumnName = "UPLOAD_ID")
	private Upload docDraftAgreement;
	
	@ManyToOne
	@JoinColumn(name = "CONSENT_LETTER", referencedColumnName = "UPLOAD_ID")
	private Upload consentletter;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID")
	private Consumer consumer;

	public Upload getDocDraftAgreement() {
		return docDraftAgreement;
	}

	public void setDocDraftAgreement(Upload docDraftAgreement) {
		this.docDraftAgreement = docDraftAgreement;
	}

	@ManyToOne
	@JoinColumn(name = "MOBILE_NUMBER_CHANGE_FILE", referencedColumnName = "UPLOAD_ID")
	private Upload mobileNumberChangeFile;

	public Upload getMobileNumberChangeFile() {
		return mobileNumberChangeFile;
	}

	public void setMobileNumberChangeFile(Upload mobileNumberChangeFile) {
		this.mobileNumberChangeFile = mobileNumberChangeFile;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Upload getRevGetPassfile() {
		return revGetPassfile;
	}

	public void setRevGetPassfile(Upload revGetPassfile) {
		this.revGetPassfile = revGetPassfile;
	}

	public Upload getConsumerUploadPdfForRefundTime() {
		return consumerUploadPdfForRefundTime;
	}

	public void setConsumerUploadPdfForRefundTime(Upload consumerUploadPdfForRefundTime) {
		this.consumerUploadPdfForRefundTime = consumerUploadPdfForRefundTime;
	}

	public Upload getDgmUploadPdfForRefundTime() {
		return DgmUploadPdfForRefundTime;
	}

	public void setDgmUploadPdfForRefundTime(Upload dgmUploadPdfForRefundTime) {
		DgmUploadPdfForRefundTime = dgmUploadPdfForRefundTime;
	}

	public Upload getStcUploadPdfForRefundTime() {
		return StcUploadPdfForRefundTime;
	}

	public void setStcUploadPdfForRefundTime(Upload stcUploadPdfForRefundTime) {
		StcUploadPdfForRefundTime = stcUploadPdfForRefundTime;
	}

	public Upload getgMUploadPdfForRefundTime() {
		return gMUploadPdfForRefundTime;
	}

	public void setgMUploadPdfForRefundTime(Upload gMUploadPdfForRefundTime) {
		this.gMUploadPdfForRefundTime = gMUploadPdfForRefundTime;
	}

	public Upload getFinanceUploadPdfForRefundTime() {
		return financeUploadPdfForRefundTime;
	}

	public void setFinanceUploadPdfForRefundTime(Upload financeUploadPdfForRefundTime) {
		this.financeUploadPdfForRefundTime = financeUploadPdfForRefundTime;
	}

	public Upload getDocWorkOrderFile() {
		return docWorkOrderFile;
	}

	public Upload getDocInspectionFile() {
		return docInspectionFile;
	}

	public void setDocInspectionFile(Upload docInspectionFile) {
		this.docInspectionFile = docInspectionFile;
	}

	public Upload getDocDebitSlipFile() {
		return docDebitSlipFile;
	}

	public void setDocDebitSlipFile(Upload docDebitSlipFile) {
		this.docDebitSlipFile = docDebitSlipFile;
	}

	public Upload getDocRequestLetter() {
		return docRequestLetter;
	}

	public void setDocRequestLetter(Upload docRequestLetter) {
		this.docRequestLetter = docRequestLetter;
	}

	public Upload getDocNotry() {
		return docNotry;
	}

	public void setDocNotry(Upload docNotry) {
		this.docNotry = docNotry;
	}

	public Upload getDocGovAuthorizedLetterFile() {
		return docGovAuthorizedLetterFile;
	}

	public void setDocGovAuthorizedLetterFile(Upload docGovAuthorizedLetterFile) {
		this.docGovAuthorizedLetterFile = docGovAuthorizedLetterFile;
	}

	public Upload getDocMraFile() {
		return docMraFile;
	}

	public void setDocMraFile(Upload docMraFile) {
		this.docMraFile = docMraFile;
	}

	public Upload getDocAddressProofFile() {
		return docAddressProofFile;
	}

	public void setDocAddressProofFile(Upload docAddressProofFile) {
		this.docAddressProofFile = docAddressProofFile;
	}

	public Upload getDocPanNoFile() {
		return docPanNoFile;
	}

	public void setDocPanNoFile(Upload docPanNoFile) {
		this.docPanNoFile = docPanNoFile;
	}

	public void setDocWorkOrderFile(Upload docWorkOrderFile) {
		this.docWorkOrderFile = docWorkOrderFile;
	}

	public Upload getDocDemandNoteFile() {
		return docDemandNoteFile;
	}

	public void setDocDemandNoteFile(Upload docDemandNoteFile) {
		this.docDemandNoteFile = docDemandNoteFile;
	}

	public Upload getDocRefundLetterFile() {
		return docRefundLetterFile;
	}

	public Upload getDocContractorReselctionFile() {
		return docContractorReselctionFile;
	}

	public void setDocContractorReselctionFile(Upload docContractorReselctionFile) {
		this.docContractorReselctionFile = docContractorReselctionFile;
	}

	public Upload getDocConsumerRefundLetterFile() {
		return docConsumerRefundLetterFile;
	}

	public void setDocConsumerRefundLetterFile(Upload docConsumerRefundLetterFile) {
		this.docConsumerRefundLetterFile = docConsumerRefundLetterFile;
	}

	public void setDocRefundLetterFile(Upload docRefundLetterFile) {
		this.docRefundLetterFile = docRefundLetterFile;
	}

	public Upload getDocCheckOrPassBookFile() {
		return docCheckOrPassBookFile;
	}

	public void setDocCheckOrPassBookFile(Upload docCheckOrPassBookFile) {
		this.docCheckOrPassBookFile = docCheckOrPassBookFile;
	}

	public Upload getDocEnergyBillFile() {
		return docEnergyBillFile;
	}

	public void setDocEnergyBillFile(Upload docEnergyBillFile) {
		this.docEnergyBillFile = docEnergyBillFile;
	}

	public Upload getDocStartGeoFile() {
		return docStartGeoFile;
	}

	public void setDocStartGeoFile(Upload docStartGeoFile) {
		this.docStartGeoFile = docStartGeoFile;
	}

	public Upload getDocEndGeoFile() {
		return docEndGeoFile;
	}

	public void setDocEndGeoFile(Upload docEndGeoFile) {
		this.docEndGeoFile = docEndGeoFile;
	}

	public Upload getDocErpReviseFile() {
		return docErpReviseFile;
	}

	public void setDocErpReviseFile(Upload docErpReviseFile) {
		this.docErpReviseFile = docErpReviseFile;
	}

	public Upload getDocSamagraFile() {
		return docSamagraFile;
	}

	public void setDocSamagraFile(Upload docSamagraFile) {
		this.docSamagraFile = docSamagraFile;
	}

	public Upload getDocGroupPeopleCommonFile() {
		return docGroupPeopleCommonFile;
	}

	public void setDocGroupPeopleCommonFile(Upload docGroupPeopleCommonFile) {
		this.docGroupPeopleCommonFile = docGroupPeopleCommonFile;
	}

	public Upload getDocRejectProposalFilePath() {
		return docRejectProposalFilePath;
	}

	public void setDocRejectProposalFilePath(Upload docRejectProposalFilePath) {
		this.docRejectProposalFilePath = docRejectProposalFilePath;
	}

	public Long getApplicationDocumentId() {
		return applicationDocumentId;
	}

	public void setApplicationDocumentId(Long applicationDocumentId) {
		this.applicationDocumentId = applicationDocumentId;
	}

	public ConsumerApplicationDetail getConsumerApplicationDetail() {
		return consumerApplicationDetail;
	}

	public void setConsumerApplicationDetail(ConsumerApplicationDetail consumerApplicationDetail) {
		this.consumerApplicationDetail = consumerApplicationDetail;
	}

	public Upload getDocRow() {
		return docRow;
	}

	public void setDocRow(Upload docRow) {
		this.docRow = docRow;
	}

	public Upload getDocRegistry() {
		return docRegistry;
	}

	public void setDocRegistry(Upload docRegistry) {
		this.docRegistry = docRegistry;
	}

	public Upload getDocNagarNigamPermission() {
		return docNagarNigamPermission;
	}

	public void setDocNagarNigamPermission(Upload docNagarNigamPermission) {
		this.docNagarNigamPermission = docNagarNigamPermission;
	}

	public Upload getDocDicOrGumasta() {
		return docDicOrGumasta;
	}

	public void setDocDicOrGumasta(Upload docDicOrGumasta) {
		this.docDicOrGumasta = docDicOrGumasta;
	}

	public Upload getDocShapathPatra() {
		return docShapathPatra;
	}

	public void setDocShapathPatra(Upload docShapathPatra) {
		this.docShapathPatra = docShapathPatra;
	}

	public Upload getDocTestReport() {
		return docTestReport;
	}

	public void setDocTestReport(Upload docTestReport) {
		this.docTestReport = docTestReport;
	}

	public Upload getDocT$cpPermission() {
		return docT$cpPermission;
	}

	public void setDocT$cpPermission(Upload docT$cpPermission) {
		this.docT$cpPermission = docT$cpPermission;
	}

	public Upload getDocReraPermission() {
		return docReraPermission;
	}

	public void setDocReraPermission(Upload docReraPermission) {
		this.docReraPermission = docReraPermission;
	}

	public Upload getDocDrawingNotarized() {
		return docDrawingNotarized;
	}

	public void setDocDrawingNotarized(Upload docDrawingNotarized) {
		this.docDrawingNotarized = docDrawingNotarized;
	}

	public Upload getDocColonyPrakoshth() {
		return docColonyPrakoshth;
	}

	public void setDocColonyPrakoshth(Upload docColonyPrakoshth) {
		this.docColonyPrakoshth = docColonyPrakoshth;
	}

	public Upload getDocColonyLicence() {
		return docColonyLicence;
	}

	public void setDocColonyLicence(Upload docColonyLicence) {
		this.docColonyLicence = docColonyLicence;
	}

	public Upload getDocLoadSheet() {
		return docLoadSheet;
	}

	public void setDocLoadSheet(Upload docLoadSheet) {
		this.docLoadSheet = docLoadSheet;
	}

	public Upload getDocNoc() {
		return docNoc;
	}

	public void setDocNoc(Upload docNoc) {
		this.docNoc = docNoc;
	}

	public Upload getDocAllPaperNotarizedSet() {
		return docAllPaperNotarizedSet;
	}

	public void setDocAllPaperNotarizedSet(Upload docAllPaperNotarizedSet) {
		this.docAllPaperNotarizedSet = docAllPaperNotarizedSet;
	}

	public Upload getDocCommittee() {
		return docCommittee;
	}

	public void setDocCommittee(Upload docCommittee) {
		this.docCommittee = docCommittee;
	}

	public Upload getDocDiversion() {
		return docDiversion;
	}

	public void setDocDiversion(Upload docDiversion) {
		this.docDiversion = docDiversion;
	}

	public Upload getDocNazul() {
		return docNazul;
	}

	public void setDocNazul(Upload docNazul) {
		this.docNazul = docNazul;
	}

	public Upload getDocMap() {
		return docMap;
	}

	public void setDocMap(Upload docMap) {
		this.docMap = docMap;
	}

	public Upload getDocKhasraKhatoni() {
		return docKhasraKhatoni;
	}

	public void setDocKhasraKhatoni(Upload docKhasraKhatoni) {
		this.docKhasraKhatoni = docKhasraKhatoni;
	}

	public Upload getDocMapCivilEngineer() {
		return docMapCivilEngineer;
	}

	public void setDocMapCivilEngineer(Upload docMapCivilEngineer) {
		this.docMapCivilEngineer = docMapCivilEngineer;
	}

	public Upload getDocApplicationConsent() {
		return docApplicationConsent;
	}

	public void setDocApplicationConsent(Upload docApplicationConsent) {
		this.docApplicationConsent = docApplicationConsent;
	}

	public Upload getDocPerforma5a() {
		return docPerforma5a;
	}

	public void setDocPerforma5a(Upload docPerforma5a) {
		this.docPerforma5a = docPerforma5a;
	}

	public Upload getDocPerforma5b() {
		return docPerforma5b;
	}

	public void setDocPerforma5b(Upload docPerforma5b) {
		this.docPerforma5b = docPerforma5b;
	}

	public Upload getDocPlotAreaDetailsOwner() {
		return docPlotAreaDetailsOwner;
	}

	public void setDocPlotAreaDetailsOwner(Upload docPlotAreaDetailsOwner) {
		this.docPlotAreaDetailsOwner = docPlotAreaDetailsOwner;
	}

	public Upload getDocAdministrative() {
		return docAdministrative;
	}

	public void setDocAdministrative(Upload docAdministrative) {
		this.docAdministrative = docAdministrative;
	}

	public Upload getDocGst() {
		return docGst;
	}

	public void setDocGst(Upload docGst) {
		this.docGst = docGst;
	}

	public Upload getDocEstimate() {
		return docEstimate;
	}

	public void setDocEstimate(Upload docEstimate) {
		this.docEstimate = docEstimate;
	}

	public Upload getDocGroup() {
		return docGroup;
	}

	public void setDocGroup(Upload docGroup) {
		this.docGroup = docGroup;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Upload getDocDtrFile1() {
		return docDtrFile1;
	}

	public void setDocDtrFile1(Upload docDtrFile1) {
		this.docDtrFile1 = docDtrFile1;
	}

	public Upload getDocDtrFile2() {
		return docDtrFile2;
	}

	public void setDocDtrFile2(Upload docDtrFile2) {
		this.docDtrFile2 = docDtrFile2;
	}

	public Upload getGetPassfile() {
		return getPassfile;
	}

	public void setGetPassfile(Upload getPassfile) {
		this.getPassfile = getPassfile;
	}

	public Upload getTrffile() {
		return trffile;
	}

	public void setTrffile(Upload trffile) {
		this.trffile = trffile;
	}

	public Upload getTestReportFile() {
		return testReportFile;
	}

	public void setTestReportFile(Upload testReportFile) {
		this.testReportFile = testReportFile;
	}

	public Upload getConsentletter() {
		return consentletter;
	}

	public void setConsentletter(Upload consentletter) {
		this.consentletter = consentletter;
	}

}
