package online.book.store.dto;

import lombok.Data;

@Data
public class CartItemAddBookRequestDto {
    private Long bookId;
    private int quantity;
}
