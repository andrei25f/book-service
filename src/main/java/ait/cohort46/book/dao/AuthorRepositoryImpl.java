package ait.cohort46.book.dao;

import ait.cohort46.book.model.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Author> findById(String name) {
        return Optional.ofNullable(em.find(Author.class, name));
    }

    @Override
    public Author save(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }
}
