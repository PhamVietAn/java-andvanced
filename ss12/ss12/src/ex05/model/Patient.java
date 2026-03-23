package ex05.model;

import java.util.Date;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String department;
    private String disease;
    private Date admissionDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public Date getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(Date admissionDate) { this.admissionDate = admissionDate; }
}