# Database Documentation

## Preview ER

<p align="center">
  <img src="../images/relation-entity.png" alt="Database Schema" width="800"/>
</p>

## Schema Structure

This project uses PostgreSQL with a schema called `encoder_test_system` that contains the following entities:

### Main Entities

1. **User**
   - Stores information about system users
   - Roles: ADMIN, PARTICIPANT

2. **Test**
   - Contains available programming tests
   - Each test is associated with a specific programming language
   - Includes time limit and activation status

3. **Question**
   - Questions associated with each test
   - Types: CODE, MULTIPLE_CHOICE, TRUE_FALSE
   - Levels: BASIC, INTERMEDIATE, ADVANCED

4. **TestCase**
   - Test cases to evaluate code responses
   - Can be visible or not to the user

5. **TestAttempt**
   - Records user attempts when taking a test
   - States: IN_PROGRESS, FINISHED

6. **UserResponse**
   - Stores user responses to questions
   - Includes compilation and execution results

## Key Relationships

- A User can be the creator of multiple Tests
- A Test has many Questions
- Each Question can have multiple TestCases
- A User can have multiple TestAttempts
- Each TestAttempt has multiple UserResponses

## Instructions for Running Scripts

### Initial Setup

1. Make sure you have PostgreSQL installed and running
2. Create a database called `encoder_test_db`:
   ```sql
   CREATE DATABASE encoder_test_db;
   ```

### Script Execution

1. First run the `schema.sql` script to create the schema and tables:
   ```bash
   psql -U postgres -d encoder_test_db -f schema.sql
   ```

2. Then, run the `data.sql` script to load the example data:
   ```bash
   psql -U postgres -d encoder_test_db -f data.sql
   ```

### Application Configuration

The application is already configured to connect to this database according to the `application.properties` file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/encoder_test_db?currentSchema=encoder_test_system
spring.datasource.username=postgres
spring.datasource.password=Mayita504321
```

## Additional Notes

- The scripts include the creation of indexes to optimize performance
- Example data has been included to facilitate testing
- The passwords of the example users are hashed (value: "admin123")

