package org.springframework.social.apple.api;



public interface UserOperations {

	AppleProfile getUserProfile();

	void setUserJson(String userJson);

}
