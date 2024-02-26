package com.github.wojciechk92.carrental.security.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class User {
  @NotBlank
  @Length(min = 3, max = 30)
  private String username;
  @NotBlank
  @Length(min = 3, max = 30)
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
