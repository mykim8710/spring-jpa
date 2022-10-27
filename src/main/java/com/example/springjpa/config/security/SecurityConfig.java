package com.example.springjpa.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // Spring Security를 활성화한다는 의미의 @annotation
public class SecurityConfig extends WebSecurityConfigurerAdapter {



}
