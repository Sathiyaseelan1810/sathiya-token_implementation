package com.spring.authImplementation.tokenSetup.controller;

import com.spring.authImplementation.tokenSetup.daosEntity.UserInfo;
import com.spring.authImplementation.tokenSetup.dtosUI.AuthTokenCred;
import com.spring.authImplementation.tokenSetup.dtosUI.DataTransObjects;
import com.spring.authImplementation.tokenSetup.dtosUI.TokenGenerator;
import com.spring.authImplementation.tokenSetup.exception.SqlException;
import com.spring.authImplementation.tokenSetup.exception.UserException;
import com.spring.authImplementation.tokenSetup.service.UserServiceAbst;
import com.spring.authImplementation.tokenSetup.service.impl.JwtServiceImpl;
import com.spring.authImplementation.tokenSetup.service.impl.UserInfoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/tokenValidation")
public class UserTokenController {

    @Autowired
    private UserServiceAbst userServiceAbst;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceImpl jwtServiceImpl;

    // Normal End Point Implementation ---
    @PostMapping(value = "/postUser") // **** Working ****
    //@Operation(summary = "Post a User Details", description = "Returns a Posted user details from the DB!")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = DataTransObjects.class)))
//    })
    ResponseEntity<String> postUser(@Valid @RequestBody DataTransObjects dataTransObjects){
        this.userServiceAbst.postUser(dataTransObjects);
        log.info("POST_REQUEST: user is created through post mapping!");
        return  new ResponseEntity<>("User is created successfully/registered in the DB ", HttpStatus.CREATED);
    }

    // **** Working ****
    @PostMapping(path = "/postMultipleUsers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<DataTransObjects>> postAllUsers(@Valid @RequestBody List<DataTransObjects> dataTransObjectsList){
        this.userServiceAbst.postAllUsers(dataTransObjectsList);
        log.info("POST_REQUEST: Multiple users are created through post mapping!");
        return new ResponseEntity<>(dataTransObjectsList,HttpStatus.CREATED);
    }

    // **** Working ****
    @GetMapping(path = "/{user_id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<DataTransObjects> getSelectiveUser(@PathVariable int user_id) throws Exception {
        log.info("GET_REQUEST: Selected user is retrieved through get mapping!");
        return new ResponseEntity<>(this.userServiceAbst.getSelectiveUser(user_id), HttpStatus.OK);
    }

    // **** Working ****
    @GetMapping(path = "/getAllUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsers() throws SqlException {
        this.userServiceAbst.getAllUsers();
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping!");
        return new ResponseEntity<>(this.userServiceAbst.getAllUsers(), HttpStatus.OK);
    }

    // **** Working ****
    @PutMapping(path = "/updateSelectiveUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<String> updateUser(@Valid @RequestBody DataTransObjects dataTransObjects){
        this.userServiceAbst.updateUser(dataTransObjects);
        log.info("PUT_REQUEST: Successfully retrieved all users from db through get mapping!");
        return new ResponseEntity<>("User is updated with new details and stored in the db",HttpStatus.OK);
    }

    // **** Working ****
    @DeleteMapping("/{user_id}")
    ResponseEntity<String> deleteUser(@PathVariable int user_id) throws UserException {
        this.userServiceAbst.deleteUser(user_id);
        log.info("DELETE_REQUEST: Successfully deleted the USER_ID:{} from DB", user_id);
        return new ResponseEntity<>("User is successfully deleted from the db!", HttpStatus.OK);
    }

    // ---- Pagination & Sorting Starts Here ----- //
    // **** Working ****
    //http://localhost:8080/tokenValidation/getAllUsersWithSort/firstName
    @GetMapping(path = "/getAllUsersWithSort/{fieldName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsersWithSort(@PathVariable String fieldName) throws SqlException {
        this.userServiceAbst.getAllUsersWithSort(fieldName);
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping and showing up in the sorting order (ASC) based on the field = {}", fieldName);
        return new ResponseEntity<>(this.userServiceAbst.getAllUsersWithSort(fieldName), HttpStatus.OK);
    }

    @GetMapping(path = "/getAllUsersWithPagination", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    //http://localhost:8080/tokenValidation/getAllUsersWithPagination?offSet=5&pageNumber=1
    ResponseEntity<List<DataTransObjects>> getAllUsersWithPage(@RequestParam Integer offSet, @RequestParam Integer pageNumber) throws SqlException {
        this.userServiceAbst.getAllUsersWithPagination(offSet, pageNumber);
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping with pagination!");
        return new ResponseEntity<>(this.userServiceAbst.getAllUsersWithPagination(offSet, pageNumber), HttpStatus.OK);
    }

    //http://localhost:8080/tokenValidation/getAllUsersWithSortAndPagination/lastName?offSet=1&pageNumber=2
    @GetMapping(path = "/getAllUsersWithSortAndPagination/{fieldName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<DataTransObjects>> getAllUsersWithSortAndPage(@PathVariable String fieldName, @RequestParam Integer offSet, @RequestParam Integer pageNumber) throws SqlException {
        this.userServiceAbst.getAllUsersWithPaginationAndSort(fieldName, offSet, pageNumber);
        log.info("GET_REQUEST: Successfully retrieved all users from db through get mapping and included the sorting and pagination mechanism");
        return new ResponseEntity<>(this.userServiceAbst.getAllUsersWithPaginationAndSort(fieldName, offSet, pageNumber), HttpStatus.OK);
    }

    // ----- Spring Security Authentication  -----
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    // **** working *****
    //http://localhost:8080/tokenValidation/user/userProfile?continue
    @ResponseBody
    public String userProfile() {
        try {
            log.info("User is matched and returned ");
            return "Welcome to User Profile";
        }
        catch (Exception exception) {
            log.info(exception.getMessage());
            throw new RuntimeException("Failed to authenticate");
        }

    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    // **** working *****
    // http://localhost:8080/tokenValidation/admin/adminProfile?continue
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping(value = "/addNewUser")
    // **** working *****
    // http://localhost:8080/tokenValidation/addNewUser
    ResponseEntity<String> addUserDetails(@Valid @RequestBody UserInfo userInfo) {
        return new ResponseEntity<>(this.userServiceAbst.addUser(userInfo), HttpStatus.CREATED);
    }

    @GetMapping("/welcome")
    @ResponseBody
    //http://localhost:8080/tokenValidation/welcome
    // **** working ****
    public String welcomePage()  {
        return "Welcome to this Page and this is not secured!!!";
    }

    //---- JWT Authentication Starts Here ----
    @PostMapping("/generateToken")
    public TokenGenerator authenticateAndGetToken(@RequestBody AuthTokenCred authTokenCred) {
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authTokenCred.getUserName(), authTokenCred.getPassWord()));
       if (authentication.isAuthenticated()) {
           TokenGenerator tokenGenerator = TokenGenerator.builder()
                   .bearerToken(jwtServiceImpl.generateToken(authTokenCred.getUserName()))
                   .build();
           return tokenGenerator;
           //return jwtServiceImpl.generateToken(authTokenCred.getUserName());
       }
       else {
           log.info("User is not in the table");
           throw new RuntimeException("Failed to authenticate");
       }


    }


}




