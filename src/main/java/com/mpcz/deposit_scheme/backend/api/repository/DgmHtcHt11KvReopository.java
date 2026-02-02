package com.mpcz.deposit_scheme.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.DgmHtcHt11Kv;

@Repository
public interface DgmHtcHt11KvReopository extends JpaRepository<DgmHtcHt11Kv, Long> {

}

