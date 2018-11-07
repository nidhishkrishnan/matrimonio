package com.matrimonio.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.matrimonio.domain.FilterProfileRequest;
import com.matrimonio.domain.UserProfileDisplayNames;
import com.matrimonio.model.Profile;
import com.matrimonio.service.ProfileService;
import com.matrimonio.util.CommonUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest extends CommonUtils {

	@Mock
    private ProfileService profileService;
	
	private MockMvc mvc;
	
	@InjectMocks
	private UserController userController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(userController)
				.build();
	}
	
	@Test
	public void getAllProfilesTest() throws Exception {
		List<Profile> allProfiles = buildAllProfiles();
	    when(profileService.getAllProfiles(anyString())).thenReturn(allProfiles);
	    mvc.perform(get("/v1/profile/list")
			.contentType(MediaType.APPLICATION_JSON).param("loginUserName", "BCD"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].age", is(45)));
	}
	
	@Test
	public void getAllUserDisplayNamesTest() throws Exception {
		List<UserProfileDisplayNames> allUserDisplayNames = buildAllUserDisplayNames();
	    when(profileService.getAllUserDisplayNames()).thenReturn(allUserDisplayNames);
	    mvc.perform(get("/v1/profile/names")
			.contentType(MediaType.APPLICATION_JSON).param("loginUserName", "BCD"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void filterProfilesTest() throws Exception {
		List<Profile> allProfiles = buildAllProfiles();
		FilterProfileRequest filterProfileRequest = buildFilterProfileRequest();
		byte[] filterProfileRequestContent = toJson(filterProfileRequest);
	    when(profileService.filterProfiles(anyString(), any())).thenReturn(allProfiles);
	    mvc.perform(post("/v1/profile/filter")
			.param("loginUserName", "BCD")
			.content(filterProfileRequestContent)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].age", is(45)));
	}

}
