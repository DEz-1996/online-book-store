package online.book.store.service.imp;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.order.OrderCreateRequestDto;
import online.book.store.dto.order.OrderItemResponseDto;
import online.book.store.dto.order.OrderResponseDto;
import online.book.store.dto.order.OrderStatusRequestDto;
import online.book.store.exception.exceptions.EntityNotFoundException;
import online.book.store.mapper.order.OrderItemMapper;
import online.book.store.mapper.order.OrderMapper;
import online.book.store.model.CartItem;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import online.book.store.repository.CartItemRepository;
import online.book.store.repository.ShoppingCartRepository;
import online.book.store.repository.order.OrderItemRepository;
import online.book.store.repository.order.OrderRepository;
import online.book.store.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final String CANT_FIND_ITEM_BY_ID_MSG = "Can't find item by id: ";
    private static final String CANT_FIND_ORDER_BY_ID_MSG = "Can't find order by id: ";
    private static final String CANT_FIND_SHOPPING_CART_BY_ID_MSG =
            "Can't find shopping cart by user id: ";

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto createOrder(OrderCreateRequestDto createRequestDto, User user) {
        Long userId = user.getId();
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                CANT_FIND_SHOPPING_CART_BY_ID_MSG + userId));
        Set<CartItem> cartItems = shoppingCart.getCartItems();

        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice().multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setShippingAddress(createRequestDto.getShippingAddress());
        order.setUser(user);
        order.setTotal(total);
        orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream()
                .map(orderItemMapper::cartItemToOrderItemWithoutOrder)
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toList());
        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(new HashSet<>(orderItems));

        cartItemRepository.deleteAll(cartItems);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponseDto setStatus(OrderStatusRequestDto statusRequestDto, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        CANT_FIND_ORDER_BY_ID_MSG + orderId));
        order.setStatus(statusRequestDto.getStatus());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public Set<OrderResponseDto> getAll(Long userId) {
        Set<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository
                .findById(itemId)
                .orElseThrow(
                        () -> new EntityNotFoundException(CANT_FIND_ITEM_BY_ID_MSG + itemId));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public Set<OrderItemResponseDto> getAllOrderItems(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }
}
