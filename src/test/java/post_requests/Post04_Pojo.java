package post_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.JsonPlaceHolderPojo;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Post04_Pojo extends JSonPLaceHolderBaseURL {
     /*
        Given
            https://jsonplaceholder.typicode.com/todos
            {
                "userId": 55,
                "title": "Tidy your room",
                "completed": false
            }
        When
            User sends a POST Request to the url
        Then
            HTTP Status Code should be 201
        And
            response body should be like
            {
                "userId": 55,
                "id": 201,
                "title": "Tidy your room",
                "completed": false
            }
     */

    @Test
    public void post04() {
//      SET THE URL
        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA with Pojo Class
        JsonPlaceHolderPojo expectedData = new JsonPlaceHolderPojo(55, "Tidy your room", false);
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).post("{first}");
        response.prettyPrint();

//      DO ASSERTION
        JsonPlaceHolderPojo actualData = response.as(JsonPlaceHolderPojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(201, response.statusCode());
        assertEquals(expectedData.getUserId(), actualData.getUserId());
        assertEquals(expectedData.getTitle(), actualData.getTitle());
        assertEquals(expectedData.getCompleted(), actualData.getCompleted());
    }
}
