package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.mpcz.deposit_scheme.backend.api.services.ReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


@Service
public class ReportServiceImpl implements ReportService{

@Autowired
private DataSource dataSource;


public JasperPrint generateReport(Map<String, Object> empParams,String filePath) throws FileNotFoundException, JRException, SQLException {
    Connection connection = dataSource.getConnection();
    String fileAbsolutePath = ResourceUtils.getFile(filePath).getAbsolutePath();
    System.out.println("FilePath : "+fileAbsolutePath+" , Params : "+empParams);
    return JasperFillManager.fillReport
                    (
                            JasperCompileManager.compileReport(
                                    ResourceUtils.getFile(filePath)
                                            .getAbsolutePath()) // path of the jasper report
                            , empParams // dynamic parameters
                            , connection
                    );
}
}