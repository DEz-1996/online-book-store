package online.book.store.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import online.book.store.model.Order;

@Data
public class OrderStatusRequestDto {
    @NotNull
    private Order.Status status;
}
