import com.fasterxml.jackson.databind.util.JSONPObject;
import groovy.json.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PetstoreSwagger {
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://petstore.swagger.io/v2/") //тогда url можно убрать из запросов
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

    ResponseSpecification responseSpecSuccess = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .expectHeader("content-type", "application/json")
            .build();

    ResponseSpecification responseSpecFail = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectHeader("content-type", "application/json")
            .build();


    public static final String createUser = "/user";
    public static final String getUserByName = "/user/{username}";


    @Test
    void testGetUserSuccess() {
        given()
                .spec(requestSpecification)
                .get(PetstoreSwagger.getUserByName, "string")//передаем код
                .then()
                .body("id", equalTo(9013685127327350923L))
                .body("username", equalTo("string"))
                .body("firstName", equalTo("string"))
                .body("lastName", equalTo("string"))
                .body("email", equalTo("string"))
                .body("password", equalTo("string"))
                .body("phone", equalTo("string"))
                .body("userStatus", equalTo(0))
                .spec(responseSpecSuccess);

    }

    @Test
    void testGetUserFail() {
        given()
                .spec(requestSpecification)
                .get(PetstoreSwagger.getUserByName, "notfounduser1")//передаем код
                .then()
                .body("message", equalTo("User not found"))
                .spec(responseSpecFail)
                .statusCode(404);

    }

    /* не знаю, как передать параметры
    @Test
    void testCreateUserSuccess() {
        given()
                .queryParam("id", "1235321")
                .queryParam("username", "examplename1")
                .queryParam("firstName", "examplename1")
                .queryParam("lastName", "examplename1")
                .queryParam("email", "examplemail1")
                .queryParam("password", "examplepassword1")
                .queryParam("phone", "examplephone1")
                .queryParam("userStatus", "0")
                .spec(requestSpecification)
                .when()
                .post(PetstoreSwagger.createUser)
                .then()
                .body("message", equalTo("1235321"))
                .spec(responseSpecSuccess);

    }*/

    @Test
    void testCreateUserFail() {
        given()
                .spec(requestSpecification)
                .when()
                .post(PetstoreSwagger.createUser)
                .then()
                .spec(responseSpecFail)
                .statusCode(405);
    }
}


