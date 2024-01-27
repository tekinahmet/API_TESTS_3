package post_requests;

import base_urls.JSonPLaceHolderBaseURL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Test;

import pojos.JsonPlaceHolderPojo;


import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class Post07_ObjectMapper_Pojo extends JSonPLaceHolderBaseURL {
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
    public void post07() throws JsonProcessingException {
//      SET THE URL
        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA
        //create an object by using JsonPlaceHolderPojo class
        JsonPlaceHolderPojo expectedData = new JsonPlaceHolderPojo(55, "Tidy your room", false);
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).post("{first}");
        //response.prettyPrint();

//      DO ASSERTION

        //convert response to hashmap by using objectmapper (kind of a serializor)//import com.fasterxml.jackson.databind.ObjectMapper;
        //write @JsonIgnoreProperties(ignoreUnknown = true) out of the class in the JsonPlaceHolderPojo
        //objectmapper() is more secure and faster
        JsonPlaceHolderPojo actualData = new ObjectMapper().readValue(response.asString(), JsonPlaceHolderPojo.class);//add exception to method signature (JsonProcessingException)
        System.out.println("actualData = " + actualData);

        assertEquals(201, response.statusCode());
        assertEquals(expectedData.getTitle(), actualData.getTitle());
        assertEquals(expectedData.getUserId(), actualData.getUserId());
        assertEquals(expectedData.getCompleted(), actualData.getCompleted());
    }
}
