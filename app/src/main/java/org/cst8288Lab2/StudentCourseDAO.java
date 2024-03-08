package org.cst8288Lab2;

import java.util.List;

/**
 * File name: StudentCourseDAO.java 
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
 * @see StudentCourseDAOImpl
 * @see StudentCourse
 * 
 * Defines the contract for the DAO handling student-course relationships. 
 * This interface outlines the standard operations to be performed on the student-course data set,
 * including creating, retrieving, updating, and deleting student-course enrollments.
 *
 */
public interface StudentCourseDAO {
    
    /**
     * Inserts a new student-course enrollment record into the data source.
     *
     * @param studentCourse the {@link StudentCourse} object representing the enrollment to be inserted.
     * @return {@code true} if the insertion is successful, {@code false} otherwise.
     */
    boolean insertStudentCourse(StudentCourse studentCourse);
    
    /**
     * Retrieves all student-course enrollment records from the data source.
     *
     * @return a list of {@link StudentCourse} objects, each representing an enrollment. 
     * The list will be empty if no records are found.
     */
    List<StudentCourse> getAllStudentCourses();
    
    /**
     * Retrieves a specific student-course enrollment record identified by student ID and course ID.
     *
     * @param studentId the ID of the student.
     * @param courseId the ID of the course.
     * @return the {@link StudentCourse} object representing the enrollment if found, {@code null} otherwise.
     */
    StudentCourse getStudentCourse(int studentId, String courseId);
    
    /**
     * Updates an existing student-course enrollment record in the data source.
     *
     * @param studentCourse the {@link StudentCourse} object containing the updated enrollment information.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
    boolean updateStudentCourse(StudentCourse studentCourse);
    
    /**
     * Deletes a specific student-course enrollment record identified by student ID and course ID from the data source.
     *
     * @param studentId the ID of the student.
     * @param courseId the ID of the course.
     * @return {@code true} if the deletion is successful, {@code false} otherwise.
     */
    boolean deleteStudentCourse(int studentId, String courseId);
    
}
