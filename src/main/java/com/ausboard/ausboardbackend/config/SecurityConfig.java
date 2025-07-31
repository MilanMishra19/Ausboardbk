package com.ausboard.ausboardbackend.config;

import com.ausboard.ausboardbackend.service.CustomUserDetailsService;

import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Still needed for general matching
import org.springframework.security.web.util.matcher.RequestMatcher; // Import RequestMatcher
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.util.matcher.AndRequestMatcher; // Import AndRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher; // Import NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher; // Import OrRequestMatcher
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests(auth -> auth
                // Allow specific paths to be accessed without authentication
                // Order matters: most specific to least specific for permitAll
                .requestMatchers(
                    "/login",
                    "/logout",
                    "/api/users", // Permit POST /api/users for registration
                    "/api/projects/**", // Assuming these are public endpoints
                    "/api/testcases/**" // Assuming these are public endpoints
                ).permitAll()
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    System.out.println("Login success for: " + authentication.getName());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Login successful\"}");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("Login failed: " + exception.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Invalid username or password\"}");
                })
                .permitAll() // Ensure login form/process itself is publicly accessible
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Logged out successfully\"}");
                })
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            )
            .exceptionHandling(exception -> exception
                // Define a RequestMatcher that applies the unauthorizedEntryPoint
                // ONLY to API paths that are NOT explicitly permitted.
                .defaultAuthenticationEntryPointFor(
                    unauthorizedEntryPoint(),
                    apiPathsRequiringAuthenticationMatcher() // Use the new bean here
                )
            );

        return http.build();
    }

    // New Bean to create the complex RequestMatcher
    @Bean
    public RequestMatcher apiPathsRequiringAuthenticationMatcher() {
        // Matcher for all API paths
        RequestMatcher allApiPaths = new AntPathRequestMatcher("/api/**");

        // Matcher for API paths that are explicitly permitted (public)
        RequestMatcher permittedApiPaths = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/users"),
            new AntPathRequestMatcher("/api/projects/**"),
            new AntPathRequestMatcher("/api/testcases/**")
        );

        // Combine: Match all API paths AND (NOT permitted API paths)
        // This means, apply the AuthenticationEntryPoint to /api/** unless it's one of the permitted ones.
        return new AndRequestMatcher(
            allApiPaths,
            new NegatedRequestMatcher(permittedApiPaths)
        );
    }

    @Bean
    public ServletContextInitializer sameSiteConfig() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setSecure(true);
            sessionCookieConfig.setHttpOnly(true);
            sessionCookieConfig.setName("JSESSIONID");
        };
    }

    @Bean
    public CookieSameSiteSupplier cookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict().whenHasName("JSESSIONID");
    }
    
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:3000",
            "https://ausboardfk.vercel.app/"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
