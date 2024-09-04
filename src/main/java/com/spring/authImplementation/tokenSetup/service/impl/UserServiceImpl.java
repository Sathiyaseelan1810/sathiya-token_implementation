package com.spring.authImplementation.tokenSetup.service.impl;

import com.github.javafaker.Faker;
import com.spring.authImplementation.tokenSetup.daosEntity.DataAccessObjects;
import com.spring.authImplementation.tokenSetup.dtosUI.Addresses;
import com.spring.authImplementation.tokenSetup.dtosUI.DataTransObjects;
import com.spring.authImplementation.tokenSetup.exception.SqlException;
import com.spring.authImplementation.tokenSetup.exception.UserException;
import com.spring.authImplementation.tokenSetup.handler.UserJwtHandler;
import com.spring.authImplementation.tokenSetup.repository.UserAddressRepository;
import com.spring.authImplementation.tokenSetup.repository.UserTokenRepository;
import com.spring.authImplementation.tokenSetup.service.UserServiceAbst;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.spring.authImplementation.tokenSetup.service.impl.CountryCode.USA;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl extends ObjectMapper implements UserServiceAbst {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJwtHandler userJwtHandler;

    private Faker faker= new Faker(new Locale("en-US"));

    @PostConstruct
    public void initUsers(){
        List<DataAccessObjects> dataAccessObjectsList = IntStream.range(1,200)
                .mapToObj(iterate-> objectMapper
                .dataTransferToAccessObjectMapperAll(new DataTransObjects(iterate,faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress(),
                        List.of(new Addresses(iterate, faker.address().streetAddress(), faker.address().city(), faker.address().state(), faker.address().zipCode(), USA.getCode())))))
                .collect(Collectors.toList());
        log.info("The above data's are stored in the persistence db when the spring components are initialized");
        this.userTokenRepository.saveAll(dataAccessObjectsList);
    }

    @Override
    public void postUser(DataTransObjects dataTransObjects) {
        this.userTokenRepository.save(objectMapper.dataTransferToAccessObjMapper(dataTransObjects));
        log.info("New user detail is posted and persisted in the DB successfully!");
    }

    @Override
    public void postAllUsers(List<DataTransObjects> dataTransObjectsList) {
        List<DataAccessObjects>dataAccessObjectsList = dataTransObjectsList
                .stream()
                .map(this::dataTransferToAccessObjMapper)
                .collect(Collectors.toList());
        this.userTokenRepository.saveAll(dataAccessObjectsList);
        log.info("Multiple new user details are posted and persisted in the DB successfully!");
    }

    @Override
    public DataTransObjects getSelectiveUser(Integer id) throws Exception {
        Optional<DataAccessObjects> findUserByID= this.userTokenRepository.findById(id);
        if(!findUserByID.isEmpty())
            throw new UserException("User ID: " + id+ " is not found in the db!");
        DataAccessObjects dataAccessObjects = findUserByID.get();
        log.info("Successfully found the 'User_ID='{} in the db", id);
        return objectMapper.dataAccessToTransferObjMapper(dataAccessObjects);
    }

    @Override
    public List<DataTransObjects> getAllUsers() throws SqlException {
        if(this.userTokenRepository.findAll().isEmpty())
            throw new SqlException("Sorry No Users found in DB!");
        log.info("All users are retrieved from the repository successfully!");
        return this.userTokenRepository.findAll()
                .stream()
                .map(this::dataAccessToTransferObjMapper)
                .collect(Collectors.toList());
    }

    //Sorting mechanism::
    @Override
    public List<DataTransObjects> getAllUsersWithSort(String fieldName) throws SqlException {
        if(this.userTokenRepository.findAll().isEmpty())
            throw new SqlException("Sorry No Users found in DB!");
        log.info("All users are retrieved from the repository successfully!");
        return this.userTokenRepository.findAll(Sort.by(Sort.Direction.DESC, fieldName))
                .stream()
                .map(this::dataAccessToTransferObjMapper)
                .collect(Collectors.toList());
    }

    // Pagination Mechanism::
    @Override
    public List<DataTransObjects> getAllUsersWithPagination(Integer offSet, Integer pageSize) throws SqlException {
        if(this.userTokenRepository.findAll().isEmpty())
            throw new SqlException("Sorry No Users found in DB!");
        log.info("All users are retrieved from the repository successfully!");
        return this.userTokenRepository.findAll(PageRequest.of(offSet,pageSize))
                .stream()
                .map(this::dataAccessToTransferObjMapper)
                .collect(Collectors.toList());
    }
    // Sorting with pagination::
    @Override
    public List<DataTransObjects> getAllUsersWithPaginationAndSort(String fieldName, Integer offSet, Integer pageSize) throws SqlException {
        if(this.userTokenRepository.findAll().isEmpty())
            throw new SqlException("Sorry No Users found in DB!");
        log.info("All users are retrieved from the repository successfully!");
        return this.userTokenRepository.findAll(PageRequest.of(offSet,pageSize).withSort(Sort.by(Sort.Direction.ASC, fieldName)))
                .stream()
                .map(this::dataAccessToTransferObjMapper)
                .collect(Collectors.toList());

    }

    @Override
    public void updateUser(DataTransObjects dataTransObjects) {
        this.userTokenRepository.save(objectMapper.dataTransferToAccessObjectMapperAll(dataTransObjects));
        log.info("User is updated with the new details and stored in the repository successfully!");
    }

    @Override
    public void deleteUser(Integer id) {
        this.userTokenRepository.deleteById(id);
        log.info("UserID = {}  is deleted from the repository successfully!", id);
    }

}
