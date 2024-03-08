package org.cst8288Lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * File name: StudentCourseDAOImpl.java 
 * Author: Tsaichun Chang
 * Course: CST8288-022
 * Assignment: Lab2
 * Date: 2024-03-03
 * Lab Professor: Gustavo Adami
 *
 * @author Tsaichun Chang 
 * @version 1
 * @since JDK 18.0.2.1
 * @see StudentCourseDAO
 * @see StudentCourse
 * 
 * Implementation of the StudentCourseDAO interface for managing student course enrollments in the database.
 * This class provides CRUD operations for the StudentCourse entity, allowing manipulation of the student course records.
 */
public class StudentCourseDAOImpl implements StudentCourseDAO{
    
    /**
    * The database connection used by this DAO to perform SQL operations.
    * This connection must be established and passed to the DAO before any operations are performed.
    * It is passed to the DAO via the constructor and stored in this private field.
    */
    private Connection connection;

    /**
     * Creates a new StudentCourseDAOImpl with the specified database connection.
     * 
     * @param connection the Connection object to the database
     */
    public StudentCourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new student course enrollment record into the database.
     * 
     * @param studentCourse the StudentCourse object to be inserted
     * @return true if the insert operation was successful, false otherwise
     */
    @Override
    public boolean insertStudentCourse(StudentCourse studentCourse) {
        String sql = "INSERT INTO StudentCourse (studentId, courseId, term, year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentCourse.getStudentId());
            pstmt.setString(2, studentCourse.getCourseId());
            pstmt.setInt(3, studentCourse.getTerm());
            pstmt.setInt(4, studentCourse.getYear());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all student course enrollment records from the database.
     * 
     * @return a List of StudentCourse objects
     */
    @Override
    public List<StudentCourse> getAllStudentCourses() {
        List<StudentCourse> studentCourses = new ArrayList<>();
        String sql = "SELECT * FROM StudentCourse";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                StudentCourse studentCourse = new StudentCourse(
                        rs.getInt("studentId"),
                        rs.getString("courseId"),
                        rs.getInt("term"),
                        rs.getInt("year"));
                studentCourses.add(studentCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCourses;
    }

    /**
     * Retrieves a specific student course enrollment record by student ID and course ID.
     * 
     * @param studentId the ID of the student
     * @param courseId the ID of the course
     * @return the StudentCourse object if found, null otherwise
     */
    @Override
    public StudentCourse getStudentCourse(int studentId, String courseId) {
        String sql = "SELECT * FROM StudentCourse WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setString(2, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new StudentCourse(
                            rs.getInt("studentId"),
                            rs.getString("courseId"),
                            rs.getInt("term"),
                            rs.getInt("year"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing student course enrollment record in the database.
     * 
     * @param studentCourse the StudentCourse object that contains updated properties
     * @return true if the update operation was successful, false otherwise
     */
    @Override
    public boolean updateStudentCourse(StudentCourse studentCourse) {
        String sql = "UPDATE StudentCourse SET term = ?, year = ? WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentCourse.getTerm());
            pstmt.setInt(2, studentCourse.getYear());
            pstmt.setInt(3, studentCourse.getStudentId());
            pstmt.setString(4, studentCourse.getCourseId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a student course enrollment record from the database based on the student ID and course ID.
     * 
     * @param studentId the ID of the student
     * @param courseId the ID of the course
     * @return true if the delete operation was successful, false otherwise
     */
    @Override
    public boolean deleteStudentCourse(int studentId, String courseId) {
        String sql = "DELETE FROM StudentCourse WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setString(2, courseId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
