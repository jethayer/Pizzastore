package dev.jethayer.pizzastore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class that sets up security policies including JWT filtering and access control.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Configures the security filter chain, including CORS, CSRF, and role-based access to endpoints.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return a configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/toppings").hasAnyAuthority("OWNER", "CHEF")
                    .requestMatchers(HttpMethod.POST, "/toppings").hasAuthority("OWNER")
                    .requestMatchers(HttpMethod.PUT, "/toppings/{id}").hasAuthority("OWNER")
                    .requestMatchers(HttpMethod.DELETE, "/toppings/{id}").hasAuthority("OWNER")
                    .requestMatchers("/pizzas/**").hasAnyAuthority("OWNER", "CHEF")
                    .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configures the authentication provider with a password encoder and user details service.
     *
     * @return a configured {@link AuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    /**
     * Provides the authentication manager using the authentication configuration.
     *
     * @param config the {@link AuthenticationConfiguration} used to retrieve the manager
     * @return the {@link AuthenticationManager}
     * @throws Exception if an error occurs during retrieval
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
