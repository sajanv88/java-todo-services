package com.sajan.todo.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HttpInterceptorConfig implements WebMvcConfigurer {
	@Autowired
	AuthHttpInterceptor httpHandler;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// '/me' api route is protected.
		registry
		.addInterceptor(httpHandler)
		.addPathPatterns("/me/**")
		.addPathPatterns("/todo/**");
	}
}
