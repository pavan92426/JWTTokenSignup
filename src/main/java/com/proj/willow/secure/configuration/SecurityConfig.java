package com.proj.willow.secure.configuration;

import com.proj.willow.secure.JWTValidatorAuthFilter;
import com.proj.willow.service.JwtService;
import com.proj.willow.service.WillowUserService;
import com.proj.willow.shared.utils.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@Slf4j
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JWTValidatorAuthFilter jwtValidatorAuthFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider,JWTValidatorAuthFilter jwtValidatorAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtValidatorAuthFilter = jwtValidatorAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("custom :: Security config filterchain request");
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, SecurityConstants.SIGNUP_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtValidatorAuthFilter
                                   , UsernamePasswordAuthenticationFilter.class)
                                .sessionManagement((session) -> session
                                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                                    //.formLogin(Customizer.withDefaults())
                                   // .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        log.info("custom :: Security config corsConfigurationSource request");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8005"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

}
