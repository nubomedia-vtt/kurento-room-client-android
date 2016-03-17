package fi.vtt.nubomedia.kurentoroomclientandroid;

import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcResponseError;

/**
 * Room error class
 */
public class RoomError {
    private String code = null;
    private String data = null;

    public RoomError(JsonRpcResponseError error){
        super();
        if(error!=null) {
            this.code = "" + error.getCode();
            if(error.getData()!=null) {
                this.data = error.getData().toString();
            }
        }
    }

    public String getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String toString(){
        return "RoomError: "+code+" - "+data;
    }
}
