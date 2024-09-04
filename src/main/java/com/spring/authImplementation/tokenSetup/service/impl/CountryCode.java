package com.spring.authImplementation.tokenSetup.service.impl;

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
