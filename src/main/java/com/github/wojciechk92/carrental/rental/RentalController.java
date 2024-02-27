package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.rental.dto.RentalReadModel;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("rentals")
@EnableMethodSecurity(jsr250Enabled = true)
public class RentalController {
  private final RentalService rentalService;

  RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @GetMapping
  @RolesAllowed("EMPLOYEE")
  public ResponseEntity<List<RentalReadModel>> getRentals(Pageable pageable) {
    return ResponseEntity.ok(rentalService.getRentals(pageable));
  }

  @GetMapping("/{id}")
  @RolesAllowed("EMPLOYEE")
  public ResponseEntity<RentalReadModel> getRental(@PathVariable Long id) {
    return ResponseEntity.ok(rentalService.getRental(id));
  }

  @PostMapping
  @RolesAllowed("EMPLOYEE")
  public ResponseEntity<RentalReadModel> createRental(@RequestBody @Valid RentalWriteModel rental) {
    RentalReadModel result = rentalService.createRental(rental);
    return ResponseEntity.created(URI.create("/rentals/" + result.getId())).body(result);
  }

  @PutMapping("/{id}")
  @RolesAllowed("EMPLOYEE")
  public ResponseEntity<Void> updateRental(@RequestBody @Valid RentalWriteModel rental, @PathVariable Long id) {
    rentalService.updateRental(rental, id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/close")
  @RolesAllowed("EMPLOYEE")
  public ResponseEntity<Void> closeRental(@PathVariable Long id) {
    rentalService.closeRental(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/cancel")
  @RolesAllowed("EMPLOYEE")
  public ResponseEntity<Void> cancelRental(@PathVariable Long id) {
    rentalService.cancelRental(id);
    return ResponseEntity.noContent().build();
  }
}
