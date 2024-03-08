package org.cst8288Lab2;

import java.util.List;

/**
 * File name: CourseDAO.java 
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
 * @see CourseDAOImpl
 * 
 * Interface for data access operations related to courses.
 * Defines the standard CRUD (Create, Read, Update, Delete) operations to be performed on a course entity.
 */
public interface CourseDAO {
    
    /**
     * Inserts a new course into the data source.
     *
     * @param course the course to insert; must not be {@code null}.
     * @return {@code true} if the course was successfully inserted, {@code false} otherwise.
     */
    boolean insertCourse(Course course);
    
    /**
     * Retrieves all courses from the data source.
     *
     * @return a list of courses, which may be empty but is never {@code null}.
     */
    List<Course> getAllCourses();
    
    /**
     * Retrieves a course by its unique identifier.
     *
     * @param courseId the ID of the course to retrieve; must not be {@code null}.
     * @return the found course if available, {@code null} otherwise.
     */
    Course getCourseById(String courseId);
    
    /**
     * Updates the details of an existing course in the data source.
     *
     * @param course the course with updated details; must not be {@code null}.
     * @return {@code true} if the course was successfully updated, {@code false} otherwise.
     */
    boolean updateCourse(Course course);
    
    /**
     * Deletes a course from the data source by its unique identifier.
     *
     * @param courseId the ID of the course to delete; must not be {@code null}.
     * @return {@code true} if the course was successfully deleted, {@code false} otherwise.
     */
    boolean deleteCourse(String courseId);
    
}
