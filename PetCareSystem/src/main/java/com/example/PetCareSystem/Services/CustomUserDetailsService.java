package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.PetCareSystem.Entities.User user = userRepository.findByUsername(username).orElseThrow();
        return new User(

                user.getUsername(),
                user.getPassword(),
                user.getAuthorities() // Kullanıcının yetkileri
        );
    }
}