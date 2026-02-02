//package com.mpcz.deposit_scheme.backend.api.services.impl;
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
//import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
//import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
//import com.mpcz.deposit_scheme.backend.api.domain.ConsumerType;
//import com.mpcz.deposit_scheme.backend.api.domain.Role;
//import com.mpcz.deposit_scheme.backend.api.domain.User;
//import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
//import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
//import com.mpcz.deposit_scheme.backend.api.exception.UserException;
//import com.mpcz.deposit_scheme.backend.api.jwt.security.util.PasswordUtil;
//import com.mpcz.deposit_scheme.backend.api.repository.UserRepositoryOld;
//import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
//import com.mpcz.deposit_scheme.backend.api.request.UserSignUpForm;
//import com.mpcz.deposit_scheme.backend.api.response.Response;
//import com.mpcz.deposit_scheme.backend.api.services.CircleService;
//import com.mpcz.deposit_scheme.backend.api.services.DiscomService;
//import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
//import com.mpcz.deposit_scheme.backend.api.services.DivisionService;
//import com.mpcz.deposit_scheme.backend.api.services.FeederService;
//import com.mpcz.deposit_scheme.backend.api.services.RegionService;
//import com.mpcz.deposit_scheme.backend.api.services.RoleServices;
//import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
//import com.mpcz.deposit_scheme.backend.api.services.SubDivisionService;
//import com.mpcz.deposit_scheme.backend.api.services.SubstationService;
//import com.mpcz.deposit_scheme.backend.api.services.UserServiceOld;
//import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;
//import com.mpcz.deposit_scheme.backend.api.utility.Utility;
//import com.mpcz.deposit_scheme.backend.api.validation.AccessLevelFieldValue;
//
//
//@Service
//public class UserServiceImplOld implements UserServiceOld{
//	
//	Logger logger = LoggerFactory.getLogger(UserServiceImplOld.class);
//
//	@Autowired
//	private MessageProperties messageProperties;
//	
//	@Autowired
//	private UserRepositoryOld userRepository;
//	
//	@Autowired
//	private DiscomService discomService;
//	
//	@Autowired
//	private RegionService regionService;
//	
//	@Autowired
//	private CircleService circleService;
//	
//	@Autowired
//	private DivisionService divisionService;
//	
//	@Autowired
//	private SubDivisionService subDivisionService;
//	
//	@Autowired
//	private DistributionCenterService distributionSenterService;
//	
//	@Autowired
//	private SubstationService substationService;
//	
//	@Autowired
//	private FeederService feederService;
//	
//	@Autowired
//	private SMSDirectService smsDirectService;
//	
//	@Autowired
//	private RoleServices roleService;
//	
//	
//	@Override
//	public User saveUser(@Valid UserSignUpForm userSingUpForm) throws Exception {
//		final String method = "UserServiceImpl : saveUser()";
//		logger.info(method);
//
//		Response<User> response = new Response<>();
//
//		User user1 = null;
//
//		if (Objects.isNull(userSingUpForm)) {
//			logger.error("Consumer object is null");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
//			throw new UserException(response);
//		} else {
//
//		
//			User user=new User();
//
//			final SMSRequest smsRequest = new SMSRequest();
//
//			final String defaultUserPassword = Utility.getPassword(8);
//			
//			smsRequest.setText(MessageFormat.format(messageProperties.getDefaultPasswordMessage(),
//					new Object[] { userSingUpForm.getUserName(), defaultUserPassword }));
//			smsRequest.setMobileNo(userSingUpForm.getUserMobileNo());
//			smsRequest.setTemplateId(messageProperties.getPasswordTemplateId());
//
//			final String password = PasswordUtil.getPasswordHash(defaultUserPassword);
//			
//			user.setUserName(userSingUpForm.getUserName().trim());
//			user.setUserLoginId(userSingUpForm.getUserLoginId().trim());
//			user.setUserCredentials(password);
//			user.setUserMobileNo(userSingUpForm.getUserMobileNo().trim());
//			user.setUserEmailId(userSingUpForm.getUserEmailId().trim());
//			user.setUserAadharNo(userSingUpForm.getUserAadharNo().trim());
//			
//			List<Role> userRoles = new ArrayList<>();
//			for (Long role : userSingUpForm.getUserRoles()) {
//
//				Role r = roleService.findRoleById(role);
//				userRoles.add(r);
//			}
//
//			user.setUserRoles(userRoles);
//		
//			user.setActive(Boolean.TRUE);
//			user.setAccountNonExpired(Boolean.FALSE);
//			user.setAccountNonLocked(Boolean.FALSE);
//			user.setIsFirstLogin(Boolean.TRUE);
//			user.setLoginAttemp(0L);
//			user.setCreated(new Date());
//			user.setCreatedBy(1L);
//			user.setUpdated(new Date());
//			user.setUpdatedBy(1L);
//			user.setLoginStatus("NEW REGISTRATION");
//			user.setCredentialNonExpired(Boolean.FALSE);
//
//			if (AccessLevelFieldValue.DISCOM_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setDiscom(discomService.findDiscomById(userSingUpForm.getDiscom()));
//			}
//			if (AccessLevelFieldValue.REGION_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setRegion(regionService.findRegionById(userSingUpForm.getRegion()));
//			}
//			if (AccessLevelFieldValue.CIRCLE_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setCircle(circleService.findCircleById(userSingUpForm.getCircle()));
//			}
//			if (AccessLevelFieldValue.DIVISION_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setDivision(divisionService.findDivisionById(userSingUpForm.getCircle()));
//			}
//			if (AccessLevelFieldValue.SUBDIVISION_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setSubdivision(subDivisionService.findSubDivisionById(userSingUpForm.getSubdivision()));
//			}
//			if (AccessLevelFieldValue.DISTRIBUTION_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setDistributionCenter(distributionSenterService.findDistributionCenterById(userSingUpForm.getDistributionCenter()));
//			}
//			if (AccessLevelFieldValue.DISTRIBUTION_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setDistributionCenter(distributionSenterService.findDistributionCenterById(userSingUpForm.getDistributionCenter()));
//			} 
//			if (AccessLevelFieldValue.SUBSTATION_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setSubstation(substationService.findSubstationById(userSingUpForm.getSubstation()));
//			} 
//			if (AccessLevelFieldValue.FEEDER_LEVEL.equals(userSingUpForm.getAccessLevel())) {
//				user.setFeeder(feederService.findFeederById(userSingUpForm.getFeeder()));
//			}
//
//			user = userRepository.save(user);
//
//			try {
//				smsDirectService.sendMessage(smsRequest);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (Objects.isNull(user)) {
//				logger.error("repository.save(consumer) is returning Null");
//				response.setCode(HttpCode.NULL_OBJECT);
//				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
//				throw new UserException(response);
//			} else {
//				return user;
//			}
//
//		}
//
//	}
//
//}
