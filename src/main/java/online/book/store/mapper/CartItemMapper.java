package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.CartItemResponseDto;
import online.book.store.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);
}
