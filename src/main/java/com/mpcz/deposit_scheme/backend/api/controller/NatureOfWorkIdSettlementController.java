package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;

@RestController
@RequestMapping("/natureOfWorkIdSettlementController")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NatureOfWorkIdSettlementController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@GetMapping("/Settlement")
	public void settlement() {
		List<Map<String, String>> settlementNautreOfWork = consumerApplictionDetailRepository.settlementNautreOfWork();

		Iterator<Map<String, String>> iterator = settlementNautreOfWork.iterator();
		Map<String,String> oldAndNew = new HashMap<>();

		while (iterator.hasNext()) {
			Map<String, String> currentMap = iterator.next();

			// Print key-value pairs in each map
			for (Map.Entry<String, String> entry : currentMap.entrySet()) {
				
				List<ConsumerApplicationDetail> upadateConsumerApplicationDetailsByDuplicateNumber = consumerApplictionDetailRepository
						.UpadateConsumerApplicationDetailsByDuplicateAppNumber(entry.getValue());

				for (int i = 1; i < upadateConsumerApplicationDetailsByDuplicateNumber.size(); i++) {
					ConsumerApplicationDetail consumerApplicationDetail = upadateConsumerApplicationDetailsByDuplicateNumber
							.get(i);
					System.out.print(consumerApplicationDetail.getConsumerApplicationNo() + "     A B C D E F G     " );
					String conAppNum = consumerApplicationDetail.getConsumerApplicationNo().substring(0, 2) + i + consumerApplicationDetail.getConsumerApplicationNo().substring(2);
					System.out.print(conAppNum );
					oldAndNew.put(conAppNum, consumerApplicationDetail.getConsumerApplicationNo());
				}
			}
		}
		for(String val:oldAndNew.keySet()) {
			System.out.println("new-:"+val+" OLD-:"+oldAndNew.get(val));
		}

	}
}
