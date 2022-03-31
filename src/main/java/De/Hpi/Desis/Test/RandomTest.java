package De.Hpi.Desis.Test;

import De.Hpi.Desis.Dao.IntermediateWindow;

import java.util.ArrayList;
import java.util.Iterator;

public class RandomTest {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Frühling");
        arrayList.add("Sommer");
        arrayList.add("Herbst");
        arrayList.add("WINTER");

//        arrayList.forEach(element -> {
//            if(!element.equals("Sommer")) {
//                System.out.println(element);
//            }else{
//                arrayList.remove("Sommer");
//            }
//        });

        Iterator<String> iter = arrayList.iterator();
        while(iter.hasNext()){
            String ele = iter.next();
            if(!ele.equals("WINTER")) {
                System.out.println(ele);
            }else{
                iter.remove();
                break;
            }
        }
        System.out.println(iter.hasNext());
    }
}
