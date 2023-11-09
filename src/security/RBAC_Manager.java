package src.security;

import src.EOperation;
import src.PrintServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

public class RBAC_Manager extends Manager{
    private Path rolesOperationsFile;
    private Path usersRolesFile;
    private Map<String, List<EOperation>> roleOperations = new HashMap<>();
    private Map<String, String> userRoles = new HashMap<>();
    static Logger logger = Logger.getLogger(PrintServer.class.getName());

    public RBAC_Manager(String rolesOperationsFile, String usersRolesFile) {
        this.rolesOperationsFile = Path.of(rolesOperationsFile);
        this.usersRolesFile = Path.of(usersRolesFile);
        loadRolesOperationsFile();
        loadUsersRolesFile();
    }

    private void loadRolesOperationsFile() {
        try {
            List<String> lines = Files.readAllLines(rolesOperationsFile);
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    roleOperations.put(parts[0], parseOperations(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading roles_operations file: " + e);
        }
    }

    private void loadUsersRolesFile() {
        try {
            List<String> lines = Files.readAllLines(usersRolesFile);
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    userRoles.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users_roles file: " + e);
        }
    }

    private List<EOperation> parseOperations(String operations) {
        String[] parts = operations.split(",");
        List<EOperation> list = new ArrayList<>();
        Arrays.stream(parts).forEach(p -> list.add(EOperation.valueOf(p.strip())));
        return list;
    }

    @Override
    public boolean verifyPermission(String username, EOperation eOperation) {
        return roleOperations.get(userRoles.get(username)).contains(eOperation);
    }
}
