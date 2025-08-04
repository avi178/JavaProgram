public class Session8 {

    public static void main(String[] args) {
        int number1 = 0;
        int number2 = 0;
        int number3 = 0;

        String help = args[0];
        if (help.equals("-h")) {
            System.out.println("Usage: java Session8 <number1> <operator> <number2>");
            return;
        }
        if (help.equals("-v")) {
            System.out.println("Version 21.12.0.1");
            System.out.println("Usage: java Session8 <number1> <operator> <number2> <name>");
            return;
        }

        for(int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);

        }

        if (args.length != 4) {
            System.out.println("Error : Invalid number of arguments");
            System.out.println("Usage: java Session8 <number1> <operator> <number2> <name>");
            return;
        }


        try {
            number1 = Integer.parseInt(args[0]);
            number2 = Integer.parseInt(args[2]);

        } catch (NumberFormatException err) {
            System.out.println("Error : Invalid number format");
            System.out.println("Usage: java Session8 <number1> <operator> <number2> <name>" );
            return;
        }


        String operator = args[1];
        String name = args[3];

        switch(operator) {
            case "+" :
                number3 = number1 + number2;
                break;
            case "-" :
                number3 = number1 - number2;
                break;
            case "*" :
                number3 = number1 * number2;
                break;
            case "/" :
                number3 = number1 / number2;
                break;
            default :
                System.out.println("Error : Invalid operator : " + operator);
                System.out.println("Valid operators: +, -, *, /");
                return;

        }
        System.out.println("Hello " + name);
        System.out.println(number1 + " " + operator + " " + number2 + " = " + number3);
    }
}
