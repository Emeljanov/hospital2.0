package by.emel.anton.restcontroller.v1.assure;

import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import by.emel.anton.repository.jpa.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRestControllerTest {

    private final String REQUEST_BODY_USER_PATH = "src/test/resources/request-body-login-user1.json";
    private final String RE = "src/test/resources/response-body-user-id-2.json";
    private final String AUTH = "Authorization";
    private final String KEY_ID = "id";
    private final String KEY_LOGIN = "login";
    private final String KEY_DOCTOR = "doctor";
    private final String KEY_PATIENT = "patient";
    private final String KEY_ADMIN = "admin";
    private final String KEY_ROLE_STRING = "roleString";
    private final String KEY_ROLE = "role";
    private final String KEY_FIRST_NAME = "firstName";
    private final String KEY_LAST_NAME = "lastName";
    private final String KEY_BIRTHDAY = "birthday";
    private final String KEY_ACTIVE = "active";
    private final String REQUEST_BODY_PATIENT_PATH = "src/test/resources/request-body-create-user.json";
    private final LocalDate doctorBirthday = LocalDate.of(1900, 1, 1);
    private final LocalDate patientBirthday = LocalDate.of(1998, 2, 3);
    private final LocalDate adminBirthday = LocalDate.of(1800, 1, 1);
    private final String RESPONSE_USER_PETROV = "src/test/resources/response-body-user-petrov.json";
    private final int NOT_EXIST_ID = 100;

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

        String responseStringBody = given().header(getAdminHeader()).when().get("/api/v1/users")
                .then()
                .statusCode(HttpStatus.OK.value()).extract().asString();

        JSONArray jsonArray = new JSONArray(responseStringBody);
        assertEquals(jsonArray.length(), 6);

        JSONObject doctorJson = jsonArray.getJSONObject(0);
        assertEquals(doctorJson.getInt(KEY_ID), 1);
        assertEquals(doctorJson.get(KEY_LOGIN), KEY_DOCTOR);
        assertEquals(doctorJson.get(KEY_FIRST_NAME), "Ivan");
        assertEquals(doctorJson.get(KEY_LAST_NAME), "Ivanov");
        assertEquals(doctorJson.get(KEY_BIRTHDAY), doctorBirthday.toString());
        assertEquals(doctorJson.get(KEY_ROLE_STRING), Role.DOCTOR.toString());
        assertTrue(doctorJson.getBoolean(KEY_ACTIVE));

        JSONObject patientJson = jsonArray.getJSONObject(1);
        assertEquals(patientJson.getInt(KEY_ID), 2);
        assertEquals(patientJson.get(KEY_LOGIN), KEY_PATIENT);
        assertEquals(patientJson.get(KEY_FIRST_NAME), "Petr");
        assertEquals(patientJson.get(KEY_LAST_NAME), "Petrov");
        assertEquals(patientJson.get(KEY_BIRTHDAY), patientBirthday.toString());
        assertEquals(patientJson.get(KEY_ROLE_STRING), Role.PATIENT.toString());
        assertTrue(patientJson.getBoolean(KEY_ACTIVE));

        JSONObject adminJson = jsonArray.getJSONObject(2);
        assertEquals(adminJson.getInt(KEY_ID), 3);
        assertEquals(adminJson.get(KEY_LOGIN), KEY_ADMIN);
        assertEquals(adminJson.get(KEY_FIRST_NAME), "Drakula");
        assertEquals(adminJson.get(KEY_LAST_NAME), "Pensilvanskiy");
        assertEquals(adminJson.get(KEY_BIRTHDAY), adminBirthday.toString());
        assertEquals(adminJson.get(KEY_ROLE_STRING), Role.ADMIN.toString());
        assertTrue(adminJson.getBoolean(KEY_ACTIVE));

    }

    @Test
    void shouldFindUserById() throws IOException, JSONException {


        given().header(getAdminHeader()).when().get("/api/v1/users/{id}", 2)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(KEY_ID, equalTo(2))
                .body(KEY_LOGIN, equalTo("patient"))
                .body(KEY_FIRST_NAME, equalTo("Petr"))
                .body(KEY_LAST_NAME, equalTo("Petrov"))
                .body(KEY_BIRTHDAY, equalTo(LocalDate.of(1998, 2, 3).toString()))
                .body(KEY_ACTIVE, equalTo(true))
                .body(KEY_ROLE_STRING, equalTo(Role.PATIENT.toString()));

        String bodyString = given().header(getAdminHeader()).when().get("/api/v1/users/{id}", 2)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        String expected = getJsonStringFromFile(RESPONSE_USER_PETROV);

        AssertJsonEquals(expected, bodyString);


    }

    private void AssertJsonEquals(String expected, String actual) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        assertEquals(objectMapper.readTree(expected), objectMapper.readTree(actual));
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
        JSONObject userJson = new JSONObject(requestBody);

        given().header(getAdminHeader()).contentType(ContentType.JSON).body(requestBody)
                .when().post("/api/v1/users")
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .body(KEY_LOGIN, equalTo("create"))
                .body(KEY_FIRST_NAME, equalTo("Creat"))
                .body(KEY_LAST_NAME, equalTo("Creatov"))
                .body(KEY_BIRTHDAY, equalTo(LocalDate.of(2000, 1, 1).toString()))
                .body(KEY_ACTIVE, equalTo(true))
                .body(KEY_ROLE_STRING, equalTo(Role.PATIENT.toString()));


        User userDB = userJpaRepository.findByLogin("create").get();

        assertEquals(userDB.getLogin(), userJson.getString(KEY_LOGIN));
        assertEquals(userDB.getLastName(), userJson.getString(KEY_LAST_NAME));
        assertEquals(userDB.getFirstName(), userJson.getString(KEY_FIRST_NAME));
        assertEquals(userDB.getBirthday(), LocalDate.parse(userJson.getString(KEY_BIRTHDAY)));
        assertEquals(userDB.getRole(), Role.valueOf(userJson.getString(KEY_ROLE)));
        assertTrue(passwordEncoder.matches("created_password", userDB.getPass()));

    }

    @Test
    void shouldGetBadRequestStatusWhenRequestBodyIsEmpty() throws IOException {
        given().header(getAdminHeader()).when().post("/api/v1/users").then()
                .assertThat().statusCode(HttpStatus.BAD_REQUEST.value());

    }


    @Test
    void shouldChangeUserStatus() throws IOException {

        given().header(getAdminHeader()).when().get("/api/v1/users/{id}", 1)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(KEY_ACTIVE, equalTo(true));

        given().header(getAdminHeader()).when().put("/api/v1/users/change/status/{userId}/{isActive}", 1, false)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(KEY_ID, equalTo(1))
                .body(KEY_LOGIN, equalTo("doctor"))
                .body(KEY_FIRST_NAME, equalTo("Ivan"))
                .body(KEY_LAST_NAME, equalTo("Ivanov"))
                .body(KEY_BIRTHDAY, equalTo(doctorBirthday.toString()))
                .body(KEY_ACTIVE, equalTo(false))
                .body(KEY_ROLE_STRING, equalTo(Role.DOCTOR.toString()));

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
