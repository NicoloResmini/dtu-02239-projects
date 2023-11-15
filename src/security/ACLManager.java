package src.security;

import src.EOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ACLManager extends Manager {
    private String accessListFile;
    private Map<String, List<EOperation>> userAccessRights = new HashMap<>();

    public ACLManager(String accessListFile) {
        this.accessListFile = accessListFile;
        loadPermissionFile();
    }

    private void loadPermissionFile() {
        try {
            List<String> lines = Files.readAllLines(Path.of(accessListFile));
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    userAccessRights.put(parts[0], parseOperations(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading file at " + accessListFile + ": " + e);
        }
    }
    @Override
    public boolean verifyPermission(String username, EOperation operation) {
        return userAccessRights.get(username).contains(operation);
    }
}
