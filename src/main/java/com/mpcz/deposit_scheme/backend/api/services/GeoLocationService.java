package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.GeoLocation;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.GeoLocationException;
import com.mpcz.deposit_scheme.backend.api.request.GeoPendingStausRequestForm;

public interface GeoLocationService {

	GeoLocation savelocation(GeoLocation geoLocation) throws GeoLocationException, ConsumerApplicationDetailException, DocumentTypeException;

	GeoLocation findbyCosumerApplicationNumber(String consumerApplicationNumber) throws GeoLocationException;

	//void findByMobileNumber(GeoPendingStausRequestForm geoPendingStausRequestForm);

	//void fetchPendingGeoLocationConsumerApplications(GeoPendingStausRequestForm geoPendingStausRequestForm)throws GeoLocationException;

	
}
