package com.elvis.springapp.dukachallenge.services;

import com.elvis.springapp.dukachallenge.payloads.request.LoginPayload;
import com.elvis.springapp.dukachallenge.payloads.request.SignupPayload;
import com.elvis.springapp.dukachallenge.security.jwt.JwtUtils;
import com.elvis.springapp.dukachallenge.domain.TaskOwner;
import com.elvis.springapp.dukachallenge.exceptions.UserWithProvidedEmailExistException;
import com.elvis.springapp.dukachallenge.payloads.JwtAccessTokenResponsePayload;
import com.elvis.springapp.dukachallenge.repository.TaskOwnerRepository;
import com.elvis.springapp.dukachallenge.security.ApplicationSecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TaskOwnerServiceImpl implements TaskOwnerService{

    private final TaskOwnerRepository ownerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils utils;
    private final PasswordEncoder encoder;

    public TaskOwnerServiceImpl(TaskOwnerRepository ownerRepository, AuthenticationManager authenticationManager, JwtUtils utils, PasswordEncoder encoder) {
        this.ownerRepository = ownerRepository;
        this.authenticationManager = authenticationManager;
        this.utils = utils;
        this.encoder = encoder;
    }

    @Override
    public TaskOwner register(SignupPayload signupPayload) {
        Optional<TaskOwner> existingUser = ownerRepository.findDistinctByEmail(signupPayload.getEmail());
        if(existingUser.isPresent()){
            log.error("Attempting to create duplicate user with email: {}", existingUser.get().getEmail());
            throw new UserWithProvidedEmailExistException("Provided email is already in use.Please provide a valid email");
        }
        TaskOwner toBeRegistered = null;
        try {
            toBeRegistered = TaskOwner.builder()
                    .id(UUID.randomUUID())
                    .firstName(signupPayload.getFirstName())
                    .lastName(signupPayload.getLastName())
                    .username(signupPayload.getUsername())
                    .email(signupPayload.getEmail())
                    .password(encoder.encode(signupPayload.getPassword()))
                    .isVerified(true)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ownerRepository.save(toBeRegistered);
    }

    @Override
    public TaskOwner updateProfile(TaskOwner user) {
       TaskOwner owner =  ownerRepository.findById(user.getId()).get();
       owner.setEmail(user.getEmail());
       owner.setFirstName(user.getFirstName());
       owner.setLastName(user.getLastName());
       owner.setUsername(user.getUsername());
       try {
           TaskOwner savedowner = ownerRepository.save(owner);
           System.out.println(savedowner);
           return savedowner;
       }catch (Exception e) {
           throw new  Error("Profile not updated");
       }
    }

    @Override
    public JwtAccessTokenResponsePayload ownerLogin(LoginPayload loginPayload) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPayload.getUsername(), loginPayload.getPassword())
        );
        ApplicationSecurityUser user = (ApplicationSecurityUser) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = utils.generateJwtToken(authentication);
        return JwtAccessTokenResponsePayload.builder()
                .status(HttpStatus.OK.toString())
                .message("Successfully Logged in.Welcome to your account")
                .authenticationToken(jwt)
                .username(user.getUsername())
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .authorities(user.getAuthorities()).build();
    }
}
