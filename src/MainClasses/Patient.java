package MainClasses;

import java.time.LocalDate;


public class Patient extends Person{
    private Room room;
    private String admissionDate;
    private String dischargeDate;

    public Patient(String nationalID, String name, String lastName, String age, String gender, String phone, String admissionDate, String dischargeDate) {
        super(nationalID, name, lastName, age, gender, phone);
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public Patient(String nationalId, String firstName, String lastName, String age, String gender, String phone, String roomId, String admissionDate, String dischargeDate) {
        super(nationalId,firstName,lastName,age,gender,phone);
        this.room=new Room();
        this.room.setRoomID(roomId);
        this.admissionDate=admissionDate;
        this.dischargeDate=dischargeDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Room: " + room.getRoomID() + ", Admission Date: " + admissionDate + ", Discharge Date: " + dischargeDate;
    }
}
