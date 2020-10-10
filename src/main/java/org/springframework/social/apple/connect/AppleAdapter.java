package org.springframework.social.apple.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.apple.api.Apple;
import org.springframework.social.apple.api.AppleProfile;

public class AppleAdapter implements ApiAdapter<Apple> {

	@Override
	public boolean test(Apple apple) {
		try {
			apple.userOperations().getUserProfile();
			return true;
		} catch(ApiException e) {
			return false;
		}
	}

	@Override
	public void setConnectionValues(Apple apple, ConnectionValues values) {
		AppleProfile profile = apple.userOperations().getUserProfile();
		values.setProviderUserId(profile.getId());
		if(profile.getFirstName() != null && profile.getLastName() != null) {
			values.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
		}
		values.setImageUrl("");
	}

	@Override
	public UserProfile fetchUserProfile(Apple apple) {
		AppleProfile profile = apple.userOperations().getUserProfile();
		return new UserProfileBuilder()
				.setFirstName(profile.getFirstName())
				.setLastName(profile.getLastName())
				.setUsername(profile.getId())
				.setEmail(profile.getEmail())
				.build();
	}

	@Override
	public void updateStatus(Apple apple, String message) {
		// not yet implemented
	}

}
