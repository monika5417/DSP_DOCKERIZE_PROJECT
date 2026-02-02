package com.mpcz.deposit_scheme.backend.api.services.impl;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.repository.SequenceRepository;

@Repository
public class SequenceRepositoryImpl implements SequenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getNextRefSequence() {
        return (String) entityManager
            .createNativeQuery("SELECT LPAD(DSP_REF_SEQ.NEXTVAL, 2, '0') FROM dual")
            .getSingleResult();
    }
}
