package org.springframework.social.apple.config.xml;

import org.springframework.social.apple.config.AppleApiHelper;
import org.springframework.social.apple.connect.AppleConnectionFactory;
import org.springframework.social.apple.security.AppleAuthenticationService;
import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.security.provider.SocialAuthenticationService;

public class AppleConfigBeanDefinitionParser extends
		AbstractProviderConfigBeanDefinitionParser {

	protected AppleConfigBeanDefinitionParser() {
		
		super(AppleConnectionFactory.class, AppleApiHelper.class);
	}
	
	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {

		return AppleAuthenticationService.class;
	}
	
}
