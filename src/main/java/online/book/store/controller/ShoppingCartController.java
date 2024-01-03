package online.book.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.CartItemAddBookRequestDto;
import online.book.store.dto.CartItemQuantityRequestDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.model.User;
import online.book.store.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart management", description = "Endpoints for mapping cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping
    @Operation(
            summary = "Retrieve user's shopping cart",
            description = "Get info and books from cart")
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getUserCartContent(user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add book to shopping cart", description = "Add book to shopping cart")
    public ShoppingCartResponseDto addBookToShoppingCart(
            @RequestBody @Valid CartItemAddBookRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToShoppingCart(requestDto, user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update quantity of a book in the shopping cart",
            description = "Update quantity of a book in the shopping cart")
    public ShoppingCartResponseDto updateItemsQuantity(
            @RequestBody @Valid CartItemQuantityRequestDto quantityRequestDto,
            @PathVariable Long cartItemId) {
        return shoppingCartService.updateItemsQuantity(cartItemId, quantityRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove a book from the shopping cart",
            description = "Remove a book from the shopping cart")
    public void delete(
            @PathVariable Long cartItemId) {
        shoppingCartService.deleteById(cartItemId);
    }
}
