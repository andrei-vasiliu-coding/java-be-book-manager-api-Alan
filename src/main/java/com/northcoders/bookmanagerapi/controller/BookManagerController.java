package com.northcoders.bookmanagerapi.controller;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookManagerController {

    @Autowired
    BookManagerService bookManagerService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String genre) {
        List<Book> books = bookManagerService.getAllBooks(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookManagerService.insertBook(book);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{idToGet}")
    public ResponseEntity<Book> getBookById(@PathVariable Long idToGet) {
        var bookResponse = bookManagerService.getBookById(idToGet);
        return bookResponse;
    }

    @PostMapping("/{idToUpdate}")
    public ResponseEntity<Book> updateBookById (@PathVariable Long idToUpdate, @RequestBody Book book) {
        var updateBookResponse = bookManagerService.updateBookById(idToUpdate, book);
        return updateBookResponse;
    }

    @DeleteMapping("/{idToDelete}")
    public ResponseEntity<String> deleteBookById (@PathVariable Long idToDelete) {
        var deleteBookResponse = bookManagerService.deleteBookById(idToDelete);

        return deleteBookResponse;
    }




}
