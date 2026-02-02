package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;

@RestController
@RequestMapping("/CorrectionOfDuplicateApplicationNumber")
public class CorrectionOfDuplicateApplicationNumber {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@GetMapping("/updateApplicationNumber")
	public void updateApplicationNumber() {

		List<Map<String, String>> findListByConsumerApplicatonNumber = consumerApplictionDetailRepository
				.findListByConsumerApplicaation();

		for (Map<String, String> m : findListByConsumerApplicatonNumber) {

			String string = m.get("CONSUMER_APPLICATION_NUMBER");

			List<ConsumerApplicationDetail> findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findListByConsumerApplicatonNumber(string);

			ConsumerApplicationDetail consumerApplicationDetail1 = findByConsumerApplicationNumber.get(0);

			String consumerApplicationNo = consumerApplicationDetail1.getConsumerApplicationNo();
			String newApplicationNumber = consumerApplicationNo.substring(0, 2) + 1
					+ consumerApplicationNo.substring(2);
			System.out.print(newApplicationNumber);
			consumerApplicationDetail1.setConsumerApplicationNo(newApplicationNumber);
			consumerApplictionDetailRepository.save(consumerApplicationDetail1);

		}

		System.out.println("jhfgdfg");
	}
}
