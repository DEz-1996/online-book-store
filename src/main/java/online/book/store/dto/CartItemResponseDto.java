package online.book.store.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
