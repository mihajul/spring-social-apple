package org.springframework.social.apple.connect;

import org.springframework.social.apple.api.Apple;
import org.springframework.social.apple.connect.support.AppleOAuth2Connection;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class AppleConnectionFactory extends OAuth2ConnectionFactory<Apple> {

	public AppleConnectionFactory(String clientId, String teamId, String keyId, String clientCertificate) {
		super("apple", new AppleServiceProvider(clientId, teamId, keyId, clientCertificate), new AppleAdapter());
	}
	
	@Override
	public Connection<Apple> createConnection(AccessGrant accessGrant) {
		return new AppleOAuth2Connection(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
				accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter());
	}
	
	protected String extractProviderUserId(AccessGrant accessGrant) {
		return null;
	}
	
	private OAuth2ServiceProvider<Apple> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<Apple>) getServiceProvider();
	}

}
