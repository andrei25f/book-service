package ait.cohort46.book.dao;

import ait.cohort46.book.model.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findById(String name);

    Author save(Author author);

    void delete(Author author);
}
