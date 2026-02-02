package com.mpcz.deposit_scheme.backend.api.response;

import java.io.Serializable;
import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;

import lombok.Data;

public @Data class PageConsumerApplicationDetailDTO implements Serializable {
	
	List<ConsumerApplicationDetail> list;
	Meta meta;
	public List<ConsumerApplicationDetail> getList() {
		return list;
	}
	public void setList(List<ConsumerApplicationDetail> list) {
		this.list = list;
	}
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
