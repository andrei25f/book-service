package ait.cohort46.book.dao;

import ait.cohort46.book.model.Publisher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PublisherRepository {
    // @Query("select distinct p.publisherName from Book b join b.publisher p join b.authors a where a.name=?1")
    List<String> findPublishersByAuthor(String authorName);

    Stream<Publisher> findDistinctByBooksAuthorsName(String authorsName);

    Optional<Publisher> findById(String publisher);

    Publisher save(Publisher publisher);
}
