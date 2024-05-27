package com.example.aggregationevent.service.security;

import com.example.aggregationevent.entity.Role;
import com.example.aggregationevent.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }
    public Collection<? extends GrantedAuthority> mapRolesToGrantedAuthorities(List<Role> roles){
        Collection<? extends GrantedAuthority>authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();
        return authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToGrantedAuthorities(user.getRoles());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail(){
        return user.getEmail();
    }
}
