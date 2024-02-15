package com.github.wojciechk92.carrental.client.dto;

import com.github.wojciechk92.carrental.client.Client;
import com.github.wojciechk92.carrental.client.ClientStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class ClientWriteModel {
  @NotBlank
  @Length(min = 3, max = 30)
  private String firstName;
  @NotBlank
  @Length(min = 3, max = 30)
  private String lastName;
  @NotBlank
  @Email
  @Length(max = 50)
  private String email;
  @Min(500_000_000)
  @Max(999_999_999)
  private int tel;
  @NotNull
  private ClientStatus status;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getTel() {
    return tel;
  }

  public void setTel(int tel) {
    this.tel = tel;
  }

  public ClientStatus getStatus() {
    return status;
  }

  public void setStatus(ClientStatus status) {
    this.status = status;
  }

  public Client toClient() {
    return new Client(firstName, lastName,email, tel, status);
  }
}
