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
import org.springframework.security.web.savedrequest.SavedRequest;

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
                    auth.requestMatchers("/addPet").hasRole("ADMIN");//hasAuthority("ROLE_ADMIN");     When a request comes to an endpoint, SecurityFilterChain checks the User object (stored in the session)
                    auth.requestMatchers("/updateById").hasRole("ADMIN");//hasAuthority("ROLE_ADMIN");
                    auth.requestMatchers("/deleteById").hasRole("ADMIN");//hasAuthority("ROLE_ADMIN");
                    auth.requestMatchers("/homePage").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().permitAll();
                })
                .userDetailsService(userDetailsService)//after user has entered details on login form "userDetailsService()" tells Spring use this service to check their details


               .formLogin(form -> form// we come to this SecurityFilterChain method and then  here if we click on a restricted endpoint and user has not yet already logged on using the authorizeHttpRequests method. and from here we go to the loadByUser method
                      .loginPage("/login")// if not already logged in then we come here which redirects us to the login page
                       .defaultSuccessUrl("/homePage", false) // First parameter ("/homePage") - The URL to redirect to after successful login, false means: "Try to go to the originally requested URL first",true would mean: "Always go to homepage no matter what"
                      .permitAll()
              )
                .exceptionHandling(exceptionHandling -> exceptionHandling// spring automatically has a inbuilt wrong details exception where "error" shows up as a url endpoint where we use "param.error" to deal with that but this here is a unauthorised
                        .accessDeniedHandler((request, response, accessDeniedException) -> {// user exception which creates a endpoint "denied" and we used "param.denied" in the template as well
                            response.sendRedirect("/login?denied");
                        })

                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}














