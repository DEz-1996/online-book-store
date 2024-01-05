package online.book.store.mapper.order;

import java.util.List;
import online.book.store.config.MapperConfig;
import online.book.store.dto.order.OrderResponseDto;
import online.book.store.model.Order;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);

    @IterableMapping(elementTargetType = OrderResponseDto.class)
    List<OrderResponseDto> toDtoList(List<Order> orders);
}
