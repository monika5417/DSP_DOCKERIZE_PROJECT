package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.MMKYParentChild;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyCalculation;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerApplicationUpdateDto;
import com.mpcz.deposit_scheme.backend.api.dto.MMKYParentChildDTO;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MMKYParentChildRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyCalculationRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.MMKYParentChildService;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MMKYParentChildServiceImpl implements MMKYParentChildService {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplicationDetailRepository;

	@Autowired
	private MmkyCalculationRepository mmkyCalculationRepository;

	@Autowired
	MMKYParentChildRepository mmkyParentChildRepository;
	
	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;
	
	@Autowired
	SchemeTypeService schemeTypeService;


	@Override
	public ResponseEntity<?> submit(String applicationNumber, String mmkyParentChildData, double mmkyLoad,
			Long KvDistance, Long dtr, String cutPoint) {
		Response response = new Response();

		try {
			ConsumerApplicationUpdateDto consumerApplicationUpdate = ConsumerApplicationUpdateDto
					.stringToJson(mmkyParentChildData);

			ConsumerApplicationDetail consumerApplicationNumber = consumerApplicationDetailRepository
					.findByConsumerApplicationNumber(applicationNumber);

			MmkyCalculation mmkyCalculation = new MmkyCalculation();

			mmkyCalculation.setKvDistance(KvDistance);
			mmkyCalculation.setMmkyLoad(mmkyLoad);
			mmkyCalculation.setParentApplicationNo(applicationNumber);
			mmkyCalculation.setDtrCapacity(dtr);
			mmkyCalculation.setCutPoint(cutPoint);
			
			mmkyCalculationRepository.save(mmkyCalculation);

			if (consumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application No. Not found!!!!");
				throw new ConsumerApplicationDetailException(response);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (consumerApplicationUpdate.getIndividualOrGroupId() == 2l) {

				List<MMKYParentChild> mmkyParentListData = new ArrayList<>();

				consumerApplicationUpdate.getMmkyParentChildDto().forEach(a -> {
					
					ConsumerApplicationDetail consumerApplicationNumDB = consumerApplicationDetailRepository
							.findByConsumerApplicationNumber(a.getChildApplicationNumber());
					
					consumerApplicationNumDB.setActive(false);
					consumerApplicationNumDB.setDeleted(true);

					MMKYParentChild mmkyListData = new MMKYParentChild();
					mmkyListData.setParentApplicationNumber(consumerApplicationNumber.getConsumerApplicationNo());
					mmkyListData.setChildApplicationNumber(a.getChildApplicationNumber());
					mmkyListData.setLoad(a.getLoad());
					mmkyListData.setConsumerName(a.getConsumerName());
					mmkyListData.setGuardianName(a.getGuardianName());
					mmkyParentListData.add(mmkyListData);

				});

				List<MMKYParentChild> saveAll = mmkyParentChildRepository.saveAll(mmkyParentListData);
				map.put("listData", saveAll);
			}

			response.setCode("200");
			response.setMessage("Application Saved successfully");
			response.setList(Arrays.asList(map));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

}
