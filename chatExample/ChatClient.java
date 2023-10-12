package chatExample;

import java.rmi.*;

public interface ChatClient extends Remote {
    public void printMsg(Message msg) throws RemoteException;
}
