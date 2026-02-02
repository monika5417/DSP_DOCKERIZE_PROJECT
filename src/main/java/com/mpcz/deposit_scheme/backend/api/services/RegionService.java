package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Region;
import com.mpcz.deposit_scheme.backend.api.exception.RegionException;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;


/**
 * 
 * @author Aditya Vyas
 * @version 1.0.0
 *
 */
public interface RegionService {
	public Response<Region> save(final Region region) throws RegionException;

	public Response<Region> update(final Region region) throws RegionException;
	
	public Response<Region> delete(final Long regionId) throws RegionException;

	public Response<Region> findById(final Long regionId) throws RegionException;
	
	public Response<List<Region>> findAll() throws RegionException;

	public List<Region> findAllRegions() throws RegionException;
	
	public Region findRegionById(final Long regionId) throws RegionException;
	
	public List<Region> findAllRegionsByDiscom(final Long discomId) throws RegionException;
	
	public String isRegionExist(DataStatusDTO dataStatusDTO) throws RegionException;
	
	public String isRegionCodeExist(DataStatusDTO dataStatusDTO) throws RegionException;
}
