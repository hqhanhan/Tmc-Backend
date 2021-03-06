package com.qk.authenticator.spring;

import com.qk.authenticator.manager.TokenManager;
import com.qk.authenticator.manager.TokenManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@ComponentScan("com.qk.authenticator")
public class AuthenticatorConfig {
	@Bean
	public TokenManager tokenManager() {
		return new TokenManagerImpl();
	}
}
