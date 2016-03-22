package com.amq.cma.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;
import com.amq.cma.services.CompanyServices;

@RestController
@RequestMapping("/services")
@Profile("dev")
public class CmaFrontController {
	private final Logger logger = LoggerFactory.getLogger(CmaFrontController.class);
	private CompanyServices companyServices;
	
	@Autowired
	public void setCompanyServices(CompanyServices companyServices){
		this.companyServices = companyServices;
	}
	
	
	@RequestMapping(value="/companies" , method=RequestMethod.GET)
	public ResponseEntity<List<Company>> listCompanies() {
		logger.debug("Listing companies...");
		
		List<Company> comps = companyServices.getAllCompanies();
		
		return new ResponseEntity<List<Company>>(comps, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/companies/{companyId}" , method=RequestMethod.GET)
	public ResponseEntity<Company> getCompany(@PathVariable long companyId){
		logger.debug("Looking for company "+ companyId +"...");
		
		Company comp = companyServices.getCompany(companyId);
		return new ResponseEntity<Company>(comp, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/companies/save", method=RequestMethod.POST)
	public ResponseEntity<Company> saveCompany(@RequestBody Company company){
		if(company == null){ return new ResponseEntity<Company>(company, HttpStatus.UNPROCESSABLE_ENTITY); }
		logger.debug("Saving company"+ company +"...");
		
		company = companyServices.saveCompany(company);
		
		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}
	
	@RequestMapping(value="/owners", method=RequestMethod.GET)
	public ResponseEntity<List<Owner>> listOwners(){
		logger.debug("Listing owners...");
		
		List<Owner> owners = companyServices.getAllOwners();
		
		return new ResponseEntity<List<Owner>>(owners, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/owners/save", method=RequestMethod.POST)
	public ResponseEntity<Owner> saveOwner(@RequestBody Owner owner){
		if(owner == null){ return new ResponseEntity<Owner>(owner, HttpStatus.UNPROCESSABLE_ENTITY); }
		logger.debug("Saving owner "+ owner +"..." );
		
		owner = companyServices.saveOwner(owner);
		return new ResponseEntity<Owner>(owner, HttpStatus.OK);
	}
}
