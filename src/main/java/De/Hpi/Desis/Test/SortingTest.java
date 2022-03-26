package De.Hpi.Desis.Test;

import De.Hpi.Desis.Dao.Tuple;

import java.util.LinkedList;
import java.util.Random;

public class SortingTest {

    public static void main(String[] args){
        LinkedList<Tuple> tupleLinkedList = new LinkedList<Tuple>();
        int listSize = 1000000;
        Random random = new Random();

        long timeBefore1 = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            Tuple tuple = new Tuple();
            tuple.DATA = random.nextDouble() * 1000000;
            tupleLinkedList.add(tuple);
        }
        tupleLinkedList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        long timeAfter1 = System.currentTimeMillis();
        System.out.println(timeAfter1 - timeBefore1);

        long timeBefore2 = System.currentTimeMillis();
        for(int j = 0; j < 10; j++){
            LinkedList<Tuple> tupleLinkedListTemp = new LinkedList<Tuple>();
            for(int i = 1; i <= 100000; i++){
                Tuple tuple = new Tuple();
                tuple.DATA = random.nextDouble() * 1000000;
                tupleLinkedList.add(tuple);
            }
            tupleLinkedListTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
            tupleLinkedList.addAll(tupleLinkedListTemp);
        }
        tupleLinkedList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        long timeAfter2 = System.currentTimeMillis();
        System.out.println(timeAfter2 - timeBefore2);
    }



}
