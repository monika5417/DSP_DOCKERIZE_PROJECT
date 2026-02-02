package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * @author user
 * @version 1.0
 */

@Entity(name = "Role")
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@Where(clause = "is_deleted=false")
@Table(name = "role" )
public @Data class Role extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="role_seq")
	@SequenceGenerator(name="role_seq",sequenceName="role_seq",allocationSize=1)
	@Column(name = "role_id")
	private Long roleId;

	
	@Column(name = "role")
	private String role;

	@Column(name = "description")
	private String description;
	
	@Column(name = "role_code")
	private String roleCode;
	
	
	@Column(name="add_action")
	private Boolean addAction;
	
	
	@Column(name="remove_action")
	private Boolean removeAction;
	

	@Column(name="update_action")
	private Boolean updateAction;
	

	@Column(name="view_action")
	private Boolean viewAction;
	
	@JsonIgnore
	@Column(name="cancel_action")
	private Boolean cancelAction;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "userRoles", cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
        }, fetch = FetchType.EAGER)
	List<User> users= new ArrayList<>();

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Boolean getAddAction() {
		return addAction;
	}

	public void setAddAction(Boolean addAction) {
		this.addAction = addAction;
	}

	public Boolean getRemoveAction() {
		return removeAction;
	}

	public void setRemoveAction(Boolean removeAction) {
		this.removeAction = removeAction;
	}

	public Boolean getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(Boolean updateAction) {
		this.updateAction = updateAction;
	}

	public Boolean getViewAction() {
		return viewAction;
	}

	public void setViewAction(Boolean viewAction) {
		this.viewAction = viewAction;
	}

	public Boolean getCancelAction() {
		return cancelAction;
	}

	public void setCancelAction(Boolean cancelAction) {
		this.cancelAction = cancelAction;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
