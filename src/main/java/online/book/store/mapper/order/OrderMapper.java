package online.book.store.mapper.order;

import online.book.store.config.MapperConfig;
import online.book.store.dto.order.OrderResponseDto;
import online.book.store.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);
}
