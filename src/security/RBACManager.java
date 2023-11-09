package src.security;

import src.EOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RBACManager extends Manager {
    private String usersRolesFile;
    private Map<String, List<EOperation>> roleOperations;
    private Map<String, String> userRoles = new HashMap<>();

    public RBACManager(String rolesOperationsFile, String usersRolesFile) {
        this.usersRolesFile = usersRolesFile;
        this.roleOperations = loadPermissionFile(rolesOperationsFile);
        loadUsersRolesFile();
    }

    private void loadUsersRolesFile() {
        try {
            List<String> lines = Files.readAllLines(Path.of(usersRolesFile));
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    userRoles.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users_roles.txt file: " + e);
        }
    }

    @Override
    public boolean verifyPermission(String username, EOperation eOperation) {
        return roleOperations.get(userRoles.get(username)).contains(eOperation);
    }
}
