package De.Hpi.Desis.Test;

import De.Hpi.Desis.Dao.Tuple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class SortingTest {

    public static void main(String[] args){
        int listSize = 1000000;
        Random random = new Random();

//        long timeBefore1 = System.nanoTime();
//        for(int i = 0; i < 1000000; i++){
//            Tuple tuple = new Tuple();
//            tuple.DATA = random.nextDouble() * 1000000;
//            tupleLinkedList.add(tuple);
//        }
//        tupleLinkedList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
//        long timeAfter1 = System.currentTimeMillis();
//        System.out.println(timeAfter1 - timeBefore1);
//
//        long timeBefore2 = System.currentTimeMillis();
//        for(int j = 0; j < 10; j++){
//            LinkedList<Tuple> tupleLinkedListTemp = new LinkedList<Tuple>();
//            for(int i = 1; i <= 100000; i++){
//                Tuple tuple = new Tuple();
//                tuple.DATA = random.nextDouble() * 1000000;
//                tupleLinkedList.add(tuple);
//            }
//            tupleLinkedListTemp.sort((a, b) -> Double.compare(a.DATA, b.DATA));
//            tupleLinkedList.addAll(tupleLinkedListTemp);
//        }
//        tupleLinkedList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
//        long timeAfter2 = System.currentTimeMillis();
//        System.out.println(timeAfter2 - timeBefore2);

        //for sorting array is faster than link
        ArrayList<Tuple> tupleLinkedList = new ArrayList<Tuple>();
        ArrayList<Tuple> tupleLinkedList1 = new ArrayList<Tuple>();
        ArrayList<Tuple> tupleLinkedList2 = new ArrayList<Tuple>();
        ArrayList<Tuple> tupleLinkedList1C = new ArrayList<Tuple>();
        ArrayList<Tuple> tupleLinkedList2C = new ArrayList<Tuple>();
        for(int i = 0; i < listSize; i++){
            Tuple tuple = new Tuple();
            tuple.DATA = random.nextDouble() * 1000000;
            tupleLinkedList1.add(tuple);
            tuple = new Tuple();
            tuple.DATA = random.nextDouble() * 1000000;
            tupleLinkedList2.add(tuple);
        }
        tupleLinkedList1C.addAll(tupleLinkedList1);
        tupleLinkedList2C.addAll(tupleLinkedList2C);


        long timeBefore1 = System.nanoTime();
        tupleLinkedList.addAll(tupleLinkedList1);
        tupleLinkedList.addAll(tupleLinkedList2);
        tupleLinkedList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        System.out.println(System.nanoTime() - timeBefore1);

        tupleLinkedList.clear();

        timeBefore1 = System.nanoTime();
        tupleLinkedList1C.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        tupleLinkedList2C.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        System.out.println(System.nanoTime() - timeBefore1);

        timeBefore1 = System.nanoTime();
        tupleLinkedList.addAll(tupleLinkedList1C);
        tupleLinkedList.addAll(tupleLinkedList2C);
        tupleLinkedList.sort((a, b) -> Double.compare(a.DATA, b.DATA));
        System.out.println(System.nanoTime() - timeBefore1);

    }



}
