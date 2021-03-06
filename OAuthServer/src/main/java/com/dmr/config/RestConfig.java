package com.dmr.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestConfig implements Filter {

    public RestConfig() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
        response.setHeader("Access-Control-Max-Age", "36000");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, content-type");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {
    }
}

/*
 * @Configuration public class RestConfig {
 *
 * @Bean
 *
 * @Primary public CorsFilter corsFilter() { UrlBasedCorsConfigurationSource
 * source = new UrlBasedCorsConfigurationSource(); CorsConfiguration config =
 * new CorsConfiguration(); //config.setAllowCredentials(false);
 * config.addAllowedOrigin("*"); //config.addAllowedHeader("*");
 * //config.addAllowedMethod("OPTIONS"); //config.addAllowedMethod("GET");
 * config.addAllowedMethod("POST"); //config.addAllowedMethod("PUT");
 * //config.addAllowedMethod("DELETE"); source.registerCorsConfiguration("/**",
 * config); return new CorsFilter(source); } }
 */
