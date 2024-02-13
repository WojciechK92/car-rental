package com.github.wojciechk92.carrental.rental.dto;

import com.github.wojciechk92.carrental.rental.Rental;

import java.time.LocalDateTime;

public class RentalReadModel {
  private Long id;
  private LocalDateTime rentalDate;
  private LocalDateTime returnDate;

  public RentalReadModel(Rental rental) {
    this.id = rental.getId();
    this.rentalDate = rental.getRentalDate();
    this.returnDate = rental.getReturnDate();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getRentalDate() {
    return rentalDate;
  }

  public void setRentalDate(LocalDateTime rentalDate) {
    this.rentalDate = rentalDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }
}
