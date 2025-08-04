
import java.util.Scanner;

public class SwitchExample {

    public static void main(String[] args) {

        Scanner input_val = new Scanner(System.in);

        System.out.print("Enter day : ");
        int day = input_val.nextInt();

        classicSwitchWithBreak(day);

        System.out.println("-------------------");

        System.out.print("Enter day : ");
        day = input_val.nextInt();
        classicSwitchFallThrough(day);
        input_val.nextLine();

        System.out.println("-------------------");

        System.out.print("Enter day (String) : ");
        String str_day = input_val.nextLine();

        switchWithString(str_day);

        System.out.println("-------------------");

        System.out.print("Enter day : ");
        day = input_val.nextInt();
        enhancedSwitchArrow(day);

        System.out.println("-------------------");

        System.out.print("Enter day : ");
        day = input_val.nextInt();

        switchExpressionWithYield(day);

        System.out.println("-------------------");

        System.out.print("Enter day : ");
        day = input_val.nextInt();

        String day_name = returnSwitchClassic(day);
        System.out.println("Returned from function: " + day_name );

        System.out.println("-------------------");

        System.out.print("Enter day : ");
        day = input_val.nextInt();
        System.out.println("Returned from function (yield): " + returnSwitchYield(day));
        System.out.println("-------------------");

        // Syntax error: Missing semicolon in enhanced switch expression
        // Uncomment to see compilation error!
        /*
        int num = 1;
        String result = switch (num) {
            case 1 -> "One"   //  Missing semicolon!
            case 2 -> "Two";
            default -> "Unknown";
        };
        System.out.println(result);
        */
    }

    // Classic switch-case with break
    public static void classicSwitchWithBreak(int day) {
        System.out.println("Classic switch-case with break:");
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            default:
                System.out.println("Invalid day");
        }

    }

    // Classic switch-case without break (fall-through)
    public static void classicSwitchFallThrough(int day) {
        System.out.println("Classic switch-case without break (fall-through):");
        switch (day) {
            case 1:
                System.out.println("Monday");
            case 2:
                System.out.println("Tuesday");
            case 3:
                System.out.println("Wednesday");
            default:
                System.out.println("Invalid day");
        }
    }

    // switch-case with String (Java 7+)
    public static void switchWithString(String lang) {
        System.out.println("Switch with String:");
        switch (lang) {
            case "Java":
                System.out.println("Language is Java");
                break;
            case "Python":
                System.out.println("Language is Python");
                break;
            default:
                System.out.println("Unknown language");
        }

    }

    // Enhanced switch with arrow syntax (Java 12+)
    public static void enhancedSwitchArrow(int day) {
        System.out.println("Enhanced switch with arrow syntax:");
        switch (day) {
            case 1 -> System.out.println("Monday");
            case 2 -> System.out.println("Tuesday");
            case 3 -> System.out.println("Wednesday");
            default -> System.out.println("Invalid day");
        }
    }

    //  switch expression with yield (Java 13+ preview, Java 14+)
    public static void switchExpressionWithYield(int day) {
        System.out.println("Switch expression with yield:");
        String result = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> {
                System.out.println("Inside block for Wednesday");
                yield "Wednesday";
            }
            default -> "Invalid day";

        }; // watch out semicolon here.
        System.out.println("Result: " + result);
    }

    //  Classic switch with return inside a function
    public static String returnSwitchClassic(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            default:
                return "Invalid day";
        }
    }

    //  return switch expression with yield
    public static String returnSwitchYield(int day) {
        return switch (day) {
            case 1 -> "Monday";
            case 2, 3, 4, 5 -> "Weekday";
            case 6 -> "Saturday";
            case 7 -> {
                System.out.println("Doing extra work inside block...");
                yield "Sunday";
            }
            default -> {
                yield "Invalid day";
            }
        }; // watch out semicolon here.
    }
}


