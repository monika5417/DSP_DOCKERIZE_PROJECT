package com.mpcz.deposit_scheme.backend.api.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mpcz.deposit_scheme.backend.api.dto.MisExcelData;

public class ExcelGenerator {

	private List<MisExcelData> misExcelDetail;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private Double totalCharges;

	public ExcelGenerator(List<MisExcelData> misExcelDetails,Double totalCharges) {
		this.misExcelDetail = misExcelDetails;
		this.totalCharges=totalCharges;
		workbook = new XSSFWorkbook();
	}

	private void writeHeader() {
		sheet = workbook.createSheet("ConsumerApplicationStatus");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		createCell(row, 0, "Serial No", style);
		createCell(row, 1, "CircleName", style);
		createCell(row, 2, "DivisionName", style);
		createCell(row, 3, "DC Name", style);
		createCell(row, 4, "ApplicationNo", style);
		createCell(row, 5, "Registration Charges", style);
		
	}

	private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (valueOfCell instanceof Integer) {
			cell.setCellValue((Integer) valueOfCell);
		} else if (valueOfCell instanceof Long) {
			cell.setCellValue((Long) valueOfCell);
		} else if (valueOfCell instanceof String) {
			cell.setCellValue((String) valueOfCell);
		}else if (valueOfCell instanceof BigDecimal) {
			BigDecimal ret=(BigDecimal)valueOfCell;
			cell.setCellValue(ret.doubleValue());
		} else if (valueOfCell instanceof Double) {
			
			cell.setCellValue((Double)valueOfCell);
		}else {
			cell.setCellValue((Boolean) valueOfCell);
		}
		cell.setCellStyle(style);
	}

	private void write() {
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		
		int columnCount = 1;
		int rowCharges=0;
		// XSSFRow row1 = sheet.createRow((short)2);

//     for(MisExcelData misExcelData:misExcelDetail) {
		for (int i = 0; i < misExcelDetail.size(); i++) {
			Row row = sheet.createRow(rowCount);
			
			createCell(row, 0,columnCount++ , style);
			createCell(row, 1, misExcelDetail.get(i).getCircleName(), style);
			createCell(row, 2, misExcelDetail.get(i).getDivisionName(), style);
			createCell(row, 3, misExcelDetail.get(i).getDistributionCenterName(), style);
			createCell(row, 4, misExcelDetail.get(i).getConsumerApplicationNo(), style);
			createCell(row, 5, misExcelDetail.get(i).getRegistrationCharges(), style);
			
			rowCount++;
			rowCharges=rowCount;
			//columnCount=0;
			
		}
		
		Row row = sheet.createRow(rowCharges+1);
		createCell(row, 4, "Total Registration Charges", style);
		createCell(row, 5, totalCharges, style);
		
	}

	public void generateExcelFile(HttpServletResponse response) throws IOException {
		writeHeader();
		write();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}