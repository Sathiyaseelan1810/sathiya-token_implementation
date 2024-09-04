package com.spring.authImplementation.tokenSetup.daosEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
@Data

public class DataAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "address_id", nullable = false)
    private Integer address_id;

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
