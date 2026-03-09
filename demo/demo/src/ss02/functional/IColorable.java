package ss02.functional;

public interface IColorable {
    String RED = "red";
    String GREEN = "green";
    String BLUE = "blue";

    void printColor(String color);

    //Java 8
    default void draw() { // không bắt buộc lớp con phải ghi đè
        System.out.println("Tô màu");
    }

    default void saw() {}

    static void erase(String color) {
        System.out.println("xoá");
    }

    private void toStr() {}
}
