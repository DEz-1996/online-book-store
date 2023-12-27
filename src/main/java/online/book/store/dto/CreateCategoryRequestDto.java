package online.book.store.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateCategoryRequestDto {
    @NotNull
    @NotEmpty
    private String name;
    private String description;
}
