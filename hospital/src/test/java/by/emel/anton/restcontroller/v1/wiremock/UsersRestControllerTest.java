package by.emel.anton.restcontroller.v1.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@SpringBootTest
class UsersRestControllerTest {

    private static final String RESPONSE_BODY_FIND_ALL = "src/test/resources/response-body-find-all-users.json";
    private static final String RESPONSE_BODY_FIND_BY_ID = "src/test/resources/response-body-user-id-2.json";
    private static final String REQUEST_CREATE_USER = "src/test/resources/request-body-create-user.json";
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
    void shouldFindAll() throws IOException, JSONException {

        String expectedBody = getJsonStringFromFile(RESPONSE_BODY_FIND_ALL);

        stubFor(get("/api/v1/users").willReturn(okJson(expectedBody).withStatus(HttpStatus.OK.value())));

        String actualBody = given().when().get("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        JSONAssert.assertEquals(expectedBody, actualBody, true);
    }

    @Test
    void shouldFindUserById() throws IOException, JSONException {
        String expectedBody = getJsonStringFromFile(RESPONSE_BODY_FIND_BY_ID);

        stubFor(get("/api/v1/users/2").willReturn(okJson(expectedBody).withStatus(HttpStatus.OK.value())));

        String actualBody = given().when().get("/api/v1/users/{id}", 2)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        JSONAssert.assertEquals(expectedBody, actualBody, true);
    }

    @Test
    void shouldSaveUser() throws IOException, JSONException {
        String requestBody = getJsonStringFromFile(REQUEST_CREATE_USER);
        String expectedBody = getJsonStringFromFile(RESPONSE_CREATE_USER);

        stubFor(post("/api/v1/users").withRequestBody(equalToJson(requestBody))
                .willReturn(okJson(expectedBody).withStatus(HttpStatus.OK.value())));

        String actualBody = given().contentType(ContentType.JSON).body(requestBody)
                .when().post("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        JSONAssert.assertEquals(expectedBody, actualBody, true);
    }

    @Test
    void changeUserStatus() throws IOException, JSONException {

        String expectedBody = getJsonStringFromFile(RESPONSE_CREATE_USER);

        stubFor(put("/api/v1/users/5/status/false")
                .willReturn(okJson(expectedBody).withStatus(HttpStatus.OK.value())));

        String actualBody = given().when().put("/api/v1/users/{userId}/status/{isActive}", 5, false)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        JSONAssert.assertEquals(expectedBody, actualBody, true);
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}