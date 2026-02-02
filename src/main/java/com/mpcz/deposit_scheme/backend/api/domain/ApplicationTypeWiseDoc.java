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
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "ApplicationTypeWiseDoc")
@Table(name = "APPLICATION_TYPE_WISE_DOC")
public @Data class ApplicationTypeWiseDoc extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPLICATION_TYPE_WISE_DOC_SEQ", sequenceName = "APPLICATION_TYPE_WISE_DOC_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_TYPE_WISE_DOC_SEQ")
	@Column(name = "APPLICATION_TYPE_WISE_DOC_ID", columnDefinition = "serial", updatable = false)
	private Long ApplicationTypeWiseDocId;

	@ManyToOne
	@JoinColumn(name = "APPLICATION_TYPE_ID", referencedColumnName = "APPLICATION_TYPE_ID")
	private ApplicationType applicationType;

	@ManyToOne
	@JoinColumn(name = "DOCUMENT_TYPE_ID", referencedColumnName = "DOCUMENT_TYPE_ID")
	private DocumentType documentType;

	public Long getApplicationTypeWiseDocId() {
		return ApplicationTypeWiseDocId;
	}

	public void setApplicationTypeWiseDocId(Long applicationTypeWiseDocId) {
		ApplicationTypeWiseDocId = applicationTypeWiseDocId;
	}

	public ApplicationType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(ApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
