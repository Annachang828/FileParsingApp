package org.cst8288Lab2;

/**
 * File name: StudentCourse.java 
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
 * @see StudentCourseDAO
 * 
 * Represents the association between a student and a course, including the term and year of enrollment.
 * This class encapsulates the details of a student's enrollment in a specific course,
 * including the term and year when the course is taken.
 */
public class StudentCourse {
    
    /**
     * The ID of the student.
     */
    private int studentId;
    
    /**
     * The ID of the course.
     */
    private String courseId;
    
    /**
     * The term during which the course is taken.
     */
    private int term;
    
    /**
     * The year during which the course is taken.
     */
    private int year;

    /**
     * Constructs a new StudentCourse with the specified student ID, course ID, term, and year.
     *
     * @param studentId the ID of the student
     * @param courseId the ID of the course
     * @param term the term during which the course is taken
     * @param year the year during which the course is taken
     */
    public StudentCourse(int studentId, String courseId, int term, int year) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.term = term;
        this.year = year;
    }

    /**
     * Returns the ID of the student.
     *
     * @return the student ID
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the ID of the student.
     *
     * @param studentId the new student ID
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the ID of the course.
     *
     * @return the course ID
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the ID of the course.
     *
     * @param courseId the new course ID
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the term during which the course is taken.
     *
     * @return the term
     */
    public int getTerm() {
        return term;
    }

    /**
     * Sets the term during which the course is taken.
     *
     * @param term the new term
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     * Returns the year during which the course is taken.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year during which the course is taken.
     *
     * @param year the new year
     */
    public void setYear(int year) {
        this.year = year;
    }
    
}
