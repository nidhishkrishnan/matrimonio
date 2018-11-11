package com.matrimonio.service;

import static com.matrimonio.common.MatrimonioConstants.AGE;
import static com.matrimonio.common.MatrimonioConstants.COMPATIBILITY_SCORE;
import static com.matrimonio.common.MatrimonioConstants.DISPLAY_NAME;
import static com.matrimonio.common.MatrimonioConstants.HEIGHT_IN_CM;
import static com.matrimonio.common.MatrimonioConstants.MAIN_PHOTO;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.matrimonio.model.Profile;
import com.matrimonio.repository.ProfileRepository;
import com.matrimonio.util.CommonUtils;

@RunWith(PowerMockRunner.class)
public class ProfileServiceImplTest extends CommonUtils {

	@Mock
	private EntityManager manager;

	@Mock
	private ProfileRepository profileRepository;

	@InjectMocks
	private ProfileServiceImpl profileService = new ProfileServiceImpl(profileRepository);

	@Mock
	CriteriaQuery<Profile> criteriaQuery;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void filterProfilesTest() {
		Root<Profile> entity = mock(Root.class, Mockito.RETURNS_DEEP_STUBS);
		Predicate wherePredicate = mock(Predicate.class, Mockito.RETURNS_DEEP_STUBS);
		CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class, Mockito.RETURNS_DEEP_STUBS);
		CriteriaQuery<Profile> criteriaQuery = mock(CriteriaQuery.class, Mockito.RETURNS_DEEP_STUBS);
		TypedQuery<Profile> typedQuery = mock(TypedQuery.class);
		when(criteriaBuilder.and(any(Predicate.class))).thenReturn(wherePredicate);
		Path lastNamePathMock = mock(Path.class);
		PowerMockito.mockStatic(Integer.class);
		when(lastNamePathMock.getJavaType()).thenReturn(Integer.class);
		when(entity.get(DISPLAY_NAME)).thenReturn(lastNamePathMock);
		when(entity.get(MAIN_PHOTO)).thenReturn(lastNamePathMock);
		when(entity.get(AGE)).thenReturn(lastNamePathMock);
		when(entity.get(COMPATIBILITY_SCORE)).thenReturn(lastNamePathMock);
		when(entity.get(HEIGHT_IN_CM)).thenReturn(lastNamePathMock);
		when(manager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		when(criteriaBuilder.createQuery(Profile.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(Profile.class)).thenReturn(entity);
		when(criteriaQuery.select(entity)).thenReturn(criteriaQuery);
		when(criteriaQuery.where(wherePredicate)).thenReturn(criteriaQuery);
		when(manager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
		when(typedQuery.getResultList()).thenReturn(buildAllProfiles());
		when(profileRepository.findById("ABC")).thenReturn(Optional.of(buildProfile()));
		assertThat(profileService.filterProfiles("ABC", buildFilterProfileRequest()), is(notNullValue()));
	}

	@Test
	public void getAllProfilesTest() throws Exception {
		when(profileRepository.findAllProfiles("BCD")).thenReturn(buildAllProfiles());
		assertThat(profileService.getAllProfiles("BCD").size(), is(1));
	}
	
	@Test
	public void findAllUserDisplayNamesTest() throws Exception {
		when(profileRepository.findAllUserDisplayNames()).thenReturn(buildAllUserDisplayNames());
		assertThat(profileService.getAllUserDisplayNames().size(), is(1));
	}
}
