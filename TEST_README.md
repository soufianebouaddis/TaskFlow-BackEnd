# TaskFlow Backend - Unit Test Suite

This document provides an overview of the comprehensive unit test suite created for the TaskFlow backend application.

## Test Structure

### Controllers Tests

#### 1. AuthControllerTest (`src/test/java/os/org/taskflow/auth/controller/AuthControllerTest.java`)
Tests authentication endpoints with MockMvc and Mockito:

- **register_ValidRequest_ReturnsSuccess**: Tests successful user registration
- **register_InvalidRequest_ReturnsBadRequest**: Tests validation errors
- **register_ServiceThrowsException_ReturnsInternalServerError**: Tests service layer exceptions
- **login_ValidRequest_ReturnsSuccess**: Tests successful login with cookie handling
- **profile_ValidRequest_ReturnsUserProfile**: Tests profile retrieval
- **updateProfile_ValidRequest_ReturnsSuccess**: Tests profile updates
- **updateProfile_ServiceThrowsException_ReturnsInternalServerError**: Tests update exceptions
- **logout_ValidRequest_ReturnsSuccess**: Tests successful logout
- **logout_HandlerThrowsException_ReturnsInternalServerError**: Tests logout exceptions

#### 2. TaskControllerTest (`src/test/java/os/org/taskflow/task/controller/TaskControllerTest.java`)
Tests task management endpoints with security annotations:

- **addNewTask_ValidRequest_ReturnsSuccess**: Tests task creation (MANAGER role)
- **addNewTask_InvalidRequest_ReturnsBadRequest**: Tests validation errors
- **addNewTask_ServiceThrowsException_ReturnsInternalServerError**: Tests service exceptions
- **findAllTasks_ValidRequest_ReturnsSuccess**: Tests task listing (MANAGER/DEVELOPER roles)
- **findAllTasks_DeveloperAccess_ReturnsSuccess**: Tests developer access
- **updateTask_ValidRequest_ReturnsSuccess**: Tests task updates (MANAGER/DEVELOPER roles)
- **updateTask_DeveloperAccess_ReturnsSuccess**: Tests developer update access
- **deleteTask_ValidRequest_ReturnsSuccess**: Tests task deletion (MANAGER role)
- **deleteTask_ServiceThrowsException_ReturnsInternalServerError**: Tests delete exceptions
- **deleteTask_DeveloperAccess_ReturnsForbidden**: Tests authorization
- **addNewTask_NoAuthentication_ReturnsUnauthorized**: Tests authentication requirements

#### 3. DeveloperControllerTest (`src/test/java/os/org/taskflow/developer/controller/DeveloperControllerTest.java`)
Tests developer listing endpoints:

- **developers_ValidRequest_ReturnsSuccess**: Tests successful developer listing
- **developers_EmptyList_ReturnsSuccess**: Tests empty result handling
- **developers_ServiceThrowsException_ReturnsInternalServerError**: Tests service exceptions
- **developers_ServiceReturnsEmptyOptional_ReturnsSuccess**: Tests optional handling
- **developers_SingleDeveloper_ReturnsSuccess**: Tests single developer response

#### 4. ManagerControllerTest (`src/test/java/os/org/taskflow/manager/controller/ManagerControllerTest.java`)
Tests manager-specific operations with security:

- **assignedTaskToDeveloper_ValidRequest_ReturnsSuccess**: Tests task assignment (MANAGER role)
- **assignedTaskToDeveloper_ServiceThrowsException_ReturnsInternalServerError**: Tests assignment exceptions
- **assignedTaskToDeveloper_DeveloperAccess_ReturnsForbidden**: Tests authorization
- **assignedTaskToDeveloper_NoAuthentication_ReturnsUnauthorized**: Tests authentication
- **addDeveloperToTeam_ValidRequest_ReturnsSuccess**: Tests team management (MANAGER role)
- **addDeveloperToTeam_ServiceThrowsException_ReturnsInternalServerError**: Tests team exceptions
- **addDeveloperToTeam_DeveloperAccess_ReturnsForbidden**: Tests authorization
- **addDeveloperToTeam_NoAuthentication_ReturnsUnauthorized**: Tests authentication
- **assignedTaskToDeveloper_WithDifferentIds_ReturnsSuccess**: Tests various ID combinations
- **addDeveloperToTeam_WithDifferentIds_ReturnsSuccess**: Tests various ID combinations

### Entity Tests

#### 1. UserTest (`src/test/java/os/org/taskflow/auth/entity/UserTest.java`)
Tests User entity with UserDetails implementation:

- **testUserCreation**: Tests basic user creation
- **testGetAuthorities**: Tests Spring Security authorities
- **testGetUsername**: Tests username retrieval (email)
- **testGetPassword**: Tests password retrieval
- **testIsAccountNonExpired**: Tests account expiration
- **testIsAccountNonLocked**: Tests account locking
- **testIsCredentialsNonExpired**: Tests credential expiration
- **testIsEnabled**: Tests account enabling
- **testUserWithNullRole**: Tests null role handling
- **testUserWithDifferentRole**: Tests role changes
- **testUserEquality**: Tests user equality
- **testUserInequality**: Tests user inequality
- **testUserWithEmptyFields**: Tests empty field handling
- **testUserSettersAndGetters**: Tests all setters and getters

#### 2. TaskTest (`src/test/java/os/org/taskflow/task/entity/TaskTest.java`)
Tests Task entity with various states:

- **testTaskCreation**: Tests basic task creation
- **testTaskWithDifferentStates**: Tests all TaskState enum values
- **testTaskWithNullDeveloper**: Tests null developer relationship
- **testTaskWithDifferentDeveloper**: Tests developer relationship changes
- **testTaskEquality**: Tests task equality
- **testTaskInequality**: Tests task inequality
- **testTaskWithEmptyFields**: Tests empty field handling
- **testTaskSettersAndGetters**: Tests all setters and getters
- **testTaskStateEnumValues**: Tests enum value validation
- **testTaskWithLongTaskLabel**: Tests long label handling
- **testTaskWithSpecialCharacters**: Tests special character handling
- **testTaskWithUnicodeCharacters**: Tests unicode character handling

### Mapper Tests

#### 1. UserMapperTest (`src/test/java/os/org/taskflow/auth/mapper/UserMapperTest.java`)
Tests MapStruct mappings for User-related DTOs:

- **testToUser_ValidRegisterRequest_ReturnsUser**: Tests RegisterRequest to User mapping
- **testToDto_ValidUser_ReturnsRegisterRequest**: Tests User to RegisterRequest mapping
- **testUserToProfile_ValidUser_ReturnsProfile**: Tests User to Profile mapping
- **testToManager_ValidRegisterRequest_ReturnsManager**: Tests RegisterRequest to Manager mapping
- **testToDeveloper_ValidRegisterRequest_ReturnsDeveloper**: Tests RegisterRequest to Developer mapping
- **testDeveloperToProfile_ValidDeveloper_ReturnsProfile**: Tests Developer to Profile mapping
- **testManagerToProfile_ValidManager_ReturnsProfile**: Tests Manager to Profile mapping
- **testToTaskDTO_ValidTask_ReturnsTaskDTO**: Tests Task to TaskDTO mapping
- **testToTaskDTOs_ValidTaskList_ReturnsTaskDTOList**: Tests Task list to TaskDTO list mapping
- **testToDeveloperDTO_ValidDeveloper_ReturnsDeveloperDTO**: Tests Developer to DeveloperDTO mapping
- **testToDeveloperDTOs_ValidDeveloperList_ReturnsDeveloperDTOList**: Tests Developer list to DeveloperDTO list mapping
- **testToUser_WithNullValues_ReturnsUserWithNulls**: Tests null value handling
- **testToTaskDTOs_EmptyList_ReturnsEmptyList**: Tests empty list handling
- **testToDeveloperDTOs_EmptyList_ReturnsEmptyList**: Tests empty list handling

#### 2. TaskMapperTest (`src/test/java/os/org/taskflow/task/mapper/TaskMapperTest.java`)
Tests MapStruct mappings for Task DTOs:

- **testToEntity_ValidTaskDTO_ReturnsTask**: Tests TaskDTO to Task mapping
- **testToEntity_WithDifferentTaskStates_ReturnsCorrectTask**: Tests all TaskState mappings
- **testToEntity_WithNullValues_ReturnsTaskWithNulls**: Tests null value handling
- **testToEntity_WithEmptyTaskLabel_ReturnsTaskWithEmptyLabel**: Tests empty label handling
- **testToEntity_WithLongTaskLabel_ReturnsTaskWithLongLabel**: Tests long label handling
- **testToEntity_WithSpecialCharacters_ReturnsTaskWithSpecialChars**: Tests special character handling
- **testToEntity_WithUnicodeCharacters_ReturnsTaskWithUnicode**: Tests unicode character handling
- **testToDto_ValidTaskDTO_ReturnsSameTaskDTO**: Tests TaskDTO to TaskDTO mapping
- **testToDto_WithNullValues_ReturnsTaskDTOWithNulls**: Tests null value handling
- **testToDto_WithDifferentStates_ReturnsCorrectState**: Tests state mapping
- **testToEntity_WithZeroId_ReturnsTaskWithNullId**: Tests ID handling
- **testToEntity_WithNegativeId_ReturnsTaskWithNullId**: Tests negative ID handling
- **testToEntity_WithLargeId_ReturnsTaskWithNullId**: Tests large ID handling

## Test Coverage

### Functional Coverage
- ✅ All controller endpoints tested
- ✅ All entity classes tested
- ✅ All mapper interfaces tested
- ✅ Happy path scenarios
- ✅ Error handling scenarios
- ✅ Validation testing
- ✅ Security testing (authentication/authorization)

### Technical Coverage
- ✅ MockMvc for controller testing
- ✅ Mockito for mocking dependencies
- ✅ Spring Security Test for security testing
- ✅ MapStruct for mapper testing
- ✅ JUnit 5 for test framework
- ✅ Edge cases and boundary conditions
- ✅ Null value handling
- ✅ Enum value testing
- ✅ Relationship mapping testing

## Running the Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=AuthControllerTest
```

### Run Tests with Coverage
```bash
mvn test jacoco:report
```

### Run Tests in IDE
- Right-click on test class and select "Run Test"
- Use IDE's test runner to run individual tests or test suites

## Dependencies Added

The following dependencies were added to `pom.xml`:

```xml
<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- JUnit Platform Suite -->
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-suite</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-suite-engine</artifactId>
    <scope>test</scope>
</dependency>
```

## Test Patterns Used

1. **Arrange-Act-Assert (AAA)**: All tests follow this pattern for clarity
2. **Mocking**: Extensive use of Mockito for dependency isolation
3. **Security Testing**: @WithMockUser annotations for role-based testing
4. **Validation Testing**: Testing both valid and invalid inputs
5. **Exception Testing**: Testing service layer exceptions
6. **Edge Case Testing**: Testing null values, empty lists, special characters
7. **Enum Testing**: Comprehensive testing of all enum values
8. **Relationship Testing**: Testing entity relationships and mappings

## Best Practices Implemented

- ✅ Descriptive test method names
- ✅ Comprehensive test coverage
- ✅ Proper test isolation
- ✅ Mocking of external dependencies
- ✅ Testing of both success and failure scenarios
- ✅ Validation of response structures
- ✅ Security testing with proper roles
- ✅ Edge case and boundary condition testing
- ✅ Clear test documentation
- ✅ Consistent test structure

This test suite provides comprehensive coverage of the TaskFlow backend application, ensuring reliability, maintainability, and confidence in the codebase. 