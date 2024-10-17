# Recipe Management Web Service

## Description

This project implements a multi-user web service for managing recipes. Users can add, update, retrieve, and delete recipes, with all recipes stored in a database. The service is built using Spring Boot and incorporates features such as user registration, authentication, and authorization to ensure that only the recipe owner can modify or delete their recipes.

## Features

The project evolves across multiple stages, introducing new features and functionalities in each stage:

### Stage 1: Basic Recipe Storage

- **Endpoints**:
  - `POST /api/recipe`: Add a single recipe (overwrites previous recipe).
  - `GET /api/recipe`: Retrieve the current recipe.
- **Recipe Structure** (initial):
  - `name`: String
  - `description`: String
  - `ingredients`: String (comma-separated)
  - `directions`: String (steps as a single string)

### Stage 2: Multiple Recipes with Unique IDs

- **Changes**:
  - Supports storing multiple recipes, each identified by a unique `id`.
  - `ingredients` and `directions` fields are now arrays.
- **Endpoints**:
  - `POST /api/recipe/new`: Add a new recipe, returns a unique `id`.
  - `GET /api/recipe/{id}`: Retrieve a recipe by its `id`.

### Stage 3: Persistent Storage and Recipe Validation

- **Changes**:
  - Connected to a database to store recipes permanently.
  - Introduced validation to ensure that all recipe fields are present and valid.
- **New Features**:
  - `DELETE /api/recipe/{id}`: Deletes a recipe by `id`.
  - Recipe validation:
    - `name`, `description`, `category` cannot be blank.
    - `ingredients` and `directions` must be non-empty arrays.

### Stage 4: Search and Update Functionality

- **Changes**:
  - Added `category` and `date` fields to the recipe structure.
- **Endpoints**:
  - `PUT /api/recipe/{id}`: Update a recipe by its `id`.
  - `GET /api/recipe/search`: Search for recipes by `category` or `name`.
- **Search Features**:
  - Case-insensitive search by `category` or `name`.
  - Results are sorted by `date`, with the newest first.

### Stage 5: User Registration and Security

- **Changes**:
  - Introduced user registration and authentication using Spring Security.
  - Recipes can only be updated or deleted by the user who created them.
- **Endpoints**:
  - `POST /api/register`: Register a new user.
  - All other endpoints require authentication via HTTP Basic Auth.
  - Only the recipe owner can modify or delete their recipes.

## Recipe Structure

The following structure defines a recipe in the final stage:

```json
{
   "name": "Warming Ginger Tea",
   "category": "beverage",
   "description": "Ginger tea is a warming drink for cool weather...",
   "ingredients": ["ginger", "lemon", "honey"],
   "directions": ["Boil water", "Steep ingredients", "Serve and enjoy"],
   "date": "2021-09-05T18:34:48.227624"
}
```

### Validations:

- `name`, `category`, `description`: Non-empty strings.
- `ingredients`, `directions`: Arrays with at least one item.
- `date`: Automatically generated, reflects when the recipe was created or updated.

## Authentication & Authorization

- **User Registration**: 
  - Register via `POST /api/register` with a JSON object containing `email` and `password`.
  - Email must contain `@` and `.` symbols, and passwords must be at least 8 characters long.
- **Authentication**:
  - All recipe management endpoints are protected with HTTP Basic Auth.
  - Only the recipe owner can delete or update their recipes.

## Example API Requests

### Add Recipe

```json
POST /api/recipe/new
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Add mint", "Steep for 3-5 minutes", "Add honey"]
}
```

### Retrieve Recipe by ID

```json
GET /api/recipe/1
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Add mint", "Steep for 3-5 minutes", "Add honey"],
   "date": "2021-09-06T14:11:51.006787"
}
```

### Search Recipes by Category

```json
GET /api/recipe/search?category=beverage
[
   {
      "name": "Fresh Mint Tea",
      "category": "beverage",
      "date": "2021-09-06T14:11:51.006787",
      ...
   }
]
```

### Update Recipe

```json
PUT /api/recipe/1
{
   "name": "Ginger Tea",
   "category": "beverage",
   "description": "A warm and spicy drink.",
   "ingredients": ["ginger", "water", "honey"],
   "directions": ["Boil ginger in water", "Add honey", "Serve hot"]
}
```

## Database Configuration

Ensure that the `application.properties` file contains the following line for database setup:

```properties
spring.datasource.url=jdbc:h2:file:../recipes_db
```

This configuration uses an H2 database to persist all recipes.

## Dependencies

The project relies on the following key dependencies:

- Spring Boot (for building the web service)
- Spring Security (for user authentication and authorization)
- H2 Database (for recipe storage)
- Project Lombok (for reducing boilerplate code)

## Running the Project

To run the project, ensure that you have Java and Gradle installed. Then, use the following commands:

```bash
./gradlew bootRun
```

This will start the Spring Boot application and expose the REST API at `http://localhost:8080`.

## Testing

You can test the API using tools like Postman or cURL, ensuring to include Basic Authentication headers for authenticated routes.

## Future Enhancements

Potential features to add:

- Recipe rating system.
- Sharing recipes between users.
- API documentation with OpenAPI (Swagger).

```

```
