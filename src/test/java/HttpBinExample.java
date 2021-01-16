import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import javax.xml.ws.Endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class HttpBinExample {
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://httpbin.org/") //тогда url можно убрать из запросов
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

    public static final String statusCode = "/status/{codes}";
    public static final String userAgent = "/user-agent";
    public static final String responseHeaders = "/response-headers";


    @Test
    void testResponseHeaders(){
        given()
                .spec(requestSpecification)
                .queryParam("freeform", "Otus")//задается для post-методов
                .when()
                .post(HttpBinExample.responseHeaders)
                .then()
                .body("freeform", equalTo("Otus"));

    }

    @Test
    void testUserAgent(){
        given()
                .spec(requestSpecification)
                .get(HttpBinExample.userAgent)
                .then()
                .statusCode(200)
                .body("user-agent", equalTo("Apache-HttpClient/4.5.3 (Java/1.8.0_241)"));

    }


    @Test
    void testStatusCode() {
        //в junit5 можно опускать public
        given()
                .spec(requestSpecification)//использвется чтобы применить requestSpecification, которые прописали вверху
                .get(HttpBinExample.statusCode, "400")//передаем код
                .then()
                .statusCode(400);//тогда ожидаем код 400

    }

    @Test
    void testGetHeaders() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("http://httpbin.org/headers")
                .then()
                .statusCode(200)
                .body("headers.Host", equalTo("httpbin.org"));
        //когда разбили json идем последовательно по дереву через точку

    }

    @Test
    public void testGetInfo() {
        Response response1 =
                given()
                        .when()
                        .get("http://httpbin.org/get?a=1")
                        .then()
                        .statusCode(200)
//                        .body("headers.Connection", equalTo("close"))
                        .body("args.a", equalTo("1"))//args передаются в запросе после вопросительного знака?
                        .extract()
                        .response();
    }
}
