package model;

import  java.io.Serializable;
import  java.util.ArrayList;
import interfaces.Schedulable;

import javax.swing.*;

public class Course implements Serializable, Schedulable {
    private String courseID;
    private String courseName;
    private ArrayList<Student> students;
    private String studentID;
    private ArrayList<Assignment> assignments;
    private static int totalCourses = 0;

    public Course(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
        this.studentID = studentID;
        totalCourses++ ;
    }


    // Getters
    public String getCourseID()   { return courseID; }

    public Object getStudentID() {
        return studentID;
    }

    public String getCourseName() { return courseName; }
    public ArrayList<Student> getStudents()    { return students;    }
    public ArrayList<Assignment> getAssignments() { return assignments; }
    public static int getTotalCourses() { return totalCourses; }


    public void addStudent(Student student) {
        if (!students.contains(student))
            students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }


    // JTable Helper
    public Object[] toTableRow() {
        return new Object[]{
                courseID,
                courseName,
                students.size(),
                assignments.size()
        };
    }

    // Equals / hashCode on courseID
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        return courseID.equals(((Course) obj).courseID);
    }

    @Override
    public int hashCode() {   // If two objects are equal they MUST have same Hashcode.
        return courseID.hashCode(); }    // Converts String into integer hash.


    // Schedulable
    @Override
    public void generateSchedule() {
        System.out.println("Schedule generated for course: " + courseName);
    }

    @Override
    public String toString() {
        return courseID + " - " + courseName + " | Students: " + students.size();
    }


}
