package fi.vtt.nubomedia.kurentoroomclientandroid;

import java.net.URI;

import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;

/**
 * Created by vvvilleh on 30.11.2015.
 */
public class Main implements KurentoRoom.RoomEvents{
	private String wsUrl = "ws://127.0.0.1";
	private KurentoRoom kurento;
	private LooperExecutor executor;
	private Room room;

	public void register(){
		URI wsUri = URI.create(wsUrl);
		kurento = new KurentoRoom(wsUri, this, executor);
	}

	@Override
	public void onRegister(KurentoRoom kurento) {
		// Create Room
		RoomOptions roomOptions = new RoomOptions();
		roomOptions.setRoom("room");
		roomOptions.setUser("user");
		room = kurento.createRoom(roomOptions);

		// TODO: Create Stream
		// TODO: Add events to Stream and Room
		// TODO: Init Stream
	}

	@Override
	public void onError(Exception e) {

	}
}
