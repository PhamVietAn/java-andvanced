package ss02.methodrefences;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Joe", "Joe", "Joe", "Joe", "Joe");
        for (String name : names){
            System.out.println(name);
        }
        Iterator<String> it = names.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        names.forEach(s -> System.out.println(s));
        names.forEach(Printer::print);


        // biến đổi thành danh sách student
        List<Student> students = names.stream().map(s -> new Student(s)).toList();


    }
}
