package org.springframework.social.apple.config.xml;

import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.config.xml.AbstractProviderConfigNamespaceHandler;

public class AppleNamespaceHandler extends AbstractProviderConfigNamespaceHandler {

	@Override
	protected AbstractProviderConfigBeanDefinitionParser getProviderConfigBeanDefinitionParser() {

		return new AppleConfigBeanDefinitionParser();
	}

}
