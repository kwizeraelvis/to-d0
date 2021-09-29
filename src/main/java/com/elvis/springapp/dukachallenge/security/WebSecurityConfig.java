package com.elvis.springapp.dukachallenge.security;

import com.elvis.springapp.dukachallenge.security.jwt.JwtAuthenticationEntryPoint;
import com.elvis.springapp.dukachallenge.security.jwt.JwtAuthenticationTokenFilter;
import com.elvis.springapp.dukachallenge.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = authenticationEntryPoint;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors().and().csrf().disable()
               .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .authorizeRequests()
               .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
               .anyRequest()
               .authenticated();
       http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
