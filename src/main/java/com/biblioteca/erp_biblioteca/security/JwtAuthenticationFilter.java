package com.biblioteca.erp_biblioteca.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                if (tokenProvider.validateToken(jwt)) {
                    String userId = tokenProvider.getUserIdFromToken(jwt);
                    UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                    
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new UnauthorizedException();
                }
            } else if (isSecuredEndpoint(request)) {
                throw new UnauthorizedException();
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            throw new UnauthorizedException();
        }

        filterChain.doFilter(request, response);
    }

    private boolean isSecuredEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api/auth/") && 
               !path.equals("/") &&
               !path.equals("/health") &&
               !path.startsWith("/swagger-ui/") &&
               !path.startsWith("/v3/api-docs") &&
               !path.startsWith("/api-docs") &&
               !path.equals("/index.html") &&
               !path.startsWith("/static/") &&
               !path.startsWith("/css/") &&
               !path.startsWith("/js/") &&
               !path.startsWith("/images/") &&
               !path.startsWith("/api/livros/disponiveis");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}