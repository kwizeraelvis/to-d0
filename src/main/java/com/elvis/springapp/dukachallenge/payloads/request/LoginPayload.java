package com.elvis.springapp.dukachallenge.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.NonLeaked;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginPayload {
    private String username;
    private String password;
}
