package online.book.store.dto;

import lombok.Data;

@Data
public class ShoppingCartCreateRequestDto {
    private Long bookId;
    private int quantity;
}
