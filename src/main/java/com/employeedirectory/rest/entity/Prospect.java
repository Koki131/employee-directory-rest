package com.employeedirectory.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prospects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Prospect {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prospect_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
    @JsonIgnore
	private Employee employee;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "prospect")
	private ProspectLinks prospectLinks;

	public Prospect() {
	}

	public Prospect(String fullName, String email) {
		this.fullName = fullName;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ProspectLinks getProspectLinks() {
		return prospectLinks;
	}

	public void setProspectLinks(ProspectLinks prospectLinks) {
		this.prospectLinks = prospectLinks;
	}

	@Override
	public String toString() {
		return "Prospect [id=" + id + ", fullName=" + fullName + ", email=" + email + ", employee=" + employee
				+ ", prospectLinks=" + prospectLinks + "]";
	}
	
	
}
