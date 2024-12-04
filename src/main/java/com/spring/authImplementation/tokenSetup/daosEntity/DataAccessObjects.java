package com.spring.authImplementation.tokenSetup.daosEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_details")

public class DataAccessObjects {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="user_id", nullable = false)
    private int userID;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name="email_id", nullable = false, unique = true)
    private String emailID;

    @OneToMany(targetEntity = DataAddress.class, fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_fn_ky", referencedColumnName = "user_id", nullable = false)
    private List<DataAddress> addresses;


}
