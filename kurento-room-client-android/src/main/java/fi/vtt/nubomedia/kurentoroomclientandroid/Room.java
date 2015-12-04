package fi.vtt.nubomedia.kurentoroomclientandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vvvilleh on 27.11.2015.
 */
public class Room {
	private KurentoRoom kurento;
	private RoomOptions roomOptions;
	private HashMap<String, String> streams;
	private List<Participant> participants;
	private boolean connected;
	private Participant localParticipant;
	private boolean subscribeToStreams;
//	private ee = new EventEmitter();

	public Room(KurentoRoom kurento, RoomOptions roomOptions) {
		this.kurento = kurento;
		this.roomOptions = roomOptions;
		streams = new HashMap<>();
		participants = new ArrayList<>();
		connected = false;
		subscribeToStreams = roomOptions.isSubscribeToStreams() ? roomOptions.isSubscribeToStreams() : true;
	}

	public Participant getLocalParticipant() {
		return localParticipant;
	}

//	public void addEventListener(){}
//	public void emitEvent(){}

	public void connect(){
		// kurento.sendRequest(JOINROOM_METHOD, roomOptions.getUser(), roomOptions.getRoom())
	}
}
