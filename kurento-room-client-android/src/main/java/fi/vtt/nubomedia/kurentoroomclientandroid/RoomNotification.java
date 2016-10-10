package fi.vtt.nubomedia.kurentoroomclientandroid;

import java.util.Map;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcNotification;

/**
 * Room notification class
 */
public class RoomNotification {

    private String method = null;
    private Map<String, Object> params = null;

    public RoomNotification(JsonRpcNotification obj){
        super();
        this.method = obj.getMethod();
        this.params = obj.getNamedParams();
    }

    @SuppressWarnings("unused")
    public Map<String, Object> getParams() {
        return params;
    }

    @SuppressWarnings("unused")
    public Object getParam(String key){
        return params.get(key);
    }

    @SuppressWarnings("unused")
    public String getMethod() {
        return method;
    }

    public String toString(){
        return "RoomNotification: "+method+" - "+paramsToString();
    }

    private String paramsToString(){
        StringBuilder sb = new StringBuilder();
        if(this.params!=null){
            for (Map.Entry<String,Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key).append("=").append(value.toString()).append(", ");
            }
            return sb.toString();
        } else return null;
    }
}