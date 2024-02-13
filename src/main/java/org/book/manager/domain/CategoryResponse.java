package org.book.manager.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record CategoryResponse(
        String name,
        Set<String> booksIds
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getName(),
                category.getBooksList().stream().map(Book::getId).collect(Collectors.toSet())
        );
    }

    public static Set<CategoryResponse> from(Collection<Category> categories) {
        return categories.stream().map(CategoryResponse::from).collect(Collectors.toSet());
    }
}
