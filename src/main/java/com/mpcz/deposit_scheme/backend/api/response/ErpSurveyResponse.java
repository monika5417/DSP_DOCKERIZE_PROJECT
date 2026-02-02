package com.mpcz.deposit_scheme.backend.api.response;

import java.util.List;
import java.util.Map;

import com.mpcz.deposit_scheme.backend.api.domain.KV11_LINE;
import com.mpcz.deposit_scheme.backend.api.domain.Kva25Dtr;
import com.mpcz.deposit_scheme.backend.api.domain.Kva63Dtr;;

public class ErpSurveyResponse {
	private List<Kva25Dtr> kva25DtrList;

	private List<Kva63Dtr> kva63DtrList;

	private List<Map<String, Object>> KV11_LINE;

	private List<Map<String, Object>> ltLINE;
	
	private List<Map<String, Object>> tappingDp;
	
	
	
	
	public List<Map<String, Object>> getTappingDp() {
		return tappingDp;
	}

	public void setTappingDp(List<Map<String, Object>> tappingDp) {
		this.tappingDp = tappingDp;
	}

	public List<Map<String, Object>> getLtLINE() {
		return ltLINE;
	}

	public void setLtLINE(List<Map<String, Object>> ltLINE) {
		this.ltLINE = ltLINE;
	}

	public List<Map<String, Object>> getKV11_LINE() {
		return KV11_LINE;
	}

	public void setKV11_LINE(List<Map<String, Object>> kV11_LINE) {
		KV11_LINE = kV11_LINE;
	}

	public List<Kva63Dtr> getKva63DtrList() {
		return kva63DtrList;
	}

	public void setKva63DtrList(List<Kva63Dtr> kva63DtrList) {
		this.kva63DtrList = kva63DtrList;
	}

	public List<Kva25Dtr> getKva25DtrList() {
		return kva25DtrList;
	}

	public void setKva25DtrList(List<Kva25Dtr> kva25DtrList) {
		this.kva25DtrList = kva25DtrList;
	}

}
