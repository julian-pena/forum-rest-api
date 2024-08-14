package com.alura.forum.config.security;

import com.alura.forum.config.security.utils.JwtUtils;
import com.alura.forum.model.dto.authentication.AuthLoginRequest;
import com.alura.forum.model.dto.authentication.AuthResponse;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    public AuthResponse loginUser(AuthLoginRequest loginRequest){

        String userName = loginRequest.username();
        String password = loginRequest.password();

        Authentication authentication = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponse(userName,
                "User logged sucessfully",
                accessToken,
                true);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userName, userDetails.getPassword(), userDetails.getAuthorities());

    }
}
