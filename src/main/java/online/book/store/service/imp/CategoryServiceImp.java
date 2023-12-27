package online.book.store.service.imp;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.CreateCategoryRequestDto;
import online.book.store.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CategoryDto getById(Long id) {
        return null;
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    public CategoryDto update(CreateCategoryRequestDto categoryRequestDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
