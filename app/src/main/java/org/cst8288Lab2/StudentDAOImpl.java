package org.cst8288Lab2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File name: StudentDAOImpl.java 
 * Author: Tsaichun Chang
 * Course: CST8288-022
 * Assignment: Lab2
 * Date: 2024-03-03
 * Lab Professor: Gustavo Adami
 *
 * @author Tsaichun Chang 
 * @version 1
 * @since JDK 18.0.2.1
 * @see Student
 * @see StudentDAO
 * 
 * Implementation of the StudentDAO interface for database operations related to the Student entity.
 * Provides methods to perform create, read, update, and delete operations on Student records within a database.
 */
public class StudentDAOImpl implements StudentDAO{
    
    /**
    * The database connection used by this DAO to perform SQL operations.
    * This connection must be established and passed to the DAO before any operations are performed.
    * It is passed to the DAO via the constructor and stored in this private field.
    */
    private Connection connection;

    /**
     * Constructs a StudentDAOImpl with the given database connection.
     * 
     * @param connection the Connection object to the database
     */
    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Inserts a Student record into the database.
     * 
     * @param student the Student object to be inserted
     * @return true if the insert operation was successful, false otherwise
     */
    @Override
    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO Student (studentId, firstName, lastName) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all Student records from the database.
     * 
     * @return a List of Student objects
     */
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Retrieves a Student record by its student ID.
     * 
     * @param studentId the ID of the student to be retrieved
     * @return the Student object if found, null otherwise
     */
    @Override
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM Student WHERE studentId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("studentId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing Student record in the database.
     * 
     * @param student the Student object to be updated
     * @return true if the update operation was successful, false otherwise
     */
    @Override
    public boolean updateStudent(Student student) {
        String sql = "UPDATE Student SET firstName = ?, lastName = ? WHERE studentId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3, student.getStudentId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a Student record from the database based on the student ID.
     * 
     * @param studentId the ID of the student to be deleted
     * @return true if the delete operation was successful, false otherwise
     */
    @Override
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM Student WHERE studentId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
