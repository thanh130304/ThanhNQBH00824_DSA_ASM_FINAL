import java.util.Scanner;
import java.util.Random;

public class StudentManager {
    private StudentStack<Student> students;
    private StudentStack<String> nameSamples;

    public StudentManager() {
        this.students = new StudentStack<>();
        this.nameSamples = new StudentStack<>();

        String[] names = {
                "Anh", "Bao", "Chuong", "Danh", "Quan", "Hieu", "Thanh", "Linh", "Minh", "Nam",
                "Phu", "Phuc", "Tuan", "Son", "Tu", "Quoc", "Viet", "Long", "Kien", "Truong",
                "Giang", "Hoang", "Huy", "Trieu"
        };

        // Đưa từng tên vào stack
        for (String name : names) {
            this.nameSamples.push(name);
        }
    }

    private double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public void generateRandomStudents(int numberOfStudents) {
        Random random = new Random();

        for (int i = 1; i <= numberOfStudents; i++) {
            int id = i;
            String name = nameSamples.isEmpty() ? "DefaultName" : nameSamples.pop();  // Sử dụng tên mặc định nếu stack rỗng
            double marks = roundToOneDecimal(1 + (9 * random.nextDouble()));
            students.push(new Student(id, name, marks));
        }
    }

    public void printStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        // Sử dụng một stack tạm thời để lưu sinh viên theo thứ tự ID từ nhỏ đến lớn
        StudentStack<Student> tempStack = new StudentStack<>();

        // Sắp xếp sinh viên trong stack
        while (!students.isEmpty()) {
            Student student = students.pop();

            // Duyệt qua tempStack và chuyển sinh viên vào students để bảo đảm ID của sinh viên trong tempStack theo thứ tự từ nhỏ đến lớn
            while (!tempStack.isEmpty() && tempStack.peek().getId() < student.getId()) {
                students.push(tempStack.pop());
            }

            tempStack.push(student);
        }

        // In ra sinh viên từ tempStack (đã sắp xếp)
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        // In ra sinh viên từ stack
        // Duyệt qua stack thủ công thay vì dùng foreach
        StudentStack<Student> temp = new StudentStack<>();
        while (!students.isEmpty()) {
            Student student = students.pop();
            System.out.println(student); // In ra sinh viên
            temp.push(student); // Đẩy lại vào stack
        }

        // Khôi phục lại stack từ temp (sau khi in xong)
        while (!temp.isEmpty()) {
            students.push(temp.pop());
        }
    }

    public void addStudent(Student student) {
        student.setMarks(roundToOneDecimal(student.getMarks()));
        students.push(student);
    }

    public boolean editStudent(int id, String newName, double newMarks) {
        StudentStack<Student> tempStack = new StudentStack<>();
        boolean found = false;

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() == id) {
                tempStack.push(new Student(id, newName, roundToOneDecimal(newMarks)));
                found = true;
            } else {
                tempStack.push(student);
            }
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        return found;
    }

    public boolean deleteStudent(int id) {
        StudentStack<Student> tempStack = new StudentStack<>();
        boolean deleted = false;

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() == id) {
                deleted = true;
            } else {
                tempStack.push(student);
            }
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        return deleted;
    }

    // QuickSort - Partition method
    private int partition(Student[] students, int low, int high) {
        double pivot = students[high].getMarks();  // Pivot is the last student's marks
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (students[j].getMarks() >= pivot) {  // Sorting in descending order
                i++;
                // Swap students[i] and students[j]
                Student temp = students[i];
                students[i] = students[j];
                students[j] = temp;
            }
        }

        // Swap pivot to the correct position
        Student temp = students[i + 1];
        students[i + 1] = students[high];
        students[high] = temp;
        return i + 1;
    }

    // QuickSort
    public void quickSort(Student[] students, int low, int high) {
        if (low < high) {
            int pi = partition(students, low, high);  // Partition index
            quickSort(students, low, pi - 1);  // Sort left part
            quickSort(students, pi + 1, high);  // Sort right part
        }
    }

    // Bubble Sort
    public void bubbleSort(Student[] students) {
        int n = students.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (students[j].getMarks() < students[j + 1].getMarks()) {  // Compare marks
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;  // If no swaps happened, array is sorted
            }
        }
    }

    public Student searchStudentById(int id) {
        StudentStack<Student> tempStack = new StudentStack<>();
        Student foundStudent = null;

        // Duyệt qua stack để tìm sinh viên
        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() == id) {
                foundStudent = student;
            }
            tempStack.push(student);
        }

        // Khôi phục các phần tử về stack chính
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        return foundStudent;
    }

    /**
     * Chuyển đổi Stack thành mảng mà không làm thay đổi stack gốc.
     */
    private Student[] toStudentArray() {
        Student[] studentArray = new Student[students.size()];
        StudentStack<Student> tempStack = new StudentStack<>();
        int i = 0;

        // Sao chép sinh viên từ stack vào mảng mà không thay đổi stack gốc
        while (!students.isEmpty()) {
            studentArray[i++] = students.peek();  // Dùng peek để không pop
            tempStack.push(students.pop());  // Đưa sinh viên vào stack tạm
        }

        // Khôi phục stack gốc
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        return studentArray;
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Generate Random Students");
            System.out.println("2. Display All Students");
            System.out.println("3. Add Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Sort Students By Marks");
            System.out.println("7. Search Student By ID");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter number of students to generate: ");
                    int num = scanner.nextInt();
                    generateRandomStudents(num);
                }
                case 2 -> printStudents();
                case 3 -> {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();
                    addStudent(new Student(id, name, marks));
                }
                case 4 -> {
                    System.out.print("Enter ID of student to edit: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Marks: ");
                    double newMarks = scanner.nextDouble();
                    if (editStudent(id, newName, newMarks)) {
                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Student with ID " + id + " not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter ID of student to delete: ");
                    int id = scanner.nextInt();
                    if (deleteStudent(id)) {
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Student with ID " + id + " not found.");
                    }
                }
                case 6 -> {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Choose sorting algorithm:");
                    System.out.println("1. QuickSort");
                    System.out.println("2. Bubble Sort");
                    int choiceSort = sc.nextInt();

                    long startTime, endTime;

                    // Kiểm tra nếu Stack rỗng
                    if (students.isEmpty()) {
                        System.out.println("No students available!");
                        break;
                    }

                    // Chuyển Stack thành mảng mà không thay đổi Stack
                    Student[] studentArray = toStudentArray();

                    // Thực hiện sắp xếp theo lựa chọn
                    if (choiceSort == 1) {
                        startTime = System.nanoTime();  // Bắt đầu đo thời gian
                        quickSort(studentArray, 0, studentArray.length - 1);  // Sắp xếp bằng QuickSort
                        endTime = System.nanoTime();  // Kết thúc đo thời gian
                    } else if (choiceSort == 2) {
                        startTime = System.nanoTime();  // Bắt đầu đo thời gian
                        bubbleSort(studentArray);  // Sắp xếp bằng BubbleSort
                        endTime = System.nanoTime();  // Kết thúc đo thời gian
                    } else {
                        System.out.println("Invalid choice! Please choose 1 or 2.");
                        break;  // Trở lại menu chính nếu lựa chọn không hợp lệ
                    }

                    // Đưa các sinh viên đã sắp xếp vào lại Stack
                    for (Student student : studentArray) {
                        students.push(student);  // Đưa sinh viên đã sắp xếp vào Stack
                    }

                    printStudents();  // In danh sách sinh viên đã sắp xếp

                    // Thời gian sắp xếp
                    System.out.println("Sorting took: " + (endTime - startTime) + " nanoseconds.");
                }
                case 7 -> {
                    System.out.print("Enter ID of student to search: ");
                    int id = scanner.nextInt();
                    Student student = searchStudentById(id);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.println("Student with ID " + id + " not found.");
                    }
                }
                case 0 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
