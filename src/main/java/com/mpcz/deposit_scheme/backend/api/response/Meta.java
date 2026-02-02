package com.mpcz.deposit_scheme.backend.api.response;

import java.io.Serializable;

import lombok.Data;

public @Data class Meta implements Serializable {

	Integer currentPage;
	Long totalItems;
	Integer totalPages;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	

}
