package online.book.store.repository;

import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    private static User user;
    private static ShoppingCart shoppingCart;
    private static final String CANT_FIND_BOOK_BY_ID_MSG = "Can't find book by id: ";
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeAll
    static void beforeAll() {
        user = new User()
                .setId(2L)
                .setEmail("test1@mail.com")
                .setPassword("$2a$10$gsoBhDrXnlZh9yjSftiht.5StHggDe7TR5a3rYkQyxzYJ1kzHHE/W")
                .setFirstName("Test1 firstname")
                .setLastName("Test1 lastname")
                .setShippingAddress("Test1 address");

        shoppingCart = new ShoppingCart()
                .setId(1L)
                .setUser(user);
    }

    @Test
    @DisplayName("Find shopping cart by user id")
    @Sql(scripts = {
            "classpath:database/users/insert-users.sql",
            "classpath:database/shopping_carts/insert-shopping-carts.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shopping_carts/delete-all-shopping-carts.sql",
            "classpath:database/users/delete-added-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByUserId_ValidUserId_ReturnOptionalShoppingCart() {
        ShoppingCart expected = shoppingCart;
        ShoppingCart actual = shoppingCartRepository.findByUserId(2L).get();
        Assertions.assertEquals(expected, actual);
    }
}
