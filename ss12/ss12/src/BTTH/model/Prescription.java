package BTTH.model;

import java.sql.Date;

public class Prescription {
    private int id;
    private int medicineId;
    private int quantitySold;
    private Date saleDate;

    public Prescription(int id, int medicineId, int quantitySold, Date saleDate) {
        this.id = id;
        this.medicineId = medicineId;
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
    }

    public int getId() {
        return id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    @Override
    public String toString() {
        return id + " - MedicineID: " + medicineId +
                " - Qty: " + quantitySold +
                " - Date: " + saleDate;
    }
}