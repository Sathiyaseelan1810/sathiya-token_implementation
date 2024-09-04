package com.spring.authImplementation.tokenSetup.dtosUI;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataTransObjects {

    private Integer userID;

    @NotBlank(message = "'First Name' should not be blank")
    private String firstName;

    @NotBlank(message = "'Last Name' should not be blank")
    private String lastName;

    @NotBlank(message = "'Mobile Number' should not be blank")
    private String mobileNumber;

    @NotBlank(message = "'Email ID' should not be blank")
    private String emailID;

    @NotNull()
    private List<Addresses> addresses;

}
