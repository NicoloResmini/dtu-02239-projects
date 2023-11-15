package src.security;

import src.EOperation;
import src.ERole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RBACManager extends Manager {
    private String usersRolesFile;
    private String roleOperationsFile;
    private Map<ERole, List<EOperation>> roleOperations = new HashMap<>();
    private Map<String, ERole> userRoles = new HashMap<>();

    public RBACManager(String rolesOperationsFile, String usersRolesFile) {
        this.usersRolesFile = usersRolesFile;
        this.roleOperationsFile = rolesOperationsFile;
        loadRoleOperationsFile();
        loadUsersRolesFile();
    }

    private void loadUsersRolesFile() {
        try {
            List<String> lines = Files.readAllLines(Path.of(usersRolesFile));
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    userRoles.put(parts[0], ERole.valueOf(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users_roles.txt file: " + e);
        }
    }

    private void loadRoleOperationsFile() {
        try {
            List<String> lines = Files.readAllLines(Path.of(roleOperationsFile));
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    roleOperations.put(ERole.valueOf(parts[0]), parseOperations(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading file at " + roleOperationsFile + ": " + e);
        }
    }

    @Override
    public boolean verifyPermission(String username, EOperation eOperation) {
        return roleOperations.get(userRoles.get(username)).contains(eOperation);
    }
}
