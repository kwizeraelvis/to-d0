package com.elvis.springapp.dukachallenge.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralResponsePayload {
    private String statusCode;
    private String message;
    private Object data;
}
