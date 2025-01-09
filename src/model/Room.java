package model;

public class Room {
    private int roomID;
    private RoomType type;
    private boolean isOccupied;

    public Room(RoomType roomType) {
        this.type = roomType;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getInfo(){
        return "room ID: " +roomID+ " ,room type: "+ type + (isOccupied ? " ,not available" :" available");
    }
}
