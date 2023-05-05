package com.example.bazaarbargains;

public class Message {

    public String card_number;
    public String expire;
    public String cvv;
    public String username;
    public String password;

    public Message(String card_number, String expire, String cvv){
        this.card_number = card_number;
        this.expire = expire;
        this.cvv = cvv;
    }
    public Message(String username, String password){
        this.username = username;
        this.password = password;
    }
}
