package org.springframework.social.apple.config.annotation;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.social.apple.config.AppleApiHelper;
import org.springframework.social.apple.connect.AppleConnectionFactory;
import org.springframework.social.apple.security.AppleAuthenticationService;
import org.springframework.social.config.support.ProviderConfigurationSupport;
import org.springframework.social.security.provider.SocialAuthenticationService;

public class AppleProviderConfigRegistrar extends ProviderConfigurationSupport {
	private final Class<? extends Annotation> providerConfigAnnotation;

	public AppleProviderConfigRegistrar() {
		super(AppleConnectionFactory.class, AppleApiHelper.class);
		this.providerConfigAnnotation = EnableApple.class;
	}
	
	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		return AppleAuthenticationService.class;
	}

	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		Map<String, Object> allAttributes = metadata.getAnnotationAttributes(providerConfigAnnotation.getName());
		registerBeanDefinitions(registry, allAttributes);
	}

	@Override
	protected String getAppId(Map<String, Object> allAttributes) {
		return (String) allAttributes.get("appId");
	}

	@Override
	protected String getAppSecret(Map<String, Object> allAttributes) {
		return (String) allAttributes.get("appSecret");
	}

}
