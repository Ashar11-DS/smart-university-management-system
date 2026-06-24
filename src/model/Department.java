package model;

import java.util.ArrayList;
import interfaces.Reportable;

public class Department extends AcademicUnit implements Reportable {
    private ArrayList<Course> courses;

    public Department(String id, String name, String location, int students, int equipment) {
        super(id, name, location, students, equipment);
        this.courses = new ArrayList<>();
    }

    //converts entire Course list to 2D array in Table format(JTable ready)
    public Object[][] getCourseTableData() {
        Object[][] data = new Object[courses.size()][4];

        for (int i = 0; i < courses.size() ; i++) {
            data[i] = courses.get(i).toTableRow();
        }
        return  data;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public ArrayList<Course> getCourses() {
        return  courses;
    }

    @Override
    public void generateReport() {
        System.out.println("Department Report: " + name);
        System.out.println("Total Courses: " + courses.size());
    }

    @Override
    public String toString() {
        return super.toString() + " | Courses: " + courses.size();
    }
}
