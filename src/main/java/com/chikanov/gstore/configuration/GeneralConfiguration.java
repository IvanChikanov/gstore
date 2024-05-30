package com.chikanov.gstore.configuration;

import com.chikanov.gstore.filters.TelegramValidateFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeneralConfiguration implements WebMvcConfigurer {
    @Value("${token.value}")
    private String token;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/**");
    }
    @Bean
    public FilterRegistrationBean<TelegramValidateFilter> tgFilter()
    {
        FilterRegistrationBean<TelegramValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TelegramValidateFilter(token));
        registrationBean.addUrlPatterns("/check/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
