package com.mpcz.deposit_scheme.backend.api.dto;
import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ProjectItemForZeroOneCase;


public class ApiResponseForZeroOrOnecaseDTO {
    private Object data;          // क्योंकि data null भी हो सकता है
    private List<ProjectItemForZeroOneCase> list;
    private String statusCode;
    private String message;
    private String error;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public List<ProjectItemForZeroOneCase> getList() {
		return list;
	}
	public void setList(List<ProjectItemForZeroOneCase> list) {
		this.list = list;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
    
    
    
}
