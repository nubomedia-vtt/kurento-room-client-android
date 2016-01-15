package fi.vtt.nubomedia.kurentoroomclientandroid;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TreeResponse {
    String id = null;


    String sessionId = null;
    private HashMap<String, String> values = null;

    public TreeResponse(String id, JSONObject obj){
        super();
        this.id = id;
        this.sessionId = this.getJSONObjectSessionId(obj);
        this.values = this.getJSONObjectValues(obj);
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public String getValue(String key){
        return values.get(key);
    }

    public void setValues(HashMap<String, String> values) {
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
        return "TreeResponse: "+id+" - "+sessionId+" - "+valuesToString();
    }

    private String getJSONObjectSessionId(JSONObject obj){
        if(obj.containsKey("sessionId")) {
            return obj.get("sessionId").toString();
        } else {
            return null;
        }
    }

    private HashMap<String, String> getJSONObjectValues(JSONObject obj){
        if(!obj.containsKey("value")) {
            return null;
        }
        HashMap<String, String> result = new HashMap<String, String>();
        JSONArray value = (JSONArray)obj.get("value");
        for(int i=0;i<value.size();i++) {
            JSONObject jo = (JSONObject) value.get(i);
            Set<String> keys = jo.keySet();
            for(String key : keys){
                result.put(key, jo.get(key).toString());
            }
        }
        return result;
    }

    private String valuesToString(){
        StringBuffer sb = new StringBuffer();
        if(this.values!=null){
            for (Map.Entry<String,String> entry : values.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key+"="+value+", ");
            }
            return sb.toString();
        } else return null;
    }



}