package org.book.manager.repository;

import org.book.manager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
