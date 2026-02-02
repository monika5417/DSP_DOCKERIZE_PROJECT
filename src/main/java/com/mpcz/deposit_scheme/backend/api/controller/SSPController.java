package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConnectionPraday;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.SspOutbox;
import com.mpcz.deposit_scheme.backend.api.dto.SSPDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConnectionPradayRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SspOutboxRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.response.SignUpResponse;
import com.mpcz.deposit_scheme.backend.api.services.SSPService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ssp")
public class SSPController {

	@Autowired
	private SSPService sSPService;

	@Autowired
	private ConnectionPradayRepository connectionPradayRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;
	
	@Autowired
	private SspOutboxRepository sspOutboxRepository;

	@PostMapping("/sspSignUp")
	public ResponseEntity<?> sspSignUp(@RequestBody SSPDto sspDto, HttpServletRequest request)
			throws ConsumerException, DocumentTypeException, DistributionCenterException, DistrictException {
		Response response = new Response<>();
		
		
		
		if (sspDto == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Object having null value");
			return ResponseEntity.ok(response);
		} else {
			SspOutbox s = new SspOutbox();
			if(sspDto.getNatureOfWork()==13l ||  sspDto.getNatureOfWork()==14l) {
				
			}else {
				s.setApplicationNo(sspDto.getNscApplicationNo());
			}
		
			s.setPayload(sspDto.toString());
			sspOutboxRepository.save(s);
			
			SignUpResponse sspSignUp = sSPService.sspSignUp(sspDto, request);
			if (!sspSignUp.equals(null)) {

				response.setCode("200");
				response.setMessage("Application Moved to DSP Portal !!!");
				response.setList(Arrays.asList(sspSignUp.getConsumer()));
				response.setToken(sspSignUp.getToken());
				response.setDspApplicationNo(sspSignUp.getConsumer().getDspApplicationNo());
				return ResponseEntity.ok(response);
			} else {
				response.setCode("100");
				response.setMessage("Not successfull");
				return ResponseEntity.ok(response);
			}
		}

	}

	@PostMapping("/saveivrs/{ivrs}/{sspPortalApplicautionNuber}")
	public ResponseEntity<?> saveIVRS(@PathVariable String ivrs, @PathVariable String sspPortalApplicautionNuber) {
		Response response = new Response<>();
		if (ivrs == null || sspPortalApplicautionNuber == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Object having null value");
			return ResponseEntity.ok(response);
		} else {
			RestTemplate restTamplate = new RestTemplate();
			ResponseEntity<String> forEntity = restTamplate.getForEntity(
					"https://rimsmis.mpcz.in:8100/connection_pradhayen/ngb/getDetailByIvrs/" + ivrs, String.class);
			if (forEntity.getStatusCode().value() == 200) {
				String body = forEntity.getBody();
				JSONObject jsonObject;
				try {
					ConsumerApplicationDetail consumerApplicayionDb = consumerApplictionDetailRepository
							.findByNscApplicationNo(sspPortalApplicautionNuber);
					if (consumerApplicayionDb.getApplicationStatus().getApplicationStatusId() == 32L) {
						ApplicationStatus applicationStatus = applicationStatusRepository
								.findById(ApplicationStatusEnum.CONNECTION_GRANTED_SUCCESSFULLY.getId()).get();
						consumerApplicayionDb.setApplicationStatus(applicationStatus);
					}
					ConnectionPraday byIvrs = connectionPradayRepository.findByIvrs(ivrs);
					if (byIvrs != null) {
						response.setCode(HttpCode.NOT_ACCEPTABLE);
						response.setMessage("Ivrs already registered with another application : "
								+ byIvrs.getConsumerApplicationNo());
						return ResponseEntity.ok(response);
					}
					jsonObject = new JSONObject(body);
					JSONArray jsonArray = jsonObject.getJSONArray("object");
					ConnectionPraday connection = new ConnectionPraday();
					connection.setAddress(jsonArray.getJSONObject(0).getString("address"));
					connection.setConnectionDate(jsonArray.getJSONObject(0).getString("connection_date"));
					connection.setConnectionType(jsonArray.getJSONObject(0).getString("connection_type"));
					connection.setConsumerApplicationNo(consumerApplicayionDb.getConsumerApplicationNo());
					connection.setConsumerName(jsonArray.getJSONObject(0).getString("consumer_name"));
					connection.setFeederName(jsonArray.getJSONObject(0).getString("feeder_name"));
					connection.setGroupNo(jsonArray.getJSONObject(0).getString("group_no"));
					connection.setIvrs(ivrs);
					connection.setLocationCode(jsonArray.getJSONObject(0).getString("location_code"));
					connection.setMeteringStatus(jsonArray.getJSONObject(0).getString("metering_status"));
					connection.setPhase(jsonArray.getJSONObject(0).getString("phase"));
					connection.setPremiseType(jsonArray.getJSONObject(0).getString("premise_type"));
					connection.setPrimaryMobileNo(jsonArray.getJSONObject(0).getString("primary_mobile_no"));
					connection.setReadingDiaryNo(jsonArray.getJSONObject(0).getString("reading_diary_no"));
					connection.setSanctionedLoad(jsonArray.getJSONObject(0).getDouble("sanctioned_load"));
					connection.setSanctionedLoadUnit(jsonArray.getJSONObject(0).getString("sanctioned_load_unit"));
					connection.setTariffCategory(jsonArray.getJSONObject(0).getString("tariff_category"));
					connection.setTariffCode(jsonArray.getJSONObject(0).getString("tariff_code"));
					connection.setDtrName(jsonArray.getJSONObject(0).getString("dtr_name"));
					connection.setCreatedOn(jsonArray.getJSONObject(0).getString("created_on"));
					connection.setContractDemand(
							Double.parseDouble(jsonArray.getJSONObject(0).getString("contract_demand")));
					connection.setContractDemandUnit(jsonArray.getJSONObject(0).getString("contract_demand_unit"));
					connection
							.setPurposeOfInstallation(jsonArray.getJSONObject(0).getString("purpose_of_installation"));
					connectionPradayRepository.save(connection);
					consumerApplicayionDb.setIvrsNo(ivrs);

					ConsumerApplicationDetail savedb = consumerApplictionDetailRepository.save(consumerApplicayionDb);

					response.setCode(forEntity.getStatusCode().value() + "");
					response.setMessage("success");
					response.setList(Arrays.asList(savedb));
					return ResponseEntity.ok(response);

				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				response.setCode(forEntity.getStatusCode().value() + "");
				response.setMessage("connection pradaye api is not working");
				return ResponseEntity.ok(response);

			}

		}
		response.setCode("100");
		response.setMessage("connection pradaye api is not working");
		return ResponseEntity.ok(response);

	}

	@PostMapping("/sendDataToSspAfterWorkOrder/{consumerApplicationNo}/{applicationStatusId}")
	public ResponseEntity<?> sendDataToSspAfterWorkOrder(@PathVariable String consumerApplicationNo,
			@PathVariable Long applicationStatusId) throws ConsumerApplicationDetailException {

		String sendDataToSspAfterWorkOrder = sSPService.sendDataToSspAfterWorkOrder(consumerApplicationNo,
				applicationStatusId);
		return ResponseEntity.ok(
				sendDataToSspAfterWorkOrder.equals("Success") ? new Response(HttpCode.OK, sendDataToSspAfterWorkOrder)
						: new Response(HttpCode.NULL_OBJECT, sendDataToSspAfterWorkOrder));

	}

}
