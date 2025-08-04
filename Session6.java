public class Session6 {

	public static void main(String[] args) {
		int num = 10;
		int num2 = 11;

		int result = 0;

		System.out.println("Binary of " + num + " is : " + Integer.toBinaryString(num));
		//System.out.println("Binary of " + num2 + " is : " + Integer.toBinaryString(num2));

		result = ~num;

		System.out.println("Binary of " + result + " is : " + Integer.toBinaryString(result));

	}
}
/*
2^10  2^9  2^8  2^7  2^6  2^5  2^4  2^3  2^2  2^1  2^0
1024  512  256  128   64   32   16   8    4    2    1
                1       1   1   1    1    1     1   1       255
				 0     0    0    0   0    1    0    1     5
				 0     0    0    1   1    1    1    1    31
				 0     1    1    1   1    1    1    1    127
				 0     1    0    0   0    0    0    0    64

               1 + 0 = 1,  0 + 1 = 1 , 0 + 0 = 0, 1 + 1 = (1)0

                0     1     0    0   0    0    0    1   65

               0      0     0    1   1    1    1    1    31
               1      1     1    0   0    0    0    0
               0      0     0    0   0    0    0    1

               1      1     1    0    0    0    0    1    -31     65 + (-31)
               0      1      0   0    0    0    0    1     65
               0      0      1   0    0    0    1    0   32 + 2 = 34
*/










