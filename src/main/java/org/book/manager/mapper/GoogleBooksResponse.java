package org.book.manager.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.book.manager.domain.BookResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleBooksResponse(
        String kind,
        String totalItems,
        List<Item> items
) {
    private static final String ISBN_13 = "ISBN_13";

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
            String id,
            VolumeInfo volumeInfo
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VolumeInfo(
            String title,
            String subtitle,
            List<String> authors,
            String publisher,
            String publishedDate,
            String description,
            List<IndustryIdentifiers> industryIdentifiers,
            int pageCount,
            List<String> categories,
            double averageRating,
            ImageLinks imageLinks,
            String language,
            String previewLink
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record IndustryIdentifiers(
            String type,
            String identifier
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ImageLinks(
            String thumbnail
    ) {
    }

    public static BookResponse from(Item item) {
        Optional<String> id = Optional.empty();
        if (item.volumeInfo.industryIdentifiers != null && !item.volumeInfo.industryIdentifiers.isEmpty()) {
            id = item.volumeInfo.industryIdentifiers.stream()
                    .filter(i -> i.type.equals(ISBN_13))
                    .map(IndustryIdentifiers::identifier)
                    .findFirst();
        }

        return new BookResponse(
                id.orElse(item.id),
                item.volumeInfo.title,
                item.volumeInfo.subtitle,
                item.volumeInfo.publisher,
                item.volumeInfo.publishedDate,
                item.volumeInfo.description,
                item.volumeInfo.pageCount,
                item.volumeInfo.imageLinks != null ? item.volumeInfo.imageLinks.thumbnail : null,
                item.volumeInfo.language,
                item.volumeInfo.previewLink,
                item.volumeInfo.averageRating,
                item.volumeInfo.authors != null ? Set.copyOf(item.volumeInfo.authors) : new HashSet<>(),
                item.volumeInfo.categories != null ? Set.copyOf(item.volumeInfo.categories) : new HashSet<>()
        );
    }
}
