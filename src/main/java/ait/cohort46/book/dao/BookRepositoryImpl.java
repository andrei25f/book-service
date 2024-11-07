package ait.cohort46.book.dao;

import ait.cohort46.book.model.Book;
import ait.cohort46.book.model.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Stream<Book> findByAuthorsNameIgnoreCase(String name) {
        return em.createQuery("select b from Book b join b.authors a where a.name=?1", Book.class)
                .setParameter(1, name)
                .getResultStream();
    }

    @Override
    public Stream<Book> findByPublisherPublisherName(String publisherName) {
        return em.createQuery("select p from Book b join b.publisher p where p.publisherName=?1", Book.class)
                .setParameter(1, publisherName)
                .getResultStream();
    }

    @Override
    public void deleteByAuthorsNameIgnoreCase(String name) {
        Stream<Book> books = findByAuthorsNameIgnoreCase(name);
        books.forEach(em::remove);
    }

    @Override
    public boolean existsById(String isbn) {
        return em.find(Book.class, isbn) != null;
    }

//    @Transactional
    @Override
    public Book save(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Optional<Book> findById(String isbn) {
        return Optional.ofNullable(em.find(Book.class, isbn));
    }

    @Override
    public void deleteById(String isbn) {
        em.remove(em.find(Book.class, isbn));
    }
}
