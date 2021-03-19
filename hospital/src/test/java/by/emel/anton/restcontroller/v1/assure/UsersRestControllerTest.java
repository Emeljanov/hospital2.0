package by.emel.anton.restcontroller.v1.assure;

import by.emel.anton.entity.Role;
import by.emel.anton.entity.User;
import by.emel.anton.facade.PatientCardFacade;
import by.emel.anton.facade.TherapyFacade;
import by.emel.anton.facade.UserFacade;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.repository.TherapyDao;
import by.emel.anton.repository.UserDao;
import by.emel.anton.repository.jpa.UserJpaRepository;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

class UsersRestControllerTest {

    private final String REQUEST_BODY_USER_PATH = "src/test/resources/request-body-login-user1.json";
    private final String AUTH = "Authorization";

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    UserFacade userFacade;
    @Autowired
    PatientCardFacade patientCardFacade;
    @Autowired
    TherapyFacade therapyFacade;
    @Autowired
    PatientCardService patientCardService;
    @Autowired
    TherapyService therapyService;

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;

    @Autowired
    TherapyDao therapyDao;

    @Autowired
    PatientCardDao patientCardDao;


    @LocalServerPort
    private int port;


    @BeforeEach
    void beforeAll() {
        RestAssured.port = port;
        User user1 = User.builder()
                .login("blag")
                .pass("blag")
                .role(Role.ADMIN)
                .isActive(true)
                .lastName("blag")
                .firstName("blag")
                .birthday(LocalDate.now())
                .build();


        userJpaRepository.save(user1);


    }

    @Test
    void findAll() throws IOException {

        String tokenAdmin = RestAssured
                .given().contentType(ContentType.JSON)
                .body(getJsonStringFromFile(REQUEST_BODY_USER_PATH))
                .when()
                .post("/api/v1/auth/login").jsonPath().get("token");
        Header headerAdmin = new Header(AUTH, tokenAdmin);

        RestAssured.given().header(headerAdmin).when().get("/api/v1/users").asString();


    }

    @Test
//    @WithUserDetails("doctor")

//    @Sql(value = {"classpath:before-each.sql"})
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

        System.out.println(userDao.findAll());
        System.out.println(userFacade.findAll());
        System.out.println(userService.findAll());
        System.out.println("JPAA" + userJpaRepository.findAll());


//                RestAssuredMockMvc.given().auth().principal()


//        RestAssured.given().auth().oauth2("fsdfsdf").when().get("api/v1/users/{id}",1).then().statusCode(200);

    }

    @Test
//    @Sql(value = {"classpath:before-each.sql"})
    void saveUser() {

        System.out.println(userDao.findAll());
        System.out.println(userFacade.findAll());
        System.out.println(userService.findAll());
        System.out.println("JPAA" + userJpaRepository.findAll());
    }

    @Test
//    @WithUserDetails("admin")
    void deleteUserById() {
        RestAssured.defaultParser = Parser.JSON;


        RestAssured.given()
                .when()
                .get("/api/v1/therapies").then().contentType(ContentType.JSON).extract().response();



       /* String token  =
                RestAssured
                        .given().contentType(ContentType.JSON)
                        .body(getJsonStringFromFile(REQUEST_BODY_USER_PATH))
                        .when()
                        .post("/api/v1/auth/login").jsonPath().get("token");

        RestAssured.given().header(new Header("Authorization",token)).when().get("/api/v1/therapies").asString()*/


//        System.out.println(st);

    }

    @Test
    void changeUserStatus() {
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}
