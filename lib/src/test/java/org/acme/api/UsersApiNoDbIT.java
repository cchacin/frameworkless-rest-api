package org.acme.api;

import org.acme.app.AppServer;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UsersApiNoDbIT implements WithAssertions {

    static AppServer SERVER;

    @BeforeAll
    static void beforeAll() throws Exception {
        SERVER = new AppServer(0);
        SERVER.start();
    }

    @AfterAll
    static void afterAll() throws Exception {
        SERVER.stop();
    }

    @Test
    void shouldReturn500ErrorWhenNoDatabase() {
        given().when()
                .port(SERVER.getPort())
                .get("/users")
                .then()
                .assertThat()
                .statusCode(404);
    }
}