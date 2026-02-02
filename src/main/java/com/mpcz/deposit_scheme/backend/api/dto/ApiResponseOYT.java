package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.List;

public class ApiResponseOYT {
//    private List<OytMaterialChargesDTO > data;
    private List<OytProjectDetails1Dto > data;
    private Object list;
    private String statusCode;
    private String message;
    private Object error;

    // Getters and setters
   

    public Object getList() {
        return list;
    }

//    public List<OytMaterialChargesDTO> getData() {
//		return data;
//	}
//
//	public void setData(List<OytMaterialChargesDTO> data) {
//		this.data = data;
//	}

    
	public void setList(Object list) {
        this.list = list;
    }

    public List<OytProjectDetails1Dto> getData() {
		return data;
	}

	public void setData(List<OytProjectDetails1Dto> data) {
		this.data = data;
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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
