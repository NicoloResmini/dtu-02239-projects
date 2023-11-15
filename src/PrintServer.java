package src;

import src.security.ACLManager;
import src.security.Manager;
import src.security.RBACManager;
import src.security.exception.AccessException;
import src.security.exception.HashingException;
import src.security.PasswordManager;
import src.security.ssl.RMISSLServerSocketFactory;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.*;
public class PrintServer extends UnicastRemoteObject implements PrintServerInterface {
    private Map<String, String> configParams = new HashMap<>();
    private PasswordManager passwordManager;
    private Manager accessManager;
    static Logger logger = Logger.getLogger(PrintServer.class.getName());

    // This constructor is used for the ACL server
    protected PrintServer(String passwordFilePath, String accessFile) throws RemoteException {
        super();
        passwordManager = new PasswordManager(passwordFilePath);
        accessManager = new ACLManager(accessFile);
    }

    // This constructor is used for the RBAC server
    protected PrintServer(String passwordFilePath, String rolesOperationsFile, String usersRolesFile) throws RemoteException {
        super();
        passwordManager = new PasswordManager(passwordFilePath);
        accessManager = new RBACManager(rolesOperationsFile, usersRolesFile);
    }

    @Override
    public boolean verifyPassword(String username, String password) throws HashingException {
        boolean valid = passwordManager.verifyPassword(username, password);
        if (!valid) {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
            return false;
        }
        return true;
    }

    @Override
    public void print(String filename, String printer, String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.PRINT)) {
                logger.log(Level.INFO, "Print operation invoked with filename: " + filename + " and printer: " + printer + " by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public String queue(String printer, String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.QUEUE)) {
                logger.log(Level.INFO, "Queue operation invoked with printer: " + printer + " by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public void topQueue(String printer, int job, String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.TOP_QUEUE)) {
                logger.log(Level.INFO, "TopQueue operation invoked with printer: " + printer + " and job: " + job +" by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public void start(String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.START)) {
                logger.log(Level.INFO, "Start operation invoked by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public void stop(String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.STOP)) {
                logger.log(Level.INFO, "Stop operation invoked by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public void restart(String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.RESTART)) {
                logger.log(Level.INFO, "Restart operation invoked by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    @Override
    public String status(String printer, String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.STATUS)) {
                logger.log(Level.INFO, "Status operation invoked with printer: " + printer + " by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public String readConfig(String parameter, String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.READ_CONFIG)) {
                logger.log(Level.INFO, "ReadConfig operation invoked with parameter: " + parameter + " by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
        return null;
    }

    @Override
    public void setConfig(String parameter, String value, String username, String password) throws RemoteException, HashingException, AccessException {
        if (passwordManager.verifyPassword(username, password)) {
            if (accessManager.verifyPermission(username, EOperation.SET_CONFIG)) {
                logger.log(Level.INFO, "SetConfig operation invoked with parameter: " + parameter + " and value: " + value + " by user: " + username);
            } else {
                logger.log(Level.WARNING, "User: " + username + " cannot execute this operation.");
                throw new AccessException("Not authorized!");
            }
        } else {
            logger.log(Level.WARNING, "Invalid credentials for user: " + username);
        }
    }

    public static void main(String[] args) {
        try {
            FileInputStream configFile = new FileInputStream("src/logging.properties");
            LogManager.getLogManager().readConfiguration(configFile);

            PrintServer server = null;

            // ask the user if he wants to use an ACL or RBAC server
            System.out.println("Hello! Do you want to use an ACL or RBAC server? (Digit 1 for ACL, 2 for RBAC)");
            System.out.println("Enter your choice:");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("You chose ACL.");
                // You can put the name of the file instead of the path only if the file is in the same directory as the src folder
                server = new PrintServer("passwords.txt", "access_list.txt");
            } else if (choice == 2) {
                System.out.println("You chose RBAC.");
                // You can put the name of the file instead of the path only if the file is in the same directory as the src folder
                server = new PrintServer("passwords.txt", "roles_operations.txt", "users_roles.txt");
            } else {
                System.out.println("Invalid choice. Exiting...");
                System.exit(1);
            }

            // Bind the remote object's stub in the registry
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
