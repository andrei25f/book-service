package ait.cohort46.book.dao;

import ait.cohort46.book.model.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<String> findPublishersByAuthor(String authorName) {
        return em.createQuery("select distinct p.publisherName from Book b join b.publisher p join b.authors a where a.name=?1", String.class)
                .setParameter(1, authorName)
                .getResultList();
    }

    @Override
    public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
        return em.createQuery("select distinct p from Book b join b.publisher p join b.authors a where a.name=?1", Publisher.class)
                .setParameter(1, authorName)
                .getResultStream();
    }

    @Override
    public Optional<Publisher> findById(String publisher) {
        return Optional.ofNullable(em.find(Publisher.class, publisher));
    }

    @Override
    public Publisher save(Publisher publisher) {
        em.persist(publisher);
        return publisher;
    }
}
