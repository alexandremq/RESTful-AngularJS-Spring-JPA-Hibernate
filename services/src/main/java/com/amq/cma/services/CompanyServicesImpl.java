package com.amq.cma.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;
import com.amq.cma.persistence.CompanyDao;

@Service("companyServices")
@Profile("dev")
public class CompanyServicesImpl implements CompanyServices {
	private final Logger logger = LoggerFactory.getLogger(CompanyServicesImpl.class);
	private CompanyDao companyDao;
	
	@Autowired
	public void setCompanyDao(CompanyDao companyDao){
		this.companyDao = companyDao;
	}
	
	@Override
	public List<Company> getAllCompanies() {
		logger.debug("Selecting all companies stored...");
		
		return companyDao.getAllCompanies();
	}
	
	@Override
	public Company getCompany(Long companyId) {
		if(companyId == null){ return null; }
		logger.debug("Selecting company with id "+ companyId +" ...");
		
		return companyDao.getCompany(companyId);
	}
	
	@Override
	public Company saveCompany(Company newCompany) {
		if(newCompany == null){ return null; }
		logger.debug("Saving company..."+ newCompany);
		
		return companyDao.saveCompany(newCompany);
	}
	
	@Override
	public Company addOwners(Long companyId, List<Owner> owners) {
		if(companyId == null){ 
			logger.warn("Company id was null, so returning null."); 
			return null; 
		}
		logger.info("Adding Owner(s) "+ owners + " to company "+ companyId +"...");
		
		return companyDao.addOwners(companyId, owners);
	}
	
	@Override
	public List<Owner> getAllOwners(){
		logger.debug("Selecting all owners stored...");
		
		return companyDao.getAllOwners();
	}
	
	@Override
	public Owner saveOwner(Owner owner){
		if(owner == null){ return owner; }		
		logger.info("Saving owner "+ owner.getName() +"...");
		
		return companyDao.saveOwner(owner);
	}

}
