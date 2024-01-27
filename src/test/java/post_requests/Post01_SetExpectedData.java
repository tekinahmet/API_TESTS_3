package post_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Post01_SetExpectedData extends JSonPLaceHolderBaseURL {
    /*
        Given
            https://jsonplaceholder.typicode.com/todos
            {
                "userId": 3,
                "title": "Have Breakfast",
                "completed": true
            }
        When
            User sends a POST Request to the url
        Then
            HTTP Status Code should be 201
        And
            response body should be like
            {
                "userId": 3,
                "id": 201,
                "title": "Have Breakfast",
                "completed": true
            }
     */
    @Test
    public void post01() {
//      SET THE URL
        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA--> payLoad --> the data to transfer
        //we can create the payload byString but it is not recommended
        //because it is not going to be dynamic to get the data while asserting it
        String expectedData = "{\n" +
                "                \"userId\": 3,\n" +
                "                \"title\": \"Have Breakfast\",\n" +
                "                \"completed\": true\n" +
                "            }";

//      SEND REQUEST AND GET RESPONSE
        //when we send post request, the content type must be declared!!!
        Response response = given(spec).contentType(ContentType.JSON).body(expectedData).post("{first}");
        response.prettyPrint();

//      DO ASSERTION
        //1st way
        JsonPath jsonPath = response.jsonPath();
        assertEquals(201, response.statusCode());
        assertEquals(3, jsonPath.getInt("userId"));
        assertEquals("Have Breakfast", jsonPath.getString("title"));
        assertTrue(jsonPath.getBoolean("completed"));
        assertEquals(201, jsonPath.getInt("id"));

        //2nd way
        response
                .then()
                .statusCode(201)
                .body("userId", equalTo(3),
                        "title", equalTo("Have Breakfast"),
                        "completed", equalTo(true),
                        "id", equalTo(201));

        //3rd way
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.statusCode(), 201);
        softAssert.assertEquals(jsonPath.getInt("userId"), 3);
        softAssert.assertEquals(jsonPath.getString("title"), "Have Breakfast");
        softAssert.assertTrue(jsonPath.getBoolean("completed"));
        softAssert.assertEquals(jsonPath.getInt("id"), 201);

        softAssert.assertAll();
    }
}
