package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.ListDistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.ListDivision;

@Repository
public interface ListDistributionCenterRepository extends JpaRepository<ListDistributionCenter, Integer> {

	public List<ListDistributionCenter> findByuserId(String userid);

}
