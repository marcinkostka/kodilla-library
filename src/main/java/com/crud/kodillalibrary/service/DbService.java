package com.crud.kodillalibrary.service;

import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.exceptions.BookCopyAvailableStatusNotFoundException;
import com.crud.kodillalibrary.exceptions.BookCopyNotFoundException;
import com.crud.kodillalibrary.exceptions.RentalNotFoundException;
import com.crud.kodillalibrary.mapper.RentalMapper;
import com.crud.kodillalibrary.repository.BookCopyRepository;
import com.crud.kodillalibrary.repository.BookRepository;
import com.crud.kodillalibrary.repository.ReaderRepository;
import com.crud.kodillalibrary.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DbService {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalMapper rentalMapper;

    public Reader saveReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public BookCopy saveBookCopy(BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public Optional<BookCopy> findBookCopyById(Long id) {
        return bookCopyRepository.findById(id);
    }

    public Optional<BookCopy> findBookCopyByIdAndAvailableStatus(Long id, BookStatuses statuses) {
        return bookCopyRepository.findByIdAndStatus(id, statuses);
    }

    public BookCopy updateStatus(BookCopyDto bookCopyDto) throws BookCopyNotFoundException{
        BookCopy bookCopy = findBookCopyById(bookCopyDto.getId())
                .orElseThrow(() -> new BookCopyNotFoundException(BookCopyNotFoundException.ERR_NOT_FOUND));
        bookCopy.setStatus(bookCopyDto.getStatus());
        return bookCopyRepository.save(bookCopy);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Integer getCountOfBookCopiesByBookTitleAndAvailable(String title) {
        return bookCopyRepository.getCountOfBookCopiesByBookTitleAndAvailable(title);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void addRental(RentalDto rentalDto) throws BookCopyAvailableStatusNotFoundException {
        BookCopy bookCopy = findBookCopyByIdAndAvailableStatus(rentalDto.getBookCopyId(), BookStatuses.AVAILABLE)
                .orElseThrow(() -> new BookCopyAvailableStatusNotFoundException(BookCopyAvailableStatusNotFoundException.ERR_NOT_FOUND));
        bookCopy.setStatus(BookStatuses.NOT_AVAILABLE);
        saveBookCopy(bookCopy);

        saveRental(rentalMapper.mapToRental(rentalDto));
    }

    public Rental updateRental (RentalDto rentalDto) throws BookCopyNotFoundException, RentalNotFoundException {
        BookCopy bookCopy = findBookCopyById(rentalDto.getBookCopyId())
                .orElseThrow(() -> new BookCopyNotFoundException(BookCopyNotFoundException.ERR_NOT_FOUND));
        bookCopy.setStatus(BookStatuses.AVAILABLE);
        saveBookCopy(bookCopy);

        Rental rental = findRentalById(rentalDto.getId())
                .orElseThrow(() -> new RentalNotFoundException(RentalNotFoundException.ERR_NOT_FOUND));
        rental.resetReturnDate();
        return rentalRepository.save(rental);
    }

    public Optional<Rental> findRentalById(Long id) {
        return rentalRepository.findById(id);
    }
}