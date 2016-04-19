package com.amq.cma.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.amq.cma.domain.Company;
import com.amq.cma.domain.Owner;
import com.amq.cma.services.CompanyServices;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:app-context.xml", "classpath*:services-context.xml"})
@ActiveProfiles("test")
public class CmaFrontControllerTest {
	Logger logger = LoggerFactory.getLogger(CmaFrontControllerTest.class); 
	@InjectMocks private CmaFrontController cmaFrontController;
	@Mock private CompanyServices companyServices;
	
	private MockMvc mockMvc;
		
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(cmaFrontController)
				.alwaysExpect(status().isOk())
				.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.build();
	}
	
	@Test
	public void listCompaniesTest() throws Exception{
		logger.debug("testing GET request to /services/companies ...");
		Owner owner = new Owner();
		owner.setOwnerId(54L);
		owner.setName("Skywalker Kenobi");
		
		Company comp = new Company();
		comp.setCompanyId(333L);
		comp.setName("Acme Inc.");
		comp.setAddress("685 Market St #7th floor");
		comp.setCity("San Francisco");
		comp.setCountry("USA");
		comp.addOwner(owner);
		
		Company comp1 = new Company();
		comp1.setCompanyId(Long.valueOf(444));
		comp1.setName("VanHack Inc.");
		comp1.setAddress("900 West Georgia St #5th floor");
		comp1.setCity("Vancouver");
		comp1.setCountry("Canada");
		comp1.addOwner(owner);
		
		List<Company> comps = new ArrayList<>();
		comps.add(comp);
		comps.add(comp1);

		Mockito.when(companyServices.getAllCompanies()).thenReturn(comps);
		
		mockMvc.perform(get("/services/companies")
			.accept(MediaType.APPLICATION_JSON_UTF8))			
			.andExpect(jsonPath("$[0].companyId").value(333)) 			//for JsonPath sintax check: https://github.com/jayway/JsonPath
			.andExpect(jsonPath("$[0].name").value("Acme Inc."))
			.andExpect(jsonPath("$[0].owners[0].ownerId").value(54))
			.andExpect(jsonPath("$[1].companyId").value(444))
			.andExpect(jsonPath("$[1].name").value("VanHack Inc."))
			.andExpect(jsonPath("$[1].owners[0].ownerId").value(54));
	}
	
	@Test
	public void getCompanyTest() throws Exception{
		logger.debug("testing GET request to /services/companies/{companyId} ...");
		Owner owner = new Owner();
		owner.setOwnerId(54L);
		owner.setName("Skywalker Kenobi");
		
		Company comp = new Company();
		comp.setCompanyId(333L);
		comp.setName("Acme Inc.");
		comp.setAddress("685 Market St #7th floor");
		comp.setCity("San Francisco");
		comp.setCountry("USA");
		comp.addOwner(owner);
		
		Mockito.when(companyServices.getCompany(333L)).thenReturn(comp);
		
		mockMvc.perform(get("/services/companies/333")
			.accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.companyId").value(333))
			.andExpect(jsonPath("$.owners[0].ownerId").value(54));
	}
	
	@Test
	public void listOwnersTest() throws Exception{
		logger.debug("testing GET request to /services/owners ...");
		
		Owner owner = new Owner();
		owner.setOwnerId(54L);
		owner.setName("Skywalker Kenobi");
		Owner owner1 = new Owner();
		owner1.setOwnerId(55L);
		owner1.setName("Trying Skywalker");
		
		List<Owner> owners = new ArrayList<>();
		owners.add(owner);
		owners.add(owner1);
		
		Mockito.when(companyServices.getAllOwners()).thenReturn(owners);
		
		mockMvc.perform(get("/services/owners")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$[0].ownerId").value(54))
		.andExpect(jsonPath("$[0].name").value("Skywalker Kenobi"))
		.andExpect(jsonPath("$[1].ownerId").value(55))
		.andExpect(jsonPath("$[1].name").value("Trying Skywalker"));
	}
	
	@Test
	public void saveCompanyTest() throws Exception{
		logger.debug("testing POST request to /services/companies/save ...");
		
		Owner owner = new Owner();
		owner.setName("Skywalker Kenobi");
		
		Company comp = new Company();
		comp.setName("Acme Inc.");
		comp.setAddress("685 Market St #7th floor");
		comp.setCity("San Francisco");
		comp.setCountry("USA");
		comp.addOwner(owner);
		
		Owner owner1 = new Owner();
		owner1.setOwnerId(54L);
		owner1.setName("Skywalker Kenobi");
		
		Company comp1 = new Company();
		comp1.setCompanyId(333L);
		comp1.setName("Acme Inc.");
		comp1.setAddress("685 Market St #7th floor");
		comp1.setCity("San Francisco");
		comp1.setCountry("USA");
		comp1.addOwner(owner1);
		
		Mockito.when(companyServices.saveCompany(comp)).thenReturn(comp1);
		
		mockMvc.perform(post("/services/companies/save")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertToJson(comp))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.companyId").value(333))
				.andExpect(jsonPath("$.owners[0].ownerId").value(54));
	}
	
	@Test
	public void saveOwnerTest() throws Exception{
		logger.debug("testing POST request to /services/owners/save ...");
		
		Owner owner = new Owner();
		owner.setName("Skywalker Kenobi");
		
		Owner owner1 = new Owner();
		owner1.setOwnerId(54L);
		owner1.setName("Skywalker Kenobi");
		
		Mockito.when(companyServices.saveOwner(owner)).thenReturn(owner1);
		
		mockMvc.perform(post("/services/owners/save")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertToJson(owner))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.ownerId").value(54));
	}
	
	private String convertToJson(Object obj) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json = mapper.writeValueAsString(obj);
		return json;
	}

}