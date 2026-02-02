package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Captcha;

@Repository
public interface CaptchaRepository extends JpaRepository<Captcha, Long> {

	@Query(value = "SELECT * FROM CAPTCHA WHERE CAPTCHA_ID = :captchaId and IS_ACTIVE = 1 ", nativeQuery = true)
	Optional<Captcha> findByCaptchaId(@Param("captchaId") Long captchaId);

}
