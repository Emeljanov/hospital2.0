package by.emel.anton.restcontroller.v1.wiremock;

import by.emel.anton.entity.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class UsersRestControllerTest {

    private static final String REQUEST_BODY_PATIENT_PATH = "src/test/resources/request-body-create-user.json";


    WireMockServer wireMockServer = new WireMockServer();


    @BeforeEach
    void startServer() {
        wireMockServer.start();
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }


    @Test
    void findAll() throws IOException, JSONException {
        /*WireMock.get().willReturn().withBasicAuth...*/

        String jsonBody = getJsonStringFromFile(REQUEST_BODY_PATIENT_PATH);
        JSONObject jsonObject = new JSONObject(jsonBody);

        String s = "sss";


        stubFor(get("/api/v1/users").willReturn(aResponse().withStatus(200).withBody(s)));

        given().when().get("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body( equalTo("sss"));


    }

    @Test
    void findUserById() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void changeUserStatus() {
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}