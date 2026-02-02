package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "Document_Type")
@Table(name = "DOCUMENT_TYPE")

public @Data class DocumentType extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DOCUMENT_TYPE_SEQ", sequenceName = "DOCUMENT_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENT_TYPE_SEQ")
	@Column(name = "DOCUMENT_TYPE_ID", columnDefinition = "serial", updatable = false)
	private Long documentTypeId;

	@Column(name = "DOCUMENT_TYPE_NAME")
	private String documentTypeName;

	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
