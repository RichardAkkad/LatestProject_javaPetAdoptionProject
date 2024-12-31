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
        User user = userRepo.findByUsername(username);//a new object is created, the name if found in a row then that row is used to create another object using setters in
        if (user == null) {//the User class similar to when we input values into the dog form and then setters were used to bind these values from the form to the object


            throw new UsernameNotFoundException("user not found");//this exception is caught internally
        }

        if (user.getRole() == Role.ADMIN) {  // checks if role is ADMIN for the user
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword())
                    .roles("ADMIN")
                    .build();//User object is created for checking security, cant use the above "user" object returned from the database for example, but the user object details above is used to
        } else {    //to create this object and then we use this User object to Verify password matches,Check permissions for future requests,Create security session(even though this is done internally but we still need this object to create a session
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        }
    }
}