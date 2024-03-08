package org.cst8288Lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * File name: App.java 
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
 * Main application class for processing bulk CSV data into a database.
 * This class is responsible for loading application properties, establishing database connections,
 * and processing a CSV file to insert data into Students, Courses, and StudentCourses tables.
 */
public class App {
    
    /**
    * The number of student records successfully added to the database.
    * This counter is incremented each time a student record is successfully inserted.
    */
    private static int studentsAdded = 0;
    
    /**
    * The number of course records successfully added to the database.
    * This counter is incremented each time a course record is successfully inserted.
    */
    private static int coursesAdded = 0;
    
    /**
    * The number of student-course association records successfully added to the database.
    * This counter is incremented each time a student-course record is successfully inserted.
    */
    private static int studentCoursesAdded = 0;
    
    /**
    * The path to the properties file containing database configuration.
    * This file includes information necessary for establishing a database connection,
    * such as the URL, user, and password.
    */
    private static final String PROPERTIES_PATH = "./app/data/database.properties.cfg";
    
    /**
    * The path to the CSV file containing bulk import data.
    */
    private static final String CSV_PATH = "./app/data/bulk-import.csv";
    
    /**
    * The path to the markdown file where the success report will be generated.
    * This report includes details on the records successfully added to the database.
    */
    private static final String SUCCESS_REPORT_PATH = "./app/data/import-report.md";
    
    /**
    * The path to the markdown file where the error report will be generated.
    * This report includes details on any errors or issues encountered during the data import process.
    */
    private static final String ERROR_REPORT_PATH = "./app/data/error-report.md";
    
    /**
     * Parses the file: bulk-import.csv
     * Validates each item in each row and updates the database accordingly.
     * @param args - Command-line arguments (not used)
     */
    public static void main(String[] args) {

        Properties dbProperties = loadProperties(PROPERTIES_PATH);
        if (dbProperties == null) {
            System.out.println("Unable to load database properties.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(
                dbProperties.getProperty("database.url"),
                dbProperties.getProperty("user"),
                dbProperties.getProperty("pass"))) {

            StudentDAO studentDao = new StudentDAOImpl(connection);
            CourseDAO courseDao = new CourseDAOImpl(connection);
            StudentCourseDAO studentCourseDao = new StudentCourseDAOImpl(connection);

            List<String> successLogs = new ArrayList<>();
            List<String> errorLogs = new ArrayList<>();

            parceCsv(CSV_PATH, studentDao, courseDao, studentCourseDao, successLogs, errorLogs);

            generateReport(SUCCESS_REPORT_PATH, successLogs, true);
            generateReport(ERROR_REPORT_PATH, errorLogs, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads database connection properties from a specified file path.
     * 
     * @param filePath The path to the properties file.
     * @return A {@link Properties} object containing database connection details.
     */
    private static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            return properties;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Parses the CSV file, validates its data, and updates the database accordingly.
     * It logs successes and errors during the data import process.
     * 
     * @param filePath The path to the CSV file.
     * @param studentDao The data access object for students.
     * @param courseDao The data access object for courses.
     * @param studentCourseDao The data access object for student-course relations.
     * @param successLogs A list to log successful operations.
     * @param errorLogs A list to log errors and failed operations.
     */
    private static void parceCsv(String filePath, StudentDAO studentDao, CourseDAO courseDao, StudentCourseDAO studentCourseDao, List<String> successLogs, List<String> errorLogs) {
        String line;
        int lineNumber = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Skip header line
                if (lineNumber == 0) continue; 

                String[] fields = line.split(",");
                if (validateFields(fields, lineNumber, errorLogs)) {
                    // Fields order: [0]studentId, [1]firstName, [2]lastName, [3]courseId, [4]courseName, [5]term, [6]year
                    int studentId = Integer.parseInt(fields[0]);
                    String courseId = fields[3];
                    int term = convertTermToNumber(fields[5]);
                    int year = Integer.parseInt(fields[6]);
                  
                    Student student = new Student(studentId, fields[1], fields[2]);
                    if (studentDao.getStudentById(studentId) == null) {
                        
                        if (studentDao.insertStudent(student)) {
                            successLogs.add("Line " + lineNumber + " is inserted to Student table: studentId " + studentId);
                            studentsAdded++;
                        } else {
                            errorLogs.add("Failed to insert Student at line " + lineNumber + ". (" + student.getStudentId() + ", " + student.getFirstName() + ", " + student.getLastName() +" )");
                        }
                    } else {
                        errorLogs.add("Line " + lineNumber + " is already in Student table. (" + student.getStudentId() + ", " + student.getFirstName() + ", " + student.getLastName() +" )");
                    }
                    
                    Course course = new Course(courseId, fields[4]);
                    if (courseDao.getCourseById(courseId) == null) {  
                        if (courseDao.insertCourse(course)) {
                            successLogs.add("Line " + lineNumber + " is inserted to Course table: courseId " + courseId);
                            coursesAdded++;
                        } else {
                            errorLogs.add("Failed to insert Course at line " + lineNumber + ". ("  + course.getCourseId() + ", " + course.getCourseName() +" )");
                        }
                    } else {
                        errorLogs.add("Line " + lineNumber + " is already in Course table. (" + course.getCourseId() + ", " + course.getCourseName() +" )");
                    }

                    StudentCourse studentCourse = new StudentCourse(studentId, courseId, term, year);
                    if (studentCourseDao.getStudentCourse(studentId, courseId) == null) {
                        if (studentCourseDao.insertStudentCourse(studentCourse)) {
                            successLogs.add("Line " + lineNumber + " is inserted to StudentCourse table: StudentId " + studentId + ", CourseId " + courseId);
                            studentCoursesAdded++;
                        } else {
                            errorLogs.add("Failed to insert StudentCourse at line " + lineNumber + ". (" + studentCourse.getStudentId() + ", " + studentCourse.getCourseId() + ", " + studentCourse.getTerm() + ", " + studentCourse.getYear() +" )");
                        }
                    } else {
                        errorLogs.add("Line " + lineNumber + " is already in StudentCourse table. (" + studentCourse.getStudentId() + ", " + studentCourse.getCourseId() + ", " + studentCourse.getTerm() + ", " + studentCourse.getYear() +" )");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorLogs.add("Error reading CSV file: " + e.getMessage());
        }
        
    }

    /**
     * Validates the fields of a row from the CSV file.
     * 
     * @param fields An array of {@link String} representing the fields in a CSV row.
     * @param lineNumber The line number being processed for logging purposes.
     * @param errorLogs A list to log validation errors.
     * @return {@code true} if all fields are valid, {@code false} otherwise.
     */
    static boolean validateFields(String[] fields, int lineNumber, List<String> errorLogs) {
        
        StringBuilder errorMessage;
        
        if (fields.length != 7) {
            errorMessage = new StringBuilder("Line " + lineNumber + " validation failed: Invalid number of fields. ");
            errorMessage = messageBuilder (fields, errorMessage);
            errorLogs.add(errorMessage.toString());
            return false;
        }
        
        if (!Pattern.matches("\\d{9}", fields[0])) {
            errorMessage = new StringBuilder("Line " + lineNumber + " validation failed: Invalid student ID format. ");
            errorMessage = messageBuilder (fields, errorMessage);
            errorLogs.add(errorMessage.toString());
            return false;
        }
        if (!Pattern.matches("[a-zA-Z]{3}\\d{4}", fields[3])) {
            errorMessage = new StringBuilder("Line " + lineNumber + " validation failed: Invalid course ID format. ");
            errorMessage = messageBuilder (fields, errorMessage);
            errorLogs.add(errorMessage.toString());
            return false;
        }
        if (!Pattern.matches("WINTER|SUMMER|FALL", fields[5])) {
            errorMessage = new StringBuilder("Line " + lineNumber + " validation failed: Invalid term format. ");
            errorMessage = messageBuilder (fields, errorMessage);
            errorLogs.add(errorMessage.toString());
            return false;
        }
        if (!Pattern.matches("\\d{4}", fields[6]) || !isYearInRange(fields[6])) {
            errorMessage = new StringBuilder("Line " + lineNumber + " validation failed: Invalid year format or out of range. ");
            errorMessage = messageBuilder (fields, errorMessage);
            errorLogs.add(errorMessage.toString());
            return false;
        }
        return true;
    }

    /**
    * Constructs a formatted error message based on the fields array.
    *
    * This method takes an array of strings representing fields and constructs a formatted error message.
    * The fields are concatenated with commas and enclosed within parentheses.
    *
    * @param fields The array of strings representing fields.
    * @param errorMessage The StringBuilder object to which the formatted error message will be appended.
    * @return A StringBuilder object containing the formatted error message.
    */
    private static StringBuilder messageBuilder (String[] fields, StringBuilder errorMessage) {
        for (int i = 0; i < fields.length; i++) {
                if (i == 0) {
                    errorMessage.append("(").append(fields[i]).append(", ");
                } else if ( i > 0 && i < fields.length-1) {
                    errorMessage.append(fields[i]).append(", ");
                } else {
                    errorMessage.append(fields[i]).append(")");
                }
            }
            return errorMessage;
    }
    
    /**
     * Converts a term string to its corresponding numerical representation.
     * 
     * @param term The term string (e.g., "WINTER", "SUMMER", "FALL").
     * @return An {@code int} representing the term.
     */
    private static int convertTermToNumber(String term) {
        switch (term.toUpperCase()) {
            case "WINTER":
                return 1;
            case "SUMMER":
                return 2;
            case "FALL":
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Checks if a given year is within an acceptable range.
     * 
     * @param yearStr The year as a string.
     * @return {@code true} if the year is within range, {@code false} otherwise.
     */
    private static boolean isYearInRange(String yearStr) {
        int year = Integer.parseInt(yearStr);
        int foundingYear = 1965;
        int currentYear = java.time.Year.now().getValue();
        return year >= foundingYear && year <= currentYear;
    }

    /**
     * Generates a report based on the logs of data import operations.
     * 
     * @param filePath The path where the report will be saved.
     * @param logs A list of log messages to include in the report.
     * @param includeCounters A flag indicating whether to include counters in the report.
     */
    private static void generateReport(String filePath, List<String> logs, boolean includeCounters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (includeCounters) {
                writer.write("## Report generated on " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "\n\n");
                writer.write("Number of Entry Added to Student table: " + studentsAdded + "\n");
                writer.write("Number of Entry Added to Courses table: " + coursesAdded + "\n");
                writer.write("Number of Entry Added StudentCourse table: " + studentCoursesAdded + "\n\n");
            } 
            for (String log : logs) {
                writer.write(log + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
