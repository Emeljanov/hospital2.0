package by.emel.anton.restcontroller.v1.assure;

import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import by.emel.anton.repository.jpa.UserJpaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"security.enable=true"})
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRestControllerTest {

    private final String REQUEST_BODY_USER_PATH = "src/test/resources/request-body-login-user1.json";
    private final String AUTH = "Authorization";
    private final String KEY_LOGIN = "login";
    private final String KEY_ROLE = "role";
    private final String KEY_FIRST_NAME = "firstName";
    private final String KEY_LAST_NAME = "lastName";
    private final String KEY_BIRTHDAY = "birthday";
    private final String KEY_ACTIVE = "active";
    private final String REQUEST_BODY_PATIENT_PATH = "src/test/resources/request-body-create-user.json";
    private final String RESPONSE_USER_PETROV = "src/test/resources/response-body-user-petrov.json";
    private final int NOT_EXIST_ID = 100;
    private final String RESPONSE_BODY_USERS = "src/test/resources/expected-response-bode-users.json";
    private final String RESPONSE_SAVE_USER = "src/test/resources/response-body-save-user.json";

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeAll() {
        RestAssured.port = port;
    }

    @Test
    void shouldFindAll() throws IOException, JSONException {

        String expectedBody = getJsonStringFromFile(RESPONSE_BODY_USERS);

        String actualBody = given().header(getAdminHeader()).when().get("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        JSONAssert.assertEquals(expectedBody, actualBody, true);

    }

    @Test
    void shouldFindUserById() throws IOException {

        String expectedBody = getJsonStringFromFile(RESPONSE_USER_PETROV);

        String actualBody = given().header(getAdminHeader()).when().get("/api/v1/users/{id}", 2)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        assertThat(actualBody, sameJSONAs(expectedBody).allowingAnyArrayOrdering());

    }


    @Test
    void shouldGetBadRequestStatusWhenUserNotExist() throws IOException {

        given().header(getAdminHeader()).when().get("/api/v1/users/{id}", NOT_EXIST_ID)
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void shouldSaveUser() throws IOException, JSONException {

        String requestBody = getJsonStringFromFile(REQUEST_BODY_PATIENT_PATH);
        String expectedBody = getJsonStringFromFile(RESPONSE_SAVE_USER);

        String actualBody = given().header(getAdminHeader()).contentType(ContentType.JSON).body(requestBody)
                .when().post("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        assertThat(actualBody, sameJSONAs(expectedBody).allowingAnyArrayOrdering());

        User userDB = userJpaRepository.findByLogin("create").get();
        JSONObject userJson = new JSONObject(requestBody);

        assertEquals(userDB.getLogin(), userJson.getString(KEY_LOGIN));
        assertEquals(userDB.getLastName(), userJson.getString(KEY_LAST_NAME));
        assertEquals(userDB.getFirstName(), userJson.getString(KEY_FIRST_NAME));
        assertEquals(userDB.getBirthday(), LocalDate.parse(userJson.getString(KEY_BIRTHDAY)));
        assertEquals(userDB.getRole(), Role.valueOf(userJson.getString(KEY_ROLE)));
        assertTrue(passwordEncoder.matches("created_password", userDB.getPass()));

    }

    @Test
    void shouldGetBadRequestStatusWhenRequestBodyIsEmpty() throws IOException {
        given().header(getAdminHeader())
                .when().post("/api/v1/users")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldChangeUserStatus() throws IOException, JSONException {
        final String RESPONSE = "src/test/resources/response-body-change-user-status-ivanov.json";


        given().header(getAdminHeader()).when().get("/api/v1/users/{id}", 1)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(KEY_ACTIVE, equalTo(true));

        String actualBody = given().header(getAdminHeader()).when().put("/api/v1/users/change/status/{userId}/{isActive}", 1, false)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        String expectedBody = getJsonStringFromFile(RESPONSE);

        JSONAssert.assertEquals(expectedBody, actualBody, true);

    }

    @Test
    void shouldGetBadRequestStatusWhenTryChangeStatusAndUserNotExist() throws IOException {
        given().header(getAdminHeader()).when().put("/api/v1/users/change/status/{userId}/{isActive}", NOT_EXIST_ID, false)
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
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
