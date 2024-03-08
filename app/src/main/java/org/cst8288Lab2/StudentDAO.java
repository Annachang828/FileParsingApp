package org.cst8288Lab2;

import java.util.List;

/**
 * File name: StudentDAO.java 
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
 * @see StudentDAOImpl
 * 
 * Interface for data access operations related to students.
 * This interface defines the standard operations to be performed on a model object 'Student'.
 */
public interface StudentDAO {
    
    /**
     * Inserts a new Student record into the data store.
     *
     * @param student the Student object to be inserted
     * @return true if the operation was successful, false otherwise
     */
    boolean insertStudent(Student student);
    
    /**
     * Retrieves all Student records from the data store.
     *
     * @return a List of all Student objects
     */
    List<Student> getAllStudents();
    
    /**
     * Retrieves a single Student record identified by its student ID.
     *
     * @param studentId the unique ID of the student
     * @return the Student object if found, null otherwise
     */
    Student getStudentById(int studentId);
    
    /**
     * Updates an existing Student record in the data store with the details of the provided Student object.
     *
     * @param student the Student object that contains updated properties
     * @return true if the operation was successful, false otherwise
     */
    boolean updateStudent(Student student);
    
    /**
     * Deletes the Student record identified by its student ID from the data store.
     *
     * @param studentId the unique ID of the student to be deleted
     * @return true if the operation was successful, false otherwise
     */
    boolean deleteStudent(int studentId);
    
}
