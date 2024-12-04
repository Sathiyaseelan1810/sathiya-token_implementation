package com.spring.authImplementation.tokenSetup.service.impl;

import com.spring.authImplementation.tokenSetup.daosEntity.UserInfo;
import com.spring.authImplementation.tokenSetup.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


// Note -> Sathiya :: From Spring 3 onwards the implementation of web security override methods
// are depreciated, and we manually create the below 3 methods..

@Service
@Slf4j
public class UserInfoService implements UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */

    @Autowired
    private UserInfoRepository userInfoRepository;


    // --> Starting Point : check if the user is valid and registered in the db(userInfo) Table .....
    // Not to use the gmail recommended (throw a invalid signture error)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // Assuming 'email' is used as username
        Optional<UserInfo> userInfo = this.userInfoRepository.findByEmail(userName);
        // Converting UserInfo(entity) to UserDetails
        log.info("USER FOUND IN THE DB. {}", userInfo);
        return userInfo
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
    }


}
