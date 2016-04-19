package com.amq.cma.services;

import java.util.List;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;

public interface CompanyServices {
	/** Return a List of all existing companies. Since not clarified what 
	 * is necessary on this list, a lazy loaded list will be returned here containing the Id and Name of the company only. */
	public List<Company> getAllCompanies();
	
	/** Return a company that matches the given companyId with all it's properties. */
	public Company getCompany(Long companyId);
	
	/** Return a List of all existing owners. */
	public List<Owner> getAllOwners();
	
	/** Create new company or update an existing one. */
	public Company saveCompany(Company newCompany);
		
	/** Create or just retrieve the given owner with its current id created or previously stored. */
	public Owner saveOwner(Owner owner);
	
	/** Add the new owner to the given company. Since not clarified, 
	 * assuming if the owner does not exists, it will be created, otherwise it will be reused. */
	public Company addOwners(Long companyId, List<Owner> owners);
}
