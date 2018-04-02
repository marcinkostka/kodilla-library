package com.crud.kodillalibrary.repository;

import com.crud.kodillalibrary.domain.Book;
import com.crud.kodillalibrary.domain.BookCopy;
import com.crud.kodillalibrary.domain.Rental;
import com.crud.kodillalibrary.domain.Reader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryRepositoryTestSuite {

    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void testLibraryRepositorySave() {

        //Given
        BookCopy bookCopy = new BookCopy("available");
        BookCopy bookCopy2 = new BookCopy("available");
        BookCopy bookCopy3 = new BookCopy("not available");
        BookCopy bookCopy4 = new BookCopy("not available");

        Book book = new Book("Kodilla Course 1", "Jan Kowalski", 2017);
        Book book2 = new Book("Kodilla Course 2","Jak Nowak", 2018);

        book.getBookCopyList().add(bookCopy);
        book2.getBookCopyList().add(bookCopy2);
        book.getBookCopyList().add(bookCopy3);
        book2.getBookCopyList().add(bookCopy4);

        Rental rental = new Rental(LocalDate.of(2017,11,30), null);
        Rental rental2 = new Rental(LocalDate.of(2018,02,25), LocalDate.of(2018,03,31));
        Rental rental3 = new Rental(LocalDate.of(2018,03,15), null);
        Rental rental4 = new Rental(LocalDate.of(2018,01,15), LocalDate.of(2018,02,11));

        Reader reader = new Reader("Marcin", "Kostka");
        Reader reader2 = new Reader("Andrzej", "Kostka");

        reader.getRentalList().add(rental);
        reader.getRentalList().add(rental4);
        reader2.getRentalList().add(rental2);
        reader2.getRentalList().add(rental3);

        bookCopy.getRentalList().add(rental);
        bookCopy2.getRentalList().add(rental2);
        bookCopy3.getRentalList().add(rental3);
        bookCopy4.getRentalList().add(rental4);

        bookCopy.setBook(book);
        bookCopy2.setBook(book2);
        bookCopy3.setBook(book);
        bookCopy4.setBook(book2);

        rental.setBookCopy(bookCopy);
        rental2.setBookCopy(bookCopy2);
        rental3.setBookCopy(bookCopy3);
        rental4.setBookCopy(bookCopy4);

        rental.setReader(reader);
        rental2.setReader(reader2);
        rental3.setReader(reader2);
        rental4.setReader(reader);

        //When
        readerRepository.save(reader);
        readerRepository.save(reader2);
        bookRepository.save(book);
        bookRepository.save(book2);
        bookCopyRepository.save(bookCopy);
        bookCopyRepository.save(bookCopy2);
        bookCopyRepository.save(bookCopy3);
        bookCopyRepository.save(bookCopy4);
        rentalRepository.save(rental);
        rentalRepository.save(rental2);
        rentalRepository.save(rental3);
        rentalRepository.save(rental4);

        long readerQuantity = readerRepository.count();
        long bookCopyQuantity = bookCopyRepository.count();
        long bookQuantity = bookRepository.count();
        long rentalQuantity = rentalRepository.count();

        //Then
        Assert.assertEquals(2,readerQuantity);
        Assert.assertEquals(4,bookCopyQuantity);
        Assert.assertEquals(2,bookQuantity);
        Assert.assertEquals(4,rentalQuantity);

        //CleanUp
        try {
            rentalRepository.deleteAll();
            bookCopyRepository.deleteAll();
            bookRepository.deleteAll();
            readerRepository.deleteAll();

        } catch (Exception e) {

        }
    }
}