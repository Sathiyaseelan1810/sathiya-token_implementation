package com.spring.authImplementation.tokenSetup.enums;

import lombok.Getter;

@Getter
public enum CountryCode {
    USA("USA"),
    CA("CANADA");

    private final String code;

    private CountryCode(String code)
    {
        this.code = code;
    }




}
