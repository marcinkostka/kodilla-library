package com.crud.kodillalibrary.service;

import com.crud.kodillalibrary.domain.*;
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

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Integer getCountOfBookCopiesByBookTitleAndAvailable(String title) {
        return bookCopyRepository.getCountOfBookCopiesByBookTitleAndAvailable(title);
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Optional<Rental> findRentalById(Long id) {
        return rentalRepository.findById(id);
    }
}