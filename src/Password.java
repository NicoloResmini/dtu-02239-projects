package src;

public class Password {
    String hashedStoredPassword;
    byte[] storedSalt;

    public Password(String hashedStoredPassword, byte[] storedSalt) {
        this.hashedStoredPassword = hashedStoredPassword;
        this.storedSalt = storedSalt;
    }

    public String getHashedStoredPassword() {
        return hashedStoredPassword;
    }

    public byte[] getStoredSalt() {
        return storedSalt;
    }
}
