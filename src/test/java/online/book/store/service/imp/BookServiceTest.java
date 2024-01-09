package online.book.store.service.imp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import online.book.store.dto.BookResponseDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.model.Category;
import online.book.store.repository.CategoryRepository;
import online.book.store.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static Book book;
    private static Category category;
    private static BookResponseDto bookResponseDto;
    private static CreateBookRequestDto createBookRequestDto;
    private static Long id;
    private static String categoryName;
    private static String title;
    private static String author;
    private static String isbn;
    private static BigDecimal price;
    private static Set<Long> categoryIds;

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;

    @BeforeAll
    static void beforeAll() {
        id = 1L;
        categoryName = "Test category";
        title = "Test title";
        author = "Test author";
        isbn = "012345678901";
        price = BigDecimal.valueOf(199.99);
        categoryIds = Set.of(id);

        category = new Category();
        category.setId(id);
        category.setName(categoryName);

        book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setCategories(Set.of(category));

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(id);
        bookResponseDto.setTitle(title);
        bookResponseDto.setAuthor(author);
        bookResponseDto.setIsbn(isbn);
        bookResponseDto.setPrice(price);
        bookResponseDto.setCategoryIds(categoryIds);

        createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle(title);
        createBookRequestDto.setAuthor(author);
        createBookRequestDto.setIsbn(isbn);
        createBookRequestDto.setPrice(price);
        createBookRequestDto.setCategoryIds(categoryIds);
    }

    @Test
    @DisplayName("Save book to db")
    void saveBookDto_ValidDto_GetValidBookResponseDto() {
        Mockito.when(bookMapper.toBook(createBookRequestDto)).thenReturn(book);
        Mockito.when(categoryRepository.findAllById(Set.of(category.getId())))
                .thenReturn(List.of(category));
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookResponseDto);
        BookResponseDto expected = bookResponseDto;
        BookResponseDto actual = bookService.save(createBookRequestDto);
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all books from db")
    void getAll_SavedValidBook_GetListWithOneBookResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(bookRepository.getAll(pageable)).thenReturn(List.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookResponseDto);
        List<BookResponseDto> expected = List.of(bookResponseDto);
        List<BookResponseDto> actual = bookService.findAll(pageable);
        Assertions.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Find book with valid id from db")
    void findById_ValidId_ReturnBookResponseDto() {
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookResponseDto);
        BookResponseDto expected = bookResponseDto;
        BookResponseDto actual = bookService.getBook(book.getId());
        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
