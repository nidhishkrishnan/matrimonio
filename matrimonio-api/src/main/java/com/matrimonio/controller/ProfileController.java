package com.matrimonio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matrimonio.domain.FilterProfileRequest;
import com.matrimonio.domain.UserProfileDisplayNames;
import com.matrimonio.model.Profile;
import com.matrimonio.service.ProfileService;

@RestController
@RequestMapping("v1/profile")
public class ProfileController {

	private ProfileService profileService;
	
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping("/list")
	public List<Profile> getAllProfiles(@Valid @RequestParam final String loginUserName) {
		return this.profileService.getAllProfiles(loginUserName);
	}
	
	@GetMapping("/names")
	public List<UserProfileDisplayNames> getAllUserDisplayNames() {
		return this.profileService.getAllUserDisplayNames();
	}
	
	@PostMapping("/filter")
	public List<Profile> filterProfiles(@Valid @RequestParam String loginUserName, @Valid @RequestBody final FilterProfileRequest filterProfileRequest) {
		return this.profileService.filterProfiles(loginUserName, filterProfileRequest);
	}
}
