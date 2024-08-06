package com.chikanov.gstore.configuration;

import com.chikanov.gstore.entity.GameType;
import com.chikanov.gstore.filters.BotRequestFilter;
import com.chikanov.gstore.filters.TelegramValidateFilter;
import com.chikanov.gstore.repositories.GameTypeRepository;
import com.chikanov.gstore.services.GameTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class GeneralConfiguration implements WebMvcConfigurer {
    @Value("${token.value}")
    private String token;

    @Autowired
    GameTypeService gameTypeService;

    @Autowired
    AutoSetWebhook autoSetWebhook;

    @Autowired
    TelegramValidateFilter telegramValidateFilter;

    @Autowired
    ResourceLoader resourceLoader;

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
    public void findGames() throws IOException
    {
        ObjectMapper om = new ObjectMapper();
        ClassPathResource classPathResource = new ClassPathResource("games/games.json");
        List<GameType> gameTypeCollection = om.readValue(classPathResource.getInputStream(), new TypeReference<>() {});
        gameTypeService.updateGameList(gameTypeCollection);
    }
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
