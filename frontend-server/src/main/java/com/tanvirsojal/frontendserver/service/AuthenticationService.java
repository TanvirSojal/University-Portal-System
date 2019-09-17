package com.tanvirsojal.frontendserver.service;

import com.tanvirsojal.frontendserver.model.Credential;
import com.tanvirsojal.frontendserver.model.LoginToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    @Value("${auth-url}/auth")
    private String authUrl;
    private RestTemplate restTemplate;

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LoginToken authenticate(String username, String password){

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("username", username);
        multiValueMap.add("password", password);

        LoginToken loginToken = restTemplate.postForObject(authUrl, multiValueMap, LoginToken.class);
        return loginToken;
    }

    public LoginToken register(Credential credential){
        LoginToken loginToken = restTemplate.postForObject(authUrl + "/register", credential, LoginToken.class);
        return loginToken;
    }

    public LoginToken updateCredentials(Credential credential){
        HttpEntity<Credential> requestUpdate = new HttpEntity<>(credential);
        restTemplate.exchange(authUrl + "/" + credential.getUsername(), HttpMethod.PUT, requestUpdate, Void.class);
        return authenticate(credential.getUsername(), credential.getPassword());
    }

}
