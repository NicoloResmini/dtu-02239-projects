package src.security;

import src.EOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class Manager {
    public abstract boolean verifyPermission(String username, EOperation eOperation);

    private List<EOperation> parseOperations(String operations) {
        String[] parts = operations.split(",");
        List<EOperation> list = new ArrayList<>();
        Arrays.stream(parts).forEach(p -> list.add(EOperation.valueOf(p.strip())));
        return list;
    }

    protected Map<String, List<EOperation>> loadPermissionFile(String filePath) {
        Map<String, List<EOperation>> map = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    map.put(parts[0], parseOperations(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading file at " + filePath + ": " + e);
        }
        return map;
    }
}
