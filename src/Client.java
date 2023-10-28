package src;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class Client {
    private Client() {}

    public static void main(String[] args) {
        try {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket("localhost", 12345); // Server address and port

            Registry registry = LocateRegistry.getRegistry(1099);
            PrintServerInterface server = (PrintServerInterface) registry.lookup("PrintServer");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Welcome to the PrintServer client!");

                System.out.println("Enter username:");
                String username = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();

                Boolean loginAuth = true;
                
                loginAuth = server.verifyPassword(username, password);
                
                if (loginAuth.equals(false)) {
                    System.out.println("Invalid Credentials");
                    continue;
                }
                
                System.out.println("Succesful Login!");

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

                switch (choice) {
                    case 1:
                        System.out.println("Enter filename:");
                        String filename = scanner.nextLine();
                        System.out.println("Enter printer:");
                        String printer = scanner.nextLine();
                        server.print(filename, printer, username, password);
                        break;
                    case 2:
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        server.queue(printer, username, password);
                        break;
                    case 3:
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        System.out.println("Enter job:");
                        int job = scanner.nextInt();
                        scanner.nextLine();  // consume newline
                        server.topQueue(printer, job, username, password);
                        break;
                    case 4:
                        server.start(username, password);
                        break;
                    case 5:
                        server.stop(username, password);
                        break;
                    case 6:
                        server.restart(username, password);
                        break;
                    case 7:
                        System.out.println("Enter printer:");
                        printer = scanner.nextLine();
                        server.status(printer, username, password);
                        break;
                    case 8:
                        System.out.println("Enter parameter:");
                        String parameter = scanner.nextLine();
                        server.readConfig(parameter, username, password);
                        break;
                    case 9:
                        System.out.println("Enter parameter:");
                        parameter = scanner.nextLine();
                        System.out.println("Enter value:");
                        String value = scanner.nextLine();
                        server.setConfig(parameter, value, username, password);
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



