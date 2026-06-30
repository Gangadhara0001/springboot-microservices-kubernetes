package com.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger= LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication= SecurityContextHolder
                .getContext().getAuthentication();
        if(authentication!=null){
            logger.info("User :  {}",authentication.getName());
            logger.info("Authorities : {}", authentication.getAuthorities());
        }


        logger.info("Incoming Request: {} {}",
                request.getMethod(),
                request.getRequestURI());
        filterChain.doFilter(request,response);
        logger.info("Response Status : {}",
                response.getStatus());
    }
}
