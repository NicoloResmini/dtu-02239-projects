package src.security;

import src.EOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class Manager {
    public abstract boolean verifyPermission(String username, EOperation eOperation);

    protected List<EOperation> parseOperations(String operations) {
        String[] parts = operations.split(",");
        List<EOperation> list = new ArrayList<>();
        Arrays.stream(parts).forEach(p -> list.add(EOperation.valueOf(p.strip())));
        return list;
    }

}
