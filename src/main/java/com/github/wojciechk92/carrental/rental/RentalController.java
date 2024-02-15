package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.rental.dto.RentalReadModel;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("rentals")
public class RentalController {
  private final RentalService rentalService;

  RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @GetMapping
  public ResponseEntity<List<RentalReadModel>> getRentals(Pageable pageable) {
    return ResponseEntity.ok(rentalService.getRentals(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RentalReadModel> getRental(@PathVariable Long id) {
    return ResponseEntity.ok(rentalService.getRental(id));
  }

  @PostMapping
  public ResponseEntity<RentalReadModel> createRental(@RequestBody @Valid RentalWriteModel rental) {
    RentalReadModel result = rentalService.createRental(rental);
    return ResponseEntity.created(URI.create("/rentals/" + result.getId())).body(result);
  }
}
