package com.crud.kodillalibrary.exceptions;

public class BookCopyNotFoundException extends Exception{
    public static String ERR_NOT_FOUND = "BookCopy doesn't exist in database";

    public BookCopyNotFoundException(String message) {
        super(message);
    }
}
