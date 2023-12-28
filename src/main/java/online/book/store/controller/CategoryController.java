package online.book.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.book.BookDtoWithoutCategoryIds;
import online.book.store.dto.category.CategoryDto;
import online.book.store.dto.category.CreateCategoryRequestDto;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category management", description = "Endpoints for mapping categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Get a list of all available categories"
    )
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Get the existing category by ID")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new category", description = "Create a new category")
    public CategoryDto createCategory(
            @RequestBody @Valid CreateCategoryRequestDto categoryRequestDto
    ) {
        return categoryService.save(categoryRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update category",
            description = "Update data about the existing category by ID"
    )
    public CategoryDto updateCategory(
            @RequestBody @Valid CreateCategoryRequestDto categoryRequestDto,
            @PathVariable Long id
    ) {
        return categoryService.update(categoryRequestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            description = "Mark existing category for deletion by ID"
    )
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/books")
    @Operation(
            summary = "Get all books from category",
            description = "Get a list of all available books from category"
    )
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            Pageable pageable,
            @PathVariable Long id
    ) {
        return bookService.findAllByCategoryId(id);
    }
}
