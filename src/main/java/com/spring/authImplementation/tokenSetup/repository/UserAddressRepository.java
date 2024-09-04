package com.spring.authImplementation.tokenSetup.repository;

import com.spring.authImplementation.tokenSetup.daosEntity.DataAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<DataAddress, Integer> {
}
