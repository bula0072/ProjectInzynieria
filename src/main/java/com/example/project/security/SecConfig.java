package com.example.project.security;

import com.example.project.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecConfig extends WebSecurityConfigurerAdapter {
    private final UserDetail userDetail;
    private final UserRepository userRepository;

    public SecConfig(UserDetail userDetail, UserRepository userRepository) {
        this.userDetail = userDetail;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager(), this.userRepository))
                .authorizeRequests()
                .anyRequest().permitAll();
                /*
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/admins/addRole").permitAll()
                .antMatchers("/api/public/**").permitAll()
               // .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/airlines/**").hasAnyRole("ADMIN", "AIRLINE_OWNER")
                .antMatchers("/api/airport/**").hasAnyRole("ADMIN", "AIRPORT_OWNER")
                .antMatchers("/api/planes/**").hasAnyRole("ADMIN", "AIRLINE_OWNER")
                .anyRequest().authenticated();*/
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider =
                new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetail);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
