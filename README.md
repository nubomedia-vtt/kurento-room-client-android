kurento-room-android
=================
This repository contains Kurento Room and Tree APIs for Android.

This project is part of [NUBOMEDIA](http://www.nubomedia.eu).

The source code is available at [https://github.com/nubomedia-vtt/kurento-room-client-android](https://github.com/nubomedia-vtt/kurento-room-client-android).


Repository structure
--------------------
This repository consists of an Android Studio library project with gradle build scripts. 

Usage
--------
THe more detailed Developers Guide and Installation Guide are available at [http://kurento-room-client-android.readthedocs.org/en/latest/](http://kurento-room-client-android.readthedocs.org/en/latest/)

You can import this project to your own Android Studio project via Maven (jCenter or Maven Central) by adding the following line to module's `build.gradle` file:
```
compile 'fi.vtt.nubomedia:kurento-room-client-android:1.0.4'
```

If you want to build the project from source, you need to import the third-party libraries via Maven by adding the following lines to
the module's `build.gradle` file
```
compile 'fi.vtt.nubomedia:utilities-android:1.0.0'
compile 'fi.vtt.nubomedia:jsonrpc-ws-android:1.0.4'
compile 'fi.vtt.nubomedia:webrtcpeer-android:1.0.0'
```

Android application code
------------------------
```
import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoRoomAPI;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomError;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomListener;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomNotification;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomResponse;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;

import fi.vtt.nubomedia.kurentoroomclientandroid.TreeError;
import fi.vtt.nubomedia.kurentoroomclientandroid.TreeListener;
import fi.vtt.nubomedia.kurentoroomclientandroid.TreeNotification;
import fi.vtt.nubomedia.kurentoroomclientandroid.TreeResponse;

String wsRoomUri = "ws://YOUR_IP_ADDRESS:8080/room";
String wsTreeUri = "http://YOUR_IP_ADDRESS:8080/kurento-tree
LooperExecutor executor = new LooperExecutor();
executor.requestStart();
RoomListener myRoomListener = ...
TreeListener myTreeListener = ...
public int requestIndex = 0;

KurentoRoomAPI roomApi = new KurentoRoomAPI(executor, wsRoomUri, myRoomListener);
roomApi.connectWebSocket();
if(roomApi.isWebSocketConnected()){
 roomApi.sendJoinRoom("My Name", "My Room", requestIndex++);
 roomApi.sendMessage("My Room", "My Name", "My message.", requestIndex++);
 roomApi.disconnectWebSocket();
}

KurentoTreeAPI treeApi = new KurentoTreeAPI(executor, wsTreeUri, myTreeListener);
treeApi.connectWebSocket();
if(treeApi.isWebSocketConnected()){
 treeApi.sendCreateTree("My Tree", requestIndex++);
 treeApi.disconnectWebSocket();
}

```

```
class MyRoomListener implements RoomListener(){
     @Override
    public void onRoomResponse(RoomResponse response) {
         String responseId = response.getId(); 
         String sessionId = response.getSessionId();
         HashMap<String><String> values = response.getValues();  
    }

    @Override
    public void onRoomError(RoomError error) {
        String errorCode = error.getCode();
        String errorData = error.getData();
    }

    @Override
    public void onRoomNotification(RoomNotification notification) {
      if(notification.getMethod()
        .equals(RoomListener.METHOD_PARTICIPANT_JOINED) {
        // TODO        
      } else if(notification.getMethod()
        .equals(RoomListener.METHOD_SEND_MESSAGE)) {
        // TODO
      } else ...
    }
}
```

```
class MyTreeListener implements TreeListener(){
     @Override
    public void onTreeResponse(TreeResponse response) {
         String responseId = response.getId();
         String sessionId = response.getSessionId();
         HashMap<String><String> values = response.getValues();
    }

    @Override
    public void onTreeError(TreeError error) {
        String errorCode = error.getCode();
        String errorData = error.getData();
    }

    @Override
    public void onTreeNotification(TreeNotification notification) {
      if(notification.getMethod()
        .equals(TreeListener.METHOD_ICE_CANDIDATE) {
        // TODO
      } else ...
    }
}
```



Licensing
---------
[BSD](https://github.com/nubomedia-vtt/kurento-room-client-android/blob/master/LICENSE)

Support
-------
Support is provided through the [NUBOMEDIA VTT Public Mailing List](https://groups.google.com/forum/#!forum/nubomedia-vtt).

