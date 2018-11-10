package com.matrimonio.service;

import java.util.List;

import com.matrimonio.domain.FilterProfileRequest;
import com.matrimonio.domain.UserProfileDisplayNames;
import com.matrimonio.model.Profile;

public interface ProfileService {

	public List<UserProfileDisplayNames> getAllUserDisplayNames();

	public List<Profile> getAllProfiles(final String loginUserName);

	public List<Profile> filterProfiles(final String loginUserName, final FilterProfileRequest filterProfileRequest);
}
