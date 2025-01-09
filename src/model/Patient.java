package model;

import java.util.Date;

public class Patient extends Person{
    private int roomID;
    private Date admissionDate;
    private Date dischargeDate;

    public Patient(int nationalID, String name, String lastName, int age, String gender, String phone, String address, int roomID, Date admissionDate, Date dischargeDate) {
        super(nationalID, name, lastName, age, gender, phone, address);
        this.roomID = roomID;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
}
