package chatExample;

import java.rmi.*;
import java.rmi.server.*;

public class ChatClientImpl extends UnicastRemoteObject
        implements ChatClient {

    public static void main(String args[]) throws Exception {
        System.setSecurityManager(new SecurityManager());
        ChatClientImpl client = new ChatClientImpl();
        client.doJob(args[0]);
    }

    public ChatClientImpl() throws RemoteException { }

    public void doJob(String serverHost) throws Exception {
        ChatServer server;
        Message msg;

        // take a reference of the server from the registry
        server = (ChatServer) Naming.lookup("rmi://"+serverHost+"/chatExample.ChatServer");

        // join
        server.join(this);

        // main loop
        while(true) {
            msg = new MyMessage(System.console().readLine());
            if(msg.getContent().equals("end")) break;
            server.sendMsg(msg);
        }
        server.leave(this);
    }

    public void printMsg(Message msg) throws RemoteException {
        System.out.println(msg.getContent());
    }
}

class MyMessage implements Message {
    private String content;
    public MyMessage(String s) {
        content = s;
    }
    public String getContent() {
        return content;
    }
}
