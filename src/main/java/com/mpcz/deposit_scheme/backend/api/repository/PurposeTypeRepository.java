/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.PurposeType;

/**
 * @author Monika Rajpoot
 * @since 12-Aug-2025
 * @description PurposeTypeRepository.java class description
 */

public interface PurposeTypeRepository extends JpaRepository<PurposeType, Long> {

}
