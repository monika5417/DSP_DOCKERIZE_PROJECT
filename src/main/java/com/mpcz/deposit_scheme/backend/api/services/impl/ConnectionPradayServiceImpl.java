package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConnectionPraday;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConnectionPradayException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ConnectionPradayRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConnectionPradayService;

@Service
public class ConnectionPradayServiceImpl implements ConnectionPradayService {

	@Value("${post.nsc.oyt.ssp}")
	private static String postNscOytSSP;

	@Autowired
	private ConnectionPradayRepository connectionPradayRepository;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	@Override
	public ResponseEntity<Response> saveConnectionPraday(ConnectionPraday connectionPraday) {
		Response response = new Response();
		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(connectionPraday.getConsumerApplicationNo());
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Application not found in database.");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (connectionPraday.getIvrs() == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("IVRS No. should not be null.");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (connectionPraday.getConnectionDate() == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Connection Date should not be null.");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			if (connectionPraday.getCreatedOn() == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Created On should not be null.");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}

			ConnectionPraday findByIvrs = connectionPradayRepository.findByIvrs(connectionPraday.getIvrs());
			if (findByIvrs != null) {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("IVRS No. already exist.");
				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
			}
			ConnectionPraday save = connectionPradayRepository.save(connectionPraday);
			if (save != null) {

				ApplicationStatus appStatusDb = applicationStatusService
						.findById(ApplicationStatusEnum.CONNECTION_GRANTED_SUCCESSFULLY.getId());

				findByConsumerApplicationNumber.setApplicationStatus(appStatusDb);
				findByConsumerApplicationNumber.setIvrsNo(save.getIvrs());

				ConsumerApplicationDetail updatedAppDetail = consumerApplictionDetailRepository
						.save(findByConsumerApplicationNumber);

//				// Send data to SSP if application was updated successfully
//				if (updatedAppDetail != null && updatedAppDetail.getNscApplicationNo() != null) {
//					String result = sendDataToSSp(updatedAppDetail.getNscApplicationNo(),
//							updatedAppDetail.getApplicationStatus().getApplicationStatusId());
//
//					// Optionally, handle SSP response here if needed
//					if ("Success".equals(result)) {
//						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//						String formattedDate = LocalDateTime.now().format(formatter);
//						updatedAppDetail.setSspApplicationCompleted(true);
//						updatedAppDetail.setSspApplicationCompleteDate(formattedDate);
//						consumerApplictionDetailRepository.save(updatedAppDetail);
//					}
//				}

			}

			response.setCode(HttpCode.OK);
			response.setMessage("Data Saved Successfully");
			response.setList(Arrays.asList(save));
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("An error occurred while processing the request.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header(ResponseMessage.APPLICATION_TYPE_JSON)
					.body(response);
		}

	}

	@Override
	public ConnectionPraday getConnectionByIvrs(String ivrs) {

		return connectionPradayRepository.findByIvrs(ivrs);

	}

	@Override
	public ConnectionPraday getConnectionDetailsByConsAppNo(String consAppNo) {

		return connectionPradayRepository.findByConsumerApplicationNo(consAppNo);

	}

//	public static String sendDataToSSp(String nscApplicationNo, Long applicationStatusId) {
//		Response response = new Response();
//
//		String url = postNscOytSSP;
//		RestTemplate restTemplate = new RestTemplate();
//
//		Map<String, Object> map = new HashMap<>();
//		map.put("nscApplicationNo", nscApplicationNo);
//		map.put("workStatus", applicationStatusId);
//
//		System.out.println("map Data : " + map.toString());
//
//		try {
//			ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, map, String.class);
//			System.out.println(postForEntity.getBody() + "aadadddddddddddddd");
//			JSONObject responseJson = new JSONObject(postForEntity.getBody());
//
//			// Extract the message and statusCode
//			String message = responseJson.getString("message");
//			int statusCode = responseJson.getInt("statusCode");
//
//			// Check if statusCode is 208 and handle accordingly
//			if (statusCode == 208) {
//				System.out.println("Work Status already updated: " + message);
//				return "Work Status already updated In Saral Sanyojan Portal !!!";
//			} else if (statusCode == 200) {
//				System.out.println("Request was successful.");
//				return "Success";
//			} else {
//				System.out.println("Request failed with status: " + statusCode);
//				return "Request failed";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Internal Server Error";
//		}
//
//	}

	@Override
	public ConnectionPraday saveIvrsConnectionByJE(ConnectionPraday connectionPraday)
			throws ConnectionPradayException, ConsumerApplicationDetailException {
		Response response = new Response();

		ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(connectionPraday.getConsumerApplicationNo());
		if (Objects.isNull(findByConsumerApplicationNumber)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application No. not found in database."));
		}

		if (connectionPraday.getIvrs() == null) {
			throw new ConnectionPradayException(new Response(HttpCode.NULL_OBJECT, "IVRS No. should not be null."));
		}

		if (connectionPraday.getConnectionDate() == null) {

			throw new ConnectionPradayException(
					new Response(HttpCode.NULL_OBJECT, "Connection Date should not be null."));

		}

		if (connectionPraday.getCreatedOn() == null) {
			throw new ConnectionPradayException(new Response(HttpCode.NULL_OBJECT, "Created On should not be null."));

		}

		ConnectionPraday findByIvrs = connectionPradayRepository.findByIvrs(connectionPraday.getIvrs());
		if (findByIvrs != null) {
			throw new ConnectionPradayException(new Response(HttpCode.NOT_ACCEPTABLE, "IVRS No. already exist."));
		}

		ConnectionPraday save = connectionPradayRepository.save(connectionPraday);
		if (save != null) {
			findByConsumerApplicationNumber.setIvrsNo(save.getIvrs());
			ConsumerApplicationDetail updatedAppDetail = consumerApplictionDetailRepository
					.save(findByConsumerApplicationNumber);
		}
		return save;
	}

}
