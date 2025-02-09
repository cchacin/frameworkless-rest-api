package org.acme.api;

import static io.restassured.RestAssured.given;

import org.acme.app.AppServer;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UsersApiNoDbIT implements WithAssertions {

    static AppServer SERVER;

    @BeforeAll
    static void beforeAll() throws Exception {
        System.setProperty("DB_JDBC_URL", "jdbc:postgresql://non-existent-host:5432/postgres");
        SERVER = new AppServer(0);
        SERVER.start();
    }

    @AfterAll
    static void afterAll() throws Exception {
        SERVER.stop();
    }

    @Test
    void shouldReturn500ErrorWhenNoDatabase() {
        given().when().port(SERVER.getPort()).get("/users").then().assertThat().statusCode(404);
    }
}
