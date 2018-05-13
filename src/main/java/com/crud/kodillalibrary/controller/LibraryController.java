package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.exceptions.BookCopyAvailableStatusNotFoundException;
import com.crud.kodillalibrary.exceptions.BookCopyNotFoundException;
import com.crud.kodillalibrary.exceptions.RentalNotFoundException;
import com.crud.kodillalibrary.mapper.BookCopyMapper;
import com.crud.kodillalibrary.mapper.BookMapper;
import com.crud.kodillalibrary.mapper.ReaderMapper;
import com.crud.kodillalibrary.mapper.RentalMapper;
import com.crud.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    @Autowired
    DbService dbService;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookCopyMapper bookCopyMapper;

    @Autowired
    ReaderMapper readerMapper;

    @Autowired
    RentalMapper rentalMapper;

    @GetMapping(value = "getReaders")
    public List<ReaderDto> getReaders() {
        return readerMapper.mapToReaderDtoList(dbService.getAllReaders());
    }

    @PostMapping(value = "addReader", consumes = APPLICATION_JSON_VALUE)
    public void addReader(@RequestBody ReaderDto readerDto) {
        dbService.saveReader(readerMapper.mapToReader(readerDto));
    }

    @GetMapping(value = "getBooks")
    public List<BookDto> getBooks() {
        return bookMapper.mapToBookDtoList(dbService.getAllBooks());
    }

    @PostMapping(value = "addBook", consumes = APPLICATION_JSON_VALUE)
    public void addBook(@RequestBody BookDto bookDto) {
        dbService.saveBook(bookMapper.mapToBook(bookDto));
    }

    @GetMapping(value = "getBookCopies")
    public List<BookCopyDto> getBookCopies() {
        return bookCopyMapper.mapToBookCopyDtoList(dbService.getAllBookCopies());
    }

    @PostMapping(value = "addBookCopy", consumes = APPLICATION_JSON_VALUE)
    public void addBookCopy(@RequestBody BookCopyDto bookCopyDto) {
        dbService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto));
    }

    @PutMapping(value = "updateBookCopyStatus", consumes = APPLICATION_JSON_VALUE)
    public BookCopyDto updateBookCopyStatus(@RequestBody BookCopyDto bookCopyDto) throws BookCopyNotFoundException {
        return bookCopyMapper.mapToBookCopyDto(dbService.updateStatus(bookCopyDto));
    }

    @GetMapping(value = "getCopiesCountByTitleAvailable")
    public Integer getCopiesCountByTitle(@RequestParam String title) {
        return dbService.getCountOfBookCopiesByBookTitleAndAvailable(title);
    }

    @GetMapping(value = "getRentals")
    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(dbService.getAllRentals());
    }

    @PostMapping (value = "rentBookCopy", consumes = APPLICATION_JSON_VALUE)
    public void addRental(@RequestBody RentalDto rentalDto) throws BookCopyAvailableStatusNotFoundException{
        dbService.addRental(rentalDto);
    }

    @PutMapping(value = "returnBookCopy", consumes = APPLICATION_JSON_VALUE)
    public RentalDto updateRental(@RequestBody RentalDto rentalDto) throws BookCopyNotFoundException, RentalNotFoundException {
        return rentalMapper.mapToRentalDto(dbService.updateRental(rentalDto));
    }
}