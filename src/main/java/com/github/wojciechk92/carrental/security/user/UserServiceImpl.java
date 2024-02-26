package com.github.wojciechk92.carrental.security.user;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

  private final RestTemplate restTemplate;

  public UserServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public UserTokenResponse login(User user) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("username", user.getUsername());
    body.add("password", user.getPassword());
    body.add("grant_type", "password");
    body.add("client_id", "car-rental-client");

    HttpEntity<Object> request = new HttpEntity<>(body, headers);

    ResponseEntity<UserTokenResponse> loginData = restTemplate.exchange(
            "http://localhost:9000/realms/CarRental/protocol/openid-connect/token",
            HttpMethod.POST,
            request,
            UserTokenResponse.class
    );

    return loginData.getBody();
  }
}
