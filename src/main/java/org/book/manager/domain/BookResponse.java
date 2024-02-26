package org.book.manager.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BookResponse(
        String id,
        String title,
        String subtitle,
        String publisher,
        String publishedDate,
        String description,
        int pageCount,
        String thumbnailUrl,
        String language,
        String previewLink,
        double rating,
        Set<String> authorsIds,
        Set<String> categoriesIds
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getSubtitle(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getDescription(),
                book.getPageCount(),
                book.getThumbnailUrl(),
                book.getLanguage(),
                book.getPreviewLink(),
                book.getRating(),
                book.getAuthors().stream()
                        .map(Author::getName)
                        .collect(Collectors.toSet()),
                book.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toSet())
        );
    }

    public static Book to(BookResponse bookDto) {
        Set<Author> authors = bookDto.authorsIds.stream()
                .map(a -> new Author(a, bookDto.rating))
                .collect(Collectors.toSet());
        Set<Category> categories = bookDto.categoriesIds.stream()
                .map(String::toLowerCase)
                .map(Category::new)
                .collect(Collectors.toSet());
        return new Book(
                bookDto.id,
                bookDto.title,
                bookDto.subtitle,
                bookDto.publisher,
                bookDto.publishedDate,
                bookDto.description,
                bookDto.pageCount,
                bookDto.thumbnailUrl,
                bookDto.language,
                bookDto.previewLink,
                bookDto.rating,
                authors,
                categories);
    }

    public static Set<BookResponse> from(Collection<Book> books) {
        return books.stream().map(BookResponse::from).collect(Collectors.toSet());
    }

    public static Set<Book> to(Collection<BookResponse> bookResponses) {
        return bookResponses.stream().map(BookResponse::to).collect(Collectors.toSet());
    }
}
