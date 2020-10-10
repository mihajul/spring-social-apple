package org.springframework.social.apple.connect;

import org.springframework.social.apple.api.Apple;
import org.springframework.social.apple.api.impl.AppleTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class AppleServiceProvider extends AbstractOAuth2ServiceProvider<Apple> {

	public AppleServiceProvider(String clientId, String teamId, String keyId, String clientCertificate) {
		super(new AppleOAuth2Template(clientId, teamId, keyId, clientCertificate));
	}

	@Override
	public Apple getApi(String accessToken) {
		return new AppleTemplate(accessToken);
	}

}
