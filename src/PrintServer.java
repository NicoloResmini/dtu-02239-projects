package src;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class PrintServer extends UnicastRemoteObject implements PrintServerInterface {
    private Map<String, String> configParams = new HashMap<>();
    private PasswordManager passwordManager;

    protected PrintServer(String passwordFilePath) throws RemoteException {
        super();
        passwordManager = new PasswordManager(passwordFilePath);
    }

    @Override
    public void print(String filename, String printer, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("Print operation invoked with filename: " + filename + " and printer: " + printer);
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
    }

    @Override
    public String queue(String printer, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("Queue operation invoked with printer: " + printer);
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public void topQueue(String printer, int job, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("TopQueue operation invoked with printer: " + printer + " and job: " + job);
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
    }

    @Override
    public void start(String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("Start operation invoked");
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
    }

    @Override
    public void stop(String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("Stop operation invoked");
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
    }

    @Override
    public void restart(String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("Restart operation invoked");
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
    }

    @Override
    public String status(String printer, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("Status operation invoked with printer: " + printer);
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public String readConfig(String parameter, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("ReadConfig operation invoked with parameter: " + parameter);
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public void setConfig(String parameter, String value, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            System.out.println("SetConfig operation invoked with parameter: " + parameter + " and value: " + value);
        } else {
            System.out.println("Invalid credentials for user: " + username);
        }
    }

    public static void main(String[] args) {
        try {
            // You can put the name of the file instead of the path only if the file is in the same directory as the src folder
            PrintServer server = new PrintServer("passwords.txt");

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("PrintServer", server);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


}

