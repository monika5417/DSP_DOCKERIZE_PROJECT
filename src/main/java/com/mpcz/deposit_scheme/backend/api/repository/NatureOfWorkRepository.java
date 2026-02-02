package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;

@Repository
public interface NatureOfWorkRepository extends JpaRepository<NatureOfWork, Long> {

	

}
