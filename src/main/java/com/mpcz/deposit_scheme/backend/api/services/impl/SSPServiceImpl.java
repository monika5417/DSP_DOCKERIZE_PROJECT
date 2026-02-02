package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.ConstantProperty;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.CheckConsumer;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerLoginToken;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.domain.LoadRequested;
import com.mpcz.deposit_scheme.backend.api.domain.NatureOfWork;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.dto.SSPDto;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.LandAreaUnitException;
import com.mpcz.deposit_scheme.backend.api.exception.LoadRequestedException;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtConsumer;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtTokenUtil;
import com.mpcz.deposit_scheme.backend.api.jwt.security.util.PasswordUtil;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.CheckConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DistributionCenterRepository;
import com.mpcz.deposit_scheme.backend.api.repository.DistrictRepository;
import com.mpcz.deposit_scheme.backend.api.repository.LandAreaUnitRepository;
import com.mpcz.deposit_scheme.backend.api.repository.LoadRequestedRepository;
import com.mpcz.deposit_scheme.backend.api.repository.NatureOfWorkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.SchemeTypeRepository;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.response.SignUpResponse;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.services.SSPService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SSPServiceImpl implements SSPService {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MessageProperties messageProperties;

	@Autowired
	private SMSDirectService smsDirectService;

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private NatureOfWorkRepository natureOfWorkRepository;

	@Autowired
	private SchemeTypeRepository schemeTypeRepository;

	@Autowired
	private DistributionCenterRepository distributionCenterRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	private CheckConsumerRepository checkConsumerRepository;

	@Autowired
	private LoadRequestedRepository loadRequestedRepository;

	@Autowired
	private LandAreaUnitRepository landAreaUnitRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMServiceIMp workCompletionChangStautsByDgmOAndMServiceIMp;

	@Autowired
	private DryUtility dryUtility;

	@Autowired
	ErpEstimateAmountServiceImpl erpEstimateAmountServiceImpl;

	private static final Logger log = LoggerFactory.getLogger(SSPServiceImpl.class);

	@Override
	public SignUpResponse sspSignUp(SSPDto sspDto, HttpServletRequest request)
			throws ConsumerException, DocumentTypeException, DistributionCenterException, DistrictException {
		// TODO Auto-generated method stub
		log.info("=== [START] saveConsumerApplication() called ===");
		log.info("=== API CALLED: /sspSignUp ===");

//		log.info("Incoming SSP DTO as GSON: {}" + new Gson().toJson(sspDto));
		if (sspDto.getNscApplicationNo() == null) {

		} else {
			ConsumerApplicationDetail consumerAppDetail = consumerApplictionDetailRepository
					.findByNscApplicationNo(sspDto.getNscApplicationNo());

			if (consumerAppDetail != null) {
				Response<Consumer> response = new Response<>();
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("NSC Application already exist");
				response.setDspApplicationNo(consumerAppDetail.getConsumerApplicationNo());
//				log.info("NSC Application already exist: {}", sspDto.getNscApplicationNo());
				throw new ConsumerException(response);
			}
		}

		validateDto(sspDto);
		Optional<Consumer> consumer = consumerRepository.findByConsumerLoginId(sspDto.getMobileNumber());

		if (consumer.isPresent()) {

			String consumerCredentials = consumer.get().getConsumerCredentials();

			ResponseEntity<Response> checkCredentials = checkCredentials(consumer.get(), request);

			Optional<Consumer> consumer3 = consumerRepository.findByConsumerLoginId(sspDto.getMobileNumber());

			Consumer consumer10 = consumer3.get();
			consumer10.setConsumerCredentials(consumerCredentials);
			Consumer updatedConsumer = consumerRepository.save(consumer10);
//				sspDto.setConsumerName(updatedConsumer.getConsumerName());
//				sspDto.setEmailId(updatedConsumer.getConsumerEmailId());
//				sspDto.setAddress(updatedConsumer.getAddress());
			ConsumerApplicationDetail saveConsumerApplication = saveConsumerApplication(sspDto);
			if (saveConsumerApplication != null) {
				updatedConsumer.setDspApplicationNo(saveConsumerApplication.getConsumerApplicationNo());
				updatedConsumer.setSspApplicationNo(saveConsumerApplication.getNscApplicationNo());
			}

			return new SignUpResponse(updatedConsumer, checkCredentials.getBody().getToken());
		} else {

			SignUpResponse saveConsumerBySSP = saveConsumerBySSP(sspDto, request);
			ConsumerApplicationDetail saveConsumerApplication = saveConsumerApplication(sspDto);
			if (saveConsumerApplication != null) {
				consumer.get().setDspApplicationNo(saveConsumerApplication.getConsumerApplicationNo());
				consumer.get().setSspApplicationNo(saveConsumerApplication.getNscApplicationNo());
			}
			return saveConsumerBySSP;
		}
	}

	public SignUpResponse saveConsumerBySSP(SSPDto sspDto, HttpServletRequest request)
			throws ConsumerException, DocumentTypeException {

		Response<Consumer> response = new Response<>();

		Consumer consumer = new Consumer();

		String consumerPassword = generatePassword();
		consumer.setConsumerCredentials(PasswordUtil.getPasswordHash(consumerPassword));

		final String password = PasswordUtil.getPasswordHash(consumerPassword);
		consumer.setConsumerName(sspDto.getConsumerName().trim());
		consumer.setConsumerLoginId(sspDto.getMobileNumber().trim());
		consumer.setConsumerMobileNo(sspDto.getMobileNumber().trim());
		consumer.setConsumerEmailId(sspDto.getEmailId().trim());
		consumer.setActive(Boolean.TRUE);
		consumer.setAccountNonExpired(Boolean.FALSE);
		consumer.setAccountNonLocked(Boolean.FALSE);
		consumer.setIsFirstLogin(Boolean.TRUE);
		consumer.setCredentialsNonExpired(null);
		consumer.setConsumerCredentials(password);
		consumer.setLoginAttemp(0L);
		consumer.setCreated(new Date());
		consumer.setCreatedBy(1L);
		consumer.setUpdated(new Date());
		consumer.setUpdatedBy(1L);
		consumer.setLoginStatus("NEW REGISTRATION");
		consumer.setCredentialsNonExpired(Boolean.FALSE);

		consumer.setAddress(sspDto.getAddress().trim());

		Consumer savedConsumer = consumerRepository.save(consumer);

		String token = getToken(savedConsumer, consumerPassword, request);

		sendPasswordSMS(sspDto, consumerPassword);

		return new SignUpResponse(savedConsumer, token);

	}

	public ResponseEntity<Response> checkCredentials(Consumer consumer1, HttpServletRequest request) {
		Response response = new Response();

		System.out.println("consumerVerificationCaptcha method calling!!!");

//       orignal password (encrpted password)
		String orginalpassword = consumer1.getConsumerCredentials();

//      temporary password (Pass@1234)
		consumer1.setConsumerCredentials("$2a$10$GvpWoJdgmP1YziOVXUUF5ewrtCTFR16XM2/vT09u6vh8fR2eqyt5i");

		String consumerLoginId = consumer1.getConsumerLoginId();
		String consumerLoginPwd = "Pass@1234";

		Consumer save = consumerRepository.save(consumer1);
		try {
//			if (consumerLoginId == null || consumerLoginId.isEmpty()) {
//				response.setCode("400");
//				response.setMessage("Id should not be null");
//				return ResponseEntity.ok().body(response);
//			}
//			if (consumerLoginPwd == null || consumerLoginPwd.isEmpty()) {
//				response.setCode("400");
//				response.setMessage("Password should not be null");
//				return ResponseEntity.ok().body(response);
//			}

			Timestamp today = new java.sql.Timestamp(new java.util.Date().getTime());
			Consumer consumer = null;
			Optional<Consumer> consumerDetail = consumerRepository.findByConsumerLoginId(consumerLoginId);

//			if (!consumerDetail.isPresent()) {
//				response.setCode("404");
//				response.setMessage("Consumer not found");
//				return ResponseEntity.ok().body(response);
//			}

			if (BCrypt.checkpw(consumerLoginPwd, consumerDetail.get().getConsumerCredentials())) {
				ConsumerLoginToken consumerLoginToken = new ConsumerLoginToken();

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(consumerLoginId + ":CONSUMER", consumerLoginPwd));
				final JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();

				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(jwtConsumer, null, request);
				System.out.println("bbbbbbbbbbbbbbbb : " + token);
				final HttpHeaders headers = new HttpHeaders();
				headers.set(ConstantProperty.CONTENT_TYPE, ConstantProperty.APPLICATION_JSON);
				headers.set("authorization", token);
				headers.set("Access-Control-Expose-Headers", "authorization");
				CheckConsumer findByConsumerNumber = checkConsumerRepository
						.findByConsumerNumber(consumer1.getConsumerMobileNo());
				if (findByConsumerNumber == null) {
					CheckConsumer checkConsumer = new CheckConsumer();
					checkConsumer.setConsumerNumber(consumerLoginId);
					checkConsumer.setCheckConsumer(consumerLoginPwd);
					checkConsumerRepository.save(checkConsumer);
				}

				response.setCode("200");
				response.setMessage("Login Successfully");
				response.setToken(token);
				response.setList(Arrays.asList(consumerDetail));

				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				response.setCode("404");
				response.setMessage("Invalid credentials");
				return ResponseEntity.ok().body(response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCode("500");
			response.setMessage(e.getMessage());
			return ResponseEntity.ok().body(response);
		}

	}

	public String getToken(Consumer consumer, String consumerPassword, HttpServletRequest request) {
		System.out.println("Provided Password: " + consumerPassword);

		// Check if the provided password matches the stored hashed password
		if (BCrypt.checkpw(consumerPassword, consumer.getConsumerCredentials())) {
			// Authenticate the user
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					consumer.getConsumerLoginId() + ":CONSUMER", consumerPassword));

			JwtConsumer jwtConsumer = (JwtConsumer) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Generate JWT token
			String token = jwtTokenUtil.generateToken(jwtConsumer, null, request);
			System.out.println("Generated Token: " + token);

			return token;
		}

		return "Credentials not matched";
	}

	private String generatePassword() {
		String uuid = UUID.randomUUID().toString();
		String numberPart = uuid.replaceAll("[^0-9]", "").substring(0, 4);
		return "Pass#" + numberPart;
	}

	private void sendPasswordSMS(SSPDto sspDto, String consumerPassword) {
		try {
			SMSRequest smsRequest = new SMSRequest();
			smsRequest.setText(MessageFormat.format(messageProperties.getDefaultPasswordMessage(),
					sspDto.getConsumerName(), consumerPassword));
			smsRequest.setMobileNo(sspDto.getMobileNumber());
			smsRequest.setTemplateId(messageProperties.getPasswordTemplateId());
			smsRequest.setHinglish(1L);
			// Send SMS (uncomment the service call if necessary)
			smsDirectService.sendMessage(smsRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConsumerApplicationDetail saveConsumerApplication(SSPDto sspDto)
			throws DistributionCenterException, DistrictException {
		log.info("=== Inside saveConsumerApplication() ===");
		try {
			NatureOfWork natureOfWork = null;
			Optional<Consumer> consumer = consumerRepository.findByConsumerLoginId(sspDto.getMobileNumber());
			Consumer consumerData = consumer.get();

			SchemeType schemeType = schemeTypeRepository.findById(sspDto.getSchemeType()).get();

//		if (sspDto.getNatureOfWork().equals(5L) && sspDto.getMeterCost() <= 0) {
//			natureOfWork = natureOfWorkRepository.findById(sspDto.getNatureOfWork()).get();
//		} else if (sspDto.getNatureOfWork().equals(5L) && sspDto.getMeterCost() > 0) {
//			natureOfWork = natureOfWorkRepository.findById(2L).get();
//		} else if (sspDto.getNatureOfWork().equals(7L)) {
//			natureOfWork = natureOfWorkRepository.findById(7L).get();
//		} else {
//			natureOfWork = natureOfWorkRepository.findById(2L).get();
//		}
//			sspDto = checkNatureOfWork(sspDto);
			natureOfWork = natureOfWorkRepository.findById(sspDto.getNatureOfWork()).get();

			long uniqueCurrentTimeMS = uniqueCurrentTimeMS();
			String applicationNumber = String.valueOf(uniqueCurrentTimeMS);

			String prefix = schemeType.getSchemeTypeId().equals(1L) ? "SV" : "DS";
			String consumerAppNo = prefix + applicationNumber;

			DistributionCenter distributionCenter = distributionCenterRepository
					.findDistributionCenterByNgbDcCd(sspDto.getDcCode());
			if (distributionCenter == null) {
				log.info("Distribution Center not found for DC Code: {}", sspDto.getDcCode());
				Response<Consumer> response = new Response<>();
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Given Dc not found in database");
				throw new DistributionCenterException(response);
			}
			log.info("Distribution Center found: {}", distributionCenter.getNgbDcCd());
			District district;
			try {
				district = districtRepository.findById(distributionCenter.getDistrict().getDistrictId())
						.orElseThrow(() -> {
							log.info("District not found for District ID: {}",
									distributionCenter.getDistrict().getDistrictId());
							Response<Consumer> response = new Response<>();
							response.setCode(HttpCode.NULL_OBJECT);
							response.setMessage("District Id not found");
							return new DistrictException(response);
						});
				log.info("District fetched: {}", district.getDistrictName());
			} catch (DistrictException e) {

				throw e;
			}

			Optional<LandAreaUnit> landAreaUnit = landAreaUnitRepository.findById(sspDto.getAreaUnit());
			if (!landAreaUnit.isPresent()) {
				log.info("Land Area Unit not found for ID: {}", sspDto.getAreaUnit());
				Response<Consumer> response = new Response<>();
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Given Load Area Unit not found in database");
				throw new LandAreaUnitException(response);
			}

//		19-05-2025
			LoadRequested loadRequestedUnit = null;
			if (sspDto.getLoadUnit() != null) {
				loadRequestedUnit = loadRequestedRepository.findByLoadRequestedName(sspDto.getLoadUnit())
						.orElseThrow(() -> new LoadRequestedException(
								new Response(HttpCode.NULL_OBJECT, "Given Load Unit not found in database")));
			}

//		log.info("Load Requested Unit fetched: {}", loadRequestedUnit.getLoadRequestedName());
//		19-05-2025
			ConsumerApplicationDetail cad = new ConsumerApplicationDetail();
			cad.setConsumers(consumerData);
			cad.setConsumerApplicationNo(consumerAppNo);
			cad.setConsumerName(sspDto.getConsumerName());
			cad.setAddress(sspDto.getAddress());
			cad.setConsumerphonNumber(sspDto.getMobileNumber());
			cad.setNscApplicationNo(sspDto.getNscApplicationNo());
			cad.setSspPortalName("SSP Portal");
			cad.setNatureOfWorkType(natureOfWork);
			cad.setSchemeType(schemeType);
			cad.setDistributionCenter(distributionCenter);
			cad.setGuardianName(sspDto.getGuardianName());
			cad.setArea(sspDto.getArea());
			cad.setLandAreaUnit(landAreaUnit.get());
			cad.setAvedakRemark(sspDto.getOrganizationName());
//		added this line for samagra id 2-jan 2025
			cad.setSamagraId(sspDto.getMemberId());
			cad.setIvrsNo(sspDto.getConsumerNo());
			cad.setPremiseAreaType(sspDto.getPremiseAreaType());
			if (sspDto.getAppliedLoad() != null) {
				cad.setLoadRequested(sspDto.getAppliedLoad().toString());
			}
//		end
			cad.setLoadRequestedId(loadRequestedUnit);
			if ((natureOfWork.getNatureOfWorkTypeId() == 2l || natureOfWork.getNatureOfWorkTypeId() == 5l)
					&& Objects.nonNull(sspDto.getTotalAmount())) {
				if (Objects.nonNull(sspDto.getRegistrationCharge()))
					cad.setSspRegistrationCharge(BigDecimal.valueOf(sspDto.getRegistrationCharge()));
				if (Objects.nonNull(sspDto.getMeterCost()))
					cad.setSspMeterCost(BigDecimal.valueOf(sspDto.getMeterCost()));
				if (Objects.nonNull(sspDto.getSecurityDeposit()))
					cad.setSspSecurityDeposit(BigDecimal.valueOf(sspDto.getSecurityDeposit()));
				if (Objects.nonNull(sspDto.getSupplyAffordingCharges()))
					cad.setSspSupplyAffordingCharges(BigDecimal.valueOf(sspDto.getSupplyAffordingCharges()));
				if (Objects.nonNull(sspDto.getTotalAmount()))
					cad.setSspTotalAmount(BigDecimal.valueOf(sspDto.getTotalAmount()));
			}
			cad.setPurposeOfInstallation(sspDto.getPurposeOfInstallation());
			cad.setPhase(sspDto.getPhase());

			if (sspDto.getIsGoverment().equals("YES")) {
				cad.setAvedakKaPrakar("Government");
			} else {
				cad.setAvedakKaPrakar("Private");
			}
			cad.setDistrict(district);
			cad.setConnectionType(sspDto.getConnectionType());
			cad.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.UNPAID_APPLICATION_VENDOR_REJECTED.getId()).get());

			cad.setConnectionCategory(sspDto.getConnectionCategory());
			cad.setMeteringStatus(sspDto.getMeteringStatus());
			cad.setCastCategory(sspDto.getCaste());
			return consumerApplictionDetailRepository.save(cad);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();

	public static long uniqueCurrentTimeMS() {
		long now = System.currentTimeMillis();
		while (true) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now)
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now))
				return now;
		}
	}

	public void validateDto(SSPDto sspDto) throws ConsumerException {
		Response<Consumer> response = new Response<>();

		if (Objects.isNull(sspDto)) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ConsumerException(response);
		} else {
			/*********** validations start ***********************/

			if (sspDto.getNatureOfWork() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Nature Of Work Can not be null");
				throw new ConsumerException(response);
			}
			// code written on 11-11-2025 start
//			if (sspDto.getNatureOfWork().equals(5l)) {
//				if (sspDto.getIsOyt().equals(1)) {
//					if (sspDto.getMeteringStatus() == null || sspDto.getMeteringStatus().equals("")) {
//						throw new ConsumerException(
//								new Response(HttpCode.NULL_OBJECT, "Metering status can not be null"));
//					}
//				}
//
//				if (sspDto.getIsOyt().equals(2)) {
//					if (sspDto.getMeteringStatus() == null || sspDto.getMeteringStatus().equals("")) {
//						throw new ConsumerException(
//								new Response(HttpCode.NULL_OBJECT, "Metering status can not be null"));
//					}
//				}
//			}

			sspDto = checkNatureOfWork(sspDto);

			// code written on 11-11-2025 end

			if (sspDto.getConsumerName() == null || sspDto.getConsumerName().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Consumer Name");
				throw new ConsumerException(response);
			}

			if (sspDto.getEmailId() == null || sspDto.getEmailId().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Email Id");
				throw new ConsumerException(response);
			}

			if (sspDto.getMobileNumber() == null || sspDto.getMobileNumber().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Mobile No");
				throw new ConsumerException(response);
			}

			if (sspDto.getAddress() == null || sspDto.getAddress().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Please fill Address");
				throw new ConsumerException(response);
			}

//			if (sspDto.getIsOyt() != null && (sspDto.getIsOyt().equals(1) || sspDto.getIsOyt().equals(2))) {
//				if (sspDto.getNscApplicationNo() == null || sspDto.getNscApplicationNo().equals("")) {
//
//					response.setCode(HttpCode.NULL_OBJECT);
//					response.setMessage("SSP Application No. Can not be null");
//					throw new ConsumerException(response);
//				}
//			}

			if (sspDto.getSchemeType() == null) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Scheme Type Can not be null");
				throw new ConsumerException(response);
			}

			if (sspDto.getDcCode() == null || sspDto.getDcCode().equals("")) {

				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Dc Code Can not be null");
				throw new ConsumerException(response);
			}

//			if(sspDto.getOrganizationName()==null || sspDto.getOrganizationName().equals("")) {
//				response.setCode(HttpCode.NULL_OBJECT);
//				response.setMessage("Organization name should not be null");
//				throw new ConsumerException(response);
//			}

			if (sspDto.getNatureOfWork() != 7l) {
				if (sspDto.getArea() == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Area should not be null");
					throw new ConsumerException(response);
				}

				if (sspDto.getAreaUnit() == null) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Area Unit should not be null");
					throw new ConsumerException(response);
				}
			}

			if (sspDto.getNatureOfWork() == 7l) {
				if (sspDto.getAppliedLoad() == null) {
					throw new ConsumerException(new Response(HttpCode.NULL_OBJECT, "Applied Load should not be null"));
				}

				if (sspDto.getLoadUnit() == null) {
					throw new ConsumerException(new Response(HttpCode.NULL_OBJECT, "Load Unit should not be null"));
				}

				if (sspDto.getConsumerNo() == null) {
					throw new ConsumerException(new Response(HttpCode.NULL_OBJECT, "Consumer No. should not be null"));
				}
			}

			if (sspDto.getNatureOfWork() == 2L || sspDto.getNatureOfWork() == 5L) {

				if (sspDto.getTotalAmount() == null
						|| BigDecimal.valueOf(sspDto.getTotalAmount()).compareTo(BigDecimal.ZERO) <= 0) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage("Total Amount should not be  null, 0 or negative in case of new NSC");
					throw new ConsumerException(response);
				}

//				|| sspDto.getIsBpl().equals("NO")  || sspDto.getIsShramik().equals("NO") ye 2 check charitra ne lagaye hai 31-12-2025
//				check added on 11-11-2025 because in case of OYT there is only 5 as TotalAmount()
				if ((sspDto.getSecurityDeposit() == null
						|| BigDecimal.valueOf(sspDto.getSecurityDeposit()).compareTo(BigDecimal.ZERO) <= 0)  
						&& (!"SC".equals(sspDto.getCaste()) && !"ST".equals(sspDto.getCaste()))) {
					response.setCode(HttpCode.NULL_OBJECT);
					response.setMessage(
							"Security Deposit Amount should not be null, 0 or negative in case of new NSC and OYT. Please edit your application.");
					throw new ConsumerException(response);
				}
				
			}
			if (sspDto.getNatureOfWork() == 13L || sspDto.getNatureOfWork() == 14L) {
				sspDto.setNatureOfWork(5l);
			}
			
			
		}
	}

	@Override
	public String sendDataToSspAfterWorkOrder(String consumerApplicationNo, Long applicationStatusId)
			throws ConsumerApplicationDetailException {
		log.info("[START] sendDataToSspAfterWorkOrder | API: /sendDataToSspAfterWorkOrder | consumerApplicationNo: {}",
				consumerApplicationNo);

		ConsumerApplicationDetail validateConsumerApplication = dryUtility
				.validateConsumerApplication(consumerApplicationNo);
//		String sendDataToSSp = workCompletionChangStautsByDgmOAndMServiceIMp.sendDataToSSp(
//				validateConsumerApplication.getNscApplicationNo(), applicationStatusId, consumerApplicationNo);

//		given line is new code 19-05-2025 commented due to production early deployment
		log.info("Calling sendDataToSSp1 for consumerApplicationNo: {}", consumerApplicationNo);
		String sendDataToSSp = workCompletionChangStautsByDgmOAndMServiceIMp
				.sendDataToSSp1(validateConsumerApplication);
//		end

		log.info("=== sendDataToSSp after sending the data to SSP Portal revert back to SSP_Service_Impl   ===");
		if ("Success".equals(sendDataToSSp) && applicationStatusId != 2l) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedDate = LocalDateTime.now().format(formatter);
			validateConsumerApplication.setSspApplicationCompleted("true");
			validateConsumerApplication.setSspApplicationCompleteDate(formattedDate);
			consumerApplictionDetailRepository.save(validateConsumerApplication);
			log.info("Application marked as completed for consumerApplicationNo: {}", consumerApplicationNo);
			return "Success";
		}

		log.info("[END] sendDataToSspAfterWorkOrder | consumerApplicationNo: {} | Result: {}", consumerApplicationNo,
				sendDataToSSp);

		return sendDataToSSp;
	}

	public static SSPDto checkNatureOfWork(SSPDto sspDto) {
		
		  
		if (sspDto.getNatureOfWork().equals(5L) && sspDto.getSchemeType() == 1l && sspDto.getMeterCost() != null
				&& sspDto.getMeterCost() <= 0) {
			sspDto.setNatureOfWork(5l);
			sspDto.setConnectionCategory("LV5");
		} else if (sspDto.getNatureOfWork().equals(5L) && sspDto.getSchemeType() == 2l && sspDto.getMeterCost() != null
				&& sspDto.getMeterCost() > 0) {
			sspDto.setNatureOfWork(2l);
			sspDto.setConnectionCategory("LV5");
		} else if((sspDto.getNatureOfWork().equals(5L) && sspDto.getSchemeType() == 1l) || (sspDto.getNatureOfWork().equals(5L) && sspDto.getSchemeType() == 2l) && sspDto.getMeterCost() == null) {
			 if (sspDto.getIsOyt() != null && sspDto.getIsOyt().equals(13)) {
					sspDto.setNatureOfWork(13l);
					sspDto.setConnectionCategory("LV5");
				} else if (sspDto.getIsOyt() != null && sspDto.getIsOyt().equals(14)) {
					sspDto.setNatureOfWork(14l);
					sspDto.setConnectionCategory("LV5");
				}
		} else if (sspDto.getNatureOfWork().equals(7L)) {
			sspDto.setNatureOfWork(7l);
		} 
		else {
			sspDto.setNatureOfWork(2l);
		}
		
		return sspDto;
		}
	
}


