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
@Table(name = "token_validation")

public class DataAccessObjects {

    @Id
    @Column(name="user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userID;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name="email_id", nullable = false, unique = true)
    private String emailID;

    @OneToMany(targetEntity = DataAddress.class, fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "tkn_add", referencedColumnName = "user_id", nullable = false)
    private List<DataAddress> addresses;


}
