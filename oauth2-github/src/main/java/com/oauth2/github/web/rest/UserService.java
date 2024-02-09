package com.oauth2.github.web.rest;

import com.oauth2.github.domain.User;
import com.oauth2.github.repository.UserRepository;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromAuthentication(AbstractAuthenticationToken authToken){
        Map<String,Object> attributes;

        if (authToken instanceof OAuth2AuthenticationToken){
            attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
        }else {
            throw  new IllegalArgumentException("Error");
        }

        User user=getUser(attributes);
        if (!ObjectUtils.isEmpty(user)){
            userRepository.save(user);
        }

        return user;

    }

    private User getUser(Map<String, Object> attributes) {
        User user = new User();
        if (attributes.get("login") != null) {
            user.setLogin((String) attributes.get("login"));
        }
        if (attributes.get("url") != null) {
            user.setUrl((String) attributes.get("url"));
        }
        if (attributes.get("site_admin") != null) {
            user.setActivated((Boolean) attributes.get("site_admin"));
        }
        if (attributes.get("name") != null) {
            user.setFirstName((String) attributes.get("name"));
        }
        if (attributes.get("location") != null) {
            user.setLocation((String) attributes.get("location"));

        }
            return user;
        }
    }



