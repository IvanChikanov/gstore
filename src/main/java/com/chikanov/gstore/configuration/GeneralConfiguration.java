package com.chikanov.gstore.configuration;

import com.chikanov.gstore.filters.BotRequestFilter;
import com.chikanov.gstore.filters.TelegramValidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeneralConfiguration implements WebMvcConfigurer {
    @Value("${token.value}")
    private String token;

    @Autowired
    AutoSetWebhook autoSetWebhook;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/**");
    }
    @Bean
    public FilterRegistrationBean<TelegramValidateFilter> tgFilter()
    {
        FilterRegistrationBean<TelegramValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TelegramValidateFilter(token));
        registrationBean.addUrlPatterns("/inside/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<BotRequestFilter> botFilter()
    {
        FilterRegistrationBean<BotRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BotRequestFilter(autoSetWebhook));
        registrationBean.addUrlPatterns("/bot");
        registrationBean.setOrder(2);
        return registrationBean;
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public TextEncryptor textEncryptor()
    {
        return Encryptors.text("coop_games_bot", "chikanov");
    }
}
