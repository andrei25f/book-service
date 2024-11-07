package ait.cohort46.book.controller;

import ait.cohort46.book.dto.AuthorDto;
import ait.cohort46.book.dto.BookDto;
import ait.cohort46.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@PostMapping("/book")
	public Boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@GetMapping("/book/{isbn}")
	public BookDto findBookByIsbn(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
	}

	@DeleteMapping("/book/{isbn}")
	public BookDto remove(@PathVariable String isbn) {
		return bookService.remove(isbn);
	}

	@PatchMapping("/book/{isbn}/title/{title}")
	public BookDto updateBookTitle(@PathVariable String isbn, @PathVariable String title) {
		return bookService.updateBookTitle(isbn, title);
	}

	@GetMapping("/books/author/{authorName}")
	public Iterable<BookDto> findBooksByAuthor(@PathVariable String authorName) {
		return bookService.findBooksByAuthor(authorName);
	}

	@GetMapping("/books/publisher/{publisherName}")
	public Iterable<BookDto> findBooksByPublisher(@PathVariable String publisherName) {
		return bookService.findBooksByPublisher(publisherName);
	}

	@GetMapping("/authors/book/{isbn}")
	public Iterable<AuthorDto> findBookAuthors(@PathVariable String isbn) {
		return bookService.findBookAuthors(isbn);
	}
	
	@GetMapping("/publishers/author/{authorName}")
	public Iterable<String> findPublishersByAuthor(@PathVariable String authorName) {
		return bookService.findPublishersByAuthor(authorName);
	}

	@DeleteMapping("/author/{authorName}")
	public AuthorDto removeAuthor(@PathVariable String authorName) {
		return bookService.removeAuthor(authorName);
	}
}
