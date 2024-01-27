package delete_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import utils.ObjectMapperUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Delete01 extends JSonPLaceHolderBaseURL {
     /*
        Given
            https://jsonplaceholder.typicode.com/todos/25
        When
            User sends a DELETE Request to the url
        Then
            HTTP Status Code should be 200
        And
            response body should be like
            { }
     */

    @Test
    public void delete01() {
//      SET THE URL
        spec.pathParams("first", "todos", "second", 25);

//      SET THE EXPECTED DATA
        Map<String, String> expectedData = new HashMap<>();

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).delete("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        Map<String, String> actualData = ObjectMapperUtils.convertJsonToJava(response.asString(), HashMap.class);
        assertEquals(200, response.statusCode());

        //1st way
        assertEquals(expectedData, actualData);

        //2nd way
        assertTrue(actualData.isEmpty());//no need to create an expectedData

        //3rd way
        assertEquals(0, actualData.size());

        //be careful when you do delete request. do not delete the others' data.
        //first create your data, then delete yours
    }
}
