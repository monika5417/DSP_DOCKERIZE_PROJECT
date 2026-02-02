package com.mpcz.deposit_scheme.backend.api.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;

@RestController
@RequestMapping(RestApiUrl.TEST_DATA_API)
public class TestingForRestTemplateController {
	
	static RestTemplate restTemplate = new RestTemplate();
		
	
	@GetMapping("/get")
	public static List<TestRestTemplateDto> getQcData() {
		String url = "https://qcdev.mpcz.in:8080/tkc/consumerbid/";
		HttpEntity<?> entity = new HttpEntity<>(null);
		List<TestRestTemplateDto> categoryDTOList = new ArrayList<>();
		ResponseEntity<String> result = null;
		try {
			URI uri = new URI(url);

			result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			if (result.getStatusCode().value() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				categoryDTOList = objectMapper.readValue(result.getBody(),
						new TypeReference<List<TestRestTemplateDto>>() {
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryDTOList;
	}

	
	
	
	
	
}
	
	

