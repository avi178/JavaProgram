//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Session1 {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        System.out.printf("Hello and welcome!\n");

        /* for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        } */

        System.out.println("Min value byte can store: " + Byte.MIN_VALUE);
        System.out.println("Max value byte can store: " + Byte.MAX_VALUE);

        System.out.println("Min value short can store: " + Short.MIN_VALUE);
        System.out.println("Max value short can store: " + Short.MAX_VALUE);

        System.out.println("Min value int can store: " + Integer.MIN_VALUE);
        System.out.println("Max value int can store: " + Integer.MAX_VALUE);

        System.out.println("Min value long can store: " + Long.MIN_VALUE);
        System.out.println("Max value long can store: " + Long.MAX_VALUE);

        System.out.println("Min value float can store: " + Float.MIN_VALUE);
        System.out.println("Max value float can store: " + Float.MAX_VALUE);

        System.out.println("Min value double can store: " + Double.MIN_VALUE);
        System.out.println("Max value double can store: " + Double.MAX_VALUE);

        System.out.println("Min value char can store: " + (int) Character.MIN_VALUE);
        System.out.println("Max value char can store: " + (int) Character.MAX_VALUE);

        int result = 5 + 2 - 3 * 4 / 2 + 1;

        /* operator precedence ---> left to right
           ^ raised to
           * / %
           + -
         */

        int a = 10;
        int b = 20;

        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        String firstName = "Omkar";
        String lastName = "Bangal";

        String fullName = firstName + " " + lastName;
        System.out.println(fullName);

        for(int i=0; i<10;) {
            ++i;
            System.out.println(i);

        }

        int i = 10;
        int j;
        j = 2  + i++;
        System.out.println("j = " + j);
        System.out.println("i = " + i);
        j = 2  + ++i;
        System.out.println("j = " + j);
        System.out.println("i = " + i);

    }
}