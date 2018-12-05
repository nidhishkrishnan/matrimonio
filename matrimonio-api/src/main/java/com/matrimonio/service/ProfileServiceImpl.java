package com.matrimonio.service;

import static com.matrimonio.common.MatrimonioConstants.AGE;
import static com.matrimonio.common.MatrimonioConstants.COMPATIBILITY_SCORE;
import static com.matrimonio.common.MatrimonioConstants.CONTACTS_EXCHANGED;
import static com.matrimonio.common.MatrimonioConstants.DISPLAY_NAME;
import static com.matrimonio.common.MatrimonioConstants.FAVOURITE;
import static com.matrimonio.common.MatrimonioConstants.HEIGHT_IN_CM;
import static com.matrimonio.common.MatrimonioConstants.MAIN_PHOTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.matrimonio.domain.FilterProfileRequest;
import com.matrimonio.domain.Range;
import com.matrimonio.domain.UserProfileDisplayNames;
import com.matrimonio.model.Profile;
import com.matrimonio.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Override
	public List<UserProfileDisplayNames> getAllUserDisplayNames() {
		return this.profileRepository.findAllUserDisplayNames();
	}
	
	@Override
	public List<Profile> getAllProfiles(final String loginUserName) {
		return this.profileRepository.findAllProfiles(loginUserName);
	}

	@Override
	public List<Profile> filterProfiles(final String loginUserName, final FilterProfileRequest filterProfileRequest) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    	CriteriaQuery<Profile> criteriaQuery = criteriaBuilder.createQuery(Profile.class);
    	Root<Profile> entity = criteriaQuery.from(Profile.class);
    	final List<Predicate> allPredicates = buildAllPredicates(criteriaBuilder, entity, filterProfileRequest);
    	allPredicates.add(criteriaBuilder.notEqual(entity.get(DISPLAY_NAME), loginUserName));
    	Predicate wherePredicate = criteriaBuilder.and(allPredicates.toArray(new Predicate[allPredicates.size()]));
    	TypedQuery<Profile> typedQuery = this.entityManager.createQuery(criteriaQuery.select(entity).where(wherePredicate));
    	return filterByDistance(loginUserName, typedQuery.getResultList(), filterProfileRequest);
    	
	}

	private List<Profile> filterByDistance(final String loginUserId, final List<Profile> userProfileList, final FilterProfileRequest filterProfileRequest) {
		Optional<Profile> loginUserProfile = this.profileRepository.findById(loginUserId);
		if(filterProfileRequest.isDistanceToCheck() && loginUserProfile.isPresent()) {
			return userProfileList.stream()
	                .filter(profile -> filterUsersByDistance(profile, loginUserProfile.get(), filterProfileRequest.isUpperBound()))
	                .collect(Collectors.toList());     
		} else {
			return userProfileList;
		}
	}

	private boolean filterUsersByDistance(Profile profileToCheck, Profile loginUserProfile, Boolean isUpperBound) {
		double theDistance = (Math.sin(Math.toRadians(loginUserProfile.getCity().getLatitude())) *
            Math.sin(Math.toRadians(profileToCheck.getCity().getLatitude())) +
            Math.cos(Math.toRadians(loginUserProfile.getCity().getLatitude())) *
            Math.cos(Math.toRadians(profileToCheck.getCity().getLatitude())) *
            Math.cos(Math.toRadians(loginUserProfile.getCity().getLongitude() - profileToCheck.getCity().getLongitude())));

		double finalBetween = Math.toDegrees(Math.acos(theDistance)) * 69.09 * 1.609344;
		if (isUpperBound) {
			return finalBetween > 300;
		} else {
			return finalBetween < 30;
		}
	}

	private List<Predicate> buildAllPredicates(final CriteriaBuilder criteriaBuilder, final Root<Profile> entity, final FilterProfileRequest filterProfileRequest) {
		final List<Predicate> predicates = Lists.newArrayList();
		if (filterProfileRequest.isHasPhotoToCheck()) {
			predicates.add(getHasPhotoPredicate(filterProfileRequest, entity, criteriaBuilder));
		}

		if (filterProfileRequest.inContactToCheck()) {
			predicates.add(getInContactPredicate(filterProfileRequest, entity, criteriaBuilder));
		}

		if (filterProfileRequest.isAgeToCheck()) {
			predicates.add(getAgePredicate(filterProfileRequest, entity, criteriaBuilder));
		}

		if (filterProfileRequest.isHeightToCheck()) {
			predicates.add(getHeightPredicate(filterProfileRequest, entity, criteriaBuilder));
		}

		if (filterProfileRequest.isCompatibilityScoreToCheck()) {
			predicates.add(getCompatibilityScorePredicate(filterProfileRequest, entity, criteriaBuilder));
		}

		if (filterProfileRequest.isFavouriteToCheck()) {
			predicates.add(getFavouritePredicate(filterProfileRequest, entity, criteriaBuilder));
		}
		return predicates;
	}

	private Predicate getFavouritePredicate(final FilterProfileRequest filterProfileRequest, final Root<Profile> entity, final CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.equal(entity.get(FAVOURITE), filterProfileRequest.getFavourite());
	}

	private Predicate getCompatibilityScorePredicate(final FilterProfileRequest filterProfileRequest, final Root<Profile> entity, final CriteriaBuilder criteriaBuilder) {
		return buildBetweenPredicate(criteriaBuilder, entity.get(COMPATIBILITY_SCORE), filterProfileRequest.getCompatibilityScore());
	}

	private Predicate getHeightPredicate(final FilterProfileRequest filterProfileRequest, final Root<Profile> entity, final CriteriaBuilder criteriaBuilder) {
		return buildBetweenPredicate(criteriaBuilder, entity.get(HEIGHT_IN_CM), filterProfileRequest.getHeight());
	}

	private Predicate getAgePredicate(final FilterProfileRequest filterProfileRequest, final Root<Profile> entity, final CriteriaBuilder criteriaBuilder) {
		return buildBetweenPredicate(criteriaBuilder, entity.get(AGE), filterProfileRequest.getAge());
	}

	private Predicate getInContactPredicate(final FilterProfileRequest filterProfileRequest, final Root<Profile> entity, final CriteriaBuilder criteriaBuilder) {
		if(filterProfileRequest.getInContact()) {
			return criteriaBuilder.ge(entity.get(CONTACTS_EXCHANGED), 1);
		} else {
			return criteriaBuilder.equal(entity.get(CONTACTS_EXCHANGED), 0);
		}
	}

	private Predicate getHasPhotoPredicate(final FilterProfileRequest filterProfileRequest, final Root<Profile> entity, final CriteriaBuilder criteriaBuilder) {
		if(filterProfileRequest.getHasPhoto()) {
			return criteriaBuilder.isNotNull(entity.get(MAIN_PHOTO));
		} else {
			return criteriaBuilder.isNull(entity.get(MAIN_PHOTO));
		}
	}

	private Predicate buildBetweenPredicate(final CriteriaBuilder criteriaBuilder, final Path<? extends Number> path, final Range range) {
		if (Integer.class.isAssignableFrom(path.getJavaType())) {
			return criteriaBuilder.between(criteriaBuilder.toInteger(path), range.getStart(), range.getEnd());
		} else {
			return criteriaBuilder.between(criteriaBuilder.toDouble(path), Double.valueOf(range.getStart())/100, Double.valueOf(range.getEnd())/100);
		}
	}

	@Override
	public Profile toggleProfile(@Valid String profileId) {
		return profileRepository.findById(profileId).map(profile -> {
			if(!profile.getHidden()) {
				profile.setFavourite(false);
			}
			profile.setHidden(!profile.getHidden());
			return profileRepository.save(profile);
		}).orElseThrow(() -> new RuntimeException("Profile not found"));
	}
	
	@Override
	public Profile addFavorite(@Valid String profileId) {
		return profileRepository.findById(profileId).map(profile -> {
			profile.setFavourite(!profile.getFavourite());
			return profileRepository.save(profile);
		}).orElseThrow(() -> new RuntimeException("Profile not found"));
	}
	
	@Override
	public Profile contact(@Valid String profileId) {
		return profileRepository.findById(profileId).map(profile -> {
			profile.setContactsExchanged(profile.getContactsExchanged()+1);
			return profileRepository.save(profile);
		}).orElseThrow(() -> new RuntimeException("Profile not found"));
	}
}
