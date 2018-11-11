package com.matrimonio.util;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrimonio.domain.FilterProfileRequest;
import com.matrimonio.domain.Range;
import com.matrimonio.domain.UserProfileDisplayNames;
import com.matrimonio.model.City;
import com.matrimonio.model.Profile;

public class CommonUtils {
	
	public FilterProfileRequest buildFilterProfileRequest() {
		FilterProfileRequest filterProfileRequest = new FilterProfileRequest();
		filterProfileRequest.setAge(buildRange(18, 55));
		filterProfileRequest.setCompatibilityScore(buildRange(2, 50));
		filterProfileRequest.setUpperBound(true);
		filterProfileRequest.setFavourite(true);
		filterProfileRequest.setHasPhoto(true);
		filterProfileRequest.setHeight(buildRange(135, 209));
		filterProfileRequest.setInContact(true);
		return filterProfileRequest;
	}

	public Range buildRange(int min, int max) {
		Range range = new Range();
		range.setStart(min);
		range.setEnd(max);
		return range;
	}

	public List<Profile> buildAllProfiles() {
		return newArrayList(buildProfile());
	}
	
	public Profile buildProfile() {
		Profile profile = new Profile();
		profile.setAge(45);
		profile.setDisplayName("ABC");
		profile.setCity(new City("CDE", 11.22, 33.44));
		profile.setCompatibilityScore(0.67);
		profile.setFavourite(true);
		profile.setHeightInCm(158);
		profile.setMainPhoto("photo.png");
		profile.setJobTitle("S/W");
		profile.setContactsExchanged(3L);
		return profile;
	}
	
	public List<UserProfileDisplayNames> buildAllUserDisplayNames() {
		return newArrayList(new UserProfileDisplayNames() {
			@Override
			public String getId() {
				return "ABCD";
			}

			@Override
			public String getText() {
				return "ABCD";
			}
		});
	}

	public byte[] toJson(Object objectContent) throws Exception {
		return new ObjectMapper().writeValueAsString(objectContent).getBytes();
	}
}
