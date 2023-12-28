package online.book.store.service.imp;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import online.book.store.dto.book.BookDto;
import online.book.store.dto.book.BookDtoWithoutCategoryIds;
import online.book.store.dto.book.CreateBookRequestDto;
import online.book.store.exception.exceptions.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.model.Category;
import online.book.store.repository.BookRepository;
import online.book.store.repository.CategoryRepository;
import online.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private static final String CANT_FIND_BY_ID_MSG = "Can't find book by id: ";
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           CategoryRepository categoryRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto save(CreateBookRequestDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        List<Category> categories = categoryRepository.findAllById(bookDto.getCategoryIds());
        book.setCategories(new HashSet<>(categories));
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto update(CreateBookRequestDto bookDto, Long id) {
        Book book = bookMapper.toEntity(bookDto);
        book.setId(id);
        boolean isIdPresent = bookRepository.findById(id).isPresent();
        if (!isIdPresent) {
            throw new EntityNotFoundException(CANT_FIND_BY_ID_MSG + id);
        }
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANT_FIND_BY_ID_MSG + id));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .collect(Collectors.toList());
    }
}
