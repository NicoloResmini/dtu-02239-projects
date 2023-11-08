package src.security;

import src.EOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AccessManager extends Manager{
    private Map<String, List<EOperation>> userAccessRights = new HashMap<>();
    private Path accessListFile;

    public AccessManager(String accessListFile) {
        this.accessListFile = Paths.get(accessListFile);
        loadAccessRightsFile();
    }

    private void loadAccessRightsFile() {
        try {
            List<String> lines = Files.readAllLines(accessListFile);
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    userAccessRights.put(parts[0], parseOperations(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading access rights: " + e);
        }
    }

    private List<EOperation> parseOperations(String operations) {
        String[] parts = operations.split(",");
        List<EOperation> list = new ArrayList<>();
        Arrays.stream(parts).forEach(p -> list.add(EOperation.valueOf(p.strip())));
        return list;
    }

    @Override
    public boolean verifyPermission(String username, EOperation operation) {
        return userAccessRights.get(username).contains(operation);
    }
}
