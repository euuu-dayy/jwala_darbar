package com.jwalanavdurga.security;

import com.jwalanavdurga.entity.User;
import com.jwalanavdurga.repository.UserRepository;
import com.jwalanavdurga.repository.UserRoleRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public CustomUserDetailsService(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Invalid username or password."
                        )
                );

        List<String> roleNames =
                userRoleRepository.findRoleNamesByUserId(
                        user.getId()
                );

        List<String> permissionNames =
                userRoleRepository.findPermissionNamesByUserId(
                        user.getId()
                );

        List<SimpleGrantedAuthority> authorities =
                new ArrayList<>();

        // Add roles
        for (String roleName : roleNames) {

            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + roleName
                    )
            );
        }

        // Add permissions
        for (String permissionName : permissionNames) {

            authorities.add(
                    new SimpleGrantedAuthority(
                            permissionName
                    )
            );
        }

        return new CustomUserDetails(
                user,
                authorities
        );
    }
}