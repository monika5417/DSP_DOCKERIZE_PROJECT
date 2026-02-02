package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "Upload")
@Table(name = "UPLOAD")

public @Data class Upload extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "UPLOAD_SEQ", sequenceName = "UPLOAD_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPLOAD_SEQ")
	@Column(name = "UPLOAD_ID", columnDefinition = "serial", updatable = false)
	private Long UploadId;

	@Column(name = "DOCUMENT_NO")
	private String documentNo;

	@ManyToOne
	@JoinColumn(name = "DOCUMENT_TYPE_ID", referencedColumnName = "DOCUMENT_TYPE_ID")
	private DocumentType documentType;

	@Column(name = "DOCUMENT_PATH")
	private String documentPath;

	@Column(name = "DOCUMENT_PUBLIC_PATH")
	private String documentPublicPath;

	@JsonIgnore
	@Column(name = "DOCUMENT_SIZE")
	private Long documentSize;

	@Column(name = "DOCUMENT_NAME")
	private String documentName;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOAD_DATE")
	private Date uploadDate;
	
	@Column(name = "consuemr_Applicaton_ID")
	private Long consuemrApplicatonID;

	@Column(name="DOCUMENT_STATUS")
	private Long documentStatus;
	
	
	public Long getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(Long documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Long getUploadId() {
		return UploadId;
	}

	public void setUploadId(Long uploadId) {
		UploadId = uploadId;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getDocumentPublicPath() {
		return documentPublicPath;
	}

	public void setDocumentPublicPath(String documentPublicPath) {
		this.documentPublicPath = documentPublicPath;
	}

	public Long getDocumentSize() {
		return documentSize;
	}

	public void setDocumentSize(Long documentSize) {
		this.documentSize = documentSize;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Long getConsuemrApplicatonID() {
		return consuemrApplicatonID;
	}

	public void setConsuemrApplicatonID(Long consuemrApplicatonID) {
		this.consuemrApplicatonID = consuemrApplicatonID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
