package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.domain.*;
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

    @RequestMapping(method = RequestMethod.GET, value = "getReaders")
    public List<ReaderDto> getReaders() {
        return readerMapper.mapToReaderDtoList(dbService.getAllReaders());
    }

    @RequestMapping(method = RequestMethod.POST, value = "addReader", consumes = APPLICATION_JSON_VALUE)
    public void addReader(@RequestBody ReaderDto readerDto) {
        dbService.saveReader(readerMapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBooks")
    public List<BookDto> getBooks() {
        return bookMapper.mapToBookDtoList(dbService.getAllBook());
    }

    @RequestMapping(method = RequestMethod.POST, value = "addBook", consumes = APPLICATION_JSON_VALUE)
    public void addBook(@RequestBody BookDto bookDto) {
        dbService.saveBook(bookMapper.mapToBook(bookDto));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBookCopies")
    public List<BookCopyDto> getBookCopies() {
        return bookCopyMapper.mapToBookCopyDtoList(dbService.getAllBookCopies());
    }

    @RequestMapping(method = RequestMethod.POST, value = "addBookCopy", consumes = APPLICATION_JSON_VALUE)
    public void addBookCopy(@RequestBody BookCopyDto bookCopyDto) {
        dbService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto));
    }

    // TODO: 2018-04-11 wyjatki dla Optional, złożone endpointy 'przenieść' do dbservice; skrocic nazwy metod @RequestMapping

    @RequestMapping(method = RequestMethod.PUT, value = "updateBookCopyStatus", consumes = APPLICATION_JSON_VALUE)
    public BookCopyDto updateBookCopyStatus(@RequestBody BookCopyDto bookCopyDto) {
        BookCopy bookCopy = dbService.findBookCopyById(bookCopyDto.getId()).get();
        bookCopy.setStatus(bookCopyDto.getStatus());
        return bookCopyMapper.mapToBookCopyDto(dbService.saveBookCopy(bookCopy));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCopiesByTitleAvailable")
    public Integer getCopiesCountByTitle(@RequestParam String title) {
        return dbService.getCountOfBookCopiesByBookTitleAndAvailable(title);
    }

    @RequestMapping(method = RequestMethod.POST, value = "rentBookCopy", consumes = APPLICATION_JSON_VALUE)
    @Transactional
    public void addRental(@RequestBody RentalDto rentalDto) {
        BookCopy bookCopy = dbService.findBookCopyById(rentalDto.getBookCopyId()).get();
        bookCopy.setStatus(BookStatuses.NOT_AVAILABLE);
        dbService.saveBookCopy(bookCopy);
        dbService.saveRental(rentalMapper.mapToRental(rentalDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "returnBookCopy", consumes = APPLICATION_JSON_VALUE)
    @Transactional
    public RentalDto updateRental(@RequestBody RentalDto rentalDto) {
        BookCopy bookCopy = dbService.findBookCopyById(rentalDto.getBookCopyId()).get();
        bookCopy.setStatus(BookStatuses.AVAILABLE);
        dbService.saveBookCopy(bookCopy);

        Rental rental = dbService.findRentalById(rentalDto.getId()).get();
        rental.resetReturnDate();
        return rentalMapper.mapToRentalDto(dbService.saveRental(rental));
    }
}