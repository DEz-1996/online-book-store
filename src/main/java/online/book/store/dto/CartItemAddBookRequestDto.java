package online.book.store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemAddBookRequestDto {
    @NotNull
    private Long bookId;
    @Positive
    private int quantity;
}
