package com.mpcz.deposit_scheme.backend.api.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ProposedFormatNonOyt;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ProposedFormatNonOytRepository;
import com.mpcz.deposit_scheme.backend.api.repository.VendorAddMaterialRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@RestController
@RequestMapping("/Proposed-Format-Non-Oyt")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProposedFormatNonOytContoller {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private VendorAddMaterialRepository vendorAddMaterialRepository;

	@Autowired
	private ProposedFormatNonOytRepository proposedFormatNonOytRepository;

	@PostMapping("/save")
	public Response<ProposedFormatNonOyt> save(@RequestBody List<ProposedFormatNonOyt> proposedFormatNonOy) {

		Response<ProposedFormatNonOyt> res = new Response<>();
		ProposedFormatNonOyt save = null;
		System.err.println("aaaaaaaaaaa : " + new Gson().toJson(proposedFormatNonOy));
		for (ProposedFormatNonOyt proposedFormatNonOyt : proposedFormatNonOy) {

			if (proposedFormatNonOyt == null) {
				res.setCode("400");
				res.setMessage("Object is null");
				return res;
			}
			if (proposedFormatNonOyt.equals(null)) {
				res.setCode("400");
				res.setMessage("Object is null");
				return res;
			}
			if (proposedFormatNonOyt.getApplicationNumbe().equals(null)
					|| proposedFormatNonOyt.getApplicationNumbe().isEmpty()
					) {
				res.setCode("400");
				res.setMessage("Consumer Application Number can not be null");
				return res;
			}
			if (proposedFormatNonOyt.getYearOfManufacturing().equals(null)
					|| proposedFormatNonOyt.getYearOfManufacturing().isEmpty()
					) {
				res.setCode("400");
				res.setMessage("Year of Manufacturing Number can not be null");
				return res;
			}
			if (proposedFormatNonOyt.getMonthOfManufacturing().equals(null)
					|| proposedFormatNonOyt.getMonthOfManufacturing().isEmpty()
					) {
				res.setCode("400");
				res.setMessage("Month of Manufacturing can not be null");
				return res;
			}
			if (proposedFormatNonOyt.getCentralZone().equals(null) || proposedFormatNonOyt.getCentralZone().isEmpty()
					) {
				res.setCode("400");
				res.setMessage("Central Zone Name  can not be null");
				return res;
			}

			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(proposedFormatNonOyt.getApplicationNumbe().trim());

			if (Objects.isNull(findByConsumerApplicationNumber)) {
				res.setCode("400");
				res.setMessage("This Application Number is not valid");
				return res;
			}
			if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 1l) {
				proposedFormatNonOyt.setApplicationSchemeCode("SUP");
			} else {
				proposedFormatNonOyt.setApplicationSchemeCode("DEP");
			}
			if (proposedFormatNonOyt.getManufacturingSerialNo().equals(null)
					|| proposedFormatNonOyt.getManufacturingSerialNo().isEmpty()
					) {
//				LocalTime now = LocalTime.now();
//				DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("ssss");
//
//				String format = ofPattern.format(now);
//
//				proposedFormatNonOyt.setManufacturingSerialNo(format);
				
				
				LocalTime now = LocalTime.now();
				String serial = String.format("%06d", now.toSecondOfDay() % 100000);

				proposedFormatNonOyt.setManufacturingSerialNo(serial);

			}
			if (proposedFormatNonOyt.getManufacturingSerialNo().length() < 4) {
				proposedFormatNonOyt.setManufacturingSerialNo("0" + proposedFormatNonOyt.getManufacturingSerialNo());
			}
			proposedFormatNonOyt.setDtrcode(
					
					proposedFormatNonOyt.getYearOfManufacturing() + 
					proposedFormatNonOyt.getMonthOfManufacturing() +
					proposedFormatNonOyt.getApplicationSchemeCode() +
					proposedFormatNonOyt.getCentralZone() +
					proposedFormatNonOyt.getManufacturingSerialNo()
					
					);
			
			
			save = proposedFormatNonOytRepository.save(proposedFormatNonOyt);

		}
		if (save != null) {
			res.setCode("200");
			res.setMessage("data successfully save");
			res.setList(Arrays.asList(save));
			return res;
		}
		return null;
	}

}
