package src;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintServerInterface extends Remote {
    void print(String filename, String printer, String username, String password) throws RemoteException, HashingException;
    String queue(String printer, String username, String password) throws RemoteException, HashingException;
    void topQueue(String printer, int job, String username, String password) throws RemoteException, HashingException;
    void start(String username, String password) throws RemoteException, HashingException;
    void stop(String username, String password) throws RemoteException, HashingException;
    void restart(String username, String password) throws RemoteException, HashingException;
    String status(String printer, String username, String password) throws RemoteException, HashingException;
    String readConfig(String parameter, String username, String password) throws RemoteException, HashingException;
    void setConfig(String parameter, String value, String username, String password) throws RemoteException, HashingException;
}
