public class SystemInfo {
    public static void main(String[] args) {
        // Print out the operating system information
        System.out.println("Operating System: " + System.getProperty("os.name"));
        System.out.println("OS Version: " + System.getProperty("os.version"));
        System.out.println("OS Architecture: " + System.getProperty("os.arch"));

        // Print out the processor information
        System.out.println("Number of CPUs: " + Runtime.getRuntime().availableProcessors());

        // Print out the user information
        System.out.println("User Home Directory: " + System.getProperty("user.home"));
        System.out.println("User Name: " + System.getProperty("user.name"));

        // Java information
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
    }
}
