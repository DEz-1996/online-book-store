package online.book.store.mapper.order;

import online.book.store.config.MapperConfig;
import online.book.store.dto.order.OrderItemResponseDto;
import online.book.store.model.CartItem;
import online.book.store.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem cartItemToOrderItemWithoutOrder(CartItem cartItem);
}
