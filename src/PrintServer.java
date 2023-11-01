package src;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
public class PrintServer extends UnicastRemoteObject implements PrintServerInterface {
    private Map<String, String> configParams = new HashMap<>();
    private PasswordManager passwordManager;
    static Logger logger = Logger.getLogger(PrintServer.class.getName());

    protected PrintServer(String passwordFilePath) throws RemoteException {
        super();
        passwordManager = new PasswordManager(passwordFilePath);
    }

    @Override
    public Boolean verifyPassword(String username, String password) {
        try {
            boolean valid = passwordManager.verifyPassword(username, password);
            if (!valid) {
                logger.log(Level.WARNING, "Invalid credentials for user: " + username);
                return false;
            }
            return true;
        } 
        catch (HashingException ex) {
        }
        return false;
    }

    
    @Override
    public void print(String filename, String printer, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "Print operation invoked with filename: " + filename + " and printer: " + printer + " by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public String queue(String printer, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "Queue operation invoked with printer: " + printer + " by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public void topQueue(String printer, int job, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "TopQueue operation invoked with printer: " + printer + " and job: " + job +" by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public void start(String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "Start operation invoked by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public void stop(String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "Stop operation invoked by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public void restart(String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "Restart operation invoked by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public String status(String printer, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "Status operation invoked with printer: " + printer + " by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public String readConfig(String parameter, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "ReadConfig operation invoked with parameter: " + parameter + " by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public void setConfig(String parameter, String value, String username, String password) throws RemoteException, HashingException {
        if (passwordManager.verifyPassword(username, password)) {
            logger.log(Level.INFO, "SetConfig operation invoked with parameter: " + parameter + " and value: " + value +" by user: " + username);
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    public static void main(String[] args) {
        try {
            FileInputStream configFile = new FileInputStream("src/logging.properties");
            LogManager.getLogManager().readConfiguration(configFile);

            // You can put the name of the file instead of the path only if the file is in the same directory as the src folder
            PrintServer server = new PrintServer("passwords.txt");
            Registry registry = LocateRegistry.createRegistry(1099);

            // Create an SslRMIServerSocket for secure communication
            RMISSLServerSocketFactory rmiSslServerSocketFactory = new RMISSLServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) rmiSslServerSocketFactory.createServerSocket(12345); // Port number for the server

            logger.log(Level.INFO, "TLS PrintServer started. Waiting for a client to connect...");
            SSLSocket clientSocket = (SSLSocket) sslServerSocket.accept();
            logger.log(Level.INFO, "Client connected.");

            registry.bind("PrintServer", server);

            logger.log(Level.INFO, "Server ready");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


}

