package online.book.store.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import online.book.store.dto.BookResponseDto;
import online.book.store.dto.CreateBookRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
    private static BookResponseDto bookResponseDto;
    private static CreateBookRequestDto createBookRequestDto;
    private static Long id;
    private static String title;
    private static String author;
    private static String isbn;
    private static BigDecimal price;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();

        id = 1L;
        title = "Test title";
        author = "Test author";
        isbn = "012345678901";
        price = BigDecimal.valueOf(199.99);

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(id);
        bookResponseDto.setTitle(title);
        bookResponseDto.setAuthor(author);
        bookResponseDto.setIsbn(isbn);
        bookResponseDto.setPrice(price);

        createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle(title);
        createBookRequestDto.setAuthor(author);
        createBookRequestDto.setIsbn(isbn);
        createBookRequestDto.setPrice(price);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("Get all books from db")
    @Sql(scripts = "classpath:database/books/insert-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-all-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBooks_ValidRequest_GetListValidBookResponseDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books")).andReturn();
        List<BookResponseDto> expected = List.of(bookResponseDto);
        List<BookResponseDto> actual = Arrays.asList(objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                BookResponseDto[].class));
        Assertions.assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
