import java.util.Scanner;

public class Session14 {
    class Customer{
        private int acc_no;
        private String name;
        private double account_balance;
        private String address;

    }
    public static void main(String[] args) {

        boolean continue_app = true;

        Scanner input_scanner = new Scanner(System.in);
        do {
            System.out.println("### Main Menu ### \n");
            System.out.println("1. Add customer ### ");
            System.out.println("2. Transfer Money ");
            System.out.println("3. Modify Address ");
            System.out.println("4. Delete Customer ");
            System.out.println("Q. Quit Application ");
            System.out.println("Enter Choice 1 - 4 (Q to Quit) : \n");
            String choice = input_scanner.nextLine();

            if (choice.equals("Q") || choice.equals("q")) {
                System.out.println("\n Thank You for using it. End of Application \n");
                continue_app = false;

            } else {
                fn_menu(choice);
            }

        } while(continue_app);
    }

    public static void fn_menu(String choice) {

        int user_choice = 0;
        try {
            user_choice = Integer.parseInt(choice);

            switch (user_choice) {
                case 1 -> System.out.println("Add Customer \n");
                case 2 -> System.out.println("Transfer Money \n");
                case 3 -> System.out.println("Modify Address\n");
                case 4 -> System.out.println("Delete Customer \n");
                default -> System.out.println("Default case : Enter correct choice 1 - 4 \n");

            }

        } catch(NumberFormatException error) {
            System.out.println("Catch : Enter correct choice 1 - 4 or Q. Error is : " + error);

        }

    }
}
