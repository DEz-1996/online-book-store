package online.book.store.service;

import java.util.List;
import online.book.store.dto.category.CategoryDto;
import online.book.store.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryRequestDto);

    CategoryDto update(CreateCategoryRequestDto categoryRequestDto, Long id);

    void deleteById(Long id);
}
