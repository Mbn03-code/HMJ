package MainClasses;

public class Room {
    private String roomID;
    private RoomType type;
    private boolean isOccupied;

    public Room(RoomType roomType) {
        this.type = roomType;
    }

    public Room(String roomId, String type, boolean isOccupied) {
        this.roomID=roomId;
        this.type= RoomType.valueOf(type);
        this.isOccupied=isOccupied;
    }

    public Room() {

    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
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
