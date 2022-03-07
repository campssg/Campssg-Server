package com.campssg.service;

import com.campssg.DB.entity.User;
import com.campssg.DB.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(user.getUserRole().toString());
        return new org.springframework.security.core.userdetails.User(user.getUserEmail(),
                user.getUserPassword(),
                Collections.singleton(grantedAuthorities));
    }
}
