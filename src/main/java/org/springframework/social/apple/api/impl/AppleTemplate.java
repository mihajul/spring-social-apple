package org.springframework.social.apple.api.impl;

import org.springframework.social.apple.api.Apple;
import org.springframework.social.apple.api.UserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;

public class AppleTemplate extends AbstractOAuth2ApiBinding implements Apple {

	private UserOperations userOperations;
	private String accessToken;
	
	public AppleTemplate() {
		initialize();
	}

	public AppleTemplate(String accessToken) {
		super(accessToken);
		this.accessToken = accessToken;
		initialize();
	}
	
	@Override
	public UserOperations userOperations() {
		return userOperations;
	}

	private void initialize() {
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis();

	}

	private void initSubApis() {
		userOperations = new UserTemplate(this, getRestTemplate(), isAuthorized(), accessToken);
	}

}
