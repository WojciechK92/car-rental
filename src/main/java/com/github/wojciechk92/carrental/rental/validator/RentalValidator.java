package com.github.wojciechk92.carrental.rental.validator;

import com.github.wojciechk92.carrental.rental.Rental;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;

public interface RentalValidator {

  Rental validateRentalWriteModel(RentalWriteModel rental);

}
