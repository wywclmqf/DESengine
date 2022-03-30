package De.Hpi.Desis.Test;

import java.util.ArrayList;

public class RandomTest {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Frühling");
        arrayList.add("Sommer");
        arrayList.add("Herbst");
        arrayList.add("WINTER");

        arrayList.forEach(element -> {
            if(!element.equals("Sommer")) {
                System.out.println(element);
            }else{
                arrayList.remove("Sommer");
            }
        });

    }
}
