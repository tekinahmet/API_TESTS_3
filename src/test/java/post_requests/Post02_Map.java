package post_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import test_data.JSonPlaceHolderTestData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class Post02_Map extends JSonPLaceHolderBaseURL {
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
    public void post02() {

//      SET THE URL

        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA

        //we can send the payload by using Map (recommended)
        Map<String, Object> expectedData = new HashMap<>();//random order
        expectedData.put("userId", 3);
        expectedData.put("id", 201);
        expectedData.put("title", "Have Breakfast");
        expectedData.put("completed", true);
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE

        // we need a serializer to convert Java Object to Json ==> serialization (jackson-databind)
        //JSon-->a common language between applications
        Response response = given(spec).body(expectedData).post("{first}");//declare contentType
        response.prettyPrint();

//      DO ASSERTION

        // to do assertion we need 2 data in same types. we need to convert json response into java object
        //to convert Json to Java object ==>de-serialization
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);
        assertEquals(expectedData.get("userId"), actualData.get("userId"));
        assertEquals(expectedData.get("title"), actualData.get("title"));
        assertEquals(expectedData.get("completed"), actualData.get("completed"));
        assertEquals(expectedData.get("id"), actualData.get("id"));

        //we did not use any hard coding in assertion part

    }
    @Test
    public void post02WithTestData() {

//      SET THE URL
        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA
        Map<String, Object> expectedData = JSonPlaceHolderTestData.expectedDataMap(3, "Have Breakfast", true);

        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        // we need a serializer to convert Java Object to Json ==> serialization (jackson-databind)
        //JSon-->a common language between applications
        Response response = given(spec).body(expectedData).post("{first}");//declare contentType
        response.prettyPrint();

//      DO ASSERTION
        // to do assertion we need 2 data in same types. we need to convert json response into java object
        //to convert Json to Java object ==> de-serialization
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);
        assertEquals(expectedData.get("userId"), actualData.get("userId"));
        assertEquals(expectedData.get("title"), actualData.get("title"));
        assertEquals(expectedData.get("completed"), actualData.get("completed"));

        //we did not use any hard coding in assertion part

    }
}
