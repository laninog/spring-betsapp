package com.betsapp.usr.services;

import com.betsapp.usr.domain.User;
import com.betsapp.usr.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository respository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = respository.findByUserName(username);

        if(myUser == null) {
            logger.error("User does not exists ".concat(username));
            throw new UsernameNotFoundException("User does not exists ".concat(username));
        }

        List<SimpleGrantedAuthority> roles = myUser.getRoles().stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName());
        }).collect(Collectors.toList());

        if(myUser.getRoles().isEmpty()) {
            logger.error("User does not have any role");
            throw new UsernameNotFoundException("User does not have any role ".concat(username));
        }

        return new org.springframework.security.core.userdetails.User(
                myUser.getUserName(),
                myUser.getPassword(),
                myUser.getEnabled(),
                true,
                true,
                true,
                roles);
    }

}
