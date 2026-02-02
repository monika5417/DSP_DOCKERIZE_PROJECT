package com.mpcz.deposit_scheme.backend.api.services;

import java.util.Map;

import com.mpcz.deposit_scheme.backend.api.exception.MasterException;
import com.mpcz.deposit_scheme.backend.api.response.Response;

/*
 * This MasterService is used to manage masters(Enums or class)
 *
 * @author Aditya Vays
 * @version 1.0
 */
public interface MasterService {

	public Response<Map<Long, String>> findAllAccessLevel() throws MasterException;

}
