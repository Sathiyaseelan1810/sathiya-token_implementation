package com.spring.authImplementation.tokenSetup.dtosUI;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseMessage {
        private String developerMessage;
        private String errorMessage;
}
