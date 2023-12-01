package online.book.store;

import java.math.BigDecimal;
import java.util.List;
import online.book.store.model.Book;
import online.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book testBook = new Book();
                testBook.setTitle("testBookTitle");
                testBook.setAuthor("testAuthor");
                testBook.setIsbn("testIsbn");
                testBook.setPrice(BigDecimal.valueOf(100));
                bookService.save(testBook);
                List<Book> books = bookService.findAll();
                System.out.println("Print all books:");
                books.forEach(System.out::println);
            }
        };
    }
}
