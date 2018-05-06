package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.Rental;
import com.crud.kodillalibrary.domain.RentalDto;
import com.crud.kodillalibrary.repository.BookCopyRepository;
import com.crud.kodillalibrary.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    ReaderRepository readerRepository;

    public Rental mapToRental(final RentalDto rentalDto) {
        return new Rental(
                rentalDto.getId(),
                readerRepository.findById(rentalDto.getReaderId()).get(),
                bookCopyRepository.findById(rentalDto.getBookCopyId()).get());
    }

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getReader().getId(),
                rental.getBookCopy().getId(),
                rental.getRentDate(),
                rental.getReturnDate());
    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(r -> new RentalDto(r.getId(),r.getReader().getId(),r.getBookCopy().getId(),r.getRentDate(),r.getReturnDate()))
                .collect(Collectors.toList());
    }
}