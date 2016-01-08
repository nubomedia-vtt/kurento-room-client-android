package fi.vtt.nubomedia.kurentoroomclientandroid;

public interface RoomListener {
    public void onRoomResponse(RoomResponse response);

    public void onRoomError(RoomError error);

    public void onRoomNotification(RoomNotification notification);

}
