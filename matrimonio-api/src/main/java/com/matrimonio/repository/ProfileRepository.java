package com.matrimonio.repository;

import static com.matrimonio.common.MatrimonioConstants.DISPLAY_NAME;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.matrimonio.domain.UserProfileDisplayNames;
import com.matrimonio.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

	@Query("Select displayName AS id, displayName AS text FROM Profile profile ORDER BY displayName")
	public List<UserProfileDisplayNames> findAllUserDisplayNames();
	
	@Query("FROM Profile profile WHERE profile.displayName!=:displayName")
	public List<Profile> findAllProfiles(@Param(DISPLAY_NAME) String loginUserDisplayName);
}
