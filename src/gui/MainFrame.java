package gui;

import model.Course;
import model.Facility;
import model.Library;
import model.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

    private JPanel sidebar;
    private JPanel mainPanel;
    private controller.CampusManager manager;
    private model.Role currentRole;

    //  Place ImagePanel here — as an inner class
    class ImagePanel extends JPanel {
        private BufferedImage backgroundImage;    // save + loads image in memory

        public ImagePanel(String resourcePath) { // takes image path
            try {
                java.net.URL imgURL = getClass().getResource(resourcePath);  // finds image inside project resources/ classpath
                if (imgURL != null) {
                    backgroundImage = ImageIO.read(imgURL);  // reads image into memory
                } else {
                    System.out.println("Image not found on classpath: " + resourcePath);
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + resourcePath + " -> " + e.getMessage());
            }
            setOpaque(true); // panel fully paints itself
        }

        @Override
        protected void paintComponent(Graphics g) {   // automatic SWING call -> redraw panel
            super.paintComponent(g);                 // clears old graphics before repainting
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public MainFrame(controller.CampusManager manager, model.Role role) {
        this.manager = manager;
        setTitle("Campus Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) { // Runs when user press close Button
                manager.saveSystem("campus.dat");
                manager.autoSave();
                dispose();               // destroys window
                System.exit(0);   // ends program
            }
        });
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        this.currentRole = role;

        //Layout
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(4, 1));
        sidebar.setBackground(Color.GRAY);
        sidebar.setPreferredSize(new Dimension(150, 0));  // width

        JButton studentBtn = new JButton("Students");
        JButton courseBtn = new JButton("Courses");
        JButton facilityBtn = new JButton("Facilities");
        JButton reportBtn = new JButton("Reports");


        // UI Customization
        studentBtn.setFocusPainted(false);
        courseBtn.setFocusPainted(false);
        facilityBtn.setFocusPainted(false);
        reportBtn.setFocusPainted(false);

        studentBtn.setForeground(Color.WHITE);
        courseBtn.setForeground(Color.WHITE);
        facilityBtn.setForeground(Color.WHITE);
        reportBtn.setForeground(Color.WHITE);

        studentBtn.setBackground(new Color(101, 55, 0));
        courseBtn.setBackground(new Color(101, 55, 0));
        facilityBtn.setBackground(new Color(101, 55, 0));
        reportBtn.setBackground(new Color(101, 55, 0));

        studentBtn.setBorderPainted(false);
        courseBtn.setBorderPainted(false);
        facilityBtn.setBorderPainted(false);
        reportBtn.setBorderPainted(false);


        // ADMIN and TEACHER see Students tab
        if (currentRole == model.Role.ADMIN || currentRole == model.Role.TEACHER) {
            sidebar.add(studentBtn);

        }
        // All roles see Course tab
        sidebar.add(courseBtn);

        // Only ADMIN sees Facilities tab
        if (currentRole == model.Role.ADMIN) {
            sidebar.add(facilityBtn);
        }
        // All roles see Reports tab
        sidebar.add(reportBtn);


        // Main Panel
        mainPanel = new JPanel(new CardLayout());

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Screen switching
        CardLayout c1 = (CardLayout) mainPanel.getLayout();


        //1. Student Panel
        ImagePanel studentPanel = new ImagePanel("/gui/student.jpg");
        studentPanel.setLayout(new BorderLayout());

        // Top Form Panel
        JPanel formPanel = new JPanel();

        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField programField = new JTextField(10);

        JButton addBtn = new JButton("Add Student");
        addBtn.setVisible(currentRole == model.Role.ADMIN || currentRole == model.Role.TEACHER);

        formPanel.add(new JLabel("ID: "));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name: "));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Program: "));
        formPanel.add(programField);
        formPanel.add(addBtn);
        JButton deleteStudentBtn = new JButton("Delete Selected");
        formPanel.add(deleteStudentBtn);

        // Table
        String[] studentColumns = {"ID", "Name", "Program"};  // Table Header
        Object[][] studentData = manager.getAllStudentsData(); // Table Data

        DefaultTableModel studentModel = new DefaultTableModel(studentData, studentColumns);
        JTable studentTable = new JTable(studentModel);  // Display rows and columns
        studentTable.setRowHeight(25);

        JScrollPane studentScroll = new JScrollPane(studentTable);
        studentScroll.setOpaque(false);
        studentScroll.getViewport().setOpaque(false);
        studentTable.setOpaque(false);

        // Add Components
        studentPanel.add(formPanel, BorderLayout.NORTH);
        studentPanel.add(studentScroll, BorderLayout.CENTER);

        // Button Logic
        addBtn.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String program = programField.getText();

            if (id.isEmpty() || name.isEmpty() || program.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            Student s = new Student(id, name, program);
            boolean added = manager.addStudent(s);

            if (added) {
                // Save immediately
                manager.saveSystem("campus.dat");
                manager.autoSave();

                // Refresh Table
                studentModel.setDataVector(manager.getAllStudentsData(), studentColumns);

                // Clear fields
                idField.setText("");
                nameField.setText("");
                programField.setText("");

                JOptionPane.showMessageDialog(null, "Student added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Student already present!", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete Student Button
        deleteStudentBtn.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a student row to delete!");
                return;
            }
            String id = (String) studentModel.getValueAt(row, 0);
            boolean removed = manager.removeStudent(id);
            if (removed) {
                manager.saveSystem("campus.dat");
                manager.autoSave();
                studentModel.setDataVector(manager.getAllStudentsData(), studentColumns);
                JOptionPane.showMessageDialog(null, "Student deleted!");
            } else {
                JOptionPane.showMessageDialog(null, "Student not found!");
            }
        });

        //2. Course Panel
        ImagePanel coursePanel = new ImagePanel("/gui/course.jpg");
        coursePanel.setLayout(new BorderLayout());

        // Top Form
        JPanel courseForm = new JPanel();
        courseForm.setOpaque(true);

        JTextField courseIDField = new JTextField(5);
        JTextField courseNameField = new JTextField(10);

        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.setVisible(currentRole == model.Role.ADMIN || currentRole == model.Role.TEACHER);

        courseForm.add(new JLabel("ID: "));
        courseForm.add(courseIDField);
        courseForm.add(new JLabel("Name: "));
        courseForm.add(courseNameField);
        courseForm.add(addCourseBtn);
        JButton deleteCourseBtn = new JButton("Delete Selected");
        courseForm.add(deleteCourseBtn);

        // Table
        String[] courseColumns = {"ID", "Name", "Students", "Assignments"};
        Object[][] courseData = manager.getAllCoursesData();

        DefaultTableModel courseModel = new DefaultTableModel(courseData, courseColumns);
        JTable courseTable = new JTable(courseModel);
        courseTable.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(courseTable);

        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        courseTable.setOpaque(false);

        // Add to Panel
        JPanel enrollPanel = new JPanel();
        enrollPanel.setOpaque(false);


        JLabel enrollCourseLabel = new JLabel("Enroll Student →  Course ID:");
        JTextField enrollCourseIDField = new JTextField(6);

        JLabel enrollStudentLabel = new JLabel("Student:");
        JComboBox<String> studentComboBox = new JComboBox<>();




        // Populate combobox with existing students
        for (model.Student s : manager.getStudentRepo().getAll()) {
            studentComboBox.addItem(s.getStudentID() + " - " + s.getName());
        }

        JButton enrollBtn = new JButton("Enroll");

        enrollPanel.add(enrollCourseLabel);
        enrollPanel.add(enrollCourseIDField);
        enrollPanel.add(enrollStudentLabel);
        enrollPanel.add(studentComboBox);
        enrollPanel.add(enrollBtn);

        // Enroll button logic
        enrollBtn.addActionListener(e -> {
            String courseID = enrollCourseIDField.getText().trim();
            int selectedIndex = studentComboBox.getSelectedIndex();

            if (courseID.isEmpty() || selectedIndex == -1) {
                JOptionPane.showMessageDialog(null, "Enter a course ID and select a student!");
                return;
            }
            // Find the course
            model.Course targetCourse = manager.findCourseByID(courseID);
            if (targetCourse == null) {
                JOptionPane.showMessageDialog(null, "Course ID not found: " + courseID);
                return;
            }

            // Get the selected student ID from combobox text (format: "S1 - Ashar")
            String comboText = (String) studentComboBox.getSelectedItem();
            String studentID = comboText.split(" - ")[0].trim();
            model.Student targetStudent = manager.findStudentByID(studentID);

            if (targetStudent == null) {
                JOptionPane.showMessageDialog(null, "Student not found!");
                return;
            }

            manager.enrollStudentInCourse(targetStudent, targetCourse);
            manager.saveSystem("campus.dat");
            manager.autoSave();

            // Refresh course table to show updated student count
            courseModel.setDataVector(manager.getAllCoursesData(), courseColumns);

            JOptionPane.showMessageDialog(null,
                    targetStudent.getName() + " enrolled in " + targetCourse.getCourseName() + "!");
        });




        // Combined top section: add form + enroll form stacked
        JPanel topSection = new JPanel(new java.awt.GridLayout(2, 1));
        topSection.add(courseForm);
        topSection.add(enrollPanel);


        // Add to Panel
        coursePanel.add(topSection, BorderLayout.NORTH);
        coursePanel.add(scroll, BorderLayout.CENTER);

        // Button Logic
        addCourseBtn.addActionListener(e -> {
            String id = courseIDField.getText();
            String name = courseNameField.getText();

            if(id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill all Fields!");
                return;
            }

            Course c = new Course(id, name);
            boolean added = manager.addCourse(c);

            if (added) {
                manager.saveSystem("campus.dat");
                manager.autoSave();
                courseModel.setDataVector(manager.getAllCoursesData(), courseColumns);
                courseIDField.setText("");
                courseNameField.setText("");
                JOptionPane.showMessageDialog(null, "Course added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Course already present!", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete Button
        deleteCourseBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a course row to delete!");
                return;
            }
            String id = (String) courseModel.getValueAt(row, 0);
            boolean removed = manager.removeCourse(id);
            if (removed) {
                manager.saveSystem("campus.dat");
                manager.autoSave();
                courseModel.setDataVector(manager.getAllCoursesData(), courseColumns);
                JOptionPane.showMessageDialog(null, "Course deleted!");
            } else {
                JOptionPane.showMessageDialog(null, "Course not found!");
            }
        });

        // 3. Facility Panel
        ImagePanel facilityPanel = new ImagePanel("/gui/facility.jpg");
        facilityPanel.setLayout(new BorderLayout());

        String[] facilityColumns = {"ID", "Name", "Location"};

        // Convert data from repository
        Object[][] facilityData = manager.getFacilityRepo().getAll()
                .stream()
                .map(f -> new Object[]{f.getEntityID(), f.getName(), f.getLocation()})
                .toArray(Object[][] :: new);

        // Table Model
        DefaultTableModel facilityModel = new DefaultTableModel(facilityData, facilityColumns);
        JTable facilityTable = new JTable(facilityModel);
        JScrollPane facilityScroll = new JScrollPane(facilityTable);

        facilityScroll.setOpaque(false);
        facilityScroll.getViewport().setOpaque(false);
        facilityTable.setOpaque(false);

        JButton addFacilityBtn = new JButton("Add Sample Facility");
        addFacilityBtn.setVisible(currentRole == model.Role.ADMIN);

        // Button Logic
        addFacilityBtn.addActionListener(e -> {
            Facility f = new Library("L" + System.currentTimeMillis(), "Central Library" , "Block X", 1000, 50);

            boolean added = manager.addFacility(f);

            if (added) {
                manager.saveSystem("campus.dat");
                manager.autoSave();
                facilityModel.setDataVector(
                        manager.getFacilityRepo().getAll()
                                .stream()
                                .map(x -> new Object[]{x.getEntityID(), x.getName(), x.getLocation()})
                                .toArray(Object[][]::new),
                        facilityColumns
                );
                JOptionPane.showMessageDialog(null, "Facility added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Facility already present!", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Layout
        facilityPanel.add(addFacilityBtn, BorderLayout.NORTH);
        facilityPanel.add(facilityScroll, BorderLayout.CENTER);



        // 4. Report Panel
        ImagePanel reportPanel = new ImagePanel("/gui/report.jpg");
        reportPanel.setLayout(new BorderLayout());

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setMargin(new Insets(20, 40, 20, 40));
        reportArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton generateBtn = new JButton("Generate Report");

        // Button Logic
        generateBtn.addActionListener(e -> {

            StringBuilder report = new StringBuilder();

            report.append("===== CAMPUS REPORT =====\n\n");
            report.append("Total Students : ").append(manager.getStudentRepo().size()).append("\n");
            report.append("Total Courses  : ").append(manager.getCourseRepo().size()).append("\n");
            report.append("Total Facilities: ").append(manager.getFacilityRepo().size()).append("\n");
            report.append("Facility Usage  : ").append(controller.CampusManager.getTotalFacilityUsage()).append("\n");

            report.append("\n===== COURSE TIMETABLE =====\n");
            for (model.Course c : manager.getCourseRepo().getAll()) {
                report.append("  ").append(c.toString()).append("\n");
                report.append("    Students enrolled: ").append(c.getStudents().size()).append("\n");
                report.append("    Assignments: ").append(c.getAssignments().size()).append("\n");
            }
            if (manager.getCourseRepo().size() == 0) {
                report.append("  No courses found.\n");
            }

            report.append("\n===== STUDENT LIST =====\n");
            for (model.Student s : manager.getStudentRepo().getAll()) {
                report.append("  ").append(s.toString()).append("\n");
            }
            if (manager.getStudentRepo().size() == 0) {
                report.append("  No students found.\n");
            }

            report.append("\nSystem Running Smoothly.");
            reportArea.setText(report.toString());
        });


        // Layout
        reportPanel.add(generateBtn, BorderLayout.NORTH);
        JScrollPane reportScroll = new JScrollPane(reportArea);
        reportScroll.setOpaque(false);
        reportScroll.getViewport().setOpaque(false);
        reportArea.setOpaque(false);

        // Center the scroll pane horizontally
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        reportScroll.setPreferredSize(new Dimension(600, 400));
        centerWrapper.add(reportScroll);
        reportPanel.add(centerWrapper, BorderLayout.CENTER);



        // Update button (ActionListener)
        studentBtn.addActionListener(e -> {
            studentModel.setDataVector(manager.getAllStudentsData(), studentColumns);
            c1.show(mainPanel, "STUDENTS");
        });

        courseBtn.addActionListener(e -> {
            courseModel.setDataVector(manager.getAllCoursesData(), courseColumns);
            // Refresh student combobox with latest students
            studentComboBox.removeAllItems();
            for (model.Student s : manager.getStudentRepo().getAll()) {
                studentComboBox.addItem(s.getStudentID() + " - " + s.getName());
            }
            c1.show(mainPanel, "COURSES");
        });


        facilityBtn.addActionListener(e -> {
            facilityModel.setDataVector(
                    manager.getFacilityRepo().getAll()
                            .stream()
                            .map(x -> new Object[]{x.getEntityID(), x.getName(), x.getLocation()})
                            .toArray(Object[][]::new),
                    facilityColumns
            );
            c1.show(mainPanel, "FACILITIES");
        });


        reportBtn.addActionListener(e -> {
            c1.show(mainPanel, "REPORTS");
        });

        mainPanel.add(studentPanel, "STUDENTS");
        mainPanel.add(coursePanel, "COURSES");
        mainPanel.add(facilityPanel, "FACILITIES");
        mainPanel.add(reportPanel, "REPORTS");

    }
}