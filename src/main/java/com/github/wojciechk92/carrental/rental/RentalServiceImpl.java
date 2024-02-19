package com.github.wojciechk92.carrental.rental;

import com.github.wojciechk92.carrental.rental.dto.RentalReadModel;
import com.github.wojciechk92.carrental.rental.dto.RentalWriteModel;
import com.github.wojciechk92.carrental.rental.exception.RentalException;
import com.github.wojciechk92.carrental.rental.exception.RentalExceptionMessage;
import com.github.wojciechk92.carrental.rental.validator.RentalValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RentalServiceImpl implements RentalService {
  private final RentalRepository rentalRepository;
  private final RentalValidator validator;

  @Autowired
  RentalServiceImpl(RentalRepository rentalRepository, RentalValidator validator) {
    this.rentalRepository = rentalRepository;
    this.validator = validator;
  }

  @Override
  public List<RentalReadModel> getRentals(Pageable pageable) {
    return rentalRepository.findAll(pageable).getContent().stream()
            .map(RentalReadModel::new)
            .toList();
  }

  @Override
  public RentalReadModel getRental(Long id) {
    return rentalRepository.findById(id)
            .map(RentalReadModel::new)
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  @Override
  public RentalReadModel createRental(RentalWriteModel toSave) {
    Rental rental = validator.validateRentalWriteModel(toSave);
    Rental result = rentalRepository.save(rental);
    return new RentalReadModel(result);
  }

  @Transactional
  @Override
  public void updateRental(RentalWriteModel toUpdate, Long id) {
    rentalRepository.findById(id)
            .map(rentalFromDb -> {
              Rental rental = validator.validateRentalWriteModel(toUpdate);
              rental.setId(rentalFromDb.getId());
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }

  @Transactional
  @Override
  public void setStatusTo(RentalStatus status, Long id) {
    rentalRepository.findById(id)
            .map(rental -> {
              rental.setStatus(status);
              return rental;
            })
            .orElseThrow(() -> new RentalException(RentalExceptionMessage.RENTAL_NOT_FOUND));
  }
}
