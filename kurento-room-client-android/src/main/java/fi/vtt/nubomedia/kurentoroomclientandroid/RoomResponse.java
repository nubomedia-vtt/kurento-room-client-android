package fi.vtt.nubomedia.kurentoroomclientandroid;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Room response class
 */
public class RoomResponse {
    String id = null;
    String sessionId = null;
    private List<HashMap<String, String>> values = null;

    public RoomResponse(String id, JSONObject obj){
        super();
        this.id = id;
        this.sessionId = this.getJSONObjectSessionId(obj);
        this.values = this.getJSONObjectValues(obj);
    }

    public List<HashMap<String, String>> getValues() {
        return values;
    }

    public List<String> getValue(String key){

        List<String> result = new Vector<>();

        for (HashMap<String, String> aMap : values) {
            result.add(aMap.get(key));
        }

        return result;
    }

    public void setValues(List<HashMap<String, String>> values) {
        this.values = values;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String toString(){
        return "RoomResponse: "+id+" - "+sessionId+" - "+valuesToString();
    }

    private String getJSONObjectSessionId(JSONObject obj){
        if(obj.containsKey("sessionId")) {
            return obj.get("sessionId").toString();
        } else {
            return null;
        }
    }

    private List<HashMap<String, String>> getJSONObjectValues(JSONObject obj){
        List<HashMap<String, String>> result = new Vector<>();

        if(obj.containsKey("value")) {
            JSONArray value = (JSONArray)obj.get("value");
            for(int i=0;i<value.size();i++) {
                HashMap<String, String> vArrayElement = new HashMap<String, String>();

                JSONObject jo = (JSONObject) value.get(i);
                Set<String> keys = jo.keySet();
                for(String key : keys){
                    vArrayElement.put(key, jo.get(key).toString());
                }
                result.add(vArrayElement);
            }
        }
        if (obj.containsKey("sdpAnswer")){
            String sd = (String)obj.get("sdpAnswer");
            HashMap<String, String> vArrayElement = new HashMap<String, String>();

            vArrayElement.put("sdpAnswer", sd);
            result.add(vArrayElement);
        }
        if (result.isEmpty()){
            result = null;
        }

        return result;
    }

    private String valuesToString(){
        StringBuffer sb = new StringBuffer();
        if(this.values!=null){
            for (HashMap<String, String> aValueMap : values ) {
                sb.append("{");
                for (Map.Entry<String, String> entry : aValueMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key + "=" + value + ", ");
                }
                sb.append("},");
            }
            return sb.toString();
        } else return null;
    }
}