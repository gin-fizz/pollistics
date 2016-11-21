package com.pollistics.config;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.hot.reloading.HotReloadingExtension;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.boot.config.JtwigViewResolverConfigurer;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Maliek & Elias on 21/11/2016.
 */


@EnableWebMvc
@Configuration
public class WebConfig implements JtwigViewResolverConfigurer {

	@Override
	public void configure(JtwigViewResolver viewResolver) {
		viewResolver.setRenderer(new JtwigRenderer(
			EnvironmentConfigurationBuilder.configuration()
				.extensions()
				.add(new HotReloadingExtension())
				.and()
				.build()
		));
	}
}
