package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Kva25Dtr;

@Repository
public interface Kva25DtrRepository extends JpaRepository<Kva25Dtr, Integer> {

}
