package com.github.wojciechk92.carrental.rental;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime rentalDate;
  private LocalDateTime returnDate;

  public Rental() {}

  public Rental(LocalDateTime rentalDate, LocalDateTime returnDate) {
    this.rentalDate = rentalDate;
    this.returnDate = returnDate;
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getRentalDate() {
    return rentalDate;
  }

  void setRentalDate(LocalDateTime orderDate) {
    this.rentalDate = orderDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }
}
