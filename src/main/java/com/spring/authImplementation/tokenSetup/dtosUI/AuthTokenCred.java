package com.spring.authImplementation.tokenSetup.dtosUI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthTokenCred {
    private String userName;
    private String passWord;

}
