package com.openclassrooms.mddapi.Security;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filter for JWT (JSON Web Token) authentication in the application.
 * This filter intercepts all incoming HTTP requests and performs JWT token
 * validation.
 * It is responsible for:
 * <ul>
 * <li>Extracting JWT tokens from the Authorization header</li>
 * <li>Validating tokens using JwtService</li>
 * <li>Setting up Spring Security authentication context</li>
 * <li>Managing the authentication flow</li>
 * </ul>
 * The filter extends OncePerRequestFilter to ensure it's only executed once per
 * request.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Processes each HTTP request to validate JWT tokens and set up authentication.
     * The filter:
     * <ol>
     * <li>Extracts the JWT token from the Authorization header</li>
     * <li>Validates the token using JwtService</li>
     * <li>Loads user details if the token is valid</li>
     * <li>Sets up Spring Security authentication context</li>
     * </ol>
     *
     * @param request     the HTTP request to process
     * @param response    the HTTP response
     * @param filterChain the filter chain to continue processing
     * @throws IOException if there's an I/O error
     */
    @Override
    @Operation(summary = "Process incoming HTTP requests", description = "Validates JWT tokens and sets up Spring Security authentication context")
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}