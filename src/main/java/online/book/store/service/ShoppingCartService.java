package online.book.store.service;

import online.book.store.dto.CartItemAddBookRequestDto;
import online.book.store.dto.CartItemQuantityRequestDto;
import online.book.store.dto.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getUserCartContent(Long id);

    ShoppingCartResponseDto addBookToShoppingCart(CartItemAddBookRequestDto requestDto, Long id);

    ShoppingCartResponseDto updateItemsQuantity(
            Long cartItemId,
            CartItemQuantityRequestDto quantityRequestDto);

    void deleteById(Long cartItemId);
}
