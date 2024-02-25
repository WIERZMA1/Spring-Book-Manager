package org.book.manager.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record AuthorResponse(
        String name,
        Set<String> booksIds,
        double rating
) {
    public static AuthorResponse from(Author author) {
        return new AuthorResponse(
                author.getName(),
                author.getBooksList().stream().map(Book::getId).collect(Collectors.toSet()),
                author.getRating()
        );
    }

    public static Set<AuthorResponse> from(Collection<Author> authors) {
        return authors.stream().map(AuthorResponse::from).collect(Collectors.toSet());
    }
}
