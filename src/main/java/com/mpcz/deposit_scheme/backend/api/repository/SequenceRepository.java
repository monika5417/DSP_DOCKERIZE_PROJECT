package com.mpcz.deposit_scheme.backend.api.repository;
public interface SequenceRepository {
    String getNextRefSequence();  // return 2-digit string like "04", "12"
}