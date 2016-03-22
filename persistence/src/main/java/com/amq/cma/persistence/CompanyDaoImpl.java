package com.amq.cma.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;

@Repository
@Transactional
@Profile("dev")
public class CompanyDaoImpl implements CompanyDao{
	private Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class); 
	private EntityManager entityManager;
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	public EntityManager getEntityManager(){
		return this.entityManager;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Company> getAllCompanies(){
		logger.debug("Selecting all companies stored...");
		
		Query query = entityManager.createNamedQuery("Company.findAll", Company.class);
		
		logger.debug("Querying database after all companies => "+ query.toString());
		
		@SuppressWarnings("unchecked")
		List<Company> companies = query.getResultList();
				
		logger.debug("List of companies found! Retrieving "+ companies.size() +" companie(s).");
		
		return companies;
	}
	
	@Transactional(readOnly=true)
	@Override
	public Company getCompany(Long companyId){
		logger.debug("Selecting company with id "+ companyId +" ...");
		
		@SuppressWarnings("unchecked")
		EntityGraph<Company> graph = (EntityGraph<Company>)entityManager.getEntityGraph("Company.with.owners");
		
		Map<String, Object> props = new HashMap<>();
		props.put("javax.persistence.fetchgraph", graph);
		
		Company comp = entityManager.find(Company.class, companyId, props);
		
		return comp;
	}
	
	@Transactional
	@Override
	public Company saveCompany(Company newCompany){
		logger.debug("Saving company..."+ newCompany);
		if(newCompany == null){ return null; }
		
		if(newCompany.getCompanyId() == null){
			logger.info("Creating new company "+ newCompany.getName() + "...");
			if(newCompany.getOwners() != null && !newCompany.getOwners().isEmpty()){
				List<Owner> owners = getOwners(newCompany.getOwners());
				newCompany.setOwners(owners);
			}
			
			entityManager.persist(newCompany);
		} else {
			logger.info("Updating company "+ newCompany.getName() +"...");
			entityManager.merge(newCompany);
		}
		
		logger.info("New company "+ newCompany.getName() +" saved with id "+ newCompany.getCompanyId() +".");
		return newCompany;
	}
	
	@Transactional
	@Override
	public Owner saveOwner(Owner owner){
		logger.debug("Saving owner "+ owner +"...");
		if(owner == null){ return null; }
		
		if(owner.getOwnerId() == null){ 
			logger.info("Creating owner "+ owner.getName() + "...");
			entityManager.persist(owner);
		} else {
			logger.info("Updating owner "+ owner.getName() + "...");
			entityManager.merge(owner);
		}
		
		return owner;
	}
	
	@Transactional
	@Override
	public Company addOwners(Long companyId, List<Owner> owners){
		Company company = getCompany(companyId);
		logger.info("Adding Owner "+ owners + " to company "+ company.getName() +"...");
		
		company.addOwners(owners);
		
		return saveCompany(company);
	}
	
	@SuppressWarnings("unchecked")
	public List<Owner> getOwners(List<Owner> owners){
		logger.debug("Selecting owners based on the given list of owners "+ owners +"...");
		
		List<Long> ids = new ArrayList<>();
		for(Owner o : owners){ ids.add(o.getOwnerId()); }
		
		Query query = entityManager.createNamedQuery("Owner.findByIds");
		query.setParameter("ids", ids);
		
		logger.debug("Querying database after owners => "+ query.toString());		
		owners = query.getResultList();				
		logger.debug("List of owners found! Retrieving "+ owners.size() +" owners(s).");
		
		return owners;
	}
	
	@SuppressWarnings("unchecked")
	public List<Owner> getAllOwners(){
		logger.debug("Selecting all owners stored...");
		
		Query query = entityManager.createQuery("SELECT o FROM Owner o ORDER BY o.name");
		
		logger.debug("Querying database after all owners => "+ query.toString());		
		List<Owner> owners = query.getResultList();				
		logger.debug("List of owners found! Retrieving "+ owners.size() +" owners(s).");
		
		return owners;
	}
}
