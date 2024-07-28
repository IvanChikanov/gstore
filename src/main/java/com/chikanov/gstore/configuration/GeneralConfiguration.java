package com.chikanov.gstore.configuration;

import com.chikanov.gstore.filters.BotRequestFilter;
import com.chikanov.gstore.filters.TelegramValidateFilter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AutoSetWebhook autoSetWebhook;

    @Autowired
    TelegramValidateFilter telegramValidateFilter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/**");
    }
    @Bean
    public FilterRegistrationBean<TelegramValidateFilter> tgFilter()
    {
        FilterRegistrationBean<TelegramValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(telegramValidateFilter);
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
    @PostConstruct
    public void findGames()
    {
        System.out.println("games_finded");
    }
}
