package model;

import java.io.Serializable;

public class Student implements Serializable {
    private String studentID;
    private String name;
    private String program;
    private Role role;
    private static int totalStudents = 0;


    public Student (String studentID, String name, String program) {
        this.studentID = studentID;
        this.name = name;
        this.program = program;
        this.role = Role.STUDENT;
        totalStudents++ ;
    }

    // Getters
    public String getStudentID() { return studentID; }
    public String getName()      { return name; }
    public String getProgram()   { return program; }
    public Role   getRole()      { return role; }

    public static int getTotalStudents() { return totalStudents; }

    // Setters
    public void setName(String name)       { this.name    = name; }
    public void setProgram(String program) { this.program = program; }


    //  JTable helper
    public Object[] toTableRow() {
        return new Object[]{
                studentID,
                name,
                program
        };
    }

    // Equals / hashCode on studentID
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        return studentID.equals(((Student) obj).studentID);
    }

    @Override
    public int hashCode() {
        return studentID.hashCode();
    }

    @Override
    public String toString() {
        return studentID + " - " + name + " (" + program + ")";
    }
}