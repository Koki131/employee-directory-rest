package com.employeedirectory.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prospect_links")
public class ProspectLinks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private Long id;
	
	@Column(name = "instagram")
	private String instagram;
	
	@Column(name = "linkedin")
	private String linkedIn;
	
	@Column(name = "facebook")
	private String facebook;
	
	@OneToOne
	@JoinColumn(name = "prospect_id")
	@JsonIgnore
	private Prospect prospect;

	public ProspectLinks() {
	}

	public ProspectLinks(String instagram, String linkedIn, String facebook) {
		this.instagram = instagram;
		this.linkedIn = linkedIn;
		this.facebook = facebook;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public Prospect getProspect() {
		return prospect;
	}

	public void setProspect(Prospect prospect) {
		this.prospect = prospect;
	}

	@Override
	public String toString() {
		return "ProspectLinks [id=" + id + ", instagram=" + instagram + ", linkedIn=" + linkedIn + ", facebook="
				+ facebook + ", prospect=" + prospect + "]";
	}
	
	
}
