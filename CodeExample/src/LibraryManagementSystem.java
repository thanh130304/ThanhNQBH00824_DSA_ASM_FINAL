// Book.java
class Book {
    private String title;
    private boolean isAvailable;

    public Book(String title) {
        this.title = title;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("You borrowed: " + title);
        } else {
            System.out.println("Book not available");
        }
    }

    public void returnBook() {
        isAvailable = true;
        System.out.println("You returned: " + title);
    }
}

// Member.java
class Member {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public void borrowBook(Book book) {
        book.borrow();
    }

    public void returnBook(Book book) {
        book.returnBook();
    }
}

// Main.java (Library Management System)
public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Creating books
        Book book1 = new Book("Java Programming");
        Book book2 = new Book("Data Structures");

        // Creating a member
        Member member = new Member("Alice");

        // Borrowing and returning books
        member.borrowBook(book1);
        member.returnBook(book2);
    }
}
