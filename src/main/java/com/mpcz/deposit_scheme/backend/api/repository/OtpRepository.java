package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Otp;
import com.mpcz.deposit_scheme.backend.api.domain.OtpId;

@Repository
public interface OtpRepository extends JpaRepository<Otp, OtpId> {

	public Optional<Otp> findByOtpId(OtpId otpId);

}
