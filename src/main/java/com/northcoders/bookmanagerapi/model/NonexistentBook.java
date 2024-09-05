package com.northcoders.bookmanagerapi.model;

public class NonexistentBook {
    public static Book get() {
        return new Book(
                -1L,
                "Book Not Found",
                "The book you requested was not found.",
                "H.G. Wells",
                Genre.Education);
    }

}
