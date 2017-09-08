package me.apptize.bollyfilmes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pasch on 2017-09-03.
 */

public class JsonUtil {

    public static List<ItemFilme> fromJsonToList(String json){
        List<ItemFilme> lista = new ArrayList<>();
        try {
            JSONObject jsonBase = new JSONObject(json);
            JSONArray results = jsonBase.getJSONArray("results");
            for(int i=0; i<results.length(); i++) {
                JSONObject filmeObject = results.getJSONObject(i);
                ItemFilme itemFilme = new ItemFilme(filmeObject);
                lista.add(itemFilme);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
