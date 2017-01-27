import java.util.Scanner;

public class Keyboard {
    public static Scanner scanner = new Scanner(System.in); // initializes the scanner object


    public static String nextLine() { return scanner.nextLine(); } // returns the scanner.nextLine() result
    public static void close() { scanner.close(); } // closes the scanner
}
