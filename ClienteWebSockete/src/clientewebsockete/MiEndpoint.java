package clientewebsockete;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class MiEndpoint {
    
    @OnOpen
    public void open(Session s){
        
    }

    @OnMessage
    public void onMessage(String message) {
       System.out.println(message);        
    }
}
