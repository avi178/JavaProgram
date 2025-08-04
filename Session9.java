public class Session9 {

    public static void fun_add(int num1, int num2) {
        System.out.println(num1 + num2);
    }

    public static void fun_add(float num1, float num2) {
        System.out.println(num1 + num2);
    }

    public static void fun_add(String name, String surname) {
        System.out.println(name + " " + surname);
    }
    public static void fun_add(int num1, float num2) {
        System.out.println(num1 + num2);
    }

    public static void fun_add(float num1, int num2) {
        System.out.println(num1 + num2);
    }

    public static void fun_add(String name, int num2) {
        int age = 18;
        int result = age + num2;
        System.out.println(name + "s new age is :" + result);
    }

    public static void main(String[] args) {

        int in_num1 = 10;
        int in_num2 = 11;

        float fl_num1 = 10.567F;
        float fl_num2 = 15.25F;

        String name = "Omkar";
        String surname = "Bangal";

        fun_add(in_num1, in_num2);
        fun_add(fl_num1, fl_num2);
        fun_add(name, surname);
        fun_add(in_num1, fl_num1);
        fun_add(fl_num2, in_num2);
        fun_add(name, in_num2);


    }
}
