package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.rental.dto.RentalReadModel;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

interface RentalService {

  List<RentalReadModel> getRentals(Pageable pageable);

  RentalReadModel getRental(Long id);

  RentalReadModel createRental(RentalWriteModel rental);

}
