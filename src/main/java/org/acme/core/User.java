package org.acme.core;

import com.fasterxml.uuid.Generators;
import java.util.UUID;

public record User(UserId userID, FirstName firstName, LastName lastName) {
    public record UserId(UUID value) {
        public static UserId of() {
            return new UserId(Generators.timeBasedEpochRandomGenerator().generate());
        }

        public static UserId of(UUID value) {
            return new UserId(value);
        }

        public static UserId of(String value) {
            return new UserId(UUID.fromString(value));
        }
    }

    public record LastName(String value) {
        public static LastName of(String value) {
            return new LastName(value);
        }
    }

    public record FirstName(String value) {
        public static FirstName of(String value) {
            return new FirstName(value);
        }
    }
}
