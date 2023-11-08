package src.security;

import src.EOperation;

public abstract class Manager {
    public abstract boolean verifyPermission(String username, EOperation eOperation);

}
