package com.mentor4you.security;

import com.mentor4you.grpc.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class JwtFilter implements Filter {

    private final AuthenticationService authenticationService;

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
            boolean isValidToken = authenticationService.isValidToken(authHeaderValue.trim());
            if(isValidToken) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
