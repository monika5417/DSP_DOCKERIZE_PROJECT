package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.AmountMatch;

public interface AmountMatchRepository extends JpaRepository<AmountMatch, Long>{

}
