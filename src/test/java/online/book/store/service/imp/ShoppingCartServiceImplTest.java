package online.book.store.service.imp;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import online.book.store.dto.CartItemResponseDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.mapper.ShoppingCartMapper;
import online.book.store.model.Book;
import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import online.book.store.repository.ShoppingCartRepository;
import online.book.store.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static User user;
    private static Book book;
    private static CartItemResponseDto cartItemResponseDto;
    private static ShoppingCart shoppingCart;
    private static ShoppingCartResponseDto shoppingCartResponseDto;
    private static Long userId = 1L;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeAll
    static void beforeAll() {
        user = new User()
                .setId(userId);
        book = new Book()
                .setId(1L)
                .setTitle("Test title")
                .setAuthor("Test author")
                .setIsbn("012345678901")
                .setPrice(BigDecimal.valueOf(199.99));
        shoppingCart = new ShoppingCart()
                .setId(userId)
                .setUser(user);
        cartItemResponseDto = new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle(book.getTitle())
                .setQuantity(3);
        shoppingCartResponseDto = new ShoppingCartResponseDto()
                .setId(1L)
                .setUserId(1L)
                .setCartItems(Set.of(cartItemResponseDto));
    }

    @Test
    @DisplayName("Get user cart content")
    void getUserCartContent_ValidUserId_GetValidShoppingCartResponseDto() {
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(shoppingCartRepository.findByUserId(userId))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(shoppingCartMapper.toResponseDto(shoppingCart))
                .thenReturn(shoppingCartResponseDto);

        ShoppingCartResponseDto actual = shoppingCartService.getUserCartContent(userId);
        ShoppingCartResponseDto expected = shoppingCartResponseDto;
        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
