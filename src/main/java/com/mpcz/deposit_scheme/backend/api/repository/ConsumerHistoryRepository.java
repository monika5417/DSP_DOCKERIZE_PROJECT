package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerHistory;

@Repository
public interface ConsumerHistoryRepository extends JpaRepository<ConsumerHistory, Long> {

}
