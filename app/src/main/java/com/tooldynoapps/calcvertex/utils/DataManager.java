package com.tooldynoapps.calcvertex.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tooldynoapps.calcvertex.models.HistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DataManager {

    private static final String PREFS_NAME   = "CalcVertexPrefs";
    private static final String KEY_HISTORY  = "calc_history";
    private static final int    MAX_HISTORY  = 20;

    private final SharedPreferences prefs;

    public DataManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveHistory(String toolName, String category, String input, String result, int color) {
        List<HistoryModel> list = getHistory();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());
        HistoryModel h = new HistoryModel(
                UUID.randomUUID().toString(),
                toolName, category, input, result, date,
                System.currentTimeMillis(), color);
        list.add(0, h);
        if (list.size() > MAX_HISTORY) list = list.subList(0, MAX_HISTORY);
        persistHistory(list);
    }

    public List<HistoryModel> getHistory() {
        List<HistoryModel> list = new ArrayList<>();
        String json = prefs.getString(KEY_HISTORY, "[]");
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                HistoryModel h = new HistoryModel();
                h.setId(o.optString("id"));
                h.setToolName(o.optString("toolName"));
                h.setCategory(o.optString("category"));
                h.setInput(o.optString("input"));
                h.setResult(o.optString("result"));
                h.setDate(o.optString("date"));
                h.setTimestamp(o.optLong("timestamp", 0));
                h.setColor(o.optInt("color", 0xFF00D4AA));
                list.add(h);
            }
        } catch (JSONException e) { e.printStackTrace(); }
        return list;
    }

    public void clearHistory() {
        prefs.edit().remove(KEY_HISTORY).apply();
    }

    private void persistHistory(List<HistoryModel> list) {
        JSONArray arr = new JSONArray();
        for (HistoryModel h : list) {
            try {
                JSONObject o = new JSONObject();
                o.put("id",        h.getId());
                o.put("toolName",  h.getToolName());
                o.put("category",  h.getCategory());
                o.put("input",     h.getInput());
                o.put("result",    h.getResult());
                o.put("date",      h.getDate());
                o.put("timestamp", h.getTimestamp());
                o.put("color",     h.getColor());
                arr.put(o);
            } catch (JSONException e) { e.printStackTrace(); }
        }
        prefs.edit().putString(KEY_HISTORY, arr.toString()).apply();
    }
}
