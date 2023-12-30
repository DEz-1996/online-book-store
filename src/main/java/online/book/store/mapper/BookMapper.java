package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.BookResponseDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    Book toBook(CreateBookRequestDto createBookRequestDto);
}
