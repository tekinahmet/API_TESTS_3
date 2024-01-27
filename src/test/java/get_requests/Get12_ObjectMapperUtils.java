package get_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.ObjectMapperUtils.convertJsonToJava;

public class Get12_ObjectMapperUtils extends JSonPLaceHolderBaseURL {
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
    public void get12() {
//      SET THE URL
        spec.pathParams("first", "todos", "second", 25);

//      SET THE EXPECTED DATA
        String stringJson = "{\n" +
                "\"userId\": 2,\n" +
                "\"id\": 25,\n" +
                "\"title\": \"voluptas quo tenetur perspiciatis explicabo natus\",\n" +
                "\"completed\": true\n" +
                "}";
        //ObjectMapperUtils.convertJsonToJava(stringJson, HashMap.class);
        Map<String, Object> expectedData = convertJsonToJava(stringJson, HashMap.class);//import static utils.ObjectMapperUtils.convertJsonToJava;
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        //response.prettyPrint();

//      DO ASSERTION
        Map<String, Object> actualData = convertJsonToJava(response.asString(), HashMap.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.get("completed"), actualData.get("completed"));
        assertEquals(expectedData.get("title"), actualData.get("title"));
        assertEquals(expectedData.get("userId"), actualData.get("userId"));
    }
}
