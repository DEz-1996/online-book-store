package online.book.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookRequestDto {
    private static final int MIN_STRING_LENGTH = 5;
    private static final int MAX_STRING_LENGTH = 255;
    private static final String STRING_SIZE_MSG = "length must be between 5 and 255 chars";
    private static final String ISBN_SIZE_MSG = "length must be 10 or 13 chars";

    @NotBlank
    @Size(min = MIN_STRING_LENGTH, max = MAX_STRING_LENGTH, message = STRING_SIZE_MSG)
    private String title;
    @NotBlank
    @Size(min = MIN_STRING_LENGTH, max = MAX_STRING_LENGTH, message = STRING_SIZE_MSG)
    private String author;
    @NotNull
    @ISBN(message = ISBN_SIZE_MSG)
    private String isbn;
    @NotNull
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
}
