package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.services.ReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(RestApiUrl.JASPER_PDF_API)
public class ReportController {
	//
	@Autowired
	ReportService reportService;

	@GetMapping(RestApiUrl.DOWNLOAD_PDF)
    public ResponseEntity<byte[]> getJasperReportPdf(@PathVariable("erp_no") String erpNo) {

        try {
            //dynamic parameters required for report
            Map<String, Object> reportParams = new HashMap<>();
            reportParams.put("erp_no",erpNo);
            String filePath = "classpath:reports/deposit_report.jrxml";

            JasperPrint report = reportService.generateReport(reportParams, filePath);

            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
          //  headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set("content-type","application/octet-stream");

            headers.setContentDispositionFormData("filename", "deposit-report-details.pdf");
          //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(report), headers, HttpStatus.OK);

        } catch (Exception e) {

        	System.out.println("EEEEEEEEEEEEEEEEEE->>>>>>>>>>>>>>>>"+e);

        	e.printStackTrace();

            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}