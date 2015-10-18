package cn.edu.bit.szw.bitunion.entities;

/**
 * Created by szw on 15-10-18.
 */
public class Quote {
    public String author;
    public String time;
    public String content;
    public Quote(String author,String time, String content){
        this.author = author;
        this.time = time;
        this.content = content;
    }
}