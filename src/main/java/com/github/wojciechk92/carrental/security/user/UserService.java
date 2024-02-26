package com.github.wojciechk92.carrental.security.user;

public interface UserService {

  UserTokenResponse login(User user);

}
