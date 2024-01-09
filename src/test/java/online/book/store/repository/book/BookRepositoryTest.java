package online.book.store.repository.book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import online.book.store.model.Book;
import online.book.store.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static Category category;
    private static Book book;
    private static List<Book> books;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        category = new Category();
        category.setId(1L);
        category.setName("Test category");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setIsbn("012345678901");
        book.setPrice(BigDecimal.valueOf(199.99));
        book.setCategories(Set.of(category));

        books = List.of(book);
    }

    @Test
    @DisplayName("Find books by category with existing category")
    @Sql(scripts = {
            "classpath:database/books/insert-books.sql",
            "classpath:database/categories/insert-categories.sql",
            "classpath:database/categories/insert-books_categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-all-books-categories.sql",
            "classpath:database/categories/delete-all-categories.sql",
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategory_CategoriesWithIdThree_ReturnOneBook() {
        List<Book> expected = books;
        List<Book> actual = bookRepository.findAllByCategoryId(category.getId());
        Assertions.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }
}
