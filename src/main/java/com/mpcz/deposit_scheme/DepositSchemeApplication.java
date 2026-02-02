package com.mpcz.deposit_scheme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
@ComponentScan({ "com.mpcz.deposit_scheme","com.mpcz.deposit_scheme.aop","com.mpcz.deposit_scheme.backend.api" })
@PropertySource({ "classpath:message.properties" })
@EnableJpaRepositories({ "com.mpcz.deposit_scheme.backend.api.repository" })
@EntityScan({ "com.mpcz.deposit_scheme.backend.api.domain" })
public class DepositSchemeApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DepositSchemeApplication.class);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(DepositSchemeApplication.class, args);
//		BasicConfigurator.configure();
		System.out.println(applicationContext);
	}



}
