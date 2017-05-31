package com.qk.seed.authenticator.spring;

import com.qk.seed.authenticator.manager.TokenManager;
import com.qk.seed.authenticator.manager.TokenManagerImpl;
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
