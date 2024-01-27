package get_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static test_data.JSonPlaceHolderTestData.expectedDataMap;

public class Get09_TestData extends JSonPLaceHolderBaseURL {
     /*
    Given
        https://jsonplaceholder.typicode.com/todos/25
    When
        user send a GEt request to the url
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
    And
        header "Via" is "1.1 vegur"
    And
        header "Server" is "cloudflare"
      */
    @Test
    public void get09() {
//      SET THE URL
        spec.pathParams("first", "todos", "second", 25);

//      SET THE EXPECTED DATA
        Map<String, Object> expectedData = expectedDataMap(2,"voluptas quo tenetur perspiciatis explicabo natus", true);
        expectedData.put("id", 25);//if we need to assert ids as well, we can add those data too.
        //to prevent hard coding in assertion, we add the headers here in expectedData
        //"headers" is meta-data
        expectedData.put("Via","1.1 vegur");
        expectedData.put("Server", "cloudflare");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        //de-serialization
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);//random order--HashMap

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.get("userId"), actualData.get("userId"));
        assertEquals(expectedData.get("title"), actualData.get("title"));
        assertEquals(expectedData.get("completed"), actualData.get("completed"));

        //to assert data in headers, we use response.header() method
        //because we do not have them in actualData
        assertEquals(expectedData.get("Via"), response.header("Via"));
        assertEquals(expectedData.get("Server"), response.header("Server"));


        //no need to assert the id, because we fetch the data by sending id
        assertEquals(expectedData.get("id"), actualData.get("id"));

    }
}
