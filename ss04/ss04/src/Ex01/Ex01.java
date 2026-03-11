package Ex01;

public class Ex01 {
    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        int length = username.length();
        if (length < 6 || length > 20) {
            return false;
        }
        if (username.contains(" ")) {
            return false;
        }
        return true;
    }
}
