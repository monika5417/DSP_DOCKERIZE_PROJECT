package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Namdeo
 *
 *         13-09-2022
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(callSuper = false)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@CreatedBy
	@Column(name = "CREATED_BY")
	private U createdBy;

	@JsonIgnore
//	@JsonFormat(shape=Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss",timezone="IST")
	@JsonFormat(shape=Shape.STRING,pattern="dd-MM-yyyy",timezone="IST")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "CREATED")
	private Date created;

	@JsonIgnore
	@LastModifiedBy
	@Column(name = "UPDATED_BY")
	private U updatedBy;

	@JsonIgnore
	// @JsonFormat(shape=Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss",timezone="IST")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED")
	private Date updated;

	@JsonIgnore
	@Column(name = "IS_ACTIVE")
	protected boolean isActive = true;

	@JsonIgnore
	@Column(name = "IS_DELETED")
	private boolean isDeleted = false;

	public U getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public U getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(U updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}