package by.emel.anton.restcontroller.v1.assure;

import by.emel.anton.facade.UserFacade;
import by.emel.anton.repository.UserDao;
import by.emel.anton.repository.jpa.UserJpaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*@ExtendWith(SpringExtension.class)*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc


@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersRestControllerTest {

    private final String REQUEST_BODY_USER_PATH = "src/test/resources/request-body-login-user1.json";

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    UserFacade userFacade;

    @Autowired
    UserDao userDao;


    @LocalServerPort
    private int portt;

    @BeforeEach
    void beforeAll() {
//        RestAssured.baseURI = "http://localhost:8080/";
//        RestAssured.basic("admin","admin");
        RestAssured.port = portt;

    }

    @Test
    void findAll() {

    }

    @Test
//    @WithUserDetails("doctor")
    void findUserById() throws IOException {
//        int Status = RestAssured.get("/api/v1/users/{id}",1).statusCode();


//        RestAssured.when().get("api/v1/users/{id}",1).then().statusCode(200);
        String responseT =

                RestAssured
                        .given().contentType(ContentType.JSON)
                        .body(getJsonStringFromFile(REQUEST_BODY_USER_PATH))
                        .when()
                        .post("/api/v1/auth/login").jsonPath().get("token");

        System.out.println(responseT);


//        RestAssured.given().auth().oauth2("fsdfsdf").when().get("api/v1/users/{id}",1).then().statusCode(200);

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