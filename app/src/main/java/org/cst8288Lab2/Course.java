package org.cst8288Lab2;

/**
 * File name: Course.java 
 * Author: Tsaichun Chang
 * Course: CST8288-022
 * Assignment: Lab2
 * Date: 2024-03-03
 * Lab Professor: Gustavo Adami
 *
 * @author Tsaichun Chang 
 * @version 1
 * @since JDK 18.0.2.1
 * @see CourseDAO
 * @see CourseDAOImpl
 *
 * Represents a course with a unique ID and a name.
 * This class encapsulates the basic properties of a course, 
 * including its identifier and name, providing getters and setters for them.
 *
 */
public class Course {
    
    /**
     * The unique identifier for the course.
     */
    private String courseId;
    
    /**
     * The name of the course.
     */
    private String courseName;

    /**
     * Constructs a new Course with the specified ID and name.
     *
     * @param courseId the unique identifier for the course, not null.
     * @param courseName the name of the course, not null.
     */
    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    /**
     * Returns the course's unique identifier.
     *
     * @return the course ID.
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the course's unique identifier.
     *
     * @param courseId the new identifier for the course.
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the course's name.
     *
     * @return the name of the course.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course's name.
     *
     * @param courseName the new name for the course.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
}
