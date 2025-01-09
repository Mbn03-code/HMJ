package model;

public class Room {
    private int roomID;
    private RoomType roomType;
    private boolean isOccupied;

    public Room(int roomID, RoomType roomType, boolean isOccupied) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.isOccupied = isOccupied;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType.toString();
    }

    public void setRoomType(RoomType roomType) {
        this.roomType=roomType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
