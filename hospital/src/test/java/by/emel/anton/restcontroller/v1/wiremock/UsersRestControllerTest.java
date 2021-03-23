package by.emel.anton.restcontroller.v1.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class UsersRestControllerTest {

    private static final String RES_FIND_ALL = "src/test/resources/response-body-find-all-users.json";
    private static final String RES_FIN_BY_ID = "src/test/resources/response-body-user-id-2.json";
    private static final String REQ_CREATE_USER = "src/test/resources/request-body-create-user.json";
    private static final String RESPONSE_CREATE_USER = "src/test/resources/request-body-create-user-answer.json";

    private final int PORT = 8070;

    WireMockServer wireMockServer = new WireMockServer(PORT);

    @BeforeEach
    void startServer() {
        wireMockServer.start();
        RestAssured.port = PORT;
        WireMock.configureFor("localhost", PORT);

    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void shouldFindAll() throws IOException {

        String resBody = getJsonStringFromFile(RES_FIND_ALL);

        stubFor(get("/api/v1/users").willReturn(okJson(resBody).withStatus(200)));

        given().when().get("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(resBody));

    }

    @Test
    void shouldFindUserById() throws IOException {
        String respBody = getJsonStringFromFile(RES_FIN_BY_ID);

        stubFor(get("/api/v1/users/2")
                .willReturn(okJson(respBody)
                        .withStatus(200)));

        given().when().get("/api/v1/users/{id}", 2)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(respBody));
    }

    @Test
    void shouldSaveUser() throws IOException {
        String reqBody = getJsonStringFromFile(REQ_CREATE_USER);
        String respBody = getJsonStringFromFile(RESPONSE_CREATE_USER);

        stubFor(post("/api/v1/users")
                .withRequestBody(equalToJson(reqBody))
                .willReturn(okJson(respBody)
                        .withStatus(200)));

        given().contentType(ContentType.JSON).body(reqBody)
                .when().post("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(respBody));

    }

    @Test
    void changeUserStatus() throws IOException {

        String respBody = getJsonStringFromFile(RESPONSE_CREATE_USER);

        stubFor(put("/api/v1/users/5/status/false")
                .willReturn(okJson(respBody)
                        .withStatus(200)));

        given().when().put("/api/v1/users/{userId}/status/{isActive}", 5, false)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(respBody));
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}