package org.acme.app;

import org.acme.api.UserDto;
import org.acme.core.User;
import org.acme.dao.UserEntity;

import java.util.List;

public interface Mapper {

    static List<User> entityToCores(List<UserEntity> users) {
        return users.stream()
                .map(Mapper::entityToCore)
                .toList();
    }

    static User entityToCore(UserEntity entity) {
        return new User(
                User.UserId.of(entity.userId()),
                User.FirstName.of(entity.firstName()),
                User.LastName.of(entity.lastName())
        );
    }

    static List<UserDto> coreToDtos(List<User> users) {
        return users.stream()
                .map(Mapper::coreToDto)
                .toList();
    }

    static UserDto coreToDto(User user) {
        return new UserDto(
                user.userID().value().toString(),
                user.firstName().value(),
                user.lastName().value()
        );
    }

}
