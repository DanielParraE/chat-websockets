/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import entities.Message;
import java.io.IOException;
import java.util.HashMap;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author lv1822
 */
@ServerEndpoint("/endpoint/{adaptorname}")
public class MiSocket {
    
    static HashMap<String, Session> hm = new HashMap<>();
    
    @OnOpen
    public synchronized void open(Session s, @PathParam("adaptorname") String adaptorname) throws IOException, EncodeException{
        for (Session value : hm.values()) {
            value.getBasicRemote().sendText(String.format("El usuario %s ha entrado al chat.", adaptorname));
        }
        hm.put(adaptorname, s);
    }

    @OnMessage
    public void onMessage(String message, Session client, @PathParam("adaptorname") String adaptorname) throws IOException, EncodeException{
        
        System.out.println(message);
        
        Message m = Message.toMessage(message);
        
        if (m.isOnlyto()) {
            hm.get(m.getDestination()).getBasicRemote().sendText(adaptorname + " (privado): " + m.getBody());
            return;
        }
        
        for (Session openSession : client.getOpenSessions()) {
            if (openSession != client) {
                openSession.getBasicRemote().sendText(adaptorname + ": " + m.getBody());
            }
        }
    }
    
    @OnClose
    public synchronized void close(@PathParam("adaptorname") String adaptorname) throws IOException, EncodeException{
        hm.remove(adaptorname);
        for (Session value : hm.values()) {
            value.getBasicRemote().sendText(String.format("El usuario %s ha salido del chat.", adaptorname));
        }
    }
    
}
