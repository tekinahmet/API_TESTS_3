package get_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Get03_SoftAssertion {
   /*
        "userId": 2,
            "id": 23,
            "title": "et itaque necessitatibus maxime molestiae qui quas velit",
            "completed": false
    */

    @Test
    public void get03() {
//        set the url
        String url = "https://jsonplaceholder.typicode.com/todos/23";

//        set expected data

//        send request and get response
        Response response = given().when().get(url);
        response.prettyPrint();

//        do assertion
        //1. WAY-HARD ASSERTION
        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("et itaque necessitatibus maxime molestiae qui quas velit"))
                .body("completed", equalTo(false))
                .body("userId", equalTo(2));

        //2.WAY-SOFT ASSERTION
        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("et itaque necessitatibus maxime molestiae qui quas velit"),
                        "completed", equalTo(false),
                        "userId", equalTo(2));

    }
}
