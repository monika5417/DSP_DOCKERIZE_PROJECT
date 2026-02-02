package com.mpcz.deposit_scheme.backend.api.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpcz.deposit_scheme.backend.api.domain.SspOutbox;

public interface SspOutboxRepository extends JpaRepository<SspOutbox, Long> {

    List<SspOutbox> findTop10ByStatusOrderByCreatedAtAsc(String status);

}
