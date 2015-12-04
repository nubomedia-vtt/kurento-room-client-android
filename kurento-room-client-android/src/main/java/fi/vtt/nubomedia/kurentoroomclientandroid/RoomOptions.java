package fi.vtt.nubomedia.kurentoroomclientandroid;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vvvilleh on 27.11.2015.
 */
public class RoomOptions {
	private String user;
	private String room;
	private String id;
	private HashMap<String, RoomOptions> streams;
	private Participant participant;
	private boolean receiveAudio;
	private boolean receiveVideo;
	private boolean subscribeToStreams;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, RoomOptions> getStreams() {
		return streams;
	}

	public void setStreams(HashMap<String, RoomOptions> streams) {
		this.streams = streams;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public boolean isReceiveAudio() {
		return receiveAudio;
	}

	public void setReceiveAudio(boolean receiveAudio) {
		this.receiveAudio = receiveAudio;
	}

	public boolean isReceiveVideo() {
		return receiveVideo;
	}

	public void setReceiveVideo(boolean receiveVideo) {
		this.receiveVideo = receiveVideo;
	}

	public boolean isSubscribeToStreams() {
		return subscribeToStreams;
	}

	public void setSubscribeToStreams(boolean subscribeToStreams) {
		this.subscribeToStreams = subscribeToStreams;
	}
}
