package online.book.store.service.imp;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import online.book.store.dto.BookResponseDto;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.exception.exceptions.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.model.Category;
import online.book.store.repository.CategoryRepository;
import online.book.store.repository.book.BookRepository;
import online.book.store.repository.book.BookSpecificationBuilder;
import online.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private static final String CANT_FIND_BY_ID_MSG = "Can't find book by id: ";
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Autowired
    public BookServiceImpl(
            BookRepository bookRepository,
            BookMapper bookMapper,
            CategoryRepository categoryRepository,
            BookSpecificationBuilder bookSpecificationBuilder
    ) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
        this.bookSpecificationBuilder = bookSpecificationBuilder;
    }

    @Override
    public BookResponseDto save(CreateBookRequestDto bookDto) {
        Book book = bookMapper.toBook(bookDto);
        List<Category> categories = categoryRepository.findAllById(bookDto.getCategoryIds());
        book.setCategories(new HashSet<>(categories));
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookResponseDto update(CreateBookRequestDto bookDto, Long id) {
        Book book = bookMapper.toBook(bookDto);
        book.setId(id);
        boolean isIdPresent = bookRepository.findById(id).isPresent();
        if (!isIdPresent) {
            throw new EntityNotFoundException(CANT_FIND_BY_ID_MSG + id);
        }
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANT_FIND_BY_ID_MSG + id));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.getAll(pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDto> search(BookSearchParametersDto searchParameters) {
        Specification<Book> build = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(build).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookResponseDto> findAllByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
