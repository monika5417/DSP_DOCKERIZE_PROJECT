package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.domain.ListDistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.ListDivision;

public class UpdateUser {


	
	private List<ListDistributionCenter> listdistributionCenter;

	private List<ListDivision> listDivision;

	public List<ListDistributionCenter> getListdistributionCenter() {
		return listdistributionCenter;
	}

	public void setListdistributionCenter(List<ListDistributionCenter> listdistributionCenter) {
		this.listdistributionCenter = listdistributionCenter;
	}

	public List<ListDivision> getListDivision() {
		return listDivision;
	}

	public void setListDivision(List<ListDivision> listDivision) {
		this.listDivision = listDivision;
	}



}


