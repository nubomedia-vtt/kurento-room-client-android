%%%%%%%%%%%%%%%%
Developers Guide
%%%%%%%%%%%%%%%%

This documents provides information how to utilize the kurento-room-client-android library for your project.


Setup the developing environment by importing the project to Android Studio.
You can import this project to your own Android Studio project via Maven (jCenter or Maven Central) by adding the following line to module's build.gradle file:


.. code:: java

    compile 'fi.vtt.nubomedia:kurento-room-client-android:1.0.6'


KurentoRoomAPI is used as follows. First import clauses

.. code:: java

	import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoRoomAPI;
	import fi.vtt.nubomedia.kurentoroomclientandroid.RoomError;
	import fi.vtt.nubomedia.kurentoroomclientandroid.RoomListener;
	import fi.vtt.nubomedia.kurentoroomclientandroid.RoomNotification;
	import fi.vtt.nubomedia.kurentoroomclientandroid.RoomResponse;
	import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;
	import fi.vtt.nubotest.util.Constants;

Implement ``RoomListener`` either via inheritance or composition (we use inheritance in this example)

.. code:: java

	public class MyClass implements RoomListener {
		public void onRoomResponse(RoomResponse response);
		public void onRoomError(RoomError error);
		public void onRoomNotification(RoomNotification notification);
	}
	
The callback functions are described in the javadoc. Next, implement ``KurentoRoomAPI``:
	
.. code:: java
	
	public class MyClass implements RoomListener {
	
		private LooperExecutor executor;
		private static KurentoRoomAPI kurentoRoomAPI;
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
			executor = new LooperExecutor();
			executor.requestStart();
			String wsRoomUri = "ws://mykurentoserver:8080/room";
			kurentoRoomAPI = new KurentoRoomAPI(executor, wsUri, this);
		}
	}

Your ``KurentoRoomAPI`` has been now created. Please refer to the Java documentation to learn more about the available API functions.


Source code is available at
https://github.com/nubomedia-vtt/kurento-room-client-android

The Javadoc is included in the source code and can be downloaded from the link below:
https://github.com/nubomedia-vtt/kurento-room-client-android/tree/master/javadoc 

Support is provided through the Nubomedia VTT Public Mailing List available at
https://groups.google.com/forum/#!forum/nubomedia-vtt


Adding a trusted self-signed certificate
========================================
KurentoRoomAPI supports developers to add a trusted self-signed certificate. This allows testing without CA certificate, and moreover if the application uses only one Kurento server, no CA certificate is needed.

Here is an example on how to include a self-signed certificate from assets in Android Studio:

.. code:: java

    KurentoRoomAPI kurentoRoomAPI;
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    InputStream caInput = new BufferedInputStream(myActivity.context.getAssets().open("my_server_certificate.cer"));
    Certificate myCert = cf.generateCertificate(caInput);
    kurentoRoomAPI.addTrustedCertificate("MyServersCertificate", myCert);
    kurentoTreeAPI.useSelfSignedCertificate(true);

Now the application trusts a server which possesses private key of certificate "my_server_certificate.cer".

WSS support on Android 5.0.x (Lollipop) and up
==============================================
kurento-room-client-android library uses Maven org.java_websocket:
http://mvnrepository.com/artifact/org.java-websocket/Java-WebSocket/

However, org.java_websocket version 1.3.0 is not compatible with Android 5.0.x systems due to malfunction in wss protocol handshake. Until a newer version is uploaded to Maven, a workaround is to compile a newer version from git:
https://github.com/TooTallNate/Java-WebSocket

This limitation is known to exist only in wss TSL handshake. Android 5.1.x and up should not have this issue.
