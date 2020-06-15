package com.example.project.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
open class SecurityConfig: WebSecurityConfigurerAdapter(){

    override fun configure(auth:AuthenticationManagerBuilder){
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
                .and()
                .withUser("patryk").password(passwordEncoder().encode("bukowski")).roles("USER")
    }

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/profile/**").authenticated()
                .antMatchers("/api/airlines/**").hasAnyRole("AIRLINE_OWNER", "ADMIN")
                .antMatchers("/api/airplanes/**").hasAnyRole("AIRLINE_OWNER", "ADMIN")
                .antMatchers("/api/admins/**").hasRole("ADMIN")
                .and()
                .httpBasic()
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder{
       return BCryptPasswordEncoder();
    }
}
