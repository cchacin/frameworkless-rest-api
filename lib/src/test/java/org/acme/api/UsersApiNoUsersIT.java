package org.acme.api;

import org.acme.app.AppServer;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@Testcontainers
class UsersApiNoUsersIT implements WithAssertions {

    static AppServer SERVER;

    @Container
    static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:17.0-bookworm")
            .withInitScript("db/create_table.sql");

    @BeforeAll
    static void beforeAll() throws Exception {
        System.setProperty("DB_JDBC_URL", POSTGRES.getJdbcUrl());
        System.setProperty("DB_USERNAME", POSTGRES.getUsername());
        System.setProperty("DB_PASSWORD", POSTGRES.getPassword());
        SERVER = new AppServer(0);
        SERVER.start();
    }

    @AfterAll
    static void afterAll() throws Exception {
        SERVER.stop();
    }

    @Test
    void shouldReturnEmptyUsersList() {
        var jsonPath = given().when()
                .port(SERVER.getPort())
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .jsonPath();
        assertThat(jsonPath.getList("", UserDto.class)).isEmpty();
    }
}