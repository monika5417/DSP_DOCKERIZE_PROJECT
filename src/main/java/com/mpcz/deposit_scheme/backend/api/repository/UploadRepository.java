package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mpcz.deposit_scheme.backend.api.domain.Upload;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {

	
	@Query(value = "select * from upload where upload_id = :uploadId and is_deleted=0", nativeQuery = true)
 	public Upload findByUploadId(@Param("uploadId") Long uploadId); 
	
	public List<Upload> findByConsuemrApplicatonID(Long consuemrApplicatonID);
}