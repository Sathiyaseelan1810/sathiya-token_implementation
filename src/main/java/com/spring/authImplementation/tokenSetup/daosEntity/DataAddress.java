package com.spring.authImplementation.tokenSetup.daosEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_address")
@Getter
@Setter

public class DataAddress {
    @Id
    @Column(name = "address_id", nullable = false)
    private int address_id;

    @Column(name="street", nullable = false, unique = false, columnDefinition = "VARCHAR(50)")
    private String street;

    @Column(name="city", nullable = false, unique = false, columnDefinition = "VARCHAR(50)")
    private String city;

    @Column(name="state", nullable = false, unique = false, columnDefinition = "VARCHAR(50)")
    private String state;

    @Column(name="zipcode", nullable = false, unique = false, length = 10)
    private String zipCode;

    @Column(name="country", nullable = false, unique = false, columnDefinition = "VARCHAR(30)")
    private String country;

}
