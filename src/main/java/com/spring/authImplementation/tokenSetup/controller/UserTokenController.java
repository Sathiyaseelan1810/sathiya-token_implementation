package com.spring.authImplementation.tokenSetup.controller;

import com.spring.authImplementation.tokenSetup.dtosUI.DataTransObjects;
import com.spring.authImplementation.tokenSetup.exception.SqlException;
import com.spring.authImplementation.tokenSetup.service.UserServiceAbst;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("tokenValidation")
public class UserTokenController{

    @Autowired
    private UserServiceAbst userServiceAbst;

    @PostMapping(path = "postUser")
    ResponseEntity<String> postUser(@Valid @RequestBody DataTransObjects dataTransObjects){
        this.userServiceAbst.postUser(dataTransObjects);
        log.info("POST_REQUEST: user is created through post mapping!");
        return  new ResponseEntity<>("User is created successfully/registered in the DB",HttpStatus.CREATED);
    }

    @PostMapping(path = "postMultipleUsers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<DataTransObjects>> postAllUsers(@Valid @RequestBody List<DataTransObjects> dataTransObjectsList){
        this.userServiceAbst.postAllUsers(dataTransObjectsList);
        log.info("POST_REQUEST: Multiple users are created through post mapping!");
        return new ResponseEntity<>(dataTransObjectsList,HttpStatus.CREATED);
    }

    @GetMapping(path = "{userID}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<DataTransObjects> getSelectiveUser(@PathVariable Integer userID) throws Exception {
        this.userServiceAbst.getSelectiveUser(userID);
        log.info("GET_REQUEST: Selected user is retrieved through get mapping!");
        return new ResponseEntity<>(this.userServiceAbst.getSelectiveUser(userID), HttpStatus.OK);
    }

    @GetMapping(path = "getAllUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsers() throws SqlException {
        this.userServiceAbst.getAllUsers();
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping!");
        return new ResponseEntity<>(this.userServiceAbst.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "getAllUsersWithSort/{fieldName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsersWithSort(@PathVariable String fieldName) throws SqlException {
        this.userServiceAbst.getAllUsersWithSort(fieldName);
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping and showing up in the sorting order (ASC) based on the field = {}", fieldName);
        return new ResponseEntity<>(this.userServiceAbst.getAllUsersWithSort(fieldName), HttpStatus.OK);
    }

    @GetMapping(path = "getAllUsersWithPagination", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsersWithPage(@RequestParam Integer offSet, @RequestParam Integer pageNumber) throws SqlException {
        this.userServiceAbst.getAllUsersWithPagination(offSet, pageNumber);
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping with pagination!");
        return new ResponseEntity<>(this.userServiceAbst.getAllUsersWithPagination(offSet, pageNumber), HttpStatus.OK);
    }

    @GetMapping(path = "getAllUsersWithSortAndPagination/{fieldName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsersWithSortAndPage(@PathVariable String fieldName, @RequestParam Integer offSet, @RequestParam Integer pageNumber) throws SqlException {
        this.userServiceAbst.getAllUsersWithPaginationAndSort(fieldName, offSet, pageNumber);
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping and included the sorting and pagination mechanism");
        return new ResponseEntity<>(this.userServiceAbst.getAllUsersWithPaginationAndSort(fieldName, offSet, pageNumber), HttpStatus.OK);
    }

    @PutMapping(path = "updateSelectiveUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<String> updateUser(@Valid @RequestBody DataTransObjects dataTransObjects){
        this.userServiceAbst.updateUser(dataTransObjects);
        log.info("PUT_REQUEST: Successfully retrieved all users from db through get mapping!");
        return new ResponseEntity<>("User is updated with new details and stored in the db",HttpStatus.CREATED);
    }

    @DeleteMapping("{userID}")
    ResponseEntity<String> deleteUser(@PathVariable Integer userID){
        this.userServiceAbst.deleteUser(userID);
        log.info("DELETE_REQUEST: Successfully deleted the USER_ID:{} from DB", userID);
        return new ResponseEntity<>("User is successfully deleted from the db!", HttpStatus.NO_CONTENT);

    }


}




