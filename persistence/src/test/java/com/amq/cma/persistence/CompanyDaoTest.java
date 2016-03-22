package com.amq.cma.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-context.xml")
@Transactional
@ActiveProfiles("test")
public class CompanyDaoTest {
	Logger logger = LoggerFactory.getLogger(CompanyDaoTest.class);
	
	@Autowired
	CompanyDao companyDao;
	
	@Test
	public void getAllCompaniesTest(){
		logger.debug("testing getAllCompanies...");
		List<Company> companies = companyDao.getAllCompanies();
		
		assertEquals(3, companies.size());
		assertEquals(Long.valueOf(111L), companies.get(0).getCompanyId());
		assertEquals("Company 1", companies.get(0).getName());
		assertEquals(Long.valueOf(222L), companies.get(1).getCompanyId());
		assertEquals("Company 2", companies.get(1).getName());
		assertEquals(Long.valueOf(333L), companies.get(2).getCompanyId());
		assertEquals("Company 3", companies.get(2).getName());
	}
	
	@Test
	public void getCompanyTest(){
		logger.debug("testing getAllCompanies...");
		Company company = companyDao.getCompany(222L);
		assertEquals(Long.valueOf(222L), company.getCompanyId());
		assertEquals("Company 2", company.getName());
	}
	
	@Test
	public void saveCompanyTestCreate(){
		logger.debug("testing saveCompanyTestCreate...");
		Owner owner = new Owner();
		owner.setOwnerId(1L);
		owner.setName("OWNER TEST 1");
		
		List<Owner> owners = new ArrayList<>();
		owners.add(owner);
		owners = companyDao.getOwners(owners);
		
		Company comp = new Company();
		comp.setName("New Acme Inc.");
		comp.setAddress("685 California St #777th floor");
		comp.setCity("San Mateo");
		comp.setCountry("USA");
		comp.addOwner(owners.get(0));
		
		comp = companyDao.saveCompany(comp);
		assertNotNull(comp.getCompanyId());
	}
	
	@Test
	public void saveCompanyTestUpdate(){
		Company comp = companyDao.getCompany(222L);
		comp.setName("Updated Acme Inc.");
		
		comp = companyDao.saveCompany(comp);
		
		Company updComp = companyDao.getCompany(222L);
		
		assertEquals(comp.getCompanyId(), updComp.getCompanyId());
		assertEquals("Updated Acme Inc.", updComp.getName());
	}
	
	@Test
	public void addOwnersTest(){
		Owner owner = new Owner();
		owner.setName("Skywalker Vader");
		Owner owner1 = new Owner();
		owner1.setName("Vader Skywalker");
		
		List<Owner> owners = new ArrayList<>();
		owners.add(owner);
		owners.add(owner1);
		
		Company comp = companyDao.addOwners(222L, owners);
		
		assertEquals(2, comp.getOwners().size());
	}
	
	@Test
	public void getAllOwnersTest(){
		logger.debug("testing getAllOwners...");
		List<Owner> owners = companyDao.getAllOwners();
		
		assertEquals(3, owners.size());
		assertEquals(Long.valueOf(1L), owners.get(0).getOwnerId());
		assertEquals("OWNER TEST 1", owners.get(0).getName());
		assertEquals(Long.valueOf(2L), owners.get(1).getOwnerId());
		assertEquals("OWNER TEST 2", owners.get(1).getName());
		assertEquals(Long.valueOf(3L), owners.get(2).getOwnerId());
		assertEquals("OWNER TEST 3", owners.get(2).getName());
	}
	
	@Test
	public void getOwnersTest(){
		Owner owner = new Owner();
		owner.setOwnerId(1L);
		owner.setName("OWNER TEST 1");
		Owner owner1 = new Owner();
		owner1.setOwnerId(2L);
		owner1.setName("OWNER TEST 2");
		
		List<Owner> owners = new ArrayList<>();
		owners.add(owner);
		owners.add(owner1);
		
		List<Owner> result = companyDao.getOwners(owners);
		
		assertEquals(owners.size(), result.size());
		assertEquals(owners.get(0).getOwnerId(), result.get(0).getOwnerId());
		assertEquals(owners.get(1).getOwnerId(), result.get(1).getOwnerId());
	}
	
	@Test
	public void saveOwnerTest(){
		Owner owner = new Owner();
		owner.setName("OWNER TEST 2");
		
		owner = companyDao.saveOwner(owner);
		assertNotNull(owner.getOwnerId());
	}
}
