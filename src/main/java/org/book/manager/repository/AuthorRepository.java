package org.book.manager.repository;

import org.book.manager.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findByNameIgnoreCase(String name);
}
