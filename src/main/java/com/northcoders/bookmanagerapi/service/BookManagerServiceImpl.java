package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import com.northcoders.bookmanagerapi.model.NonexistentBook;
import com.northcoders.bookmanagerapi.repository.BookManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    @Autowired
    BookManagerRepository bookManagerRepository;

    @Override
    public List<Book> getAllBooks(String genre) {
        if (genre == null) {
            ArrayList<Book> books = new ArrayList<>();
            bookManagerRepository.findAll().forEach(books::add);

            return books;
        }

        return findBooksByGenre(genre).getBody();
    }

    @Override
    public Book insertBook(Book book) {
        return bookManagerRepository.save(book);
    }

    @Override
    public ResponseEntity<Book> getBookById(Long id) {
        Optional<Book> book = bookManagerRepository.findById(id);

        if (book.isEmpty()) {
            Book invalidBook = NonexistentBook.get();

            return new ResponseEntity<>(invalidBook, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Book> updateBookById(Long id, Book book) {

        if (bookManagerRepository.existsById(id)) {
            bookManagerRepository.deleteById(id);
            insertBook(book);

            return new ResponseEntity<>(book, HttpStatus.OK);
        }

        return new ResponseEntity<>(NonexistentBook.get(), HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<String> deleteBookById(Long id) {

        if (bookManagerRepository.existsById(id)){
            bookManagerRepository.deleteById(id);
            return new ResponseEntity<>("Book deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Book with that ID doesn't exist.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public ResponseEntity<ArrayList<Book>> findBooksByGenre(String genre) {
        Genre genreEnum;

        try {
            genreEnum = Enum.valueOf(Genre.class, genre);
            ArrayList<Book> booksOfGenre = new ArrayList<>();

            for (Book book: bookManagerRepository.findAll()) {
                if (book.getGenre() == genreEnum)
                    booksOfGenre.add(book);
            }

            return new ResponseEntity<>(booksOfGenre, HttpStatus.OK);

        } catch (IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(new ArrayList<>() {{add(NonexistentBook.get());}}, HttpStatus.NOT_FOUND);
        }
    }

}
