package post_requests;

import base_urls.JSonPLaceHolderBaseURL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;
import test_data.JSonPlaceHolderTestData;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static test_data.JSonPlaceHolderTestData.expectedDataMap;

public class Post06_ObjectMapper extends JSonPLaceHolderBaseURL {
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
    public void post06() throws JsonProcessingException {
//      SET THE URL
        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA
        Map<String, Object> expectedData = expectedDataMap(55, "Tidy your room", false);
        System.out.println("expectedData = " + expectedData);//expectedData = {completed=false, title=Tidy your room, userId=55}

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).post("{first}");
        //response.prettyPrint();

//      DO ASSERTION
        //convert response to hashmap by using objectmapper (kind of a serializor)//import com.fasterxml.jackson.databind.ObjectMapper;
        Map<String, Object> actualData = new ObjectMapper().readValue(response.asString(), HashMap.class);//add exception to method signature (JsonProcessingException)
        System.out.println("actualData = " + actualData);//actualData = {completed=false, id=201, title=Tidy your room, userId=55}

        assertEquals(201, response.statusCode());
        assertEquals(expectedData.get("completed"), actualData.get("completed"));
        assertEquals(expectedData.get("title"), actualData.get("title"));
        assertEquals(expectedData.get("userId"), actualData.get("userId"));

        //soft assertion
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 201);
        softAssert.assertEquals(actualData.get("completed"), expectedData.get("completed"));
        softAssert.assertEquals(actualData.get("title"), expectedData.get("title"));
        softAssert.assertEquals(actualData.get("userId"), expectedData.get("userId"));

    }
}
