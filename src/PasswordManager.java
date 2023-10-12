package src;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class PasswordManager {
    private Map<String, String> userPasswords = new HashMap<>();
    private Path passwordFilePath;

    public PasswordManager(String passwordFilePath) {
        this.passwordFilePath = Paths.get(passwordFilePath);
        loadPasswords();
    }

    private void loadPasswords() {
        try {
            List<String> lines = Files.readAllLines(passwordFilePath);
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    userPasswords.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading passwords: " + e);
        }
    }

    public boolean verifyPassword(String username, String password) {
        String storedPassword = userPasswords.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}


