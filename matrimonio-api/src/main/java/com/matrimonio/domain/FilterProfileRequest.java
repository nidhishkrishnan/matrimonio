package com.matrimonio.domain;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimonio.validation.InRange;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class FilterProfileRequest {
	
	private Boolean favourite;
	private Boolean hasPhoto;
	private Boolean upperBound;
	private Boolean inContact;

	@Valid
	@InRange(
			max = 99, 
			min = 1,
			message = "start and end compatibility score should be in range from 1% to 99%")
	private Range compatibilityScore;

	@Valid
	@InRange(
			max = 95, 
			min = 18,
			message = "start and end age should be in range from 18 to 95")
	private Range age;

	@Valid
	@InRange(
			max = 210, 
			min = 135,
			message = "start and end height should be in range from 135cm to 210cm")
	private Range height;

	public Boolean getHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(Boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public Boolean getInContact() {
		return inContact;
	}

	public void setInContact(Boolean inContact) {
		this.inContact = inContact;
	}

	public Boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}

	public Range getCompatibilityScore() {
		return compatibilityScore;
	}

	public void setCompatibilityScore(Range compatibilityScore) {
		this.compatibilityScore = compatibilityScore;
	}

	public Range getAge() {
		return age;
	}

	public void setAge(Range age) {
		this.age = age;
	}

	public Range getHeight() {
		return height;
	}

	public void setHeight(Range height) {
		this.height = height;
	}

	public Boolean isUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Boolean upperBound) {
		this.upperBound = upperBound;
	}

	@JsonIgnore
	public boolean isAgeToCheck() {
		return age != null;
	}

	@JsonIgnore
	public boolean isHeightToCheck() {
		return height != null;
	}

	@JsonIgnore
	public boolean isCompatibilityScoreToCheck() {
		return compatibilityScore != null;
	}

	@JsonIgnore
	public boolean isHasPhotoToCheck() {
		return hasPhoto != null;
	}

	@JsonIgnore
	public boolean isFavouriteToCheck() {
		return favourite != null;
	}

	@JsonIgnore
	public boolean isDistanceToCheck() {
		return upperBound != null;
	}

	@JsonIgnore
	public boolean inContactToCheck() {
		return inContact != null;
	}
}
