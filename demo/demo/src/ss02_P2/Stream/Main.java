package ss02_P2.Stream;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        for(Integer value : list){
            if (value.equals(2)){
                list.remove(value);
            }
        }
        System.out.println(list);

        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()){
            if (iterator.next().equals(1)){
                iterator.remove();
            }
        }
        System.out.println(list);

        // tạo Stream: Array và Collection
        int[] arr = {1, 2, 3, 4, 5};
        IntStream streamInt = Arrays.stream(arr);

        Stream<Integer> stream2 = list.stream();

        // thao tác trung gian: sorted, limit, skip, filter, map,...
        IntStream stream3 = streamInt.filter(value -> value%2 == 0);

        // thao tác đầu cuối: sum, count, forEach, collect, reduce, anyMatch, allMatch, noneMatch,...
        OptionalDouble avg = stream3.average();
        System.out.println("Trung bình cộng: " + avg.getAsDouble());

        // ví dụ: tạo 1 List 1000 s nguyên ngẫu nhiên từ -200 đến 200
        List<Integer> randomList = Stream.generate(() -> new Random().nextInt(400) - 200)
                .limit(1000)
                .toList();
        // 1. lọc và in các số nguyên dương ra màn hình
        randomList.stream().filter(value -> value > 0).forEach(integer ->  System.out.println(integer+" "));

        // 2. loại bỉ các số trùng lặp
        List<Integer> distinctList = randomList.stream().distinct().toList();
        System.out.println("\ndistinctList: " + distinctList);

        // 3. sắp xếp các số theo thứ tự từ lớn đến bé
        randomList.stream().sorted().forEach(integer ->  System.out.println(integer+" "));

        // 4. tính min, max
        Integer min = randomList.stream().min(Comparator.comparingInt(o -> o)).get();
        Integer max = randomList.stream().max(Comparator.comparingInt(o -> o)).get();

        // 5. tính tổng của tất cả các phần tử
        Integer sum = randomList.stream().reduce(0, Integer::sum);

        // 6. kiểm tra giá trị n nhập vào có tồn tại trong danh sách không
        boolean isExist = randomList.stream().anyMatch(integer -> integer == 100);
        System.out.println("Giá trị 100 có tồn tại trong danh sách không? " + isExist);

        // 7. chuyển các số âm thành số đối của nó
        List<Integer> mapInteger = randomList.stream().map(value -> value < 0 ? -value : value).toList();
        System.out.println("mapInteger: " + mapInteger);
    }
}
