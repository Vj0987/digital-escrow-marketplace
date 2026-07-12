package org.cdac.digital_escrow_marketplace.booking_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return new RequestInterceptor() {

            @Override
            public void apply(RequestTemplate requestTemplate) {

                ServletRequestAttributes attributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes == null) {
                    return;
                }

                HttpServletRequest request = attributes.getRequest();

                String authorizationHeader = request.getHeader("Authorization");

                if (authorizationHeader != null
                        && authorizationHeader.startsWith("Bearer ")) {

                    requestTemplate.header("Authorization", authorizationHeader);
                }
            }
        };
    }
    
    @Bean
    public org.springframework.boot.web.servlet.FilterRegistrationBean<
            org.springframework.web.filter.RequestContextFilter> requestContextFilter() {

        org.springframework.boot.web.servlet.FilterRegistrationBean<
                org.springframework.web.filter.RequestContextFilter> filter =
                new org.springframework.boot.web.servlet.FilterRegistrationBean<>();

        filter.setFilter(new org.springframework.web.filter.RequestContextFilter());

        filter.setOrder(Integer.MIN_VALUE);

        return filter;
    }
}