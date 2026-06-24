package main;
import controller.CampusManager;
import model.*;

import javax.swing.*;
import gui.loginFrame;

public class TestRun {

    public static void main(String[] args) {

        // Load Saved System
        CampusManager manager =
                CampusManager.loadSystem("campus.dat");

        // START LOGIN GUI ONLY
        SwingUtilities.invokeLater(() -> {
            new loginFrame().setVisible(true);
        });

        // Data Validation
        manager.validateData();

        // Academic Test
        Department dept = new Department("D1", "CS" , "Block A", 200, 50);

        Course course = new Course("C1", "OOP");

        CampusManager cm = new CampusManager();
        cm.findStudentByID("course");

        Student s1 = new Student("S1", "Ashar", "AI");
        Student s2 = new Student("S2", "Jawad", "CS");

        course.addStudent(s1);
        course.addStudent(s2);

        course.addAssignment(new Assignment("Assignment 1", 10));

        dept.addCourse(course);

        System.out.println(course);
        dept.generateReport();

        // Facility + Zone Test
        Library lib = new Library("L1", "Central Library", "Block B", 5000, 100);
        Book b1 = new Book(1, "Java Programming");
        lib.addBook(b1);

        Cafeteria cafe = new Cafeteria("CAF1", "Main Cafe", "Block C", 3000, 80, 100);

        SecurityService sec = new SecurityService("SEC1", "Campus Security", "Gate", 24, 10);
        HealthCenter hc = new HealthCenter("HC1", "Health Unit", "Block D", 12, 5);

        CampusZone zone = new CampusZone("Zone A");

        zone.addFacility(lib);
        zone.addFacility(cafe);

        zone.addServices(sec);
        zone.addServices(hc);

        zone .displayZoneDetails();

        // Notification Test
        sec.sendNotification("Unauthorized entry detected!");
        hc.sendNotification("Medical Emergency in Block A!");

        // Manager + Logic Test
        manager.addStudent(s1);
        manager.addStudent(s2);
        manager.addStudent(s1); // duplicate test (1 error)
        manager.addCourse(course);
        manager.enrollStudentInCourse(s1, course);

        System.out.println(course);



        // Emergency System
        manager.handleEmergency(sec, hc, "Emergency in LAB!");

        // Dynamic Scheduling
        Classroom room = new Classroom("CR1", "Room A", "Block A", 60,15,false);

        manager.checkAndReschedule(room, course);

        // Transport System
        TransportService ts = new TransportService("T1", "Transport", "Campus", 12, 5);
        ts.adjustForPeakHours(true);

        // Static Test
        System.out.println("Total Students: " + Student.getTotalStudents());

        // Role-Based Access Test
        Admin admin = new Admin("A1", "Super Admin");

        manager.performAdminTask(Role.ADMIN); // Allowed
        manager.performAdminTask(Role.STUDENT); // Denied

        // Report Data Test
        Object[][] studentData = manager.getAllStudentsData();

        System.out.println("Student Table Data");
        for(Object[] row: studentData) {
            for(Object col: row) {
                System.out.print(col + " | ");
            }
            System.out.println();
        }

        // Save + AutoSave
        manager.saveSystem("campus.dat");
        manager.autoSave();


    }
}
