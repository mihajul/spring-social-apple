package org.springframework.social.apple.security;

import org.springframework.social.apple.api.Apple;
import org.springframework.social.apple.connect.AppleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class AppleAuthenticationService extends OAuth2AuthenticationService<Apple> {
	
	public AppleAuthenticationService(String clientId, String teamId, String keyId, String clientCertificate) {
		
		super(new AppleConnectionFactory( clientId,  teamId,  keyId,  clientCertificate));
	}
	

}
