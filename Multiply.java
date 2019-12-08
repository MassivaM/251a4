import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        int val1;
        int val2;
        int val3; 
        int val4;
        int val5; 
        int try1 = (int) (x % Math.pow(2,size));
        int try2 = (int) (y % Math.pow(2,size));

        int[] output = new int[2];

        if(size == 0) {
            return new int[] {0,0};
        }

        if (size == 1) {
          output[0] = (x % 2) * (y % 2);
          output[1] = 1;
          return output;
        }


        else {

        val5 = (int)Math.ceil((size / 2.0));
        val1 = (int) (try1 / Math.pow(2, val5));
        val2 = (int) (try1 % Math.pow(2, val5));
        val3 = (int) (try2 / Math.pow(2, val5));
        val4 = (int) (try2 % Math.pow(2, val5));
        int[] naive1,naive2,naive3,naive4;

        naive1 = naive(val5,val1,val3);
        naive2 = naive(val5,val2,val3);
        naive3 = naive(val5,val2,val4);
        naive4 = naive(val5,val1,val4);

        output[0] = (((int) Math.pow(2, 2*val5)) *naive1[0]) + (((int) Math.pow(2,val5)) * (naive2[0] + naive4[0])) + naive3[0];
        output[1] = 3 * val5 + (naive1[1]+naive3[1]+naive2[1]+naive4[1]);
        return output;
        }
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
        
        int[] output = new int[2];
        int try1, try2;
        int val1, val2, val3, val4, val5;
        int alternate1 = (int) (x % Math.pow(2, size));
        int alternate2 = (int) (y % Math.pow(2, size));

        if (size == 0) {
            return new int[] { 0, 0 };
        }

        if (size == 1) {
            try1 = x % 2;
            try2 = y % 2;
            output[0] = try1 * try2;
            output[1] = 1;
            return output;
        }

        else {
           

            val5 = (int) Math.ceil(size / 2.0);
            val1 = (int) (alternate1 / Math.pow(2, val5));
            val2 = (int) (alternate1 % Math.pow(2, val5));
            val3 = (int) (alternate2 / Math.pow(2, val5));
            val4 = (int) (alternate2 % Math.pow(2, val5));
            
            int[] kar1, kar2, kar3;
             kar1 = karatsuba(val5, val1, val3);
             kar2 = karatsuba(val5, (val1 - val2), (val3 - val4));
             kar3 = karatsuba(val5, val2, val4);
            

            output[0] = ((int) (Math.pow(2, (2 * val5)))) * kar1[0] + ((int) (Math.pow(2, val5))) * (kar1[0] + kar3[0] - kar2[0]) + kar3[0];
            output[1] = (kar1[1] + kar3[1] + kar2[1]) + (6 * val5);
            return output;
        }
     
    
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
