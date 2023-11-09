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
        this.userAccessRights = load_string_list_File(this.accessListFile);
    }

    @Override
    public boolean verifyPermission(String username, EOperation operation) {
        return userAccessRights.get(username).contains(operation);
    }
}
