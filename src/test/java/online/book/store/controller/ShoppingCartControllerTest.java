package online.book.store.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Set;
import online.book.store.dto.CartItemResponseDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.model.Book;
import online.book.store.model.Role;
import online.book.store.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    private static Book book;
    private static User user;
    private static CartItemResponseDto cartItemResponseDto;
    private static ShoppingCartResponseDto shoppingCartResponseDto;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        Role role = new Role()
                .setId(1L)
                .setName(Role.RoleName.ROLE_USER);
        user = new User()
                .setId(2L)
                .setEmail("test1@mail.com")
                .setPassword("$2a$10$gsoBhDrXnlZh9yjSftiht.5StHggDe7TR5a3rYkQyxzYJ1kzHHE/W")
                .setRoles(Set.of(role));
        book = new Book()
                .setId(1L)
                .setTitle("Test title")
                .setAuthor("Test author")
                .setIsbn("012345678901")
                .setPrice(BigDecimal.valueOf(199.99));
        cartItemResponseDto = new CartItemResponseDto()
                .setId(1L)
                .setBookId(book.getId())
                .setBookTitle(book.getTitle())
                .setQuantity(1);
        shoppingCartResponseDto = new ShoppingCartResponseDto()
                .setId(1L)
                .setUserId(user.getId())
                .setCartItems(Set.of(cartItemResponseDto));
    }

    @BeforeEach
    void setUp() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("Get user shopping cart from db")
    @Sql(scripts = {
            "classpath:database/books/insert-books.sql",
            "classpath:database/users/insert-users.sql",
            "classpath:database/shopping_carts/insert-shopping-carts.sql",
            "classpath:database/cart_items/insert-cart-items.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/cart_items/delete-all-cart-items.sql",
            "classpath:database/shopping_carts/delete-all-shopping-carts.sql",
            "classpath:database/users/delete-added-user.sql",
            "classpath:database/books/delete-all-books.sql"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCart_ValidUser_GetValidShoppingCartResponseDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cart")).andReturn();
        ShoppingCartResponseDto expected = shoppingCartResponseDto;
        ShoppingCartResponseDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), ShoppingCartResponseDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
