package org.acme.api;

import static io.restassured.RestAssured.given;

import org.acme.app.AppServer;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class GetUsersApiWithOneUsersIT implements WithAssertions {

    static AppServer SERVER;

    @Container
    static PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:17.0-bookworm")
                    .withInitScripts("db/create_table.sql", "db/insert_1_user.sql");

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
    void shouldReturnOneUser() {
        var jsonPath =
                given().when()
                        .port(SERVER.getPort())
                        .get("/users")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .and()
                        .extract()
                        .body()
                        .jsonPath();
        assertThat(jsonPath.getList("", UserDto.class))
                .containsExactly(
                        new UserDto("1d0bbcb2-9ef6-11ef-ba7c-6f55e9414db3", "John", "Smith"));
    }
}
