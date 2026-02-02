package com.mpcz.deposit_scheme.backend.api.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ContractorRemark;
import com.mpcz.deposit_scheme.backend.api.domain.DgmStcRemark;

@Repository
public interface DgmStcRemarkRepository extends JpaRepository<DgmStcRemark, Long> {

	List<DgmStcRemark> findByConsumerAppNo(String consumerAppNo);

	List<DgmStcRemark> findAllByConsumerAppNo(String consumerAppNo);


}
