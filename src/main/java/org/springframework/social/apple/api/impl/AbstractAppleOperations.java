package org.springframework.social.apple.api.impl;

import java.net.URI;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.MultiValueMap;

public class AbstractAppleOperations {
	private final boolean isAuthorized;

	public AbstractAppleOperations(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException("apple");
		}
	}

	protected String buildUri(String path) {
		return API_URL_BASE + path;
	}

	public URI buildUri(String path, String name, String value) {

		URI uri = URIBuilder.fromUri(API_URL_BASE+path).queryParam(name, value).build();	
		return uri;
	}

	public URI buildUri(String path, MultiValueMap<String, String> queryParams) {

		URI uri = URIBuilder.fromUri(API_URL_BASE + path).queryParams(queryParams).build();
		return uri;
	}

	/**
	 * Base URL Apple API's
	 */
	private static final String API_URL_BASE = "https://apis.apple.net/v5.0/";

}
