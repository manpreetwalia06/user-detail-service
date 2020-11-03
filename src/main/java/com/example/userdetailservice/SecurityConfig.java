package com.example.userdetailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http.csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/user-detail/*").access("hasRole('USER') or hasRole('ADMIN')")
        .antMatchers(HttpMethod.POST, "/user-detail/*").access("hasRole('ADMIN')")
        .antMatchers(HttpMethod.PUT, "/user-detail").access("hasRole('ADMIN')")
        .and().httpBasic();
    }
  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
        auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER").and()
        .withUser("admin").password("{noop}password").roles("ADMIN");
    }
}
