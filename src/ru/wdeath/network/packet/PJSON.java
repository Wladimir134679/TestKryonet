package ru.wdeath.network.packet;

import org.json.JSONObject;

/**
 * @author WDeath
 */
public class PJSON {

    public String json;
    
    public JSONObject getJson(){
       return new JSONObject(json);
    }
    
    public void setJson(JSONObject obj){
        this.json = obj.toString();
    }
}
