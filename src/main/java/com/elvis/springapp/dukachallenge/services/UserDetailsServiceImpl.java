package com.elvis.springapp.dukachallenge.services;

import com.elvis.springapp.dukachallenge.domain.TaskOwner;
import com.elvis.springapp.dukachallenge.repository.TaskOwnerRepository;
import com.elvis.springapp.dukachallenge.security.ApplicationSecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TaskOwnerRepository ownerRepository;

    public UserDetailsServiceImpl(TaskOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TaskOwner owner = ownerRepository.findDistinctByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with specified username"));
        return ApplicationSecurityUser.builder()
                .id(owner.getId())
                .username(owner.getUsername())
                .email(owner.getEmail())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .password(owner.getPassword())
                .isEnabled(owner.getIsVerified())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();

    }
}
