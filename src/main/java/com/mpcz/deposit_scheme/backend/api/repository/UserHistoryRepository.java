/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.UserHistory;

/**
 * @author Amit
 *
 * 31-Aug-2020
 */
@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	public List<UserHistory> findByUserName(final String userName);
}
