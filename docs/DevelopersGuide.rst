%%%%%%%%%%%%%%%%
Developers Guide
%%%%%%%%%%%%%%%%

This documents provides information how to utilize the kurento-room-client-android library for your project.


Setup the developing environment by importing the project to Android Studio.
You can import this project to your own Android Studio project via Maven (jCenter or Maven Central) by adding the following line to module's build.gradle file:


.. code:: java

    compile 'fi.vtt.nubomedia:kurento-room-client-android:1.0.6'


Android application code

.. code:: java

    import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoRoomAPI;
    import fi.vtt.nubomedia.kurentoroomclientandroid.RoomError;
    import fi.vtt.nubomedia.kurentoroomclientandroid.RoomListener;
    import fi.vtt.nubomedia.kurentoroomclientandroid.RoomNotification;
    import fi.vtt.nubomedia.kurentoroomclientandroid.RoomResponse;
    import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;
    
    
    String wsRoomUri = "ws://YOUR_IP_ADDRESS:8080/room";
    LooperExecutor executor = new LooperExecutor();
    executor.requestStart();
    RoomListener myRoomListener = ...
    public int requestIndex = 0;
    
    KurentoRoomAPI roomApi = new KurentoRoomAPI(executor, wsRoomUri, myRoomListener);
    roomApi.connectWebSocket();
    if(roomApi.isWebSocketConnected()){
     roomApi.sendJoinRoom("My Name", "My Room", requestIndex++);
     roomApi.sendMessage("My Room", "My Name", "My message.", requestIndex++);
     roomApi.disconnectWebSocket();
    }
    
    
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
    

Source code is available at
https://github.com/nubomedia-vtt/kurento-room-client-android

The Javadoc is included in the source code and can be downloaded from the link below:
https://github.com/nubomedia-vtt/kurento-room-client-android/tree/master/javadoc 

Support is provided through the Nubomedia VTT Public Mailing List available at
https://groups.google.com/forum/#!forum/nubomedia-vtt




