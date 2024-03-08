package org.cst8288Lab2;

/**
 * File name: Student.java 
 * Author: Tsaichun Chang
 * Course: CST8288-022
 * Assignment: Lab2
 * Date: 2024-03-03
 * Lab Professor: Gustavo Adami
 *
 * @author Tsaichun Chang 
 * @version 1
 * @since JDK 18.0.2.1
 * @see StudentDAO
 * @see StudentDAOImpl
 * 
 * Represents a student with a unique ID, first name, and last name.
 * This class encapsulates the basic attributes of a student, 
 * providing accessors and mutators for each attribute.
 */
public class Student {
    
    /**
     * The unique identifier for the student.
     */
    private int studentId;
    
    /**
     * The first name of the student.
     */
    private String firstName;
    
    /**
     * The last name of the student.
     */
    private String lastName;

    /**
     * Constructs a new Student with the specified ID, first name, and last name.
     *
     * @param studentId the unique identifier for the student
     * @param firstName the first name of the student
     * @param lastName the last name of the student
     */
    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the student's unique identifier.
     *
     * @return the student ID
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the student's unique identifier.
     *
     * @param studentId the new student ID
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the student's first name.
     *
     * @return the first name of the student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the student's first name.
     *
     * @param firstName the new first name for the student
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the student's last name.
     *
     * @return the last name of the student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the student's last name.
     *
     * @param lastName the new last name for the student
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
