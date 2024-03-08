package org.cst8288Lab2;
    
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * File name: CourseDAOImpl.java 
 * Author: Tsaichun Chang
 * Course: CST8288-022
 * Assignment: Lab2
 * Date: 2024-03-03
 * Lab Professor: Gustavo Adami
 *
 * @author Tsaichun Chang 
 * @version 1
 * @since JDK 18.0.2.1
 * @see Course
 * @see CourseDAO
 * 
 * Implementation of the CourseDAO interface, providing concrete JDBC-based persistence operations
 * for {@link Course} entities. This class encapsulates SQL operations for inserting, updating,
 * deleting, and querying Course records in the database.
 */
public class CourseDAOImpl implements CourseDAO {
    
    /**
    * The database connection used by this DAO to perform SQL operations.
    * This connection must be established and passed to the DAO before any operations are performed.
    * It is passed to the DAO via the constructor and stored in this private field.
    */
    private final Connection connection;

    /**
     * Constructs a CourseDAOImpl with a specific database connection.
     *
     * @param connection the {@link Connection} to be used for database operations, not {@code null}.
     */
    public CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new {@link Course} record into the database.
     *
     * @param course the {@link Course} to be inserted; not {@code null}.
     * @return {@code true} if the insert operation is successful, {@code false} otherwise.
     */
    @Override
    public boolean insertCourse(Course course) {
        String sql = "INSERT INTO Course (courseId, courseName) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all {@link Course} records from the database.
     *
     * @return a {@link List} of {@link Course} objects, possibly empty but never {@code null}.
     */
    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getString("courseId"),
                        rs.getString("courseName"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Retrieves a {@link Course} record by its course ID.
     *
     * @param courseId the unique ID of the course to retrieve; not {@code null}.
     * @return the {@link Course} if found, {@code null} otherwise.
     */
    @Override
    public Course getCourseById(String courseId) {
        String sql = "SELECT * FROM Course WHERE courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getString("courseId"),
                            rs.getString("courseName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing {@link Course} record in the database with the information
     * contained in the provided {@link Course} object.
     *
     * @param course the {@link Course} containing updated information; not {@code null}.
     * @return {@code true} if the update operation is successful, {@code false} otherwise.
     */
    @Override
    public boolean updateCourse(Course course) {
        String sql = "UPDATE Course SET courseName = ? WHERE courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a {@link Course} record from the database, identified by its course ID.
     *
     * @param courseId the unique ID of the course to delete; not {@code null}.
     * @return {@code true} if the delete operation is successful, {@code false} otherwise.
     */
    @Override
    public boolean deleteCourse(String courseId) {
        String sql = "DELETE FROM Course WHERE courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
