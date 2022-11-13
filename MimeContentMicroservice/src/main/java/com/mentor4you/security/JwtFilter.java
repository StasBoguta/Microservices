package com.mentor4you.security;

import com.mentor4you.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Order(1)
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)  servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeaderValue = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(Objects.isNull(authHeaderValue)) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            boolean isValidToken = authService.isValidToken(authHeaderValue.trim());
            if(isValidToken) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
