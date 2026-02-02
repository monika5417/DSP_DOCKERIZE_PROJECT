package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ReturnMaterialData;
import com.mpcz.deposit_scheme.backend.api.domain.ReturnProjectItem;
import com.mpcz.deposit_scheme.backend.api.dto.ReturnMaterialDataDto;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ReturnMaterialDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ReturnProjectItemRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ReturnMaterialDataController")
public class ReturnMaterialDataController {

	Logger LOG = LoggerFactory.getLogger(ConsumerApplicationDetailController.class);

	@Autowired
	private ReturnMaterialDataRepository returnMaterialDataRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ReturnProjectItemRepository returnProjectItemREpository;

	@PostMapping("/save")
	public ResponseEntity<?> saveReturnMaterialData(@RequestBody ReturnMaterialDataDto returnMaterialDataDto) {
		ReturnMaterialData save = null;
		ConsumerApplicationDetail findByConsumerApplicationNumber = null;
		Response<List<ReturnMaterialData>> response = new Response();

		List<ReturnMaterialData> savedDataList = new ArrayList<>();

		if (returnMaterialDataDto.getReturnMaterialData().isEmpty()
				&& returnMaterialDataDto.getReturnMaterialData() == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("List Should not be empty");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		for (ReturnMaterialData returnMaterialData1 : returnMaterialDataDto.getReturnMaterialData()) {
			findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(returnMaterialData1.getConsumerApplicationNumber());
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Consumer Application No. Not Found In Database");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			save = returnMaterialDataRepository.save(returnMaterialData1);
			savedDataList.add(save);
		}

		if (save != null) {
			findByConsumerApplicationNumber.setReturnMaterialData("True");
			consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
		}

		response.setCode(HttpCode.OK);
		response.setMessage("Data Submitted Successfully.....");
		response.setList(Arrays.asList(savedDataList));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
	}

	@PostMapping("/saveReturnMaterialData1")
	public Response<?> saveReturnMaterialData1(@RequestBody List<ReturnProjectItem> rejectProjectItem) {
		Response resposne = new Response();

		boolean anyMatch = rejectProjectItem.stream().anyMatch(item -> item.getBalQty() > 0.0);

		if (anyMatch) {
			resposne.setCode("406");
			resposne.setMessage("Material not submit by Contractor");

			return resposne;
		} else {
			List<ReturnProjectItem> findByProjectNumber = returnProjectItemREpository
					.findByProjectNumber(rejectProjectItem.get(0).getProjectNumber());
			if (findByProjectNumber.size() > 0) {
				resposne.setCode("200");
				resposne.setMessage("Data All ready exit");
				return resposne;
			} else {
				List<ReturnProjectItem> saveAll = returnProjectItemREpository.saveAll(rejectProjectItem);
				if (!saveAll.isEmpty()) {
					resposne.setCode("200");
					resposne.setMessage("Data save");
					resposne.setList(saveAll);
					return resposne;
				} else {
					resposne.setCode("100");
					resposne.setMessage("data not save");
					resposne.setList(saveAll);
					return resposne;
				}
			}
		}
		
	}

}
