package com.gotunis.eurekazull.security;


import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    Environment environment;

    public AuthorizationFilter(AuthenticationManager authManager, Environment environment) {
        super(authManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            chain.doFilter(req, resp);
            return;
        }
        UsernamePasswordAuthenticationToken authentification = getAuthentification(req);
        SecurityContextHolder.getContext().setAuthentication(authentification);
        chain.doFilter(req, resp);
    }

    private UsernamePasswordAuthenticationToken getAuthentification(HttpServletRequest req) {
        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));
        logger.info(authorizationHeader);
        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace("Bearer", "");
        String email = Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if (email == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
    }
}
