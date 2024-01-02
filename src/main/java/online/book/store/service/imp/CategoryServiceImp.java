package online.book.store.service.imp;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.category.CategoryDto;
import online.book.store.dto.category.CreateCategoryRequestDto;
import online.book.store.exception.exceptions.EntityNotFoundException;
import online.book.store.mapper.CategoryMapper;
import online.book.store.model.Category;
import online.book.store.repository.CategoryRepository;
import online.book.store.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private static final String CANT_FIND_BY_ID_MSG = "Can't find category by id: ";
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANT_FIND_BY_ID_MSG + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toEntity(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(CreateCategoryRequestDto categoryRequestDto, Long id) {
        Category category = categoryMapper.toEntity(categoryRequestDto);
        category.setId(id);
        boolean isIdPresent = categoryRepository.existsById(id);
        if (!isIdPresent) {
            throw new EntityNotFoundException(CANT_FIND_BY_ID_MSG + id);
        }
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
