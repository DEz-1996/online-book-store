package online.book.store.service;

import java.util.List;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookDto);

    BookDto update(CreateBookRequestDto bookDto, Long id);

    BookDto getBook(Long id);

    List<BookDto> findAll(Pageable pageable);

    void deleteById(Long id);
}
