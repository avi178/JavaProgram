public class Session13 {



    public static void main(String[] args) {
        int entry_rollno = 101;
        String entry_name = "Omkar";
        int entry_age = 20;
        String entry_year = "SYBE";

        Student s1 = new Student(entry_rollno, entry_name, entry_age, entry_year); // create object
        //s1.addNewStudent(entry_rollno, entry_name, entry_age, entry_year);
        s1.display();

        entry_rollno = 102;
        entry_name = "Varad";
        entry_age = 19;
        entry_year = "FYBE";

        Student s2 = new Student(entry_rollno, entry_name, entry_age, entry_year);  // create object
        //s2.addNewStudent(entry_rollno, entry_name, entry_age, entry_year);
        s2.display();

        entry_rollno = 103;
        entry_name = "Sunidhi";
        entry_age = 18;
        entry_year = "TYBCA";
        Student s3 = new Student(entry_rollno, entry_name, entry_age, entry_year);  // create object

        //s3.addNewStudent(entry_rollno, entry_name, entry_age, entry_year);
        s3.display();

        entry_year = "SYBE";
        s2.modifyYear(entry_year);
        s2.display();

        /*System.out.println(s1.rollno);
        System.out.println(s1.name);
        System.out.println(s1.age);
        System.out.println(s1.year);*/


    }
}

class Student {
    private int rollno;
    private String name;
    private int age;
    private String year;

    public Student(int rollno, String name, int age, String year) {
        this.rollno = rollno;
        this.name = name;
        this.age = age;
        this.year = year;
    }

    /*void addNewStudent(int rollno, String name, int age, String year) {
        System.out.println("Adding new student");
        this.rollno = rollno;
        this.name = name;
        this.age = age;
        this.year = year;
    }*/

    void display() {
        System.out.println("Roll No : " + rollno);
        System.out.println("Name : " + name);
        System.out.println("Age : " + age);
        System.out.println("Year : " + year);
    }

    void modifyYear(String year) {
        this.year = year;
    }
}


