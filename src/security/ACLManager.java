package src.security;

import src.EOperation;

import java.util.*;

public class ACLManager extends Manager {
    private Map<String, List<EOperation>> userAccessRights;

    public ACLManager(String accessListFile) {
        this.userAccessRights = loadPermissionFile(accessListFile);
    }

    @Override
    public boolean verifyPermission(String username, EOperation operation) {
        return userAccessRights.get(username).contains(operation);
    }
}
