package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.SamagraList;

public interface SamagraListRepository  extends JpaRepository<SamagraList, Long> {

	List<SamagraList> findByConsumerApplicationNo(String consumerApplicationNo);

}
