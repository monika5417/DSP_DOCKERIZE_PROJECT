package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "DYNAMIC_QUERIES")
public class DynamicQuery {

    @Id
    private Long id;

    @Column(name = "QUERY_NAME")
    private String queryName;

    @Lob
    @Column(name = "QUERY_TEXT")
    private String queryText;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;
    
    @Column(name = "PARAM_KEYS")
    private String paramKeys;

    
    
    
	public String getParamKeys() {
		return paramKeys;
	}

	public void setParamKeys(String paramKeys) {
		this.paramKeys = paramKeys;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

    // Getters & setters
    
    
}
