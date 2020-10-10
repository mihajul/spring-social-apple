package org.springframework.social.apple.connect.support;

import org.springframework.social.apple.api.Apple;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class AppleOAuth2Connection extends  OAuth2Connection<Apple> {
		
	public AppleOAuth2Connection(String providerId, String providerUserId, String accessToken, String refreshToken,
			Long expireTime, OAuth2ServiceProvider<Apple> serviceProvider, ApiAdapter<Apple> apiAdapter) {
		super(providerId, providerUserId, accessToken, refreshToken, expireTime, serviceProvider, apiAdapter);
		//getApi().userOperations().setUserJson(userJson);
		
	}

	public AppleOAuth2Connection(ConnectionData data, OAuth2ServiceProvider<Apple> serviceProvider,
			ApiAdapter<Apple> apiAdapter) {
		super(data, serviceProvider, apiAdapter);
	}

	private static final long serialVersionUID = 820850663866405849L;

}
