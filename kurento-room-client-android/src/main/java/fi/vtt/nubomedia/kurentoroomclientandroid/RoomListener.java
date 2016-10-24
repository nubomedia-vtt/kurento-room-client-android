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
