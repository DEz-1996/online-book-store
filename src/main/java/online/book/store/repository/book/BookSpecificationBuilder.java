package online.book.store.repository.book;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.SpecificationBuilder;
import online.book.store.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String ISBN = "isbn";

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;
    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (bookSearchParameters.getTitles() != null && bookSearchParameters.getTitles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(TITLE)
                    .getSpecification(bookSearchParameters.getTitles()));
        }
        if (bookSearchParameters.getAuthors() != null && bookSearchParameters.getAuthors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(AUTHOR)
                    .getSpecification(bookSearchParameters.getAuthors()));
        }
        if (bookSearchParameters.getIsbn() != null && bookSearchParameters.getIsbn().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(ISBN)
                    .getSpecification(bookSearchParameters.getIsbn()));
        }
        return spec;
    }
}
