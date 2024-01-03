package online.book.store.service;

import java.util.Set;
import online.book.store.dto.order.OrderCreateRequestDto;
import online.book.store.dto.order.OrderItemResponseDto;
import online.book.store.dto.order.OrderResponseDto;
import online.book.store.dto.order.OrderStatusRequestDto;
import online.book.store.model.User;

public interface OrderService {
    OrderResponseDto createOrder(OrderCreateRequestDto createRequestDto, User user);

    OrderResponseDto setStatus(OrderStatusRequestDto statusRequestDto, Long orderId);

    Set<OrderResponseDto> getAll(Long userId);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);

    Set<OrderItemResponseDto> getAllOrderItems(Long orderId);
}
