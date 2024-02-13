package com.gatepass.GatePass.securityconf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConf {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
        .csrf((csrf)->csrf.disable())
        .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers("/frontend/**", "/any/**").permitAll())
        .authorizeHttpRequests((authorizeHttpRequests)-> authorizeHttpRequests.requestMatchers("/admin/**").hasAuthority("ADMIN"))
        .authorizeHttpRequests((authorizeHttpRequests)-> authorizeHttpRequests.requestMatchers("/rep/**").hasAuthority("REP"))
        .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.anyRequest().authenticated())
        .sessionManagement((sessionManagement)->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new CustomAuthorization(), UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}
