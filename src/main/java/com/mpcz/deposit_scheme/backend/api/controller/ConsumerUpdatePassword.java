package com.mpcz.deposit_scheme.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.CheckConsumer;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerUpdatePasswordDTO;
import com.mpcz.deposit_scheme.backend.api.jwt.security.util.PasswordUtil;
import com.mpcz.deposit_scheme.backend.api.repository.CheckConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerService;


//  Created By Monika

@RestController
@RequestMapping("/api/updateConsumer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConsumerUpdatePassword {

		@Autowired
		private ConsumerRepository consumerRepository;

		@Autowired
		private ConsumerService consumerService;
		
		@Autowired
		private CheckConsumerRepository checkConsumerRepository;

		@PostMapping("/password")
		public Response<?> changePassword(@RequestBody ConsumerUpdatePasswordDTO consumerUpdatePasswordDTO) {
			Response<?> response = new Response<>();
			Consumer save = null;
			try {
				Consumer consumer = consumerService.findByMobileNo(consumerUpdatePasswordDTO.getConsumerMobileNo());
				if (!consumerUpdatePasswordDTO.getPassword().equals(consumerUpdatePasswordDTO.getConfirmPassword())) {
					response.setCode("100");
					response.setMessage("password or confirm password not match");
					response.setList(null);
					return response;
				} else {
					CheckConsumer consumerNumberDB = checkConsumerRepository.findByConsumerNumber(consumerUpdatePasswordDTO.getConsumerMobileNo());
					if(consumerNumberDB!=null) {
						consumerNumberDB.setCheckConsumer(consumerUpdatePasswordDTO.getPassword());
						checkConsumerRepository.save(consumerNumberDB);
					}else {
						CheckConsumer consumerNumber = new CheckConsumer();
						consumerNumber.setConsumerNumber(consumerUpdatePasswordDTO.getConsumerMobileNo());
						consumerNumber.setCheckConsumer(consumerUpdatePasswordDTO.getPassword());
						checkConsumerRepository.save(consumerNumber);
					}
					final String password = PasswordUtil.getPasswordHash(consumerUpdatePasswordDTO.getPassword());
					
					consumer.setConsumerCredentials(password);
					save = consumerRepository.save(consumer);
				}

			} catch (Exception e) {
				response.setCode("100");
				response.setMessage("Consumer not found plesase give valid Mobile No");
				return response;
			}
			if (save != null) {
				response.setCode("200");
				response.setMessage("password Sucessfully changed");
				return response;
			} else {
				response.setCode("100");
				response.setMessage("password not changed");
				return response;
			}

		}

	}

	

