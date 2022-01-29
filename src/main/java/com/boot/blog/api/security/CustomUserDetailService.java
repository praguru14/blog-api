package com.boot.blog.api.security;

import com.boot.blog.api.models.RoleModel;
import com.boot.blog.api.models.UserModel;
import com.boot.blog.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       UserModel user=  userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(()-> new UsernameNotFoundException("User not found with username or email"));
        return new User(user.getEmail(),user.getPassword(),mapRoles(user.getRoles()));
    }
    private Collection  <? extends GrantedAuthority> mapRoles(Set<RoleModel> roles){
       return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
