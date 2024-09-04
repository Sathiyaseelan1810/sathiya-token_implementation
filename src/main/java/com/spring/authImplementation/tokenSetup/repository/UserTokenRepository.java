package com.spring.authImplementation.tokenSetup.repository;

import com.spring.authImplementation.tokenSetup.daosEntity.DataAccessObjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<DataAccessObjects, Integer> {
}


