import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ChatServerImpl extends UnicastRemoteObject
        implements ChatServer {

    private List<ChatClient> clients;

    public ChatServerImpl() throws RemoteException {
        clients = new ArrayList<ChatClient>();
    }

    public synchronized void join(ChatClient client) throws RemoteException {
        clients.add(client);
    }

    public synchronized void leave(ChatClient client) throws RemoteException {
        clients.remove(client);
    }

    public void sendMsg(Message msg) throws RemoteException {
        List<ChatClient> clientsCopy = null;
        synchronized(this) {
            clientsCopy = new ArrayList<ChatClient>(clients);
        }
        for(ChatClient c: clientsCopy) {
            c.printMsg(msg);
        }
    }
    public static void main(String args[]) throws Exception {
        System.setSecurityManager(new SecurityManager());
        ChatServerImpl server = new ChatServerImpl();
        Registry registry = LocateRegistry.getRegistry("localhost");
        registry.bind("ChatServer", server);
        System.out.println("Server bound and ready");
    }
}


