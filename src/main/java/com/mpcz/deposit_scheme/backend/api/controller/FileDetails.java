package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@RestController
@RequestMapping("/fileDetails")
public class FileDetails {

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@GetMapping("/getFileDetailsByConsumerApplicationId/{ApplicationId}")
	public Response<?> getFileDetailsByConsumerApplicationId(@PathVariable Long ApplicationId) {

		Response<List> response = new Response<>();
		ApplicationDocument findByConsumerApplicationDetailId = applicationDocumentRepository
				.findByConsumerApplicationDetailId(ApplicationId);

		List<File> list =new ArrayList<>();
	
		if (findByConsumerApplicationDetailId.getDocT$cpPermission() != null) {
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocT$cpPermission().getUploadId());
            file.setFileName("docTAndcpPermission");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocT$cpPermission().getDocumentStatus());
            list.add(file);

		}
		if (findByConsumerApplicationDetailId.getDocReraPermission() != null) {
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocReraPermission().getUploadId());
            file.setFileName("docReraPermission");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocReraPermission().getDocumentStatus());
            list.add(file);
		}
		if (findByConsumerApplicationDetailId.getDocNoc() != null) {
			
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocNoc().getUploadId());
            file.setFileName("docNoc");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocNoc().getDocumentStatus());
            list.add(file);
			
		}
		if (findByConsumerApplicationDetailId.getDocGst() != null) {
			
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocGst().getUploadId());
            file.setFileName("docGst");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocGst().getDocumentStatus());
            list.add(file);
            
			
		}
		if (findByConsumerApplicationDetailId.getDocEstimate() != null) {
			
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocEstimate().getUploadId());
            file.setFileName("docEstimate");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocEstimate().getDocumentStatus());
            list.add(file);
            
			
		}
		if (findByConsumerApplicationDetailId.getDocGroupPeopleCommonFile() != null) {
			
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocGroupPeopleCommonFile().getUploadId());
            file.setFileName("docGroupPeopleCommonFile");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocGroupPeopleCommonFile().getDocumentStatus());
            list.add(file);
            
			
		}
		if (findByConsumerApplicationDetailId.getDocSamagraFile() != null) {
			
			File file =new File();
            file.setUploadId(findByConsumerApplicationDetailId.getDocSamagraFile().getUploadId());
            file.setFileName("docSamagraFile");
            file.setFileStatus(findByConsumerApplicationDetailId.getDocSamagraFile().getDocumentStatus());
            list.add(file);
            
			Long docSamagraFile = findByConsumerApplicationDetailId.getDocSamagraFile().getUploadId();
		}

		response.setCode("200");
		response.setList(Arrays.asList(list));
		return response;
	}

class File{
	String fileName;
	Long uploadId;
	Long fileStatus;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getUploadId() {
		return uploadId;
	}
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}
	public Long getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(Long fileStatus) {
		this.fileStatus = fileStatus;
	}
	
	
	
	
}

}
