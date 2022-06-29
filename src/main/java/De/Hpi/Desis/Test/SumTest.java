package De.Hpi.Desis.Test;

import De.Hpi.Desis.Dao.*;

import java.util.ArrayList;

public class SumTest {

    public static void main(String[] args){
        int calculationTimes = 1000000;
        int queryNumber = 1;
        double[] result = new double[1];
        result[0] = 0;
        ArrayList<Long> testList = new ArrayList<Long>();

        for(int i = 0; i < calculationTimes; i++){
            testList.add(System.nanoTime());
        }

        long startTime = System.nanoTime();
        testList.forEach(temp -> {
            result[0] += temp;
        });
        System.out.println(System.nanoTime() - startTime);


    }



}
