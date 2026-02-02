package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * <h1>User Designation Class!</h1> Used to manage designation master.
 * 
 * @author Aditya Vyas
 * @version 1.0
 * 
 */
@Entity(name = "Designation")
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users") 
@Table(name = "designation")
public @Data class Designation extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "designation_seq", sequenceName = "designation_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "designation_seq")
	@Column(name = "designation_id", columnDefinition = "serial", updatable = false)
	private Long designationId;

		@Column(name = "designation")
	private String designation;

	@Column(name = "designation_short_form")
	private String designationShortForm;
	
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userDesignation", cascade = CascadeType.ALL)
	private Set<User> users;

}
