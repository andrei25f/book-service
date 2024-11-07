package ait.cohort46.book.dao;

import ait.cohort46.book.model.Book;

import java.util.Optional;
import java.util.stream.Stream;

public interface BookRepository {
    Stream<Book> findByAuthorsNameIgnoreCase(String name);

    Stream<Book> findByPublisherPublisherName(String publisherName);

    void deleteByAuthorsNameIgnoreCase(String name);

    boolean existsById(String isbn);

    Book save(Book book);

    Optional<Book> findById(String isbn);

    void deleteById(String isbn);
}
