import java.util.ArrayList;
import java.util.Collections;

public class School {
    private ArrayList<String> students;

    public School() {
        students = new ArrayList<>();
    }

    public void addStudent(String studentName) {
        students.add(studentName);
    }

    // Separate the functionality of printing all students
    public void printStudents() {
        for (String student : students) {
            System.out.println(student);
        }
    }

    // Separate the functionality of printing the first student
    public void printFirstStudent() {
        if (!students.isEmpty()) {
            System.out.println(students.get(0));
        } else {
            System.out.println("No students available.");
        }
    }

    // Add functionality to sort students by name
    public void sortStudents() {
        Collections.sort(students);
    }
}
