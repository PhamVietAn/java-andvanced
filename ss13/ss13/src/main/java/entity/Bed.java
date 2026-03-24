package entity;

public class Bed {
    private String bedCode;
    private String status;

    public Bed(String bedCode, String status) {
        this.bedCode = bedCode;
        this.status = status;
    }

    public String getBedCode() { return bedCode; }
    public String getStatus() { return status; }
}
