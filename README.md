# Online Book Store
The application was crafted to streamline the operations of the online bookstore, encompassing the following:

- overseeing the inventory of available books and categories (introducing new items, eliminating outdated ones)
- filling shopping carts (with cart items) and fulfilling orders based on their contents
- overseeing the user's orders (from the admin's endpoints)

## Feature
1. Shopping Cart: Our Online Book Store enables users to incorporate books into their shopping carts. You can effortlessly peruse our extensive collection of books, pick your favorites, and include them in your cart. The cart operates seamlessly, empowering users to view, modify, and finalize their purchases before proceeding to checkout.
2. Order Management: Once you've populated your shopping cart with the books you crave, our application furnishes a streamlined order management system. You can scrutinize your orders and include them.
3. Book Management with Pagination and Sorting: We recognize the significance of effective book exploration. Our application presents a sophisticated search functionality that empowers users to discover all books by categories, author, title, or by id. With pagination support, you can navigate through an extensive catalog of books without inundating your search results.
4. Security and JWT Tokens for User Auth: Security is paramount in our application. We've integrated Spring Security to safeguard your data and transactions. JWT (JSON Web Tokens) are employed for secure authentication and authorization, guaranteeing that only authorized users can access sensitive functionalities.

## Running with Docker Compose
To set up Online Book Store, follow these simple steps:
1. Clone the repository to your local machine from the console with the command: git clone https://github.com/DEz-1996/online-book-store.git.
2. Ensure you have Java and Maven installed.
3. Ensure that the docker-compose.yml file in the root of the project is configured appropriately. You can customize environment variables, ports, and other settings in this file.
4. Execute the following commands in the project root directory:
   ```bash
   mvn package
   docker-compose build
   docker-compose up

## Technologies used
### Core Technologies:
- Java
- Maven (Build tool)
### Spring Framework:
Spring Boot
Spring Data JPA
Spring Boot Security
Lombok
Mapstruct
### Database and Persistence:
Hibernate
MySQL
Liquibase
### Testing:
JUnit 5
Mockito
Docker
### API Documentation:
Swagger

## Endpoints
### User
POST: /api/auth/registration - endpoint to register new user (No authorities)
POST: /api/auth/login - endpoint to login exist user (No authorities)
### Books
GET: /api/books - endpoint to get a list of all available books (User authority)
GET: /api/books/{id} - endpoint to get the existing book by ID (User authority)
POST: /api/books - endpoint to create a new book (Admin authority)
PUT: /api/books/{id} - endpoint for update data about the existing book by ID (Admin authority)
DELETE: /api/books/{id} - endpoint for mark existing book for deletion by ID (Soft delete is used, Admin authority)
GET: /api/books/search - endpoint for search books by parameters: title, author, ISBN (User authority)
### Categories
GET: /api/categories - endpoint to look at all categories (User authority)
GET: /api/categories/{id} - endpoint for get the existing category by ID (User authority)
POST: /api/categories - endpoint for create a new category (User and Admin authority)
PUT: /api/categories/{id} - endpoint for update data about the existing category by ID (Admin authority)
DELETE: /api/categories/{id} - endpoint for mark existing category for deletion by ID (Soft delete is used, Admin authority)
GET: /api/categories/{id}/books - endpoint to get a list of all available books from category (User and Admin authority)
### Orders
GET: /api/orders - endpoint for get info about all user orders (User authority)
POST: /api/orders - endpoint for place an order based on the contents of the user's shopping cart (User authority)
PATCH: /api/orders/{id} - endpoint for set order status by order id. List of statuses: "{PENDING, DELIVERED, COMPLETED}"(Admin authority)
GET: /api/orders/{orderId}/items - endpoint for get info about all user order items by order id (User authority)
GET: /api/orders/{orderId}/items/{itemId} - endpoint for get info about user's order item by order item id (User authority)
### Shopping Cart
GET: /api/cart - endpoint for get info and books from cart (User authority)
POST: /api/cart - endpoint to add book to shopping cart (User authority)
PUT: /api/cart/cart-items/{cartItemId} - endpoint for update quantity of a book in the shopping cart (User authority)
DELETE: /api/cart/cart-items/{cartItemId} - endpoint for remove a book from the shopping cart (Soft delete is used, User authority)

## Project Structure
- model: Entity models representing the database schema
- repository: Spring Data JPA repositories for database operations
- service: Implementation of the business logic
- controller: Controllers containing endpoints with methods for handling HTTP requests
- dto: Data Transfer Objects used for communication between the client and server
- mapper: Mapper interfaces used for conversions between DTOs and entity models

## Challenges and Solutions
Challenges and Solutions
Encountering and Addressing Obstacles in Constructing Our Online Book Store

Challenge 1: Security
Issue: Ensuring data security and user authentication took precedence.
Solution: We implemented Spring Security and adopted JWT tokens to fortify protection.

Challenge 2: Exception Handling
Issue: Methodically managing errors and exceptions was pivotal, necessitating application-wide handling, not confined to a single controller.
Solution: We devised a universal handler and customized exceptions to enhance error reporting.

Challenge 3: Token-Based Authentication
Issue: Integrating JWT (JSON Web Tokens) for token-based authentication presented challenges concerning token creation, validation, and secure storage.
Solution: We embraced JWT as the authentication mechanism, adhering to industry best practices. Established libraries were employed, and security guidelines for token creation, validation, and storage were adhered to.

Challenge 4: User Management
Issue: Effectively managing user roles, permissions, and access control within the API required a structured approach.
Solution: We implemented a bespoke UserDetailsService to oversee user roles, permissions, and access control. This customization facilitated user management tailored to specific requirements while upholding security.

## What is next?
It may seem like this project is already complete, but I'm not going to leave it at that. Every store should give customers the opportunity to return goods, and this one is no exception. So, the next step is to add a return function. Also, I guess adding an interface and a nice design would make it much more user-friendly.
