package fi.vtt.nubomedia.kurentoroomclientandroid;

public interface TreeListener {

    /**
     *  Notification method names
     */
    public static final String METHOD_ICE_CANDIDATE = "iceCandidate";


    public void onTreeResponse(TreeResponse response);

    public void onTreeError(TreeError error);

    public void onTreeNotification(TreeNotification notification);


}
