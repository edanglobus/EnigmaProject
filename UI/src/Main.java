import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MachineManager mainManager = new MachineManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Choose an order:
                    1. Load XML file
                    2. Machine configuration
                    3. Manual Configuration
                    4. Automatic Configuration
                    5. Encode/Decode String
                    6. Reset to the original code
                    7. Show history
                    8. Exit""");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> mainManager.order1();
                    case "2" -> mainManager.order2();
                    case "3" -> mainManager.order3();
                    case "4" -> mainManager.order4();
                    case "5" -> mainManager.order5();
                    case "6" -> mainManager.order6();
                    case "7" -> mainManager.order7();
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
