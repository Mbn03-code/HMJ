package model;

import java.time.LocalDate;


public class Patient extends Person{
    private Room room;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;

    public Patient(String nationalID, String name, String lastName, int age, String gender, String phone, Room room, LocalDate admissionDate, LocalDate dischargeDate) {
        super(nationalID, name, lastName, age, gender, phone);
        this.room = room;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoomID(Room roomID) {
        this.room = room;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Room: " + room.getRoomID() + ", Admission Date: " + admissionDate + ", Discharge Date: " + dischargeDate;
    }
}
