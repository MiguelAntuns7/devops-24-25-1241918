package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldCreateEmployee() {
        // Act
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 0);

        //Assert
        assertNotNull(employee);
    }

    public static Stream<Arguments> provideInvalidFirstName() {
        return Stream.of(
                Arguments.of(null, "Antunes", "Employee", "DevOps Engineer", 5, "First Name field must not be empty."),
                Arguments.of("", "Antunes", "Employee", "DevOps Engineer", 5, "First Name field must not be empty.")
        );
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidFirstName")
    void shouldThrowExceptionIfFirstNameIsInvalid(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobTitle, jobYears);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    public static Stream<Arguments> provideInvalidLastName() {
        return Stream.of(
                Arguments.of("Miguel", null, "Employee", "DevOps Engineer", 5, "Last Name field must not be empty."),
                Arguments.of("Miguel", "", "Employee", "DevOps Engineer", 5, "Last Name field must not be empty.")
        );
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidLastName")
    void shouldThrowExceptionIfLastNameIsInvalid(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobTitle, jobYears);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }


    public static Stream<Arguments> provideInvalidDescription() {
        return Stream.of(
                Arguments.of("Miguel", "Antunes", null, "DevOps Engineer", 5, "Description field must not be empty."),
                Arguments.of("Miguel", "Antunes", "", "DevOps Engineer", 5, "Description field must not be empty.")
        );
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidDescription")
    void shouldThrowExceptionIfDescriptionIsInvalid(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobTitle, jobYears);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    public static Stream<Arguments> provideInvalidJobTitle() {
        return Stream.of(
                Arguments.of("Miguel", "Antunes", "Employee", null, 5, "Job Title field must not be empty."),
                Arguments.of("Miguel", "Antunes", "Employee", "", 5, "Job Title field must not be empty.")
        );
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidJobTitle")
    void shouldThrowExceptionIfJobTitleIsInvalid(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobTitle, jobYears);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    public static Stream<Arguments> provideInvalidJobYears() {
        return Stream.of(
                Arguments.of("Miguel", "Antunes", "Employee", "DevOps Engineer", null, "Job Years field must be a positive value."),
                Arguments.of("Miguel", "Antunes", "Employee", "DevOps Engineer", -1, "Job Years field must be a positive value.")
        );
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidJobYears")
    void shouldThrowExceptionIfJobYearsIsInvalid(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobTitle, jobYears);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEqualsMethod() {
        // Arrange
        Employee employee1 = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        String firstName = employee1.getFirstName();

        // Assert
        assertEquals("Miguel", firstName);
    }

    @Test
    void testEqualsMethodIsNotEqual() {
        // Arrange
        Employee employee1 = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);
        Employee employee2 = new Employee("Eduarda", "Antunes", "Employee", "Marketeer", 0);

        // Act
        employee1.equals(employee2);

        // Assert
        assertFalse(employee1.equals(employee2));
    }

    @Test
    void testHashCode() {
        // Arrange
        Employee employee1 = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);
        Employee employee2 = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act + Assert
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        // Arrange
        Employee employee1 = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);
        Employee employee2 = new Employee("Eduarda", "Antunes", "Employee", "Marketeer", 0);

        // Act + Assert
        assertNotEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testGetIdAndSetId() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        employee.setId(1L);

        //Assert
        assertEquals(1L, employee.getId());
    }

    @Test
    void testGetFirstNameAndSetFirstName() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        employee.setFirstName("Filipe");

        // Assert
        assertEquals("Filipe", employee.getFirstName());
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidFirstName")
    void testInvalidSetFirstName(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Arrange
        Employee employee = new Employee("Miguel", lastName, description, jobTitle, jobYears);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employee.setFirstName(firstName);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetLastNameAndSetLastName() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        employee.setLastName("Sousa");

        // Assert
        assertEquals("Sousa", employee.getLastName());
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidLastName")
    void testInvalidSetLastName(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Arrange
        Employee employee = new Employee(firstName, "Antunes", description, jobTitle, jobYears);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employee.setLastName(lastName);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetDescriptionAndSetDescription() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        employee.setDescription("Unemployed");

        // Assert
        assertEquals("Unemployed", employee.getDescription());
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidDescription")
    void testInvalidSetDescription(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Arrange
        Employee employee = new Employee(firstName, lastName, "Employee", jobTitle, jobYears);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employee.setDescription(description);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetJobTitleAndSetJobTitle() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        employee.setJobTitle("Marketeer");

        // Assert
        assertEquals("Marketeer", employee.getJobTitle());
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidJobTitle")
    void testInvalidSetJobTitle(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Arrange
        Employee employee = new Employee(firstName, lastName, description, "DevOps Engineer", jobYears);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employee.setJobTitle(jobTitle);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetJobYearsAndSetJobYears() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        // Act
        employee.setJobYears(0);

        // Assert
        assertEquals(0, employee.getJobYears());
    }

    @ParameterizedTest
    @MethodSource ("provideInvalidJobYears")
    void testInvalidSetJobYears(String firstName, String lastName, String description, String jobTitle, Integer jobYears, String expectedMessage) {
        // Arrange
        Employee employee = new Employee(firstName, lastName, description, jobTitle, 5);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employee.setJobYears(jobYears);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testToString() {
        // Arrange
        Employee employee = new Employee("Miguel", "Antunes", "Employee", "DevOps Engineer", 5);

        String expected = "Employee{" +
                "id=" + employee.getId() +
                ", firstName='" + employee.getFirstName() + '\'' +
                ", lastName='" + employee.getLastName() + '\'' +
                ", description='" + employee.getDescription() + '\'' +
                ", jobTitle='" + employee.getJobTitle() + '\'' +
                ", jobYears='" + employee.getJobYears() + '\'' +
                '}';
        // Act and Assert
        assertEquals(expected, employee.toString());
    }
}