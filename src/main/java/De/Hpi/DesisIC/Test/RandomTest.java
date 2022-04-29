package De.Hpi.DesisIC.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RandomTest {
    public static void main(String[] args) {
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(1.0);
        arrayList.add(2.3);
        arrayList.add(3.3);
        arrayList.add(4.3);
        arrayList.add(5.3);
        arrayList.add(6.3);

//        Iterator<String> iter = arrayList.iterator();
//        while(iter.hasNext()){
//            String ele = iter.next();
//            if(!ele.equals("WINTER")) {
//                System.out.println(ele);
//            }else{
//                iter.remove();
//                break;
//            }
//        }

        Double result = arrayList.stream().mapToDouble(item -> item).sum();

        arrayList.stream().limit(3).collect(Collectors.toList()).stream();

        System.out.println(result);


    }
}
