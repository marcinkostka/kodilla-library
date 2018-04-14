package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.Rental;
import com.crud.kodillalibrary.domain.RentalDto;
import com.crud.kodillalibrary.repository.BookCopyRepository;
import com.crud.kodillalibrary.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    ReaderRepository readerRepository;

    public Rental mapToRental(RentalDto rentalDto) {
        return new Rental(
                rentalDto.getId(),
                readerRepository.findById(rentalDto.getReaderId()).get(),
                bookCopyRepository.findById(rentalDto.getBookCopyId()).get());
    }

    public RentalDto mapToRentalDto(Rental rental) {
        return new RentalDto(
           rental.getId(),
           rental.getReader().getId(),
           rental.getBookCopy().getId());
    }
}
