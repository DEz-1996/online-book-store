package online.book.store.service.imp;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.CartItemAddBookRequestDto;
import online.book.store.dto.CartItemQuantityRequestDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.exception.exceptions.EntityNotFoundException;
import online.book.store.mapper.ShoppingCartMapper;
import online.book.store.model.Book;
import online.book.store.model.CartItem;
import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import online.book.store.repository.CartItemRepository;
import online.book.store.repository.ShoppingCartRepository;
import online.book.store.repository.UserRepository;
import online.book.store.repository.book.BookRepository;
import online.book.store.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final String CANT_FIND_USER_BY_ID_MSG = "Can't find user by id: ";
    private static final String CANT_FIND_BOOK_BY_ID_MSG = "Can't find book by id: ";

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToShoppingCart(
            CartItemAddBookRequestDto requestDto,
            Long userId) {
        Long bookId = requestDto.getBookId();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(CANT_FIND_BOOK_BY_ID_MSG + bookId));

        ShoppingCart shoppingCart = getUserCart(userId);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getUserCartContent(Long userId) {
        ShoppingCart shoppingCart = getUserCart(userId);
        return shoppingCartMapper.toResponseDto(shoppingCart);
    }

    private ShoppingCart getUserCart(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(CANT_FIND_USER_BY_ID_MSG + userId));
        return shoppingCartRepository
                .findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newShoppingCart = new ShoppingCart();
                    newShoppingCart.setUser(user);
                    return shoppingCartRepository.save(newShoppingCart);
                });
    }

    @Override
    public ShoppingCartResponseDto updateItemsQuantity(
            Long cartItemId,
            CartItemQuantityRequestDto quantityRequestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException(CANT_FIND_USER_BY_ID_MSG + cartItemId));
        cartItem.setQuantity(quantityRequestDto.getQuantity());
        ShoppingCart shoppingCart = cartItem.getShoppingCart();
        return shoppingCartMapper.toResponseDto(shoppingCart);
    }

    @Override
    public void deleteById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
