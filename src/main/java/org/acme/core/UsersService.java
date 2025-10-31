package org.acme.core;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record UsersService(
        Supplier<List<User>> getUsersFromDb, Function<User.UserId, User> getUserFromDb) {

    public List<User> users() {
        return getUsersFromDb.get();
    }

    public Optional<User> user(User.UserId userId) {
        return Optional.ofNullable(getUserFromDb.apply(userId));
    }
}
