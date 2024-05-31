package com.chikanov.gstore.filters;

import com.chikanov.gstore.configuration.AutoSetWebhook;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BotRequestFilter implements Filter {
    private final AutoSetWebhook webhook;
    public BotRequestFilter(AutoSetWebhook webhook)
    {
        this.webhook = webhook;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getHeader("X-Telegram-Bot-Api-Secret-Token").equals(webhook.getWebhookToken()))
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(401);
        }
    }
}
