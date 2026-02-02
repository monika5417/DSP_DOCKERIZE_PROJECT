package com.mpcz.deposit_scheme.backend.api.builddesk.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mpcz.deposit_scheme.backend.api.domain.ApplicationType;
import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.Discom;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.domain.Feeder;
import com.mpcz.deposit_scheme.backend.api.domain.Region;

@Configuration
public class CacheStoreConfig {
	@Bean
    public CacheStore<ApplicationType> applicationTypeCache() {
        return new CacheStore<ApplicationType>(23, TimeUnit.HOURS);
    }
	
	@Bean
    public CacheStore<Discom> discomTypeCache() {
        return new CacheStore<Discom>(23, TimeUnit.HOURS);
    }
	
	@Bean
    public CacheStore<Region> regionTypeCache() {
        return new CacheStore<Region>(23, TimeUnit.HOURS);
    }
	
	@Bean
    public CacheStore<Circle> circleTypeCache() {
        return new CacheStore<Circle>(23, TimeUnit.HOURS);
    }
	
	@Bean
    public CacheStore<Division> divisionTypeCache() {
        return new CacheStore<Division>(23, TimeUnit.HOURS);
    }
	
	@Bean
    public CacheStore<DistributionCenter> distributionCenterTypeCache() {
        return new CacheStore<DistributionCenter>(23, TimeUnit.HOURS);
    }
	
//	@Bean
//    public CacheStore<Feeder> feederTypeCache() {
//        return new CacheStore<Feeder>(23, TimeUnit.HOURS);
//    }
//	
//	@Bean
//    public CacheStore<District> districtTypeCache() {
//        return new CacheStore<District>(23, TimeUnit.HOURS);
//    }
	
	
}
