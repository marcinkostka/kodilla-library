package com.crud.kodillalibrary.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.mapper.BookCopyMapper;
import com.crud.kodillalibrary.mapper.BookMapper;
import com.crud.kodillalibrary.mapper.ReaderMapper;
import com.crud.kodillalibrary.mapper.RentalMapper;
import com.crud.kodillalibrary.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private BookCopyMapper bookCopyMapper;

    @MockBean
    private RentalMapper rentalMapper;

    @Test
    public void shouldGetReaders() throws Exception {
        //Given
        List<ReaderDto> readerDtoList = new ArrayList<>();
        readerDtoList.add(new ReaderDto(1L, "firstName 1", "lastName 1"));
        readerDtoList.add(new ReaderDto(2L, "firstName 2", "lastName 2"));

        when(readerMapper.mapToReaderDtoList(anyList())).thenReturn(readerDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/getReaders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("firstName 2")))
                .andExpect(jsonPath("$[1].lastName",is("lastName 2")));
    }

    @Test
    public void shouldAddReader() throws Exception {
        //Given
        ReaderDto readerDto = new ReaderDto(1L, "firstName", "lastName");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(readerDto);

        //When & Then
        mockMvc.perform(post("/v1/library/addReader")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetBooks() throws Exception {
        //Given
        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(new BookDto(1L, "Title 1", "Author 1", 2018));
        bookDtoList.add(new BookDto(2L, "Title 2", "Author 2", 2010));
        bookDtoList.add(new BookDto(3L, "Title 3", "Author 3", 2008));

        when(bookMapper.mapToBookDtoList(anyList())).thenReturn(bookDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/getBooks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].title", is("Title 3")))
                .andExpect(jsonPath("$[2].author", is("Author 3")))
                .andExpect(jsonPath("$[2].publicationYear",is(2008)));
    }

    @Test
    public void shouldAddBook() throws Exception {
        //Given
        BookDto bookDto = new BookDto(1L, "Title", "Author", 2018);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookDto);

        //When & Then
        mockMvc.perform(post("/v1/library/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetBookCopies() throws Exception {
        //Given
        List<BookCopyDto> bookCopyDtoList = new ArrayList<>();
        bookCopyDtoList.add(new BookCopyDto(1L, 1L, BookStatuses.AVAILABLE));
        bookCopyDtoList.add(new BookCopyDto(2L, 1L, BookStatuses.DAMAGED));
        bookCopyDtoList.add(new BookCopyDto(3L, 2L, BookStatuses.LOST));

        when(bookCopyMapper.mapToBookCopyDtoList(anyList())).thenReturn(bookCopyDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/getBookCopies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].bookId", is(2)))
                .andExpect(jsonPath("$[2].status",is("LOST")));
    }

    @Test
    public void shouldAddBookCopy() throws Exception {
        //Given
        BookCopyDto bookCopyDto = new BookCopyDto(1L, 1L, BookStatuses.AVAILABLE);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookCopyDto);

        //When & Then
        mockMvc.perform(post("/v1/library/addBookCopy")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateBookCopyStatus() throws Exception {
        //Given
        BookCopyDto bookCopyDto = new BookCopyDto(1L, 1L, BookStatuses.AVAILABLE);
        BookCopyDto bookCopyDtoUpdate = new BookCopyDto(1L, 1L, BookStatuses.NOT_AVAILABLE);

        when(bookCopyMapper.mapToBookCopyDto(dbService.updateStatus(bookCopyDto))).thenReturn(bookCopyDtoUpdate);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookCopyDto);

        //When & Then
        mockMvc.perform(put("/v1/library/updateBookCopyStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.status",is("NOT_AVAILABLE")));
    }

    @Test
    public void shouldGetCopiesCountByTitle() throws Exception {
        //Given

        when(dbService.getCountOfBookCopiesByBookTitleAndAvailable(anyString())).thenReturn(2);

        //When & Then
        mockMvc.perform(get("/v1/library/getCopiesCountByTitleAvailable")
                .param("title","anyString")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2)));
    }

    @Test
    public void shouldGetRentals() throws Exception {
        //Given
        List<RentalDto> rentalDtoList = new ArrayList<>();
        rentalDtoList.add(new RentalDto(1L, 1L, 1L, LocalDate.of(2018,5,11), null));
        rentalDtoList.add(new RentalDto(2L, 2L, 3L, LocalDate.of(2018,4,11), LocalDate.of(2018,5,11)));

        when(rentalMapper.mapToRentalDtoList(anyList())).thenReturn(rentalDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/getRentals")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].readerId", is(1)))
                .andExpect(jsonPath("$[0].bookCopyId", is(1)))
                .andExpect(jsonPath("$[0].rentDate", hasSize(3)))
                .andExpect(jsonPath("$[0].rentDate[0]", is(2018)))
                .andExpect(jsonPath("$[0].rentDate[1]", is(5)))
                .andExpect(jsonPath("$[0].rentDate[2]", is(11)))
                .andExpect(jsonPath("$[0].returnDate",isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].returnDate",hasSize(3)));
    }
    
    @Test
    public void shouldAddRental() throws Exception {
        //Given
        RentalDto rentalDto = new RentalDto(1L, 1L, 1L, LocalDate.now(), null);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentalDto);

        //When & Then
        mockMvc.perform(post("/v1/library/rentBookCopy")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateRental() throws Exception {
        //Given
        RentalDto rentalDto = new RentalDto(1L, 1L, 1L, LocalDate.of(2018,05,10), null);
        RentalDto rentalDtoUpdate = new RentalDto(1L, 1L, 1L, LocalDate.of(2018,05,10), LocalDate.of(2018,5,11));

        Book book = new Book(1L,"Title","Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, book);
        Reader reader = new Reader(1L, "firstName", "lastName");
        Rental rental = new Rental(1L, reader, bookCopy);

        when(dbService.updateRental(ArgumentMatchers.any())).thenReturn(rental);
        when(rentalMapper.mapToRentalDto(dbService.updateRental(rentalDto))).thenReturn(rentalDtoUpdate);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentalDto);

        //When & Then
        mockMvc.perform(put("/v1/library/returnBookCopy")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.readerId", is(1)))
                .andExpect(jsonPath("$.bookCopyId", is(1)))
                .andExpect(jsonPath("$.rentDate", hasSize(3)))
                .andExpect(jsonPath("$.returnDate[0]", is(2018)))
                .andExpect(jsonPath("$.returnDate[1]", is(5)))
                .andExpect(jsonPath("$.returnDate[2]", is(11)));
    }
}