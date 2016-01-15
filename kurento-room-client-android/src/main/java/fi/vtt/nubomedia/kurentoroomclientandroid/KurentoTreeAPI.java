package fi.vtt.nubomedia.kurentoroomclientandroid;

import android.util.Log;

import net.minidev.json.JSONObject;

import java.util.HashMap;

import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcNotification;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcResponse;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcWebSocketClient;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;


public class KurentoTreeAPI extends KurentoAPI {
    private static final String LOG_TAG = "KurentoRoomAPI";
    private JsonRpcWebSocketClient client = null;
    private LooperExecutor executor = null;
    private String wsUri = "http://treeserver:port/kurento-tree";
    private TreeListener treeListener = null;

    /**
     * Constructor that initializes required instances and parameters for the API calls.
     * WebSocket connections are not established in the constructor. User is responsible
     * for opening, closing and checking if the connection is open through the corresponding
     * API calls.
     *
     * @param executor is the asynchronous UI-safe executor for tasks.
     * @param uri is the web socket link to the tree web services.
     * @param listener interface handles the callbacks for responses, notifications and errors.
     */
    public KurentoTreeAPI(LooperExecutor executor, String uri, TreeListener listener){
        super();
        this.executor = executor;
        this.wsUri = uri;
        this.treeListener = listener;
    }

    /**
     * The method represents client's request to create a new tree in Tree server.
     * The response contains attributes "value" and "sessionId", where value is the name
     * of the created tree.
     *
     * @param treeId is the name of the tree to be created.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendCreateTree(String treeId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        send("createTree", namedParameters, id);
    }

    /**
     * The method represents a request to configure the emitter (source) in a broadcast
     * session (tree). The response contains attributes "sdpAnswer" and "sessionId".
     *
     * @param treeId is the name of the tree this method refers to.
     * @param offerSdp is the SDP offer sent by this client.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendSetTreeSource(String treeId, String offerSdp, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("offerSdp", offerSdp);
        send("setTreeSource", namedParameters, id);
    }

    /**
     * The method requests to remove the current emitter of a tree.
     * The response includes the following parameters: sessionId
     *
     * @param treeId is the name of the tree this method refers to.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendRemoveTreeSource(String treeId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        send("removeTreeSource", namedParameters, id);
    }

    /**
     * The method requests to add a new viewer (sink) to the tree.
     *  The response includes the following parameters: sdpAnswer, sinkId, and sessionId.
     *
     * @param treeId is the name of the tree this method refers to.
     * @param offerSdp is the SDP offer sent by this client.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendAddTreeSink(String treeId, String offerSdp, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("offerSdp", offerSdp);
        send("addTreeSink", namedParameters, id);
    }

    /**
     * The method requests to remove a previously connected sink (viewer).
     * The response includes the following parameters: sessionId
     *
     * @param treeId  is the name of the tree this method refers to.
     * @param sinkId is the name of the previously connected sink.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendRemoveTreeSink(String treeId, String sinkId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("sinkId", sinkId);
        send("removeTreeSink", namedParameters, id);
    }


    /**
     * The method to request to add a new ice candidate.
     * The response includes the following parameters: sessionId
     *
     * @param treeId  is the name of the tree this method refers to.
     * @param sinkId is the name of the previously connected sink.
     * @param sdpMid is the media stream identification, "audio" or "video", for the m-line.
     * @param sdpMLineIndex is the index (starting at 0) of the m-line in the SDP
     * @param candidate contains the candidate attribute information
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendAddIceCandidate(String treeId,String sinkId, String sdpMid, int sdpMLineIndex, String candidate, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("sinkId", sinkId);
        namedParameters.put("sdpMid", sdpMid);
        namedParameters.put("sdpMLineIndex", new Integer(sdpMLineIndex));
        namedParameters.put("candidate", candidate);
        send("addIceCandidate", namedParameters, id);
    }

    /**
     * The method to request to remove a tree.
     * The response includes the following parameters: sessionId
     *
     * @param treeId  is the name of the tree this method refers to.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendRemoveTree(String treeId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        send("removeTree", namedParameters, id);
    }

        /* WEB SOCKET CONNECTION EVENTS */


    /**
     * Callback method that relays the TreeResponse or TreeError to the TreeListener interface.
     */
    @Override
    public void onResponse(JsonRpcResponse response) {
        if(response.isSuccessful()){
            JSONObject jsonObject = (JSONObject)response.getResult();
            TreeResponse roomResponse = new TreeResponse(response.getId().toString(), jsonObject);
            treeListener.onTreeResponse(roomResponse);
        } else {
            TreeError treeError = new TreeError(response.getError());
            treeListener.onTreeError(treeError);
        }
    }

    /**
     * Callback method that relays the RoomNotification to the RoomListener interface.
     */
    @Override
    public void onNotification(JsonRpcNotification notification) {
        TreeNotification treeNotification = new TreeNotification(notification);
        treeListener.onTreeNotification(treeNotification);
    }

    @Override
    public void onError(Exception e) {
        Log.e(LOG_TAG, "onError: "+e.getMessage(), e);
    }


}
