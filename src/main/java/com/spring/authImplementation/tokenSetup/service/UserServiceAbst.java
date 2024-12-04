package com.spring.authImplementation.tokenSetup.service;

import com.spring.authImplementation.tokenSetup.daosEntity.UserInfo;
import com.spring.authImplementation.tokenSetup.dtosUI.DataTransObjects;
import com.spring.authImplementation.tokenSetup.exception.SqlException;
import com.spring.authImplementation.tokenSetup.exception.UserException;

import java.util.List;

public interface UserServiceAbst {

    void postUser(DataTransObjects  dataTransObjects);
    void postAllUsers(List<DataTransObjects> dataTransObjectsList);
    DataTransObjects getSelectiveUser(int user_id) throws Exception;
    List<DataTransObjects> getAllUsersWithSort(String fieldName) throws SqlException;
    List<DataTransObjects> getAllUsersWithPagination(int offSet, int pageSize) throws SqlException;
    List<DataTransObjects> getAllUsersWithPaginationAndSort(String fieldName, int offSet, int pageSize) throws SqlException;
    List<DataTransObjects> getAllUsers() throws SqlException;
    void updateUser (DataTransObjects dataTransObjects);
    void deleteUser(int user_id) throws UserException;
    String addUser(UserInfo userInfo);


}
