package chatExample;

import chatExample.ChatClient;

import java.rmi.*;

public interface ChatServer extends Remote {
    public void join(ChatClient client) throws RemoteException;
    public void leave(ChatClient client) throws RemoteException;
    public void sendMsg(Message msg) throws RemoteException;
}


