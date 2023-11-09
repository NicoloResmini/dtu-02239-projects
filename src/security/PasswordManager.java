package src.security;

import src.security.exception.HashingException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.file.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class PasswordManager {
    private Map<String, Password> userPasswords = new HashMap<>();
    private String passwordFilePath;
    private int NUM_OF_ITERATIONS = 10000;
    private int KEY_LENGTH = 256;

    public PasswordManager(String passwordFilePath) {
        this.passwordFilePath = passwordFilePath;
        loadPasswords();
    }

    private void loadPasswords() {
        try {
            List<String> lines = Files.readAllLines(Path.of(passwordFilePath));
            for (String line : lines) {
                String[] parts = line.split(":", 3);
                if (parts.length == 3) {
                    userPasswords.put(parts[0], new Password(parts[1], Base64.getDecoder().decode(parts[2])));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading passwords: " + e);
        }
    }

    /**
     * Method generates salt, only when the password is being stored (e.g. for user enrollment)
     *
     * @return salt
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Method hashes new password using PBKDF2WithHmacSHA256
     *
     * @param password   string value that user is trying to log in with
     * @param storedSalt out of file, stored with real password
     * @return hashed password
     * @throws HashingException
     */
    private String hashPassword(String password, byte[] storedSalt) throws HashingException {
        char[] passwordChars = password.toCharArray();
        KeySpec spec = new PBEKeySpec(passwordChars, storedSalt, NUM_OF_ITERATIONS, KEY_LENGTH);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashedPassword = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HashingException(e.getMessage());
        }
    }

    /**
     * Method checks whether stored password matches the one that user is logging in with
     *
     * @param username
     * @param password
     * @return true if passwords match, false otherwise
     * @throws HashingException
     */
    public boolean verifyPassword(String username, String password) throws HashingException {
        if (!userPasswords.containsKey(username)) {
            return false;
        }
        Password user = userPasswords.get(username);

        String checkPassword = hashPassword(password, user.getStoredSalt());

        return user.getHashedStoredPassword().equals(checkPassword);
    }

    /**
     * To be used for new user enrollment
     * @throws HashingException
     */
    private void createPassword() throws HashingException {
        byte [] salt = generateSalt();
        System.out.println(Base64.getEncoder().encodeToString(salt));
        System.out.println(hashPassword("password", salt));
    }
}
