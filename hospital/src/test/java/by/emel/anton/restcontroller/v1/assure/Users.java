package by.emel.anton.restcontroller.v1.assure;

import by.emel.anton.entity.Role;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class Users {

    private final String REQUEST_BODY_USER_PATH = "src/test/resources/request-body-login-user1.json";
    private final String RE = "src/test/resources/response-body-user-id-2.json";
    private final String AUTH = "Authorization";

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeAll() {
        RestAssured.port = port;
    }


    @Test
    void shouldFindUserById() throws IOException {

        given().when().get("/api/v1/users/{id}", 2)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }
    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    private Header getHeader(String bodyPath) throws IOException {
        return new Header(AUTH, given().contentType(ContentType.JSON)
                .body(getJsonStringFromFile(bodyPath))
                .when()
                .post("/api/v1/auth/login").jsonPath().get("token"));
    }

    private Header getAdminHeader() throws IOException {
        return getHeader(REQUEST_BODY_USER_PATH);
    }
}
