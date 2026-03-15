package com.tooldynoapps.calcvertex.models;

public class HistoryModel {
    private String id;
    private String toolName;
    private String category;
    private String input;
    private String result;
    private String date;
    private long timestamp;
    private int color;

    public HistoryModel() {}

    public HistoryModel(String id, String toolName, String category,
                        String input, String result, String date,
                        long timestamp, int color) {
        this.id        = id;
        this.toolName  = toolName;
        this.category  = category;
        this.input     = input;
        this.result    = result;
        this.date      = date;
        this.timestamp = timestamp;
        this.color     = color;
    }

    public String getId()        { return id; }
    public String getToolName()  { return toolName; }
    public String getCategory()  { return category; }
    public String getInput()     { return input; }
    public String getResult()    { return result; }
    public String getDate()      { return date; }
    public long   getTimestamp() { return timestamp; }
    public int    getColor()     { return color; }
    public void   setId(String id)            { this.id = id; }
    public void   setToolName(String t)       { this.toolName = t; }
    public void   setCategory(String c)       { this.category = c; }
    public void   setInput(String i)          { this.input = i; }
    public void   setResult(String r)         { this.result = r; }
    public void   setDate(String d)           { this.date = d; }
    public void   setTimestamp(long ts)       { this.timestamp = ts; }
    public void   setColor(int c)             { this.color = c; }
}
