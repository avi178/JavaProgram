import java.util.Scanner;

public class Session7 {
    public static void main(String[] args) {
        int age = 0;
        float discount = 0.50F;
        float price = 100.00F;
        float ticket_price = 0;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter your name : ");
        String name = input.nextLine();

        System.out.print("Enter your age : ");
        age = input.nextInt();

        input.nextLine();

        if (age <= 18) {
            ticket_price = price * discount;
            System.out.println("Ticket price is " + ticket_price);
        } else {
            ticket_price = price;
            System.out.println("Ticket price is " + ticket_price);
        }

       // expression ? true/value : false/value;
        ticket_price = age <= 18 ? price * discount: price;

        System.out.println(" Ticket Price for " + name + " is " + ticket_price);

        try {
            name = System.console().readLine("Enter your Surname using Console : ");
            System.out.println("Your Surname is " + name);
        } catch (NullPointerException e) {
            System.out.print("Enter your Surname name using Scanner : ");
            name = input.nextLine();
            System.out.println("Your Surname name is " + name);
        }



    }
}
