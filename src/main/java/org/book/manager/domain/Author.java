package org.book.manager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "author")
public class Author {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authors", targetEntity = Book.class)
    @Column(name = "books")
    private Set<Book> booksList;

    @Column(name = "rating")
    private double rating;

    public Author(String name, double rating) {
        this.name = name;
        DecimalFormat df = new DecimalFormat("#.##");
        rating = Double.isNaN(rating) ? 0.0 : rating;
        this.rating = this.rating == 0.0 && rating != 0.0
                ? rating
                : Double.parseDouble(df.format(this.rating + rating / 2));
    }
}
