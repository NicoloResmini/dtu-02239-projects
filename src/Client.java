package src;

import src.security.exception.AccessException;
import src.security.ssl.RMISSLClientSocketFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;

public class Client {
    private Client() {}
    public static void main(String[] args) {
        try {
            RMISSLClientSocketFactory sslSocketfactory = (RMISSLClientSocketFactory) new RMISSLClientSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sslSocketfactory.createSocket("localhost", 12345);

            Registry registry = LocateRegistry.getRegistry(1099);
            PrintServerInterface server = (PrintServerInterface) registry.lookup("PrintServer");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the PrintServer client!");
            while (true) {

                System.out.println("Enter username:");
                String username = scanner.nextLine();

                if (username.isEmpty()) {
                    System.out.print("Username cannot be empty!\n");
                    continue;
                }

                System.out.println("Enter password:");
                String password = scanner.nextLine();

                if (!server.verifyPassword(username, password)) {
                    System.out.println("Invalid Credentials, try again!");
                    continue;
                }

                System.out.println("Successful Login!");

                System.out.println("Select an operation:");
                System.out.println("1. Print");
                System.out.println("2. Queue");
                System.out.println("3. TopQueue");
                System.out.println("4. Start");
                System.out.println("5. Stop");
                System.out.println("6. Restart");
                System.out.println("7. Status");
                System.out.println("8. ReadConfig");
                System.out.println("9. SetConfig");
                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine();  // consume newline
                String printer;
                String parameter;

                switch (choice) {
                    case 1:
                        System.out.println("Enter filename:");
                        String filename = scanner.nextLine();
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        try {
                            server.print(filename, printer, username, password);
                            System.out.println("Done!");
                        } catch (AccessException e){
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        try {
                            server.queue(printer, username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 3:
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        System.out.println("Enter job:");
                        int job = scanner.nextInt();
                        scanner.nextLine();  // consume newline
                        try {
                            server.topQueue(printer, job, username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 4:
                        try {
                            server.start(username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 5:
                        try {
                            server.stop(username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 6:
                        try {
                            server.restart(username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 7:
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        try {
                            server.status(printer, username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 8:
                        System.out.println("Enter parameter:");
                        parameter = scanner.nextLine();
                        try {
                            server.readConfig(parameter, username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    case 9:
                        System.out.println("Enter parameter:");
                        parameter = scanner.nextLine();
                        System.out.println("Enter value:");
                        String value = scanner.nextLine();
                        try {
                            server.setConfig(parameter, value, username, password);
                            System.out.println("Done!");
                        } catch (AccessException e) {
                            System.out.println("You do not have rights to execute this operation, please use different credentials.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice: input must be an integer between 1 and 9 inclusive.");
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}



