package org.book.manager.controller;

import lombok.RequiredArgsConstructor;
import org.book.manager.domain.Author;
import org.book.manager.domain.AuthorResponse;
import org.book.manager.domain.BookResponse;
import org.book.manager.domain.CategoryResponse;
import org.book.manager.service.DbService;
import org.book.manager.service.GoogleBooksService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final GoogleBooksService googleBooksService;
    private final DbService service;

    @GetMapping("/googleBooks/query/{query}/maxResults/{maxResults}")
    public long saveBooks(@PathVariable String query, @PathVariable String maxResults) {
        Optional<Set<BookResponse>> books = googleBooksService.getBooksFromGoogleApi(query, maxResults);
        if (books.isPresent()) {
            return service.saveBooks(books.map(BookResponse::to).orElse(new HashSet<>()));
        }
        return 0;
    }

    @GetMapping("/books")
    public Set<BookResponse> getBooks() {
        return BookResponse.from(service.getAllBooks());
    }

    @GetMapping("/books/size")
    public long getBooksNumber() {
        return service.getNumberOfBooks();
    }

    @GetMapping("/book/{isbn}")
    public BookResponse getBook(@PathVariable String isbn) throws BookNotFoundException {
        return BookResponse.from(service.getBook(isbn).orElseThrow(BookNotFoundException::new));
    }

    @GetMapping("/book/search")
    public Set<BookResponse> searchBook(@RequestParam String str) {
        return BookResponse.from(service.searchBookContaining(str));
    }

    @GetMapping("/category/{categoryName}/books")
    public Set<BookResponse> getBooksByCategory(@PathVariable String categoryName) {
        return BookResponse.from(service.getAllBooksByCategory(categoryName));
    }

    @GetMapping("/author/{authorName}/books")
    public Set<BookResponse> getBooksByAuthor(@PathVariable String authorName) {
        return BookResponse.from(service.getAllBooksByAuthor(authorName));
    }

    @DeleteMapping("/book/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        service.deleteBook(isbn);
    }

    @PutMapping(value = "/book", consumes = APPLICATION_JSON_VALUE)
    public BookResponse updateBook(@RequestBody BookResponse bookDto) {
        return BookResponse.from(service.saveBook(BookResponse.to(bookDto)));
    }

    @PostMapping(value = "/book", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody BookResponse bookDto) {
        service.saveBook(BookResponse.to(bookDto));
    }

    @GetMapping("/authors")
    public Set<AuthorResponse> getAuthors() {
        return AuthorResponse.from(service.getAllAuthors());
    }

    @GetMapping("/author/{name}")
    public AuthorResponse getAuthor(@PathVariable String name) {
        return AuthorResponse.from(service.getAuthor(name).orElse(new Author()));
    }

    @GetMapping("/author/search")
    public Set<AuthorResponse> searchAuthor(@RequestParam String str) {
        return AuthorResponse.from(service.searchAuthorContaining(str));
    }

    @GetMapping("/categories")
    public Set<CategoryResponse> getCategories() {
        return CategoryResponse.from(service.getAllCategories());
    }

    @DeleteMapping("/books")
    public void deleteBooks() {
        service.deleteAllBooks();
    }
}
