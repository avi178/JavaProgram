public class Sesssion3 {

    public static void main(String[] args) {

        String month = "Jul";

        int number = MonthQuarter(month);
        System.out.println("Quarter is " + number);

//        if (month == "Jan") {
//            System.out.println("Jan is 1st Month");
//
//        } else if (month == "Feb") {
//            System.out.println("Feb is 1st Month");
//
//        } else if (month == "Mar") {
//            System.out.println("Mar is 1st Month");
//
//        } else if (month == "Apr") {
//            System.out.println("Apr is 1st Month");
//
//        } else {
//            System.out.println("Invalid month");
//        }

       /* switch (month) {
            case "Jan" : case "Feb" : case "Mar" :
                System.out.println(month + " Belongs to 1st quarter");
                break;
            case "Apr" : case "May" : case "Jun" :
                System.out.println(month + " Belongs to 1st quarter");
                break;
            case "Jul" : case "Aug" : case "Sep" :
                System.out.println(month + " Belongs to 1st quarter");
                break;
            case "Oct" : case "Nov" : case "Dec" :
                System.out.println(month + " Belongs to 1st quarter");
                break;
            default:
                System.out.println(month + " Bad Month");
        }*/


    }


    public static int MonthQuarter(String month) {


        switch (month) {
            case "Jan", "Feb", "Mar" -> {
                return 1;
            }
            case "Apr", "May", "Jun" -> {
                return 2;
            }

            case "Jul", "Aug", "Sep" -> {
                return 3;
            }

            case "Oct", "Nov", "Dec" -> {
                return 4;
            }

            default -> {
                return 0;
            }
        }

    }
}
