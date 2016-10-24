/*
 * (C) Copyright 2016 VTT (http://www.vtt.fi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package fi.vtt.nubomedia.kurentoroomclientandroid;

import android.util.Log;

import net.minidev.json.JSONObject;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.handshake.ServerHandshake;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Vector;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcNotification;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcResponse;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;

/**
 * Class that handles all Room API calls and passes asynchronous
 * responses and notifications to a RoomListener interface.
 */
public class KurentoRoomAPI extends KurentoAPI {

    public enum Method {JOIN_ROOM, PUBLISH_VIDEO, UNPUBLISH_VIDEO, RECEIVE_VIDEO, STOP_RECEIVE_VIDEO}

    private static final String LOG_TAG = "KurentoRoomAPI";
    private KeyStore keyStore;
    private boolean usingSelfSigned = false;
    private Vector<RoomListener> listeners;

    /**
     * Constructor that initializes required instances and parameters for the API calls.
     * WebSocket connections are not established in the constructor. User is responsible
     * for opening, closing and checking if the connection is open through the corresponding
     * API calls.
     *
     * @param executor is the asynchronous UI-safe executor for tasks.
     * @param uri is the web socket link to the room web services.
     * @param listener interface handles the callbacks for responses, notifications and errors.
     */
    public KurentoRoomAPI(LooperExecutor executor, String uri, RoomListener listener){
        super(executor, uri);

        listeners = new Vector<>();
        listeners.add(listener);

        // Create a KeyStore containing our trusted CAs
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for the user to join the room. If the room does not exist previously,
     * it will be created.
     *
     * The response will contain the list of users and their streams in the room.
     *
     * @param userId is the username as it appears to all other users.
     * @param roomId is the name of the room to be joined.
     * @param dataChannelsEnabled True if data channels should be enabled for this user
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendJoinRoom(String userId, String roomId, boolean dataChannelsEnabled, int id){
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("user", userId);
        namedParameters.put("room", roomId);
        namedParameters.put("dataChannels", dataChannelsEnabled);
        send("joinRoom", namedParameters, id);
    }

    /**
     * Method will leave the current room.
     *
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendLeaveRoom(int id){
        send("leaveRoom", null, id);
    }

    /**
     * Method to publish a video. The response will contain the sdpAnswer attribute.
     *
     * @param sdpOffer is a string sent by the client
     * @param doLoopback is a boolean value enabling media loopback
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendPublishVideo(String sdpOffer, boolean doLoopback, int id){
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("sdpOffer", sdpOffer);
        namedParameters.put("doLoopback", doLoopback);
        send("publishVideo", namedParameters, id);
    }

    /**
     * Method unpublishes a previously published video.
     *
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendUnpublishVideo(int id){
        send("unpublishVideo", null, id);
    }

    /**
     * Method represents the client's request to receive media from participants in
     * the room that published their media. The response will contain the sdpAnswer attribute.
     *
     * @param sender is a combination of publisher's name and his currently opened stream
     *               (usually webcam) separated by underscore. For example: userid_webcam
     * @param sdpOffer is the SDP offer sent by this client.
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendReceiveVideoFrom(String sender, String streamId, String sdpOffer, int id){
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("sdpOffer", sdpOffer);
        namedParameters.put("sender", sender + "_" + streamId);
        send("receiveVideoFrom", namedParameters, id);
    }

    /**
     * Method represents a client's request to stop receiving media from a given publisher.
     * Response will contain the sdpAnswer attribute.
     *
     * @param userId is the publisher's username.
     * @param streamId is the name of the stream (typically webcam)
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendUnsubscribeFromVideo(String userId, String streamId, int id){
        String sender = userId+"_"+streamId;
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("sender", sender);
        send("unsubscribeFromVideo", namedParameters, id);
    }

    /**
     * Method carries the information about the ICE candidate gathered on the client side.
     * This information is required to implement the trickle ICE mechanism.
     *
     * @param endpointName is the username of the peer whose ICE candidate was found
     * @param candidate contains the candidate attribute information
     * @param sdpMid is the media stream identification, "audio" or "video", for the m-line,
     *               this candidate is associated with.
     * @param sdpMLineIndex is the index (starting at 0) of the m-line in the SDP,
     *                      this candidate is associated with.
     */
    @SuppressWarnings("unused")
    public void sendOnIceCandidate(String endpointName, String candidate, String sdpMid, String sdpMLineIndex, int id){
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("endpointName", endpointName);
        namedParameters.put("candidate", candidate);
        namedParameters.put("sdpMid", sdpMid);
        namedParameters.put("sdpMLineIndex", sdpMLineIndex);
        send("onIceCandidate", namedParameters, id);
    }

    /**
     * Method sends a message from the user to all other participants in the room.
     *
     * @param roomId is the name of the room.
     * @param userId is the username of the user sending the message.
     * @param message is the text message sent to the room.
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendMessage(String roomId, String userId, String message, int id){
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("message", message);
        namedParameters.put("userMessage", userId);
        namedParameters.put("roomMessage", roomId);
        send("sendMessage", namedParameters, id);
    }

    /**
     * Method to send any custom requests that are not directly implemented by the Room server.
     *
     * @param names is an array of parameter names.
     * @param values is an array of parameter values where the index is corresponding with
     *               the applicable name value in the names array.
     * @param id is an index number to track the corresponding response message to this request.
     */
    @SuppressWarnings("unused")
    public void sendCustomRequest(String[] names, String[] values, int id){
        if(names==null || values==null||names.length!=values.length){
            return;  // mismatching name-value pairs
        }
        HashMap<String, Object> namedParameters = new HashMap<>();
        for(int i=0;i<names.length;i++) {
            namedParameters.put(names[i], values[i]);
        }
        send("customRequest", namedParameters, id);

    }

    /**
     * This methods can be used to add a self-signed SSL certificate to be trusted when establishing
     * connection.
     * @param alias is a unique alias for the certificate
     * @param cert is the certificate object
     */
    @SuppressWarnings("unused")
    public void addTrustedCertificate(String alias, Certificate cert){
        try {
            keyStore.setCertificateEntry(alias, cert);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches on/off the self-signed certificate support.
     *
     * @see KurentoRoomAPI#addTrustedCertificate(String, Certificate)
     * @param use Boolean which indicates whether to use self-signed certificates
     */
    @SuppressWarnings("unused")
    public void useSelfSignedCertificate(boolean use){
        this.usingSelfSigned = use;
    }

    /**
     * Opens a web socket connection to the predefined URI as provided in the constructor.
     * The method responds immediately, whether or not the connection is opened.
     * The method isWebSocketConnected() should be called to ensure that the connection is open.
     * Secure socket is created if protocol contained in Uri is either https or wss.
     */
    public void connectWebSocket() {
        if(isWebSocketConnected()){
            return;
        }
        // Switch to SSL web socket client factory if secure protocol detected
        String scheme;
        try {
            scheme = new URI(wsUri).getScheme();
            if (scheme.equals("https") || scheme.equals("wss")){

                // Create an SSLContext that uses our or default TrustManager
                SSLContext sslContext = SSLContext.getInstance("TLS");

                if (usingSelfSigned) {
                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);
                    sslContext.init(null, tmf.getTrustManagers(), null);
                } else {
                    sslContext.init(null, null, null);
                }
                webSocketClientFactory = new DefaultSSLWebSocketClientFactory(sslContext);
            }
        } catch (URISyntaxException|NoSuchAlgorithmException|KeyStoreException|KeyManagementException e) {
            e.printStackTrace();
        }
        super.connectWebSocket();
    }

    /* WEB SOCKET CONNECTION EVENTS */

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        super.onOpen(handshakedata);

        synchronized (listeners) {
            for (RoomListener rl : listeners) {
                rl.onRoomConnected();
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        super.onClose(code, reason, remote);

        synchronized (listeners) {
            for (RoomListener rl : listeners) {
                rl.onRoomDisconnected();
            }
        }
    }

    /**
     * Callback method that relays the RoomResponse or RoomError to the RoomListener interface.
     */
    @Override
    public void onResponse(JsonRpcResponse response) {
        if(response.isSuccessful()){
            JSONObject jsonObject = (JSONObject)response.getResult();
            RoomResponse roomResponse = new RoomResponse(response.getId().toString(), jsonObject);

            synchronized (listeners) {
                for (RoomListener rl : listeners) {
                    rl.onRoomResponse(roomResponse);
                }
            }
        } else {
            RoomError roomError = new RoomError(response.getError());

            synchronized (listeners) {
                for (RoomListener rl : listeners) {
                    rl.onRoomError(roomError);
                }
            }
        }
    }

    /**
     * Callback method that relays the RoomNotification to the RoomListener interface.
     */
    @Override
    public void onNotification(JsonRpcNotification notification) {
        RoomNotification roomNotification = new RoomNotification(notification);

        synchronized (listeners) {
            for (RoomListener rl : listeners) {
                rl.onRoomNotification(roomNotification);
            }
        }
    }

    @SuppressWarnings("unused")
    public void addObserver(RoomListener listener){
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    @SuppressWarnings("unused")
    public void removeObserver(RoomListener listener){
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    /**
     * Callback method that relays error messages to the RoomListener interface.
     * @param e The exception instance
     */
    @Override
    public void onError(Exception e) {
        Log.e(LOG_TAG, "onError: "+e.getMessage(), e);
    }

}