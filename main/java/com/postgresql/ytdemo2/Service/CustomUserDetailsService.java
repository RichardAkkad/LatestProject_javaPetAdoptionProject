package com.postgresql.ytdemo2.Service;

import com.postgresql.ytdemo2.Enums.Role;
import com.postgresql.ytdemo2.model.User;
import com.postgresql.ytdemo2.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (user.getRole() == Role.ADMIN) {  // Only give ADMIN role if user is actually an admin
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword())
                    .roles("ADMIN")
                    .build();
        } else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        }
    }
}