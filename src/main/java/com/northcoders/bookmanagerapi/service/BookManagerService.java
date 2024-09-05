package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface BookManagerService {

    List<Book> getAllBooks(String genre);
    Book insertBook(Book book);
    ResponseEntity<Book> getBookById(Long id);
    ResponseEntity<Book> updateBookById(Long id, Book book);
    ResponseEntity<String> deleteBookById(Long id);
    ResponseEntity<ArrayList<Book>> findBooksByGenre(String genre);
}
