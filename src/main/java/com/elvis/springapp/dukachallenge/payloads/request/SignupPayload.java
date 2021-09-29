package com.elvis.springapp.dukachallenge.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupPayload {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
