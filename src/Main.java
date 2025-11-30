
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MainManager mainManager = new MainManager();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an order:\n1. Load XML file\n3. Manual Configuration\n5. Encode/Decode String\n0. Exit");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> mainManager.order1();
                    case "3" -> mainManager.order3();
                    case "5" -> mainManager.order5();
                    case "0" -> {
                        System.out.println("Exiting program.");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    };
};