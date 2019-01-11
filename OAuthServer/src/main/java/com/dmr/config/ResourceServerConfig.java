package com.dmr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// http.cors().and().anonymous().disable().authorizeRequests().antMatchers("/api/**").authenticated().and().exceptionHandling()
		// .accessDeniedHandler(new OAuth2AccessDeniedHandler());
		http.authorizeRequests().antMatchers("/api/private/**").authenticated();
		// antMatchers(HttpMethod.OPTIONS,"/oauth/token").permitAll()
	}
}


