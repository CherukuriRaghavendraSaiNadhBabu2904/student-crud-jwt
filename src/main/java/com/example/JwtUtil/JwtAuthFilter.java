package com.example.JwtUtil;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	 SecurityContextHolder.clearContext(); // ✅ important

             response.reset(); // ✅ FIRST
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(
                "{\"status\":401,\"message\":\"JWT token required\"}"
            );
            response.getWriter().flush();
            return;
        }

       // String token = authHeader.substring(7);

        try {
        	String token = authHeader.substring(7);
            String user = jwtUtil.validateAndExtract(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user, null, Collections.emptyList());

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {
        	SecurityContextHolder.clearContext(); // ✅ important
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(
                "{\"status\":401,\"message\":\"Invalid or expired JWT\"}"
            );
            response.getWriter().flush(); 
            return;
        }

        chain.doFilter(request, response);
    }
}
