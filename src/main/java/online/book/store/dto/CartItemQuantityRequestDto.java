package online.book.store.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemQuantityRequestDto {
    @Positive
    private int quantity;
}
