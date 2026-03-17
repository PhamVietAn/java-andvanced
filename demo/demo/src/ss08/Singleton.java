package ss08;

public class Singleton {
    public static void main(String[] args) {
        StudentMenu intance = StudentMenu.getInstance();
        intance.printMenu();
    }
}

class StudentMenu {
    private  static StudentMenu instance = new StudentMenu();
    private StudentMenu() {
        // khởi tạo các thành phần
    }

    public static StudentMenu getInstance() {
        if (instance == null) {
            instance = new StudentMenu();

        }
        return  instance;
    }

    public void printMenu() {
    }
}
