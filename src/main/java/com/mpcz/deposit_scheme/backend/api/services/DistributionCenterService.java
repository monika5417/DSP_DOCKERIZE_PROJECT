package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This DistributionCenterService is used to Manage DistributionCenters
 * 
 * @author Aditya Vays
 * @version 1.0
 */

public interface DistributionCenterService {
	public Response<DistributionCenter> save(final DistributionCenter distributionCenter)
			throws DistributionCenterException;

	public Response<DistributionCenter> update(final DistributionCenter distributionCenter)
			throws DistributionCenterException;

	public Response<DistributionCenter> delete(final long distributionCenterId) throws DistributionCenterException;

	public Response<DistributionCenter> findById(final long distributionCenterId) throws DistributionCenterException;

	public Response<List<DistributionCenter>> findAll() throws DistributionCenterException;

	public List<DistributionCenter> findAllDistributionCenters() throws DistributionCenterException;

	public DistributionCenter findDistributionCenterById(Long distributionCenterId) throws DistributionCenterException;

	public List<DistributionCenter> findAllDistributionCentersBySubDivision(final Long subDivisionId)
			throws DistributionCenterException;

	public String isDistributionCenterExist(DataStatusDTO dataStatusDTO) throws DistributionCenterException;

	public String isDistributionCenterCodeExist(DataStatusDTO dataStatusDTO) throws DistributionCenterException;

//	sandeep, starts
	public List<DistributionCenter> findAllActiveDistributionCenter() throws DistributionCenterException;
//	sandeep, ends

	public DistributionCenter findDistributionCenterByDcCode(String dcCode) throws DistributionCenterException;

	
	List<DistributionCenter> findAllDistributionCentersByDistrict(Long districtId) throws DistributionCenterException;
	
	
	List<DistributionCenter> findAllDistributionCentersBydcIdAnddcSubdivision_subDivisionIdAndSubdivisionDivision_divisionId(Long divisionId) ;
	

}
