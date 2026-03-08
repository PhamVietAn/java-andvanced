package ss01.exception;

public class MyUncheckedException extends Exception {
    private int code;
    public MyUncheckedException(String message) {
        super(message);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
