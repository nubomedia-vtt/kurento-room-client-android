/*
 * (C) Copyright 2016 VTT (http://www.vtt.fi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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