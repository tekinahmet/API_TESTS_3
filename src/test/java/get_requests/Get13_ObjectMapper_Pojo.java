package get_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.JsonPlaceHolderPojo;
import utils.ObjectMapperUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.ObjectMapperUtils.convertJsonToJava;

public class Get13_ObjectMapper_Pojo extends JSonPLaceHolderBaseURL {
     /*
            Given
                 https://jsonplaceholder.typicode.com/todos/25
            When
                 User send a GEt request to the url
            Then
                 HTTP Status Code should be 200
            And
                Response body should be like
                {
                    "userId": 2,
                    "id": 25,
                    "title": "voluptas quo tenetur perspiciatis explicabo natus",
                    "completed": true
                }
        */
    @Test
    public void get13(){
//      SET THE URL
        spec.pathParams("first", "todos", "second", 25);

//      SET THE EXPECTED DATA
        String stringJson = "{\n" +
                "                    \"userId\": 2,\n" +
                "                    \"id\": 25,\n" +
                "                    \"title\": \"voluptas quo tenetur perspiciatis explicabo natus\",\n" +
                "                    \"completed\": true\n" +
                "                }";

        JsonPlaceHolderPojo expectedData = convertJsonToJava(stringJson, JsonPlaceHolderPojo.class);//import static utils.ObjectMapperUtils.convertJsonToJava;
        //Map<String, Object> expectedData = convertJsonToJava(stringJson, JsonPlaceHolderPojo.class);//import static utils.ObjectMapperUtils.convertJsonToJava;
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        //de-serialization

        JsonPlaceHolderPojo actualData = convertJsonToJava(response.asString(), JsonPlaceHolderPojo.class);
        System.out.println("actualData = " + actualData);
        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getTitle(), actualData.getTitle());
        assertEquals(expectedData.getCompleted(), actualData.getCompleted());
        assertEquals(expectedData.getUserId(), actualData.getUserId());

//        Map<String, Object> actualData = convertJsonToJava(stringJson, HashMap.class);
//        System.out.println("actualData = " + actualData);
//        assertEquals(200, response.statusCode());
//        assertEquals(expectedData.getTitle(), actualData.get("title"));
//        assertEquals(expectedData.getCompleted(), actualData.get("completed"));
//        assertEquals(expectedData.getUserId(), actualData.get("userId"));
    }
}
