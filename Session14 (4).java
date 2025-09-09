import java.util.ArrayList;
import java.util.Scanner;

public class Session14 {

    public static ArrayList<Customer> customer_list = new ArrayList<Customer>();

    public static class Customer{
        private int    acc_no;
        private String name;
        private double account_balance;
        private String address;

        //Constructor
        Customer(int acc_no, String name, double account_balance, String address) {
            System.out.println("Constructor called");
            this.acc_no = acc_no;
            this.name = name;
            this.account_balance = account_balance;
            this.address = address;
        }

        private void display() {
            System.out.println("### Customer Details ###");
            System.out.println("Account Number : " + acc_no);
            System.out.println("Customer Name : " + name);
            System.out.println("Account Balance : " + account_balance);
            System.out.println("Address : " + address);
        }

        private void withdraw(double amount) {
            if (this.account_balance < amount) {
                System.out.println("Insufficient Balance");
                return;
            }
            this.account_balance = this.account_balance - amount;
            System.out.println("Amount Withdrawn. Updated Balance");
        }

        private void deposit(double amount) {
            this.account_balance = this.account_balance + amount;
            System.out.println("Amount Deposited. Updated Balance");
        }


    }

    public static void main(String[] args) {

        boolean continue_app = true;

        Scanner input_scanner = new Scanner(System.in);
        do {
            System.out.println("### Main Menu ### \n");
            System.out.println("1. Add customer ### ");
            System.out.println("2. Withdraw Money ");
            System.out.println("3. Display Customer Details ");
            System.out.println("4. Deposit Money ");
            System.out.println("Q. Quit Application ");
            System.out.println("Enter Choice 1 - 4 (Q to Quit) : \n");
            String choice = input_scanner.nextLine();

            if (choice.equals("Q") || choice.equals("q")) {
                System.out.println("\n Thank You for using it. End of Application \n");
                continue_app = false;

            } else {
                fn_menu(choice, input_scanner);
            }

        } while(continue_app);

        input_scanner.close();
    }

    public static void fn_menu(String choice, Scanner input_scanner) {

        int user_choice = 0;
        try {
            user_choice = Integer.parseInt(choice);

            switch (user_choice) {
                case 1 -> add_customer(input_scanner);
                case 2 -> withdraw_money(input_scanner);
                case 3 -> display_customer_details(input_scanner);
                case 4 -> deposit_money(input_scanner);
                default -> System.out.println("Default case : Enter correct choice 1 - 4 \n");

            }

        } catch(NumberFormatException error) {
            System.out.println("Catch : Enter correct choice 1 - 4 or Q. Error is : " + error);

        }

    }
    public static void add_customer(Scanner input_scanner) {


        System.out.println("Add Customer \n");
        System.out.println("Enter Customer Name : ");
        String customer_name = input_scanner.nextLine();
        System.out.println("Enter Customer Address : ");
        String customer_address = input_scanner.nextLine();
        System.out.println("Enter Customer Account Number : ");
        int customer_acc_no = Integer.parseInt(input_scanner.nextLine());
        System.out.println("Enter Customer Account Balance : ");
        double customer_balance = Double.parseDouble(input_scanner.nextLine());

        customer_list.add(new Customer(customer_acc_no, customer_name, customer_balance, customer_address));

        // ArrayList = {Customer@1081, Customer@1095}  0 , 1 = 2


    }

    public static void withdraw_money(Scanner input_scanner) {


        System.out.println("Withdraw Money \n");
        System.out.println("Enter Customer Account Number : ");
        int customer_acc_no = Integer.parseInt(input_scanner.nextLine());
        System.out.println("Enter withdraw amount : ");
        double withdraw_amount = 0.00;
        try {
         withdraw_amount = Double.parseDouble(input_scanner.nextLine());
        }
        catch(NumberFormatException werror) {
            System.out.println("Catch : Enter correct amount : " + werror);
        }

        for (Customer customer : customer_list) {
            if (customer.acc_no == customer_acc_no) {
                customer.withdraw(withdraw_amount);
                break;
            }
        }
    }

    public static void deposit_money(Scanner input_scanner){


        System.out.println("Deposit Money \n");
        System.out.println("Enter Customer Account Number : ");
        int customer_acc_no = Integer.parseInt(input_scanner.nextLine());
        System.out.println("Enter deposit amount : ");
        double deposit_amount = 0.00;
        try {
            deposit_amount = Double.parseDouble(input_scanner.nextLine());
        }
        catch(NumberFormatException werror) {
            System.out.println("Catch : Enter correct amount : " + werror);
        }

        for (Customer customer : customer_list) {
            if (customer.acc_no == customer_acc_no) {
                customer.deposit(deposit_amount);
                break;
            }
        }
    }

    public static void display_customer_details(Scanner input_scanner) {

        System.out.println("Display Customer Details \n");

        // ArrayList customer_list= {Customer@1081, Customer@1095}  0 , 1 = 2

        for (int i = 0; i < customer_list.size(); i++) { //i < 2, 100, 100000
            customer_list.get(i).display();  //pritnf("Name = %s",customer[i])
            //customer_list(0) = Customer@1081 customer_list(1) = Customer@1095
            //Customer@1081.name = "Amit"
            //Customer@1095.name = "Omkar"
        }

        for (Customer customer : customer_list) {
            //iterator customer = Customer@1081, customer = Customer@1095
            customer.display();
            //Customer@1081.name = "Amit"
            //Customer@1095.name = "Omkar"
        }

        System.out.println("Enter Customer Name \n");
        String customer_name = input_scanner.nextLine();

        for (Customer customer : customer_list) {
            if (customer.name.equals(customer_name)) {

                   customer.display();
                   break;
            }

        }

    }
}
