package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.CheckUser;

public interface CheckUserRepository extends JpaRepository<CheckUser, Integer> {

	CheckUser findByUserId(String userId);
	

}
