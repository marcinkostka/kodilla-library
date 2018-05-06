package com.crud.kodillalibrary.exceptions;

public class RentalNotFoundException extends Exception {
    public static String ERR_NOT_FOUND = "Rental doesn't exist in database";

    public RentalNotFoundException(String message) {
        super(message);
    }
}
