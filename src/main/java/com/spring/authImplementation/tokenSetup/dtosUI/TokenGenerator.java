package com.spring.authImplementation.tokenSetup.dtosUI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenGenerator {

    private String bearerToken;


}
