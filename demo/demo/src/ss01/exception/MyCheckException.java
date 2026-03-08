package ss01.exception;

public class MyCheckException extends Exception {
    private int code;
    public MyCheckException(String message) {
        super(message);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
