package com.alura.forum.config.security;

import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user " + username + " doesn't exists"));

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        userEntity.getRoles()
                .forEach(role -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(("ROLE_".concat(role.getRole().name())))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission ->simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermission().name())));

        return new User(userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.isEnabled(),
                        userEntity.isAccountNonExpired(),
                        userEntity.isCredentialsNonExpired(),
                        userEntity.isAccountNonLocked(),
                        simpleGrantedAuthorities);
    }
}
