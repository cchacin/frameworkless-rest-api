package org.acme.core;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class UsersService {

    private final Supplier<List<User>> getUsersFromDb;
    private final Function<User.UserId, User> getUserFromDb;

    public UsersService(
            Supplier<List<User>> getUsersFromDb, Function<User.UserId, User> getUserFromDb) {
        this.getUsersFromDb = getUsersFromDb;
        this.getUserFromDb = getUserFromDb;
    }

    public List<User> users() {
        return getUsersFromDb.get();
    }

    public Optional<User> user(User.UserId userId) {
        return Optional.ofNullable(getUserFromDb.apply(userId));
    }
}
