package put_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static test_data.JSonPlaceHolderTestData.expectedDataMap;

public class Put01_Serialization extends JSonPLaceHolderBaseURL {
    /*
        Given
            https://jsonplaceholder.typicode.com/todos
                {
                    "userId": 10,
                    "title": "have a good day",
                    "completed": true
                }
        When
            User sends a PUT Request to the url
        Then
            HTTP Status Code should be 200
        And
            response body should be like
                {
                    "userId": 10,
                    "title": "have a good day",
                    "completed": true,
                    "id": 177
                }
     */
    @Test
    public void put01(){
//      SET THE URL
        spec.pathParams("first", "todos", "second", 177);

//      SET THE EXPECTED DATA
        //serialization
        Map<String, Object> expectedData = expectedDataMap(10, "have a good day", true);
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).put("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        //de-serialization
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);
        assertEquals(200, response.statusCode());
        assertEquals(expectedData.get("userId"), actualData.get("userId"));
        assertEquals(expectedData.get("completed"), actualData.get("completed"));
        assertEquals(expectedData.get("title"), actualData.get("title"));
    }
}
