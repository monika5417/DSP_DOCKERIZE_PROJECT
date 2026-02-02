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

import com.mpcz.deposit_scheme.backend.api.dto.MisExcelHeader;


public class ExcelGeneratorHeader { 

   // private List<MisExcelData>  misExcelDetail;
    private List<MisExcelHeader> misExcelHeader;
   private List<Double> listOfDemandChargesDetails;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    
    public ExcelGeneratorHeader(List<MisExcelHeader> misExcelHeader, List<Double> listOfDemandChargesDetails) {
    	        //this.misExcelDetail = misExcelDetails;
    	        this.misExcelHeader=misExcelHeader;
    	        this.listOfDemandChargesDetails= listOfDemandChargesDetails;
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
        createCell(row, 5, "SV CHARGES ON COST OFMATERIAL", style);  
        createCell(row, 6, "CGST", style);  
        createCell(row, 7, "SGST", style);  
        createCell(row, 8, "KRISHI CESS", style);  
        createCell(row, 9, "EDUCATION CESS", style);  
        createCell(row, 10, "SECONDARY HIGHER EDU CESS", style);  
        createCell(row, 11, "SYSTEM DEVELOPMENT CHARGES", style);  
        createCell(row, 12, "COST OF ESTIMATE", style);  
        createCell(row, 13, "OTHER INFRA STRUC RELATED COST", style);
        createCell(row, 14, "SUPPLY AFFORDING CHARGES", style);
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
        } else if (valueOfCell instanceof BigDecimal) {
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
        
        Row row = sheet.createRow(rowCount);
          int columnCount = 1;
      

    
     int rowCharges=0;
	for (int i = 0; i < misExcelHeader.size(); i++) {
			Row rows = sheet.createRow(rowCount);
			createCell(rows, 0,columnCount++, style);
			createCell(rows, 1,misExcelHeader.get(i).getCircleName(), style);
	    	 createCell(rows, 2,misExcelHeader.get(i).getDivisionName(), style);
	         createCell(rows, 3,misExcelHeader.get(i).getDistributionCenterName(), style);
	         createCell(rows, 4,misExcelHeader.get(i).getConsumerApplicationNo(), style);  
	         createCell(rows, 5,misExcelHeader.get(i).getSupervisionChargesOnCostOfMaterial(), style);
	         createCell(rows, 6,misExcelHeader.get(i).getCgst(), style);
	         createCell(rows, 7,misExcelHeader.get(i).getSgst(), style);
	         createCell(rows, 8,misExcelHeader.get(i).getKrishiCess(), style); 
	         createCell(rows, 9,misExcelHeader.get(i).getEducationCess(), style);  
	         createCell(rows, 10,misExcelHeader.get(i).getEecondaryHigherEduCess(), style);  
	         createCell(rows, 11,misExcelHeader.get(i).getSystemDevelopmentCharges(), style);  
	         createCell(rows, 12,misExcelHeader.get(i).getCostOfEstimate(), style);  
	         createCell(rows, 13,misExcelHeader.get(i).getOtherInfraStrucRelatedCost(), style);  
	         createCell(rows,14,misExcelHeader.get(i).getSupplyAffordingCharges(), style);  
			rowCount++;
			rowCharges=rowCount;
			
			
		}
     Row row1 = sheet.createRow(rowCharges+1);
		createCell(row1, 4, "Total Of Charges", style);
		createCell(row1, 5,listOfDemandChargesDetails.get(0), style);
		createCell(row1, 6,listOfDemandChargesDetails.get(1), style);
		createCell(row1, 7,listOfDemandChargesDetails.get(2), style);
		createCell(row1, 8,listOfDemandChargesDetails.get(5), style);
		createCell(row1, 9,listOfDemandChargesDetails.get(3), style);
		createCell(row1, 10,listOfDemandChargesDetails.get(4), style);
		createCell(row1, 11,listOfDemandChargesDetails.get(8), style);
		createCell(row1, 12,listOfDemandChargesDetails.get(9), style);
		createCell(row1, 13,listOfDemandChargesDetails.get(6), style);
		createCell(row1, 14,listOfDemandChargesDetails.get(7), style);
		
        
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