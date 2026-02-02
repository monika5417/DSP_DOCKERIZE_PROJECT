package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.ChargeRateMaster;
import com.mpcz.deposit_scheme.backend.api.repository.ChargeRateMasterRepository;
import com.mpcz.deposit_scheme.backend.api.services.ChargeRateMasterService;

@Service
public class ChargeRateMasterServiceImpl implements ChargeRateMasterService {

	Date today = null;

	@Autowired
	ChargeRateMasterRepository chargeRateMasterRepository;

	@Override
	public List<ChargeRateMaster> findAllChargeRates() {

//		String date_string = "01-11-2022";
//
//		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//
//		try {
//			today = formatter.parse(date_string);
//		} catch (ParseException e) {
//
//			e.printStackTrace();
//		}
//		System.out.println("Date value: " + today);
		
		today = new Date();

		List<ChargeRateMaster> chargeRateMasterList = chargeRateMasterRepository.findAll();

		List<ChargeRateMaster> chargeMasterList = null;

		if (chargeRateMasterList != null) {
			chargeMasterList = chargeRateMasterList.stream().filter((d) ->

			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = d.getStartDate();

				try {
					startDate = sdf.parse(sdf.format(startDate));
				} catch (ParseException e) {
				}

				Date endDate = d.getEndDate();

				try {
					endDate = sdf.parse(sdf.format(endDate));
				} catch (ParseException e) {
				}

				if (startDate.compareTo(today) <= 0 && endDate.compareTo(today) >= 0) {
					return true;
				} else {
					return false;
				}
			}

			).filter(ch -> ch.isActive()).collect(Collectors.toList());
		}

		return chargeMasterList;

	}

	@Override
	public ChargeRateMaster findChargeRateByType(Long chargeTypeId) {
		List<ChargeRateMaster> chargeRates = findAllChargeRates();
		Optional<ChargeRateMaster> chargeRateMasterOptional = Optional.empty();
		if (chargeRates != null) {
			chargeRateMasterOptional = chargeRates.stream()
					.filter(ch -> ch.getChargesType().getChargeTypeId().compareTo(chargeTypeId) == 0).findFirst();

		}

		if (chargeRateMasterOptional.isPresent()) {
			return chargeRateMasterOptional.get();
		} else {
			return null;
		}

	}

}
