package com.crud.kodillalibrary.exceptions;

public class BookCopyAvailableStatusNotFoundException extends Exception{
    public static String ERR_NOT_FOUND = "BookCopy doesn't exist in database or is not available";

    public BookCopyAvailableStatusNotFoundException(String message) {
        super(message);
    }
}