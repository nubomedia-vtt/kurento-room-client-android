package fi.vtt.nubomedia.kurentoroomclientandroid;

/**
 * Interface class defining the KurentoRoomAPI room events
 */
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

    /**
     * Room has responded to a message
     * @param response The response object
     */
    public void onRoomResponse(RoomResponse response);

    /**
     * The room has encountered an error
     * @param error The error object
     */
    public void onRoomError(RoomError error);

    /**
     * The room has sent a notification
     * @param notification The notification object
     */
    public void onRoomNotification(RoomNotification notification);

    /**
     * The connection to room is ready.
     */
    public void onRoomConnected();

    /**
     * The connection to room is lost or disconnected.
     */
    public void onRoomDisconnected();
}
