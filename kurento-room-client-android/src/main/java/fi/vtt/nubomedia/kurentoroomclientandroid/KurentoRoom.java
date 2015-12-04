package fi.vtt.nubomedia.kurentoroomclientandroid;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Map;

import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcNotification;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcRequest;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcResponse;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcWebSocketClient;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;


public class KurentoRoom implements JsonRpcWebSocketClient.WebSocketConnectionEvents{
	private URI wsUri;
	private JsonRpcWebSocketClient wsClient;
	private RoomEvents roomEvents;
	private Room room;
	private String userName;

	public interface RoomEvents{
		void onRegister(KurentoRoom kurento);
		void onError(Exception e);
	}

	public interface RpcResponseEvents{
		void onJoinRoom();
		void onLeaveRoom();
		void onUnpublishVideo();
		void onUnsubscribeFromVideo();
		void onIceCandidate();
		void onPublishVideo();
		void onReceiveVideoFrom();
		//void onSendMessage(); // TODO
		//void onSendCustomRequest(); // TODO
	}

	public KurentoRoom(URI wsUri, RoomEvents roomEvents, LooperExecutor executor) {
		this.roomEvents = roomEvents;

		wsClient = new JsonRpcWebSocketClient(wsUri, this, executor);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		// FIXME: Use LooperExecutor
		roomEvents.onRegister(this);
		// TODO: Create Room
		// TODO: Create Stream
		// TODO: Add events to Stream and Room
		// TODO: Init Stream
	}


	@Override
	public void onRequest(JsonRpcRequest request){

	}
	@Override
	public void onResponse(JsonRpcResponse response){
		Object id = response.getId();

	}
	@Override
	public void onNotification(JsonRpcNotification notification){

	}


	@Override
	public void onClose(int code, String reason, boolean remote) {

	}

	@Override
	public void onError(Exception e) {
		roomEvents.onError(e);
	}


	/**
	 *
	 * @param id
	 * @param method
	 * @param namedParams
	 * @param positionalParams
	 */
	public void sendRequestWithPositionalParams(Object id, String method, Map<String, Object> namedParams, List<Object> positionalParams){
		JsonRpcRequest request = new JsonRpcRequest();
		request.setId(id);
		request.setMethod(method);
		request.setNamedParams(namedParams);
		request.setPositionalParams(positionalParams);

		// TODO: Store ID-method pairs in order to match the upcoming response to the corresponding request

		wsClient.sendRequest(request);
	}


	/**
	 *
	 * @param roomOptions
	 * @return
	 */
	public Room createRoom(RoomOptions roomOptions){
		this.room = new Room(this, roomOptions);
		userName = roomOptions.getUser();

		return this.room;
	}
}
