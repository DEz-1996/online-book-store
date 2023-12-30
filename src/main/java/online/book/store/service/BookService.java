package online.book.store.service;

import java.util.List;
import online.book.store.dto.BookResponseDto;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto bookDto);

    BookResponseDto update(CreateBookRequestDto bookDto, Long id);

    BookResponseDto getBook(Long id);

    List<BookResponseDto> findAll(Pageable pageable);

    void deleteById(Long id);

    List<BookResponseDto> search(BookSearchParametersDto searchParameters);
}
