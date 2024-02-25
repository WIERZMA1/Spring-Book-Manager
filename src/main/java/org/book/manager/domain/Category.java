package org.book.manager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "category")
public class Category {

    @Id
    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories", targetEntity = Book.class)
    @Column(name = "books")
    private Set<Book> booksList;
}
