package ait.cohort46.book.service;

import ait.cohort46.book.dao.AuthorRepository;
import ait.cohort46.book.dao.BookRepository;
import ait.cohort46.book.dao.PublisherRepository;
import ait.cohort46.book.dto.AuthorDto;
import ait.cohort46.book.dto.BookDto;
import ait.cohort46.book.dto.exception.EntityNotFoundException;
import ait.cohort46.book.model.Author;
import ait.cohort46.book.model.Book;
import ait.cohort46.book.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Boolean addBook(BookDto bookDto) {
        if (bookRepository.existsById(bookDto.getIsbn())) {
            return false;
        }
        // publisher
        Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
                .orElseGet(() -> publisherRepository.save(new Publisher(bookDto.getPublisher())));
        // authors
        Set<Author> authors = bookDto.getAuthors().stream()
                .map(a -> authorRepository.findById(a.getName())
                        .orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
                .collect(Collectors.toSet());
        Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
        bookRepository.save(book);
        return true;
    }

    @Override
    public BookDto findBookByIsbn(String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto remove(String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
        bookRepository.deleteById(isbn);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto updateBookTitle(String isbn, String title) {
        Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
        book.setTitle(title);
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public Iterable<BookDto> findBooksByAuthor(String authorName) {
        Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
        return author.getBooks().stream()
                .map(b -> modelMapper.map(b, BookDto.class))
                .toList();
    }

    @Override
    public Iterable<BookDto> findBooksByPublisher(String publisherName) {
        Publisher publisher = publisherRepository.findById(publisherName).orElseThrow(EntityNotFoundException::new);
        return publisher.getBooks().stream()
                .map(b -> modelMapper.map(b, BookDto.class))
                .toList();
    }

    @Override
    public Iterable<AuthorDto> findBookAuthors(String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
        return book.getAuthors().stream()
                .map(a -> modelMapper.map(a, AuthorDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<String> findPublishersByAuthor(String authorName) {
        return publisherRepository.findDistinctByBooksAuthorsName(authorName)
                .map(Publisher::getPublisherName)
                .toList();
    }

    @Transactional
    @Override
    public AuthorDto removeAuthor(String authorName) {
        Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
        authorRepository.delete(author);
        return modelMapper.map(author, AuthorDto.class);
    }
}
