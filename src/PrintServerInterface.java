package src;

import src.security.exception.AccessException;
import src.security.exception.HashingException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintServerInterface extends Remote {
    void print(String filename, String printer, String username, String password) throws RemoteException, HashingException, AccessException;
    String queue(String printer, String username, String password) throws RemoteException, HashingException, AccessException;
    void topQueue(String printer, int job, String username, String password) throws RemoteException, HashingException, AccessException;
    void start(String username, String password) throws RemoteException, HashingException, AccessException;
    void stop(String username, String password) throws RemoteException, HashingException, AccessException;
    void restart(String username, String password) throws RemoteException, HashingException, AccessException;
    String status(String printer, String username, String password) throws RemoteException, HashingException, AccessException;
    String readConfig(String parameter, String username, String password) throws RemoteException, HashingException, AccessException;
    void setConfig(String parameter, String value, String username, String password) throws RemoteException, HashingException, AccessException;
    boolean verifyPassword(String username, String password) throws RemoteException, HashingException;
}
