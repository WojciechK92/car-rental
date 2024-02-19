package com.github.wojciechk92.carrental.client;

import com.github.wojciechk92.carrental.client.dto.ClientReadModel;
import com.github.wojciechk92.carrental.common.personalDetails.PersonalDetails;
import com.github.wojciechk92.carrental.rental.Rental;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Embedded
  private PersonalDetails personalDetails;
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "VARCHAR(30)")
  private ClientStatus status;
  @OneToMany(mappedBy = "client")
  private Set<Rental> rentals = new HashSet<>();

  public Client() {
  }

  public Client(String firstName, String lastName, String email, int tel, ClientStatus status) {
    personalDetails = new PersonalDetails(firstName, lastName, email, tel);
    this.status = status;
  }

  public Client(ClientReadModel client) {
    this.id = client.getId();
    personalDetails = new PersonalDetails(client.getFirstName(), client.getLastName(), client.getEmail(), client.getTel());
    this.status = client.getStatus();
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public PersonalDetails getPersonalDetails() {
    return personalDetails;
  }

  void setPersonalDetails(PersonalDetails personalDetails) {
    this.personalDetails = personalDetails;
  }

  public ClientStatus getStatus() {
    return status;
  }

  void setStatus(ClientStatus status) {
    this.status = status;
  }

  public Set<Rental> getRentals() {
    return rentals;
  }

  void setRentals(Set<Rental> rentals) {
    this.rentals = rentals;
  }
}
