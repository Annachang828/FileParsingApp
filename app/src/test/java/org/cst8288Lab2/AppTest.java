package org.cst8288Lab2;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


/**
 * File name: AppTest.java 
 * Author: Tsaichun Chang
 * Course: CST8288-022
 * Assignment: Lab2
 * Date: 2024-03-03
 * Lab Professor: Gustavo Adami
 *
 * @author Tsaichun Chang 
 * @version 1
 * @since JDK 18.0.2.1
 * 
 * Test class for {@link App}'s field validation functionality.
 * This class contains unit tests that verify the field validation logic within the {@code App} class,
 * ensuring that valid and invalid inputs are correctly identified.
 *
 */
class AppTest {

    /**
     * Verifies that field validation passes when all input fields are valid.
     * This test provides a set of valid fields to the {@code validateFields} method and expects
     * the validation to succeed with no errors logged.
     */
    @Test
    void whenFieldsAreValid_thenValidationShouldPass() {
        String[] fields = {"123456789", "John", "Doe", "CSE1001", "Software Engineering", "WINTER", "2024"};
        List<String> errorLogs = new ArrayList<>();

        boolean isValid = App.validateFields(fields, 1, errorLogs);

        assertTrue(isValid, "Valid fields should pass validation.");
        assertTrue(errorLogs.isEmpty(), "No errors should be logged for valid fields.");
    }

    /**
     * Tests that the validation fails when the student ID is invalid.
     * This test checks that the {@code validateFields} method correctly identifies an invalid student ID
     * and logs an appropriate error message.
     */
    @Test
    void whenStudentIdIsInvalid_thenValidationShouldFail() {
        String[] fields = {"12345", "John", "Doe", "CSE1001", "Software Engineering", "WINTER", "2024"};
        List<String> errorLogs = new ArrayList<>();

        boolean isValid = App.validateFields(fields, 2, errorLogs);

        assertFalse(isValid, "Invalid student ID should fail validation.");
        assertFalse(errorLogs.isEmpty(), "An error should be logged for invalid student ID.");
        assertTrue(errorLogs.get(0).contains("Invalid student ID format"), "Error message should mention invalid student ID format.");
    }

    /**
     * Tests that the validation fails when the course ID is invalid.
     * This test ensures that the {@code validateFields} method properly identifies an invalid course ID
     * and logs a corresponding error message.
     */
    @Test
    void whenCourseIdIsInvalid_thenValidationShouldFail() {
        String[] fields = {"123456789", "John", "Doe", "CS1001", "Software Engineering", "WINTER", "2024"};
        List<String> errorLogs = new ArrayList<>();

        boolean isValid = App.validateFields(fields, 3, errorLogs);

        assertFalse(isValid, "Invalid course ID should fail validation.");
        assertFalse(errorLogs.isEmpty(), "An error should be logged for invalid course ID.");
        assertTrue(errorLogs.get(0).contains("Invalid course ID format"), "Error message should mention invalid course ID format.");
    }

    /**
     * Verifies that validation fails for an invalid term.
     * This test checks that the {@code validateFields} method accurately flags an invalid term input
     * and records an appropriate error in the logs.
     */
    @Test
    void whenTermIsInvalid_thenValidationShouldFail() {
        String[] fields = {"123456789", "John", "Doe", "CSE1001", "Software Engineering", "SPRING", "2024"};
        List<String> errorLogs = new ArrayList<>();

        boolean isValid = App.validateFields(fields, 4, errorLogs);

        assertFalse(isValid, "Invalid term should fail validation.");
        assertFalse(errorLogs.isEmpty(), "An error should be logged for invalid term.");
        assertTrue(errorLogs.get(0).contains("Invalid term format"), "Error message should mention invalid term format.");
    }

    /**
     * Ensures that the validation process identifies an invalid year.
     * This test confirms that the {@code validateFields} method correctly detects when the year input
     * is out of the accepted range and logs a suitable error message.
     */
    @Test
    void whenYearIsInvalid_thenValidationShouldFail() {
        String[] fields = {"123456789", "John", "Doe", "CSE1001", "Software Engineering", "WINTER", "1964"};
        List<String> errorLogs = new ArrayList<>();

        boolean isValid = App.validateFields(fields, 5, errorLogs);

        assertFalse(isValid, "Invalid year should fail validation.");
        assertFalse(errorLogs.isEmpty(), "An error should be logged for invalid year.");
        assertTrue(errorLogs.get(0).contains("Invalid year format or out of range"), "Error message should mention invalid year format or out of range.");
    }
    
}
