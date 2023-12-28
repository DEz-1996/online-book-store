package online.book.store.repository;

import java.util.List;
import java.util.Optional;
import online.book.store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(Long categoryId);

    @Query("SELECT b FROM Book b JOIN FETCH b.categories WHERE b.id = :id")
    Optional<Book> findById(Long id);
}
