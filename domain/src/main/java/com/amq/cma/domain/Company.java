package com.amq.cma.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQuery(name="Company.findAll", query="SELECT DISTINCT NEW com.amq.cma.domain.Company(c.companyId, c.name) FROM Company c ORDER BY c.name")
@NamedEntityGraph(name="Company.with.owners", includeAllAttributes=true)
public class Company implements Serializable{
    @Transient
	private static final long serialVersionUID = -9121332635976602513L;

	@Id @Column(name="company_id") @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long companyId;
    
    @Column(length=100, nullable=false, unique=true)
    private String name;
    
    @Column(length=100, nullable=true, unique=false)
    private String email;
    
    @Column(length=20, nullable=true, unique=false)
    private String phoneNumber;
    
    @Column(length=200, nullable=true, unique=false)
    private String address;
    
    @Column(length=100, nullable=true, unique=false)
    private String city;
    
    @Column(length=100, nullable=true, unique=false)
    private String country;
    
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE}) 
    @JoinTable(name="comp_owner", joinColumns=@JoinColumn(name="company_id"), inverseJoinColumns=@JoinColumn(name="owner_id"))    
    private List<Owner> owners;
    
    public Company(){
    	owners = new ArrayList<>();
    }
    
    public Company(Long companyId, String name){
    	this();
    	this.companyId = companyId;
    	this.name = name;
    }

    public Long getCompanyId(){ return companyId; }
    public void setCompanyId(Long companyId){ this.companyId = companyId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getPhoneNumber(){ return phoneNumber; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }
    
    public String getAddress(){ return address; }
    public void setAddress(String address){ this.address = address; }
    
    public String getCity(){ return city; }
    public void setCity(String city){ this.city = city; }
    
    public String getCountry(){ return country; }
    public void setCountry(String country){ this.country = country; }
    
    public List<Owner> getOwners(){ return owners; }
    public void setOwners(List<Owner> owners){ this.owners = owners; }
    public boolean addOwner(Owner owner){ return owners.add(owner); }
    public boolean addOwners(List<Owner> owners){ return this.owners.addAll(owners); }
    public boolean removeOwner(Owner owner){ return owners.remove(owner); }
    public int ownersNumber(){ return owners.size(); }
        
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj){ return true; }
		if(obj == null){ return false; }
		if(getClass() != obj.getClass()){ return false; }
		
		Company other = (Company) obj;
		if(companyId == null) {
			if(other.companyId != null){ return false; }
		} else if(!companyId.equals(other.companyId)) { return false; }
		
		return true;
	}
	@Override
    public String toString(){
        return companyId +"-"+ name;
    }

}
