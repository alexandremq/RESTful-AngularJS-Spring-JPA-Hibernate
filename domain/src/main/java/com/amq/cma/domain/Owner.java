package com.amq.cma.domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@NamedQuery(name="Owner.findByIds", query="SELECT o FROM Owner o WHERE o.ownerId in :ids ORDER BY o.name")
public class Owner implements Serializable{
	@Transient
	private static final long serialVersionUID = -8607277250011280663L;

	@Id @Column(name="owner_id") @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long ownerId;
	
	@Column(length=100, nullable=false, unique=true)
	private String name;
	
	public Owner(){}
	public Owner(Long ownerId){
		this.ownerId = ownerId;
	}
	
	public Long getOwnerId(){ return ownerId; }
	public void setOwnerId(Long ownerId){ this.ownerId = ownerId; }
	
	public String getName(){ return name; }
	public void setName(String name){ this.name = name; }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Owner other = (Owner) obj;
		if (ownerId == null) {
			if (other.ownerId != null){ return false; }
		} else if (!ownerId.equals(other.ownerId)){ return false; }
		return true;
	}
	
	@Override
	public String toString() {
		return ownerId +"-"+ name;
	}	
}
