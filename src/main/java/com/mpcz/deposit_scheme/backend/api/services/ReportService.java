package com.mpcz.deposit_scheme.backend.api.services;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ReportService {

	public JasperPrint generateReport(Map<String, Object> empParams, String filePath) throws FileNotFoundException, JRException, SQLException;
}
