package com.amq.cma.persistence;

import java.util.List;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;

public interface CompanyDao {
	/** Return a list with all companies available in the app's repository. */
	public List<Company> getAllCompanies();
	
	/** Search the repository and return a company based on the given companyId. */
	public Company getCompany(Long companyId);
	
	/** Store the given company into this app's repository. */
	public Company saveCompany(Company newCompany);

	/** Add all given owners in an existing company based on the given companyId. */
	public Company addOwners(Long companyId, List<Owner> owners);
	
	/** Retrieve a list of all owners stored in the underline repository. */
	public List<Owner> getAllOwners();
	
	/** Fetch a list of owners from the underline repository based on the given owners list. 
	 * This service is mainly used within this persistence module to fetch previous stored owners.*/
	public List<Owner> getOwners(List<Owner> owners);
	
	/** Save the given owner on the app's repository. */
	public Owner saveOwner(Owner owner);
}
