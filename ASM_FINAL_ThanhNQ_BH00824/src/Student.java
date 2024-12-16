import java.util.Stack;

public class Student {
    private int id;
    private String name;
    private double marks;
    private String rank;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = calculateRank(marks);
    }

    // Point-based ranking method
    private String calculateRank(double marks) {
        if (marks <= 5.0) return "Fail";
        if (marks <= 6.5) return "Medium";
        if (marks <= 7.5) return "Good";
        if (marks <= 9.0) return "Very Good";
        return "Excellent";
    }

    // Getters for properties
    public int getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }

    public void setMarks(double marks) {
        this.marks = marks;
    }
    public String getRank() { return rank; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + rank;
    }


}
