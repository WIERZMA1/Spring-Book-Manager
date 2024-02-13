package org.book.manager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.book.manager.domain.BookResponse;
import org.book.manager.mapper.GoogleBooksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksService {

    @Value("${google.books.api.url}")
    private String googleBooksApiUrl;

    private final RestTemplate restTemplate;
    private static final int MAX_RESULTS = 40;

    public Optional<Set<BookResponse>> getBooksFromGoogleApi(String query, String maxResults) {
        Set<BookResponse> books = new HashSet<>();
        int maxResultsSet = Integer.parseInt(maxResults);
        int startIndex = 0;
        Map<String, String> uriVariables = new HashMap<>();
        do {
            uriVariables.put("query", query);
            uriVariables.put("maxResults", String.valueOf(maxResultsSet - startIndex > 40
                    ? MAX_RESULTS
                    : maxResultsSet - startIndex));
            uriVariables.put("startIndex", String.valueOf(startIndex));
            startIndex += MAX_RESULTS;

            try {
                GoogleBooksResponse response = restTemplate.getForObject(googleBooksApiUrl,
                        GoogleBooksResponse.class, uriVariables);
                if (response != null && response.items() != null) {
                    books.addAll(response.items().stream().map(GoogleBooksResponse::from).collect(Collectors.toSet()));
                } else {
                    break;
                }
            } catch (HttpClientErrorException exception) {
                log.error(exception.getLocalizedMessage());
            }
        } while (startIndex < maxResultsSet);
        return Optional.of(books);
    }
}
