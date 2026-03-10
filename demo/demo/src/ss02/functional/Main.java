package ss02.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        IColorable obj = new Shape();

        obj.draw();
        obj.printColor("red");

        // Function dựng sẵn: Consumer, Predicate, Function, Supplier

        // Lambda expression: là cú pháp viết gọn của 1 method dùng để viết nhanh trong lớp interface
//        IFunctional lb = (a,b) -> 1;

        Comparator<Cat> com1 = (c1,c2) -> -1;
        Collections.sort(new ArrayList<>(), (c1,c2) -> 1);

        int[] arr = {1,2,3,4,5};
        Arrays.stream(arr).map(value -> value*value).forEach(t -> System.out.println(t));
    }
}
