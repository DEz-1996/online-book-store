package online.book.store.mapper;

import java.util.stream.Collectors;
import online.book.store.config.MapperConfig;
import online.book.store.dto.BookDtoWithoutCategoryIds;
import online.book.store.dto.BookResponseDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;
import online.book.store.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookResponseDto toDto(Book book);

    Book toBook(CreateBookRequestDto createBookRequestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto bookDto, Book book) {
        if (!book.getCategories().isEmpty()) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }
}
