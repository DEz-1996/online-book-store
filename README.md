# online-book-store

[//]: # (Spring Boot practice)

[//]: # (1&#41; Add Category model)

[//]: # (   At the end of this HW you should add a support for a new entity:)

[//]: # (   Category, so you need to implement the missing functionality.)

[//]: # (User Use Cases &#40;means these operations are allowed to users with role USER&#41;)

[//]: # (Here are the list of use cases that we will cover in this part of HW:)

[//]: # (Category Browsing:)

[//]: # (As a User, I want to browse categories, so I can find books by category. I will:)

[//]: # (Send a GET request to /api/categories to retrieve all categories.)
Send a GET request to /api/categories/{id}/books to retrieve books by a specific category.//TODO:

[//]: # (Admin Use Cases &#40;means these operations are allowed to users with role ADMIN&#41;)

[//]: # (Category Management)

[//]: # (As an Admin, I want to create a new category so books can be categorized. I will:)

[//]: # (Send a POST request to /api/categories with the details of the new category.)
[//]: # (As an Admin, I want to update the details of a category so the categories are up-to-date. I will:)

[//]: # (Send a PUT request to /api/categories/{id} with the updated details of the category.)
[//]: # (As an Admin, I want to remove a category, so it is no longer available. I will:)

[//]: # (Send a DELETE request to /api/categories/{id} to remove the category.)

[//]: # (Domain models &#40;entities&#41;)

[//]: # (There is a list of all entities that should be present in the project after this HW:)

[//]: # ()
[//]: # (Book: Represents a book available in the store.)

[//]: # (User: Contains information about the registered user including their authentication details and personal information.)

[//]: # (Role: Represents the role of a user in the system, for example, admin or user.)

[//]: # (Category: Represents a category that a book can belong to.)

[//]: # (Category entity)

[//]: # (Category: Represents a category that a book can belong to.)

[//]: # (Add a new entity Category with the next fields:)

[//]: # (id &#40;Long, PK&#41;)

[//]: # (name &#40;String, not null&#41;)

[//]: # (description &#40;String&#41;)
Book class now should have the following field: private Set<Category> categories = new HashSet<>();
HINT: you can use the next method in the BookRepository.class:
List<Book> findAllByCategoryId(Long categoryId);

[//]: # (Implement the CategoryRepository interface that will use JpaRepository interface)

[//]: # (Implement DTO classes for Category entity)
Modify the BookMapper class to have such methods:
BookDto toDto(Book book);
Book toEntity(CreateBookRequestDto bookDto);
BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
(HINT: BookDtoWithoutCategoryIds class could be used as a
response class for @GetMapping("/{id}/books") endpoint)
@AfterMapping default void setCategoryIds(@MappingTarget BookDto bookDto, Book book)
Add CategoryMapper class with such methods:

[//]: # (CategoryDto toDto&#40;Category category&#41;;)
[//]: # (Category toEntity&#40;CategoryDto categoryDTO&#41;;)
[//]: # (Implement CategoryService interface with methods and CategoryServiceImpl class:)

[//]: # (List findAll&#40;&#41;;)
[//]: # (CategoryDto getById&#40;Long id&#41;;)
[//]: # (CategoryDto save&#40;CategoryDto categoryDto&#41;;)
[//]: # (CategoryDto update&#40;Long id, CategoryDto categoryDto&#41;;)
[//]: # (void deleteById&#40;Long id&#41;;)
[//]: # (Add CategoryController class with such methods:)

[//]: # (public CategoryDto createCategory&#40;CategoryDto categoryDto&#41;)
[//]: # (public List getAll&#40;&#41;)
[//]: # (public CategoryDto getCategoryById&#40;Long id&#41;)
[//]: # (public CategoryDto updateCategory&#40;Long id, CategoryDto categoryDto&#41;)
[//]: # (public void deleteCategory&#40;Long id&#41;)
[//]: # (public List getBooksByCategoryId&#40;Long id&#41; &#40;endpoint: "/{id}/books"&#41;)

[//]: # (General requirements)
Don't forget to use Liquibase
Don't forget to implement soft delete approach
Add Pagination, Sorting, and Swagger to all controllers you have
Endpoints

Below you will find the list of endpoints that should be in your project at the end of this HW.

User Endpoints: These endpoints should be done in the previous PRs

Book Endpoints: These endpoints should be done in the previous PRs,
but pay attention you have added the categoryIds field to the DTO classes

Category Endpoints:
POST: /api/categories (Create a new category)
Example of request body:
{
"name": "Fiction",
"description": "Fiction books"
}
GET: /api/categories (Retrieve all categories)
Example of response body:
{
"id": 1,
"name": "Fiction",
"description": "Fiction books"
}
GET: /api/categories/{id} (Retrieve a specific category by its ID)
PUT: /api/categories/{id} (Update a specific category)
Example of request body:
{
"name": "Fiction",
"description": "Fiction books"
}
DELETE: /api/categories/{id} (Delete a specific category)
GET: /api/categories/{id}/books (Retrieve books by a specific category)

SECURITY REQUIREMENTS
Available for non authenticated users:
POST: /api/auth/register
POST: /api/auth/login
Available for users with role USER
GET: /api/books
GET: /api/books/{id}
GET: /api/categories
GET: /api/categories/{id}
GET: /api/categories/{id}/books
Available for users with role ADMIN
POST: /api/books/
PUT: /api/books/{id}
DELETE: /api/books/{id}
POST: /api/categories
PUT: /api/categories/{id}
DELETE: /api/categories/{id}
