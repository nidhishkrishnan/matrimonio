package com.matrimonio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(name = "uc_profile_display_name", columnNames = {
		"display_name" }))
public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "display_name", nullable = false)
	public String displayName;

	@Column(name = "age")
	public Integer age;

	@Column(name = "job_title")
	public String jobTitle;

	@Column(name = "height_in_cm")
	public Integer heightInCm;

	@Column(name = "hidden")
	public Boolean hidden;

	@Embedded
	public City city;

	@Column(name = "main_photo")
	public String mainPhoto;

	@Column(name = "compatibility_score")
	public Double compatibilityScore;

	@Column(name = "contacts_exchanged")
	public Long contactsExchanged;

	@Column(name = "favourite")
	public Boolean favourite;

	@Column(name = "religion")
	public String religion;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getHeightInCm() {
		return heightInCm;
	}

	public void setHeightInCm(Integer heightInCm) {
		this.heightInCm = heightInCm;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getMainPhoto() {
		return mainPhoto;
	}

	public void setMainPhoto(String mainPhoto) {
		this.mainPhoto = mainPhoto;
	}

	public Double getCompatibilityScore() {
		return compatibilityScore;
	}

	public void setCompatibilityScore(Double compatibilityScore) {
		this.compatibilityScore = compatibilityScore;
	}

	public Long getContactsExchanged() {
		return contactsExchanged;
	}

	public void setContactsExchanged(Long contactsExchanged) {
		this.contactsExchanged = contactsExchanged;
	}

	public Boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Profile() {

	}
}
