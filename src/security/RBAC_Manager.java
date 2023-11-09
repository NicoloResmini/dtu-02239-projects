package src.security;

import src.EOperation;
import src.PrintServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RBAC_Manager extends Manager{
    private Path rolesOperationsFile;
    private Path usersRolesFile;
    private Map<String, List<EOperation>> roleOperations;
    private Map<String, String> userRoles = new HashMap<>();

    public RBAC_Manager(String rolesOperationsFile, String usersRolesFile) {
        this.rolesOperationsFile = Path.of(rolesOperationsFile);
        this.usersRolesFile = Path.of(usersRolesFile);
        this.roleOperations = load_string_list_File(this.rolesOperationsFile);
        loadUsersRolesFile();
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

    @Override
    public boolean verifyPermission(String username, EOperation eOperation) {
        return roleOperations.get(userRoles.get(username)).contains(eOperation);
    }
}
