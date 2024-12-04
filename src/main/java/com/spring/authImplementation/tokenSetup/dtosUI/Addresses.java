package com.spring.authImplementation.tokenSetup.dtosUI;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Addresses {

    private Integer address_id;

    @NotBlank(message = "'Street' should not be blank")
    private String street;

    @NotBlank(message = "'City' should not be blank")
    private String city;

    @NotBlank(message = "'State' should not be blank")
    private String state;

    @NotBlank(message = "'Zipcode' should not be blank")
    private String zipCode;

    @NotBlank(message = "'Country' should not be blank")
    private String country;

}
