//package com.mpcz.deposit_scheme.database;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import com.google.gson.Gson;
//
//@Configuration
//public class DataSourceConfig {
//
//    private final DbConnectorForSpring dbConnectorForSpring;
//
//    public DataSourceConfig(DbConnectorForSpring dbConnectorForSpring) {
//        this.dbConnectorForSpring = dbConnectorForSpring;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//        dataSource.setUrl(dbConnectorForSpring.buildDbUrl());
//        dataSource.setUsername(dbConnectorForSpring.getCreds().get("username").toString());
//        dataSource.setPassword(dbConnectorForSpring.getCreds().get("password").toString());
//        System.err.println("aaaaaaaaaaaaaaaa : " +new Gson().toJson(dataSource));
//        return dataSource;
//    }
//}
