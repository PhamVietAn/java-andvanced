package ss02_P2.datetime;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // trước java 8
        Date date = new Date(); // trả vể thời gian của hệ thống theo mili giây
        System.out.println(date);
        System.out.println("mili giây: " + date.getTime());

        // tạo 1 ngày 02-02-2007

        // DateTimeAPI
        LocalDate today = LocalDate.now();
        LocalTime yesterday = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(today.toString());
        System.out.println(yesterday.toString());
        System.out.println(dateTime.toString());
        System.out.println(zonedDateTime.toString());

        LocalDate bornIn = LocalDate.of(2006, 11, 8);

        Period age = Period.between(bornIn, today);
        System.out.println("Tuổi: " + age.getYears());

        LocalDate nextDate = bornIn.plusDays(100);
        System.out.println("Ngày sinh + 100 ngày: " + nextDate);

        // optional:
        Optional<Integer> op1 = Optional.empty();
        Optional<Integer> op2 = Optional.of(1);
        Optional<Integer> op3 = Optional.ofNullable(2);

        if (op3.isPresent()) {
            // có giá trị khác null
            // lấy ra giá trị
            System.out.println("op3: " + op3.get());
        }

        // lấy trực tiếp giá trị nếu nó tồn tại hoặc 1 giá trị chỉ định
        Integer value = op3.orElse(100);

        // Lấy trực tiếp giá trị hoặc ném ngoại lệ
        Integer val = op3.orElseThrow(() -> new RuntimeException("Không có giá trị"));

        // ví dụ:
        List<Integer> randomList = Stream.generate(() -> new Random().nextInt(400) - 200)
                .limit(1000)
                .toList();
        // tìm giá trị lớn nhất trong danh sách
        // tìm ra giá trị đầu tiên chia hết cho 3 trong danh sách
        Integer max = randomList.stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE);

        Integer find = randomList.stream().filter(value1 -> value1 % 3 == 0).findFirst().orElseThrow(() -> new RuntimeException("Không tìm thấy giá trị chia hết cho 3"));
    }
}
