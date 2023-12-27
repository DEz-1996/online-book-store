package online.book.store.service;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.CreateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryRequestDto);

    CategoryDto update(CreateCategoryRequestDto categoryRequestDto, Long id);

    void deleteById(Long id);
}
