package com.spring.authImplementation.tokenSetup.service;

import com.spring.authImplementation.tokenSetup.dtosUI.DataTransObjects;
import com.spring.authImplementation.tokenSetup.exception.SqlException;

import java.util.List;

public interface UserServiceAbst {

    void postUser(DataTransObjects  dataTransObjects);
    void postAllUsers(List<DataTransObjects> dataTransObjectsList);
    DataTransObjects getSelectiveUser(Integer id) throws Exception;
    List<DataTransObjects> getAllUsersWithSort(String fieldName) throws SqlException;
    List<DataTransObjects> getAllUsersWithPagination(Integer offSet, Integer pageSize) throws SqlException;
    List<DataTransObjects> getAllUsersWithPaginationAndSort(String fieldName, Integer offSet, Integer pageSiz) throws SqlException;
    List<DataTransObjects> getAllUsers() throws SqlException;
    void updateUser (DataTransObjects dataTransObjects);
    void deleteUser(Integer id);


}
