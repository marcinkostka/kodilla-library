package com.crud.kodillalibrary.repository;

import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.exceptions.BookCopyNotFoundException;
import com.crud.kodillalibrary.mapper.BookCopyMapper;
import com.crud.kodillalibrary.service.DbService;
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
    public void testSaveReader() {
        //Given
        Reader reader = new Reader("Marcin", "Kostka");

        //When
        readerRepository.save(reader);
        Reader fetchedReader = readerRepository.findById(reader.getId()).get();
        long quantity = readerRepository.count();

        //Then
        Assert.assertEquals(fetchedReader.getId(),reader.getId());
        Assert.assertEquals(1,quantity);

        //CleanUp
        readerRepository.deleteAll();
    }

    @Test
    public void testSaveBook() {
        //Given
        Book book = new Book("Title","Author", 2018);

        //When
        bookRepository.save(book);
        Book fetchedBook = bookRepository.findById(book.getId()).get();
        long quantity = bookRepository.count();

        //Then
        Assert.assertEquals(fetchedBook.getId(),book.getId());
        Assert.assertEquals(1,quantity);

        //CleanUp
        bookRepository.deleteAll();
    }

    @Test
    public void testSaveBookCopy() {
        //Given
        Book book = new Book("Title","Author", 2018);
        bookRepository.save(book);
        BookCopy bookCopy = new BookCopy();

        bookCopy.setBook(book);
        book.getBookCopyList().add(bookCopy);

        //When
        bookCopyRepository.save(bookCopy);
        BookCopy fetchedBookCopy = bookCopyRepository.findById(bookCopy.getId()).get();

        //Then
        Assert.assertEquals(fetchedBookCopy.getId(),bookCopy.getId());
        Assert.assertEquals(fetchedBookCopy.getBook().getId(), book.getId());

        //CleanUp
        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testUpdateStatusOfBookCopy() {
        //Given
        Book book = new Book("Title","Author", 2018);
        BookCopy bookCopy = new BookCopy();

        bookCopy.setBook(book);
        book.getBookCopyList().add(bookCopy);
        bookRepository.save(book);
        bookCopyRepository.save(bookCopy);

        //When
        BookCopy fetchedBookCopy = bookCopyRepository.findById(bookCopy.getId()).get();
        fetchedBookCopy.setStatus(BookStatuses.LOST);
        bookCopyRepository.save(fetchedBookCopy);

        //Then
        Assert.assertEquals(BookStatuses.LOST, bookCopyRepository.findById(bookCopy.getId()).get().getStatus());

        //CleanUp
        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testCountAvailableBookCopies() {
        //Given
        Book book = new Book("Title","Author", 2018);
        String title = book.getTitle();

        BookCopy bookCopy1 = new BookCopy();
        BookCopy bookCopy2 = new BookCopy();

        bookCopy1.setBook(book);
        bookCopy2.setBook(book);
        book.getBookCopyList().add(bookCopy1);
        book.getBookCopyList().add(bookCopy2);
        bookRepository.save(book);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);

        //When
        int count = bookCopyRepository.getCountOfBookCopiesByBookTitleAndAvailable(title);

        //Then
        Assert.assertEquals(2,count);

        //CleanUp
        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testSaveRental() {
        //Given
        Reader reader = new Reader("Marcin", "Kostka");

        Book book = new Book("Title","Author", 2018);
        BookCopy bookCopy = new BookCopy();

        bookCopy.setBook(book);
        book.getBookCopyList().add(bookCopy);
        bookRepository.save(book);

        Rental rental = new Rental();
        rental.setReader(reader);
        rental.setBookCopy(bookCopy);

        reader.getRentalList().add(rental);
        readerRepository.save(reader);

        bookCopy.getRentalList().add(rental);
        bookCopyRepository.save(bookCopy);

        //When
        rentalRepository.save(rental);
        Rental fetchedRental = rentalRepository.findById(rental.getId()).get();

        //Then
        Assert.assertEquals(fetchedRental.getId(), rental.getId());
        Assert.assertEquals(fetchedRental.getBookCopy().getId(), bookCopy.getId());
        Assert.assertEquals(fetchedRental.getBookCopy().getBook().getId(), book.getId());
        Assert.assertEquals(fetchedRental.getReader().getId(), reader.getId());

        //CleanUp
        rentalRepository.deleteAll();
        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
        readerRepository.deleteAll();
    }

    @Test
    public void testResetReturnDateRental() {
        //Given
        Reader reader = new Reader("Marcin", "Kostka");

        Book book = new Book("Title","Author", 2018);
        BookCopy bookCopy = new BookCopy();

        bookCopy.setBook(book);
        book.getBookCopyList().add(bookCopy);
        bookRepository.save(book);

        Rental rental = new Rental();
        rental.setReader(reader);
        rental.setBookCopy(bookCopy);

        reader.getRentalList().add(rental);
        readerRepository.save(reader);

        bookCopy.getRentalList().add(rental);
        bookCopyRepository.save(bookCopy);

        //When
        rental.resetReturnDate();
        rentalRepository.save(rental);

        Rental fetchedRental = rentalRepository.findById(rental.getId()).get();

        //Then
        Assert.assertEquals(fetchedRental.getReturnDate(), rental.getReturnDate());

        //CleanUp
        rentalRepository.deleteAll();
        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
        readerRepository.deleteAll();
    }

    /*@Test
    public void testLibraryRepository() {

        //Given
        BookCopy bookCopy = new BookCopy();
        BookCopy bookCopy2 = new BookCopy();
        BookCopy bookCopy3 = new BookCopy();
        BookCopy bookCopy4 = new BookCopy();

        bookCopy4.setStatus(BookStatuses.DAMAGED);

        Book book = new Book("Kodilla", "Jan Kowalski", 2017);
        Book book2 = new Book("Kodilla Course 2","Jak Nowak", 2018);

        book.getBookCopyList().add(bookCopy);
        book2.getBookCopyList().add(bookCopy2);
        book.getBookCopyList().add(bookCopy3);
        book2.getBookCopyList().add(bookCopy4);

        Rental rental = new Rental();
        Rental rental2 = new Rental();
        Rental rental3 = new Rental();
        Rental rental4 = new Rental();

        rental4.resetReturnDate();

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
            //rentalRepository.deleteAll();
            //bookCopyRepository.deleteAll();
            //bookRepository.deleteAll();
            //readerRepository.deleteAll();

        } catch (Exception e) {

        }
    }*/
}