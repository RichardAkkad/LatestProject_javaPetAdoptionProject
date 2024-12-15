package com.postgresql.ytdemo2.Config;

import com.postgresql.ytdemo2.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/addPet").hasAuthority("ROLE_ADMIN");//hasRole("ADMIN");
                    auth.requestMatchers("/updateById").hasAuthority("ROLE_ADMIN");//hasRole("ADMIN");
                    auth.requestMatchers("/deleteById").hasAuthority("ROLE_ADMIN");//hasRole("ADMIN");
                    auth.requestMatchers("/homePage").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().permitAll();
                })
                .userDetailsService(userDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/homePage", false)  // false means don't always redirect here
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/login?error=true");
                        })
                        


                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}














