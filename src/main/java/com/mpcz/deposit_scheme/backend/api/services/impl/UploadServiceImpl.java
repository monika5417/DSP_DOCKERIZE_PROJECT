package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.DocumentType;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.UploadRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.DocumentTypeService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.backend.api.util.FileUploadUtility;

@Service
public class UploadServiceImpl implements UploadService {

	@Value("${upload.path}")
	private String uploadPath;

	Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

	@Autowired
	UploadRepository uploadRepository;

	@Autowired
	DocumentTypeService documentTypeService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Upload save(Upload upload) {
		return uploadRepository.save(upload);
	}

	@Override
	public Upload findUpload(Long uploadId) {
		return uploadRepository.findByUploadId(uploadId);

	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Upload uploadFile(MultipartFile uploadDoc, String fileTypeName) throws DocumentTypeException {

		final Response<SchemeType> response = new Response<>();
		Upload uploadDb = null;
		DocumentType documentType = null;

		if (fileTypeName.equals("AADHAR_CARD")) {
			documentType = documentTypeService.findDocumentTypeById(1l);
		}

		if (fileTypeName.equals("PAN_CARD")) {
			documentType = documentTypeService.findDocumentTypeById(2l);
		}

		if (fileTypeName.equals("ENERGY_BILLS")) {
			documentType = documentTypeService.findDocumentTypeById(3l);
		}

		if (fileTypeName.equals("REGISTRY_OR_LEASE_DOCUMENT")) {
			documentType = documentTypeService.findDocumentTypeById(4l);
		}

		if (fileTypeName.equals("RESIDENTIAL_PROOF")) {
			documentType = documentTypeService.findDocumentTypeById(5l);
		}

		if (fileTypeName.equals("BUILDING_PERMISSION_OR_TNCP_DOCUMENT")) {
			documentType = documentTypeService.findDocumentTypeById(6l);
		}

		if (fileTypeName.equals("ELECTRICITY_BILL")) {
			documentType = documentTypeService.findDocumentTypeById(7l);
		}
		if (fileTypeName.equals("SURVEY")) {
			documentType = documentTypeService.findDocumentTypeById(8l);
		}

		/******* CHARITRA *****/
		if (fileTypeName.equals("LATLONG_IMAGE")) {
			documentType = documentTypeService.findDocumentTypeById(10l);
		}
		/******* CHARITRA *****/

		if (fileTypeName.equals("DEMAND")) {
			documentType = documentTypeService.findDocumentTypeById(9l);
		}

		if (fileTypeName.equals("ROW")) {
			documentType = documentTypeService.findDocumentTypeById(13l);
		}

		if (fileTypeName.equals("REGISTRY")) {
			documentType = documentTypeService.findDocumentTypeById(14l);
		}

		if (fileTypeName.equals("NAGAR_NIGAM_PERMISSION")) {
			documentType = documentTypeService.findDocumentTypeById(15l);
		}

		if (fileTypeName.equals("DIC_OR_GUMASTA")) {
			documentType = documentTypeService.findDocumentTypeById(16l);
		}

		if (fileTypeName.equals("SHAPATH_PATRA")) {
			documentType = documentTypeService.findDocumentTypeById(17l);
		}

		if (fileTypeName.equals("TEST_REPORT")) {
			documentType = documentTypeService.findDocumentTypeById(18l);
		}

		if (fileTypeName.equals("T$CP_PERMISSION")) {
			documentType = documentTypeService.findDocumentTypeById(19l);
		}

		if (fileTypeName.equals("RERA_PERMISSION")) {
			documentType = documentTypeService.findDocumentTypeById(20l);
		}

		if (fileTypeName.equals("DRAWING_NOTARIZED")) {
			documentType = documentTypeService.findDocumentTypeById(21l);
		}

		if (fileTypeName.equals("COLONY_PRAKOSHTH")) {
			documentType = documentTypeService.findDocumentTypeById(23l);
		}

		if (fileTypeName.equals("COLONY_LICENCE")) {
			documentType = documentTypeService.findDocumentTypeById(24l);
		}

		if (fileTypeName.equals("LOAD_SHEET")) {
			documentType = documentTypeService.findDocumentTypeById(25l);
		}

		if (fileTypeName.equals("NOC")) {
			documentType = documentTypeService.findDocumentTypeById(26l);
		}

		if (fileTypeName.equals("ALL_PAPER_NOTARIZED_03_SET")) {
			documentType = documentTypeService.findDocumentTypeById(27l);
		}

		if (fileTypeName.equals("COMMITTEE")) {
			documentType = documentTypeService.findDocumentTypeById(28l);
		}

		if (fileTypeName.equals("DIVERSION")) {
			documentType = documentTypeService.findDocumentTypeById(29l);
		}

		if (fileTypeName.equals("NAZUL")) {
			documentType = documentTypeService.findDocumentTypeById(30l);
		}

		if (fileTypeName.equals("MAP")) {
			documentType = documentTypeService.findDocumentTypeById(31l);
		}

		if (fileTypeName.equals("KHASRA_KHATONI")) {
			documentType = documentTypeService.findDocumentTypeById(32l);
		}

		if (fileTypeName.equals("MAP_CIVIL_ENGINEER")) {
			documentType = documentTypeService.findDocumentTypeById(33l);
		}

		if (fileTypeName.equals("APPLICATION_CONSENT")) {
			documentType = documentTypeService.findDocumentTypeById(34l);
		}

		if (fileTypeName.equals("PERFORMA_5A")) {
			documentType = documentTypeService.findDocumentTypeById(35l);
		}

		if (fileTypeName.equals("PERFORMA_5B")) {
			documentType = documentTypeService.findDocumentTypeById(36l);
		}

		if (fileTypeName.equals("PLOTAREA_DETAILS_WITH_OWNER")) {
			documentType = documentTypeService.findDocumentTypeById(37l);
		}
		if (fileTypeName.equals("Group_File")) {
			documentType = documentTypeService.findDocumentTypeById(38l);
		}
		if (fileTypeName.equals("Administrative")) {
			documentType = documentTypeService.findDocumentTypeById(39l);
		}

		if (fileTypeName.equals("GST")) {
			documentType = documentTypeService.findDocumentTypeById(40l);
		}

		if (fileTypeName.equals("ESTIMATEPDF")) {
			documentType = documentTypeService.findDocumentTypeById(41l);
		}

		if (fileTypeName.equals("energyBillDoc")) {
			documentType = documentTypeService.findDocumentTypeById(42l);
		}

		if (fileTypeName.equals("SAMAGRA_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(43l);
		}

		if (fileTypeName.equals("REJECTION_PROPOSAL")) {
			documentType = documentTypeService.findDocumentTypeById(44l);
		}

		if (fileTypeName.equals("GROUP_PEOPLE_COMMON_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(45l);
		}

		if (fileTypeName.equals("ERP_REVISE")) {
			documentType = documentTypeService.findDocumentTypeById(46l);
		}
//		monika code regarding refund amount start Date (20-july-2024)
		if (fileTypeName.equals("REFUND_LETTER")) {
			documentType = documentTypeService.findDocumentTypeById(47l);
		}

		if (fileTypeName.equals("CHECK_OR_PASSBOOK")) {
			documentType = documentTypeService.findDocumentTypeById(48l);
		}

//		28-AUGUST-2024 added CONSUMER_REFUND_LETTER
		if (fileTypeName.equals("CONSUMER_REFUND_LETTER")) {
			documentType = documentTypeService.findDocumentTypeById(49l);
		}

//		30-August-2024 added CONTRACTOR_RESELECTION_FILE
		if (fileTypeName.equals("CONTRACTOR_RESELECTION_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(50l);
		}

//		CODE END

		if (fileTypeName.equals("WORK_ORDER_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(51l);
		}

		if (fileTypeName.equals("DEMAND_NOTE_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(52l);
		}

		if (fileTypeName.equals("PAN_NO_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(53l);
		}

		if (fileTypeName.equals("DOC_ADDRESS_PROOF")) {
			documentType = documentTypeService.findDocumentTypeById(54l);
		}

		if (fileTypeName.equals("DOC_NOTRY")) {
			documentType = documentTypeService.findDocumentTypeById(55l);
		}

		if (fileTypeName.equals("DOC_AUTHORIZED_LETTER")) {
			documentType = documentTypeService.findDocumentTypeById(56l);
		}

		if (fileTypeName.equals("DOC_MRA")) {
			documentType = documentTypeService.findDocumentTypeById(57l);
		}

		if (fileTypeName.equals("DOC_REQUEST_LETTER")) {
			documentType = documentTypeService.findDocumentTypeById(58l);
		}

		if (fileTypeName.equals("DOC_DEBIT_SLIP_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(59l);
		}

		if (fileTypeName.equals("DOC_INSPECTION_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(60l);
		}

		if (fileTypeName.equals("DTR_PIC")) {
			documentType = documentTypeService.findDocumentTypeById(61l);
		}

		if (fileTypeName.equals("GET_PASS_PIC")) {
			documentType = documentTypeService.findDocumentTypeById(62l);
		}

		if (fileTypeName.equals("TRF_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(63l);
		}
		if (fileTypeName.equals("TEST_REPORT")) {
			documentType = documentTypeService.findDocumentTypeById(64l);
		}
		
		if(fileTypeName.equals("CONSUMER_UPLOAD_PDF_FOR_REFUND_TIME")) {
			documentType = documentTypeService.findDocumentTypeById(65l);
		}

		if(fileTypeName.equals("DGM_UPLOAD_PDF_FOR_REFUND_TIME")) {
			documentType = documentTypeService.findDocumentTypeById(66l);
		}
		
		if(fileTypeName.equals("STC_UPLOAD_PDF_FOR_REFUND_TIME")) {
			documentType = documentTypeService.findDocumentTypeById(67l);
		}
		
		if(fileTypeName.equals("GM_UPLOAD_PDF_FOR_REFUND_TIME")) {
			documentType = documentTypeService.findDocumentTypeById(68l);
		}
		
		if(fileTypeName.equals("FINANCE_UPLOAD_PDF_FOR_REFUND_TIME")) {
			documentType = documentTypeService.findDocumentTypeById(69l);
		}
		if (fileTypeName.equals("REV_GET_PASS_PIC")) {
			documentType = documentTypeService.findDocumentTypeById(70l);
		}
		if (fileTypeName.equals("DOC_DRAFT_AGREEMENT")) {
			documentType = documentTypeService.findDocumentTypeById(71l);
		}
		
		if (fileTypeName.equals("CHANGE_MOBILE_NUMBER_FILE")) {
			documentType = documentTypeService.findDocumentTypeById(72l);
		}
		
		
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

		String pdfName = loginId + "_" + fileTypeName + "_" + System.currentTimeMillis();

		String fileName = uploadPath + File.separator;
		File file = new File(fileName);
		if (!file.exists()) {
			file.mkdirs();
		}

		fileName = fileName + pdfName;
		file = new File(fileName);

		try {

			String fileExtension = FilenameUtils.getExtension(uploadDoc.getOriginalFilename());
			File convFile = new File(uploadDoc.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.flush();
			fos.close();

			byte[] pdfData1 = uploadDoc.getBytes();

			Upload upload = new Upload();
			String filePath = FileUploadUtility.uploadFile(pdfData1, fileTypeName, pdfName + "." + fileExtension,
					uploadPath);
			upload.setDocumentPath(filePath);
			upload.setDocumentType(documentType);
			upload.setDocumentName(pdfName);
			upload.setUploadDate(new Date());

//			Monika Code Start
			upload.setDocumentStatus(1L);
//			Monika Code End

			convFile.delete();

//			check added for cheking the file existance on path 26-08-2025
			String fullPath = Paths.get(uploadPath, filePath).toString();
			System.err.println("ADD UPLOAD DOCS NEW filePath: " + fullPath);
			if (!new File(fullPath).exists()) {
				logger.error("documentTypeRepository.findAll is returning Null when findAllDocumentType call");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Error in uploading document : file not exist on given path !!" + fullPath);
				throw new DocumentTypeException(response);

			} else {
				uploadDb = uploadRepository.save(upload);
				if (uploadDb == null || uploadDb.getUploadId() == null) {
				    throw new DocumentTypeException(new Response(HttpCode.NULL_OBJECT,"Error in saving document details to DB"));
				}
			}
//			check added for cheking the file existance on path 26-08-2025
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return uploadDb;

	}

}
