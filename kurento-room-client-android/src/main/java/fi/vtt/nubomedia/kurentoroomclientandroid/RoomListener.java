package fi.vtt.nubomedia.kurentoroomclientandroid;

public interface RoomListener {

    /**
     *  Notification method names
     */
    public static final String METHOD_PARTICIPANT_JOINED = "participantJoined";
    public static final String METHOD_PARTICIPANT_PUBLISHED = "participantPublished";
    public static final String METHOD_PARTICIPANT_UNPUBLISHED = "participantUnpublished";
    public static final String METHOD_ICE_CANDIDATE = "iceCandidate";
    public static final String METHOD_PARTICIPANT_LEFT = "participantLeft";
    public static final String METHOD_SEND_MESSAGE = "sendMessage";
    public static final String METHOD_MEDIA_ERROR = "mediaError";


    public void onRoomResponse(RoomResponse response);

    public void onRoomError(RoomError error);

    public void onRoomNotification(RoomNotification notification);


}
