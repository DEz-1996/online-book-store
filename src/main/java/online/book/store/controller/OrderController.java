package online.book.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.order.OrderCreateRequestDto;
import online.book.store.dto.order.OrderItemResponseDto;
import online.book.store.dto.order.OrderResponseDto;
import online.book.store.dto.order.OrderStatusRequestDto;
import online.book.store.model.User;
import online.book.store.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Orders management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping
    @Operation(
            summary = "Create order",
            description = "Place an order based on the contents of the user's shopping cart")
    public OrderResponseDto createOrder(
            @RequestBody @Valid OrderCreateRequestDto orderCreateRequestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(orderCreateRequestDto, user);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Set order status",
            description = "Set order status by order id. List of statuses:"
                    + "{PENDING, DELIVERED, COMPLETED}")
    public OrderResponseDto setOrderStatus(
            @RequestBody @Valid OrderStatusRequestDto statusRequestDto,
            @PathVariable(name = "id") Long orderId) {
        return orderService.setStatus(statusRequestDto, orderId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping
    @Operation(
            summary = "Retrieve user orders history",
            description = "Get info about all user orders")
    public Set<OrderResponseDto> getAllOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAll(user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(
            summary = "Retrieve user order item",
            description = "Get info about user's order item by order item id")
    public OrderItemResponseDto getOrderItemById(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        return orderService.getOrderItem(orderId, itemId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    @Operation(
            summary = "Retrieve all user order items",
            description = "Get info about all user order items by order id")
    public Set<OrderItemResponseDto> getAllOrderItems(@PathVariable Long orderId) {
        return orderService.getAllOrderItems(orderId);
    }
}
