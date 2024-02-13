package org.book.manager.service;

import lombok.RequiredArgsConstructor;
import org.book.manager.domain.Author;
import org.book.manager.domain.Book;
import org.book.manager.domain.Category;
import org.book.manager.repository.AuthorRepository;
import org.book.manager.repository.BookRepository;
import org.book.manager.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DbService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public long getNumberOfBooks() {
        return bookRepository.count();
    }

    public Optional<Book> getBook(final String id) {
        return bookRepository.findById(id);
    }

    public Set<Book> searchBookContaining(final String str) {
        return getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(str.toLowerCase())
                        || b.getSubtitle() != null && b.getSubtitle().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toSet());
    }

    public Set<Book> getAllBooksByCategory(final String category) {
        return categoryRepository.findByNameIgnoreCase(category).isPresent() ?
                categoryRepository.findByNameIgnoreCase(category).get().getBooksList() : new HashSet<>();
    }

    public Set<Book> getAllBooksByAuthor(final String author) {
        return authorRepository.findByNameIgnoreCase(author).isPresent() ?
                authorRepository.findByNameIgnoreCase(author).get().getBooksList() : new HashSet<>();
    }

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public long saveBooks(final Set<Book> books) {
        return bookRepository.saveAll(books).size();
    }

    public void deleteBook(final String isbn) {
        bookRepository.deleteById(isbn);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthor(String name) {
        return authorRepository.findById(name);
    }

    public Set<Author> searchAuthorContaining(final String str) {
        return getAllAuthors().stream()
                .filter(a -> a.getName().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toSet());
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }
}
