package org.prajwal.authservice.security;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filteringCriteria(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/signup/**").permitAll()//Anyone can access signup; authentication is not required for this endpoint.
                        .requestMatchers("/api/v1/auth/signin/**").permitAll()
                        .anyRequest().authenticated() // Everything else requires authentication.
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    /*
    checks the input password and db password
    AuthenticationManager calls this authProvider()
    -----------------------------------------------
    an interface with one job: given an Authentication request (e.g., username+password), verify it's correct,
    and return a fully-populated Authentication if valid (or throw an exception if not).
    This is where the actual verification logic lives — e.g., "does this password match the hashed password on file."
     */
    public AuthenticationProvider passengerAuthenticationProvider(@Qualifier("passengerUserDetailsService") UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }


    @Bean
    @Primary
    /*
    The orchestrator.
    It doesn't do verification itself — it delegates to one or more AuthenticationProviders
     */
    public AuthenticationManager passengerAuthenticationManager(@Qualifier("passengerAuthenticationProvider") AuthenticationProvider authenticationProvider) throws Exception {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public AuthenticationProvider driverAuthenticationProvider(@Qualifier("driverUserDetailsService") UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager driverAuthenticationManager(@Qualifier("driverAuthenticationProvider") AuthenticationProvider authenticationProvider) throws Exception {
        return new ProviderManager(authenticationProvider);
    }
}
