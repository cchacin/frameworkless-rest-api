package org.acme.core;

import java.util.List;
import java.util.function.Supplier;

public class UsersService {

    private final Supplier<List<User>> getUsersFromDb;

    public UsersService(
            Supplier<List<User>> getUsersFromDb) {
        this.getUsersFromDb = getUsersFromDb;
    }

    public List<User> users() {
        return getUsersFromDb.get();
    }
}
