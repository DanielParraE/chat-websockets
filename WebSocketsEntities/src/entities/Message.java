package entities;

import com.google.gson.Gson;

public class Message {
    
    private boolean onlyto;
    private String destination;
    private String body;

    public Message() {
    }

    public Message(String body) {
        this.body = body;
    }

    public Message(boolean onlyto, String destination, String body) {
        this.onlyto = onlyto;
        this.destination = destination;
        this.body = body;
    }

    public boolean isOnlyto() {
        return onlyto;
    }

    public void setOnlyto(boolean onlyto) {
        this.onlyto = onlyto;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public static Message toMessage(String s) {
        Gson gson = new Gson();
        return gson.fromJson(s, Message.class);
    }
    
    public static String toJson(Message m) {
        Gson gson = new Gson();
        return gson.toJson(m, Message.class);
    }

    @Override
    public String toString() {
        return "Message{" + "onlyto=" + onlyto + ", destination=" + destination + ", body=" + body + '}';
    }
    
}
