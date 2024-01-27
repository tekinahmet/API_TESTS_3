package get_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Get04_Spec extends JSonPLaceHolderBaseURL {
    @Test
    public void get04(){
//      SET THE URL

        //1 WAY to get url
//        String url = "https://jsonplaceholder.typicode.com/todos/";

        //2 WAY to get url
        spec.pathParam("first", "todos");
        //to reach spec object we need to extend it to the related class
        //"first" named parameter represents the "todos" parameter in the endpoint

//      SET THE EXPECTED DATA

//      SEND REQUEST AND GET RESPONSE

        //1. WAY to get spec
//        Response response = given().spec(spec).when().get("{first}");

        //2. WAY to get spec
        Response response = given(spec).when().get("{first}");
        response.prettyPrint();

//      DO ASSERTION
        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", hasSize(200),
                        "title", hasItem("quis eius est sint explicabo"),
                        "userId", hasItems(2,7,9));
        /*
        If a response body returns as a List,
            -we can check a List size with hasSize() method
            -we can check if a List contains an item with hasItem() method
            -we can check if a List contains multiple items with hasItems() method
         */
    }
}
