package com.pollistics.config;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.hot.reloading.HotReloadingExtension;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.asset.SpringAssetExtension;
import org.jtwig.spring.asset.resolver.AssetResolver;
import org.jtwig.spring.asset.resolver.BaseAssetResolver;
import org.jtwig.spring.boot.config.JtwigViewResolverConfigurer;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements JtwigViewResolverConfigurer {

	@Override
	public void configure(JtwigViewResolver viewResolver) {
		viewResolver.setRenderer(new JtwigRenderer(
			EnvironmentConfigurationBuilder.configuration()
				.extensions()
				.add(new HotReloadingExtension())
				.add(new SpringAssetExtension())
				.and()
				.build()
		));
	}

	@Bean
    public AssetResolver assetResolver () {
        BaseAssetResolver assetResolver = new BaseAssetResolver();
        assetResolver.setPrefix("");
        return assetResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }
}
