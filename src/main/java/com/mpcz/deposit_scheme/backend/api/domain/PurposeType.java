/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Monika Rajpoot
 * @since 12-Aug-2025
 * @description PurposeType.java class description
 */

@Table(name = "PURPOSE_TYPE")
@Entity
public class PurposeType extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PURPOSE_TYPE_SEQ", sequenceName = "PURPOSE_TYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PURPOSE_TYPE_SEQ")
	@Column(name="PURPOSE_ID",columnDefinition = "serial", updatable = false)
	private Long purposeId;
	
	@Column(name="PURPOSE_TYPE_NAME",columnDefinition = "serial", updatable = false)
	private String purposeTypeName;
	
	@Column(name="ENTITY_TYPE",columnDefinition = "serial", updatable = false)
	private String entityType;

	
	
	
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Long getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(Long purposeId) {
		this.purposeId = purposeId;
	}

	public String getPurposeTypeName() {
		return purposeTypeName;
	}

	public void setPurposeTypeName(String purposeTypeName) {
		this.purposeTypeName = purposeTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
