package online.book.store.service;

import java.util.Set;
import online.book.store.dto.order.OrderCreateRequestDto;
import online.book.store.dto.order.OrderItemResponseDto;
import online.book.store.dto.order.OrderResponseDto;
import online.book.store.dto.order.OrderStatusRequestDto;
import online.book.store.model.User;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(OrderCreateRequestDto createRequestDto, User user);

    OrderResponseDto updateStatus(OrderStatusRequestDto statusRequestDto, Long orderId);

    Set<OrderResponseDto> getAll(Long userId, Pageable pageable);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);

    Set<OrderItemResponseDto> getAllOrderItems(Long orderId, Pageable pageable);
}
