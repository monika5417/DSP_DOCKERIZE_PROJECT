package com.mpcz.deposit_scheme.backend.api.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.DemandNoteAllowedStatus;

@Repository
public interface DemandNoteAllowedStatusRepository
        extends JpaRepository<DemandNoteAllowedStatus, Long> {

    @Query("SELECT d.statusId FROM DemandNoteAllowedStatus d WHERE d.isActive = 'Y'")
    List<Long> findActiveAllowedStatusIds();
}
