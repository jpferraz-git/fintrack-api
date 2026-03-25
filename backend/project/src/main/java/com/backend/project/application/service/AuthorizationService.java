package com.backend.project.application.service;


import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         UserEntity user = userRepository.findByEmail(username);
         if(user == null) {
             throw new UsernameNotFoundException("User not found with email: " + username);
         }
         return user;
    }


}
