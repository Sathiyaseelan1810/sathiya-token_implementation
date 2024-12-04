package com.spring.authImplementation.tokenSetup.service.impl;

import com.spring.authImplementation.tokenSetup.daosEntity.DataAccessObjects;
import com.spring.authImplementation.tokenSetup.daosEntity.DataAddress;
import com.spring.authImplementation.tokenSetup.dtosUI.Addresses;
import com.spring.authImplementation.tokenSetup.dtosUI.DataTransObjects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ObjectMapper {

    /**
     *
     * @param dataTransObjects
     * @return
     */
    // DTO(UI) to DAO(entity)--> POST
    public DataAccessObjects dataTransferToAccessObjMapper(DataTransObjects dataTransObjects){
        log.info("Started mapping all the DTO -> DAO objects");
        DataAccessObjects dataAccessObjects = DataAccessObjects.builder()
                .firstName(dataTransObjects.getFirstName())
                .lastName(dataTransObjects.getLastName())
                .emailID(dataTransObjects.getEmailID())
                .mobileNumber(dataTransObjects.getMobileNumber())
                .addresses(getDataAccessAddressObjectsList(dataTransObjects.getAddresses()))
                .build();
        return dataAccessObjects;
    }

    /**
     *
     * @param dataTransAddressObjects
     * @return
     */
    private DataAddress dataAccessAddressObjects(Addresses dataTransAddressObjects) {
        log.info("Started mapping all the DTO Address Objects -> DAO Address Objects");
        return DataAddress.builder()
                .address_id(dataTransAddressObjects.getAddress_id())
                .street(dataTransAddressObjects.getStreet())
                .city(dataTransAddressObjects.getCity())
                .state(dataTransAddressObjects.getState())
                .zipCode(dataTransAddressObjects.getZipCode())
                .country(dataTransAddressObjects.getCountry())
                .build();
    }

    /**
     *
     * @param dataTransAddressObjectsList
     * @return
     */
    private List<DataAddress> getDataAccessAddressObjectsList(List<Addresses> dataTransAddressObjectsList) {
        return dataTransAddressObjectsList
                .stream()
                .map(this::dataAccessAddressObjects)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param dataTransObjects
     * @return
     */
    // DTO(UI) to DAO(entity)--> PUT
    public DataAccessObjects dataTransferToAccessObjectMapperAll(DataTransObjects dataTransObjects){
        log.info("Started mapping all the DTO -> DAO objects");
        DataAccessObjects dataAccessObjects = DataAccessObjects.builder()
                .userID(dataTransObjects.getUserID())
                .firstName(dataTransObjects.getFirstName())
                .lastName(dataTransObjects.getLastName())
                .mobileNumber(dataTransObjects.getMobileNumber())
                .emailID(dataTransObjects.getEmailID())
                .addresses(getDataAccessAddressObjectsList(dataTransObjects.getAddresses()))
                .build();
        log.info("Completed mapping all the DTO -> DAO objects");
        return dataAccessObjects;
    }


    //DAO(entity) to DTO(UI) --> GET
    public DataTransObjects dataAccessToTransferObjMapper(DataAccessObjects dataAccessObjects){
        log.info("Started mapping all the DAO -> DTO objects");
        return DataTransObjects.builder()
                .userID(dataAccessObjects.getUserID())
                .firstName(dataAccessObjects.getFirstName())
                .lastName(dataAccessObjects.getLastName())
                .mobileNumber(dataAccessObjects.getMobileNumber())
                .emailID(dataAccessObjects.getEmailID())
                .addresses(getDataTransAddressObjectsList(dataAccessObjects.getAddresses()))
                .build();
    }

    private Addresses dataTransAddressObjects(DataAddress dataAccessAddressObjects) {
        log.info("Started mapping all the DAO Address Objects -> DTO Address Objects");
        return Addresses.builder()
                .address_id(dataAccessAddressObjects.getAddress_id())
                .street(dataAccessAddressObjects.getStreet())
                .city(dataAccessAddressObjects.getCity())
                .state(dataAccessAddressObjects.getState())
                .zipCode(dataAccessAddressObjects.getZipCode())
                .country(dataAccessAddressObjects.getCountry())
                .build();
    }

    private List<Addresses> getDataTransAddressObjectsList(List<DataAddress> dataAccessAddressObjectsList) {
        return dataAccessAddressObjectsList
                .stream()
                .map(this::dataTransAddressObjects)
                .collect(Collectors.toList());
    }



}
