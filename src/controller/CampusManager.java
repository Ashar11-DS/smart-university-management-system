package controller;

import interfaces.Notifiable;
import model.*;
import repository.CampusRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class CampusManager implements Serializable {

    // ── Repositories (Generic collections) ────────────────────────────────
    private CampusRepository<Student>   studentRepo;
    private CampusRepository<Course>    courseRepo;
    private CampusRepository<Facility>  facilityRepo;
    private CampusRepository<Classroom> classroomRepo;
    private CampusRepository<Lab>       labRepo;
    private CampusRepository<CampusZone> zoneRepo;
    private static int totalFacilityUsage = 0;   // incremented on each facility add

    public CampusManager() {
        studentRepo   = new CampusRepository<>();
        courseRepo    = new CampusRepository<>();
        facilityRepo  = new CampusRepository<>();
        classroomRepo = new CampusRepository<>();
        labRepo       = new CampusRepository<>();
        zoneRepo      = new CampusRepository<>();
    }

    // ══════════════════════════════════════════════════════════════════════
    //  STUDENT MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════

    public boolean addStudent(Student s) {
        try { studentRepo.add(s); return true; }
        catch (utils.DuplicateEntryException e) { return false; }
    }

    /** Remove student by ID. Returns true if found and removed. */
    public boolean removeStudent(String studentID) {
        Student target = findStudentByID(studentID);
        if (target == null) return false;
        studentRepo.remove(target);
        // also un-enroll from all courses
        for (Course c : courseRepo.getAll()) c.removeStudent(target);
        return true;
    }

    /** Update student name and program by ID. */
    public boolean updateStudent(String studentID, String newName, String newProgram) {
        Student s = findStudentByID(studentID);
        if (s == null) return false;
        s.setName(newName);
        s.setProgram(newProgram);
        return true;
    }

    public Student findStudentByID(String id) {
        for (Student s : studentRepo.getAll())
            if (s.getStudentID().equals(id)) return s;
        return null;
    }

    public CampusRepository<Student> getStudentRepo() { return studentRepo; }

    /** JTable-ready 2-D array */
    public Object[][] getAllStudentsData() {
        ArrayList<Student> list = studentRepo.getAll();
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++)
            data[i] = list.get(i).toTableRow();
        return data;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  COURSE MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════

    public boolean addCourse(Course c) {
        try { courseRepo.add(c); return true; }
        catch (utils.DuplicateEntryException e) { return false; }
    }

    public boolean removeCourse(String courseID) {
        Course target = findCourseByID(courseID);
        if (target == null) return false;
        courseRepo.remove(target);
        return true;
    }

    public boolean updateCourse(String courseID, String newName) {
        // Course name is set via a simple workaround — we store and replace
        Course old = findCourseByID(courseID);
        if (old == null) return false;
        // Transfer students and assignments to a new Course object
        Course updated = new Course(courseID, newName);
        for (Student s    : old.getStudents())
            updated.addStudent(s);
        for (Assignment a : old.getAssignments())
            updated.addAssignment(a);
        int idx = courseRepo.getAll().indexOf(old);
        courseRepo.update(idx, updated);
        return true;
    }

    public Course findCourseByID(String id) {
        for (Course c : courseRepo.getAll())
            if (c.getCourseID().equals(id))
                return c;

        return null;
    }

    public Course findStudentID(String id) {
        for (Course c : courseRepo.getAll())
            if (c.getStudentID().equals(id)){
                return c;

            }
        return null;
    }

    public CampusRepository<Course> getCourseRepo() { return courseRepo; }

    public Object[][] getAllCoursesData() {
        ArrayList<Course> list = courseRepo.getAll();
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++)
            data[i] = list.get(i).toTableRow();

        return data;
    }

    // Enroll student in course (Association b/w Student and course)
    public void enrollStudentInCourse(Student s, Course c) {
        c.addStudent(s);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  FACILITY MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════

    public boolean addFacility(Facility f) {
        try {
            facilityRepo.add(f);
            totalFacilityUsage++;
            return true;
        } catch (utils.DuplicateEntryException e) {
            return false;
        }
    }

    public boolean removeFacility(String entityID) {
        Facility target = findFacilityByID(entityID);
        if (target == null)
            return false;
        facilityRepo.remove(target);
        return true;
    }

    public Facility findFacilityByID(String id) {
        for (Facility f : facilityRepo.getAll())
            if (f.getEntityID().equals(id))
                return f;
        return null;
    }

    public CampusRepository<Facility> getFacilityRepo() { return facilityRepo; }

    public static int getTotalFacilityUsage() { return totalFacilityUsage; }

    // ══════════════════════════════════════════════════════════════════════
    //  CLASSROOM MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════

    public boolean addClassroom(Classroom cr) {
        try { classroomRepo.add(cr);
            return true; }
        catch (utils.DuplicateEntryException e) {
            return false; }
    }

    public boolean removeClassroom(String entityID) {
        for (Classroom c : classroomRepo.getAll()) {
            if (c.getEntityID().equals(entityID)) {
                classroomRepo.remove(c);
                return true;
            }
        }
        return false;
    }

    public CampusRepository<Classroom> getClassroomRepo() { return classroomRepo; }

    public Object[][] getAllClassroomsData() {
        ArrayList<Classroom> list = classroomRepo.getAll();
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            Classroom cr = list.get(i);
            data[i] = new Object[]{
                    cr.getEntityID(), cr.getName(), cr.getLocation(),
                    cr.isAvailable() ? "Available" : "Unavailable",
                    String.format("%.0f", cr.calculateOperationalCost())
            };
        }
        return data;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  LAB MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════

    public boolean addLab(Lab lab) {
        try { labRepo.add(lab); return true; }
        catch (utils.DuplicateEntryException e) { return false; }
    }

    public CampusRepository<Lab> getLabRepo() { return labRepo; }

    public Object[][] getAllLabsData() {
        ArrayList<Lab> list = labRepo.getAll();
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            Lab lab = list.get(i);
            data[i] = new Object[]{
                    lab.getEntityID(), lab.getName(), lab.getLocation(),
                    lab.getLabType(),
                    lab.isAvailable() ? "Available" : "Unavailable"
            };
        }
        return data;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  CAMPUS ZONE MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════

    public boolean addZone(CampusZone z) {
        try { zoneRepo.add(z); return true; }
        catch (utils.DuplicateEntryException e) { return false; }
    }

    public CampusRepository<CampusZone> getZoneRepo() { return zoneRepo; }

    // ══════════════════════════════════════════════════════════════════════
    //  COMPLEXITY: EMERGENCY + SCHEDULING
    // ══════════════════════════════════════════════════════════════════════

    /** Notifies two services simultaneously — demonstrates interface polymorphism */
    public void handleEmergency(Notifiable n1, Notifiable n2, String message) {
        System.out.println("EMERGENCY TRIGGERED");
        n1.sendNotification(message);
        n2.sendNotification(message);
    }

    /** If classroom is unavailable, triggers Course.generateSchedule() */
    public String checkAndReschedule(Classroom classroom, Course course) {
        if (!classroom.isAvailable()) {
            course.generateSchedule();
            return "Classroom " + classroom.getName()
                    + " unavailable — rescheduled: " + course.getCourseName();
        }
        return classroom.getName() + " is available. No rescheduling needed.";
    }

    // ══════════════════════════════════════════════════════════════════════
    //  ROLE-BASED ACCESS
    // ══════════════════════════════════════════════════════════════════════

    public boolean canManageStudents(Role role) {
        return role == Role.ADMIN || role == Role.TEACHER;  // Authorization + access Control
    }

    public boolean canManageCourses(Role role) {
        return role == Role.ADMIN || role == Role.TEACHER;
    }

    public boolean canManageFacilities(Role role) {
        return role == Role.ADMIN;
    }

    public boolean canViewReports(Role role) {
        return true;     // All roles can view reports
    }

    // Restricted Functionality
    public void performAdminTask(Role role) {
        if (role != Role.ADMIN) {
            System.out.println("Access Denied: Admin only.");
            return;
        }
        System.out.println("Admin task performed.");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PERSISTENCE
    // ══════════════════════════════════════════════════════════════════════

    public void saveSystem(String filename) {
        utils.FileHandler.saveData(this, filename);
        System.out.println("System saved to " + filename);
    }

    public static CampusManager loadSystem(String filename) {
        Object obj = utils.FileHandler.loadData(filename);
        if (obj instanceof CampusManager) {
            System.out.println("Data loaded from " + filename);
            return (CampusManager) obj;
        }
        System.out.println("Trying backup...");
        obj = utils.FileHandler.loadData("campus_backup.dat");
        if (obj instanceof CampusManager) {
            System.out.println("Backup loaded.");
            return (CampusManager) obj;
        }
        System.out.println("No data found. Starting fresh.");
        return new CampusManager();
    }

    // Backup Recovery ( Incase MainFile fails, current file is loaded )
    public void autoSave() {
        utils.FileHandler.saveData(this, "campus_backup.dat");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  VALIDATION ( Checks missing data )
    // ══════════════════════════════════════════════════════════════════════

    public void validateData() {
        if (studentRepo.size() == 0)
            System.out.println("Warning: No students.");
        if (courseRepo.size()  == 0)
            System.out.println("Warning: No courses.");
    }
}