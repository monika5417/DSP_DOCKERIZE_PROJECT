package com.mpcz.deposit_scheme.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.domain.OytOf14;
import com.mpcz.deposit_scheme.backend.api.exception.NatureOfWork14Exception;
import com.mpcz.deposit_scheme.backend.api.repository.Oyt14Repository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@RestController
@RequestMapping("/oyt14")
@CrossOrigin(origins = "*",maxAge = 3600)
public class Oyt14Controller {
	
	
	@Autowired
	private Oyt14Repository oyt14Repository; 
	
	@RequestMapping("/save")
	public Response<OytOf14> save(@RequestBody List<OytOf14> oyt) throws NatureOfWork14Exception {
		
		Response<OytOf14> r = new Response();
		
		int sum = oyt.stream().mapToInt(o-> o.getLoad()).sum();
		if(sum<=21) {
			throw new NatureOfWork14Exception("Application not allowed: Load must be 21 or above for this nature of work.");
		}
		
		 List<OytOf14> saveAll = oyt14Repository.saveAll(oyt);
		 r.setCode("200");
		 r.setMessage("data submited");
		 r.setList(saveAll);
		 return r;
		
	}
	
	@GetMapping("/getbyapplication-no/{applicationNo}")
	public Response<OytOf14> getDataAppliationbyapplicationNO(@PathVariable String applicationNo) throws NatureOfWork14Exception {
		
		Response<OytOf14> r = new Response();
		
		
		
		 List<OytOf14> saveAll = oyt14Repository.findByconsumerApplciationNo(applicationNo);
		 
		 if(!saveAll.isEmpty()) {
			 r.setCode("404");
			 r.setMessage("data not found");
			 r.setList(saveAll);
			 return r;
		 }
		 r.setCode("200");
		 r.setMessage("data retrive successfull");
		 r.setList(saveAll);
		 return r;
		
	}

	
	@PutMapping("/updatby")
	public Response<OytOf14> getDataAppliationbyapplicationNO(@RequestBody List<OytOf14> oyt) throws NatureOfWork14Exception {
		
		Response<OytOf14> r = new Response();
		
		oyt.stream().forEach(o->{
			oyt14Repository.deleteById(o.getId());
		});
		
		 List<OytOf14> saveAll = oyt14Repository.saveAll(oyt);
		 
		 if(saveAll.isEmpty()) {
			 r.setCode("404");
			 r.setMessage("data not found");
			 
			 return r;
		 }
		 r.setCode("200");
		 r.setMessage("data retrive successfull");
		 r.setList(saveAll);
		 return r;
		
	}
}
