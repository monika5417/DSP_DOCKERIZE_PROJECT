package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.dto.MisExcelData;
import com.mpcz.deposit_scheme.backend.api.dto.MisExcelHeader;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.MisExcelDataException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;


public interface MisExcelDataService {

	List<MisExcelData> findAllDetails() throws MisExcelDataException,PaymentHistoryException, ConsumerApplicationDetailException, DistributionCenterException, SubDivisionException, DivisionException, CircleException;

	List<MisExcelHeader> findAllHeaderDetails() throws PaymentHistoryException, ConsumerApplicationDetailException,
			DistributionCenterException, SubDivisionException, DivisionException, CircleException, DemandDetailException;

	Double totalOfRegistrationCharges(List<MisExcelData> misExcelDetails);

	List<Double> totalOfDemandChargesDetails(List<MisExcelHeader> misExcelHeaderDetails);

}
