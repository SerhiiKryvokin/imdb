package com.imdb.config;

import com.imdb.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/rate/**").authenticated()
                    .antMatchers("/resources/**").permitAll()
                    .anyRequest().permitAll();


        http
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/j_spring_security_check")
                    .failureUrl("/login")
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(2419200)
                    .key("imdbKey")
                    .rememberMeParameter("remember");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){ return new BCryptPasswordEncoder(); }

}
