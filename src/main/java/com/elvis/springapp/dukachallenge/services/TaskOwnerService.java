package com.elvis.springapp.dukachallenge.services;

import com.elvis.springapp.dukachallenge.payloads.request.LoginPayload;
import com.elvis.springapp.dukachallenge.payloads.request.SignupPayload;
import com.elvis.springapp.dukachallenge.domain.TaskOwner;
import com.elvis.springapp.dukachallenge.payloads.JwtAccessTokenResponsePayload;

public interface TaskOwnerService {
    TaskOwner register(SignupPayload signupPayload);
    TaskOwner updateProfile(TaskOwner signupPayload);
    JwtAccessTokenResponsePayload ownerLogin(LoginPayload loginPayload);
}
