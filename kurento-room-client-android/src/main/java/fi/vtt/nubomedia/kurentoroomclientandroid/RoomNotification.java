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

    public Map<String, Object> getParams() {
        return params;
    }

    public Object getParam(String key){
        return params.get(key);
    }

    public String getMethod() {
        return method;
    }

    public String toString(){
        return "RoomNotification: "+method+" - "+paramsToString();
    }

    private String paramsToString(){
        StringBuffer sb = new StringBuffer();
        if(this.params!=null){
            for (Map.Entry<String,Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key+"="+value.toString()+", ");
            }
            return sb.toString();
        } else return null;
    }
}