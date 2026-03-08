public class Ex03 {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Tuoi khong the am!");
        }
        this.age = age;
    }

    public static void main(String[] args) {

        Ex03 user = new Ex03();

        try {
            user.setAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Chuong trinh van tiep tuc...");
    }
}