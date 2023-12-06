package online.book.store.repository;

import java.util.List;
import java.util.Optional;
import online.book.store.model.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findBookById(Long id);

    List<Book> findAll();
}
