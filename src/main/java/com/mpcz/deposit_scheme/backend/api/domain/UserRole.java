package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLE")
public class UserRole  extends Auditable<Long> {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "USER_ROLE_ID")
	    private Long userRoleId;

	  
	    @Column(name = "AD_USER_ID")
	    private Long user;

	  
	    @Column(name = "ROLE_ID")
	    private Long role;


		public Long getUserRoleId() {
			return userRoleId;
		}


		public void setUserRoleId(Long userRoleId) {
			this.userRoleId = userRoleId;
		}


		public Long getUser() {
			return user;
		}


		public void setUser(Long user) {
			this.user = user;
		}


		public Long getRole() {
			return role;
		}


		public void setRole(Long role) {
			this.role = role;
		}


}
