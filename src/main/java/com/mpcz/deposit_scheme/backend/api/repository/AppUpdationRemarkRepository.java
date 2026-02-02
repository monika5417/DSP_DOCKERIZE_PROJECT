package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.AppUpdationRemark;

public interface AppUpdationRemarkRepository extends JpaRepository<AppUpdationRemark, Long> {

	List<AppUpdationRemark> findByConsumerAppNo(String consumerAppNo);

}
