/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientewebsockete;

import entities.Message;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class ClienteWebSockete {

    private static Object waitLock = new Object();

    public static void main(String[] args) {
        WebSocketContainer container = null;//
        Session session = null;
        try {
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();
            //WS1 is the context-root of my web.app
            //ratesrv is the  path given in the ServerEndPoint annotation on server implementation
            Scanner sc = new Scanner(System.in);
            System.out.print("Ingrese su nombre de usuario: ");
            String name = sc.nextLine();
            session = container.connectToServer(MiEndpoint.class,
                    URI.create("ws://192.168.1.65:8080/webSocketEjemplo/endpoint/" + name));
            RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
            System.out.println("Se ha unido al chat.");
            String msj = null;
            do {
                msj = sc.nextLine();
                Message msgListo = new Message();

                if (msj.startsWith("onlyto:")) {
                    String destination = msj.split(" ")[0].split(":")[1];
                    msgListo.setOnlyto(true);
                    msgListo.setBody(msj.split("onlyto:" + destination + " ")[1]);
                    msgListo.setDestination(destination);
                } else {
                    msgListo.setBody(msj);
                }

                basicRemote.sendText(Message.toJson(msgListo));
            } while (!msj.equals("!exit!"));

        } catch (DeploymentException | IOException ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
