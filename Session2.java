public class Session2 {

    public static void factorial() {
        int num = 6;
        int i = 1;
        /* factorial :  6! =  6 * 5 * 4 * 3 * 2 * 1  */
        int factorial = 1;

        while(num > 1) {

            factorial = factorial * num;
            System.out.println("Num : " + num + " Loop : " + i);
            num--;
            i++;

        }
        System.out.println("Factorial of 6! = " + factorial);
    }
    public static void main(String[] args) {

        factorial();

        int num = 1;
        do {

            if(num % 2 == 0) {
                System.out.println(num);
            }
            ++num;


        } while(num < 100);

        boolean gender_male = true;

        while(gender_male) {
            System.out.println("While Loop Gender is :" + gender_male);
            gender_male = false;
        }

        do {

            System.out.println("Do While Loop Gender is :" + gender_male);
            gender_male = false;
        }while(gender_male);

    }





}
