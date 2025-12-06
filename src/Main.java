import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        MainManager mainManager = new MainManager();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an order:\n" +
                    "1. Load XML file\n" +
                    "2. Machine configuration\n" +
                    "3. Manual Configuration\n" +
                    "5. Encode/Decode String\n" +
                    "6. Reset code to the initial configuration" +
                    "8. Exit");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> mainManager.order1();
                    case "2" -> mainManager.order2();
                    case "3" -> mainManager.order3();
                    case "5" -> mainManager.order5();
                    case "6" -> mainManager.order6();
                    case "8" -> {
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
