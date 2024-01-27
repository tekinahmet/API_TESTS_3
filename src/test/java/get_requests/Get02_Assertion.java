package get_requests;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class Get02_Assertion {
    /*
         Given
             https://restful-booker.herokuapp.com/booking/10
         When
             User sends a GET Request to the url
         Then
             HTTP Status Code should be 200
         And
             Content Type should be application/json
         And
             Status Line should be HTTP/1.1 200 OK
         And
             Response body contains "Not Found"
         And
             Response body does not contain "bootcamp"
         And
             Server is "Cowboy"
    */
    @Test
    public void get02(){
//        set the url
        String url = "https://restful-booker.herokuapp.com/booking/0";

//        set expected data

//        send request and get response
        Response response = given().when().get(url);
        response.prettyPrint();

//        do assertion
        response
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found");

        String responseString = response.asString();
        System.out.println("responseString = " + responseString);

        assertEquals("Body does not contain 'Not Found'","Not Found",responseString);
        assertTrue(responseString.contains("Not Found"));

        assertNotEquals("bootcamp", responseString);
        assertFalse(responseString.contains("bootcamp"));

        //1.way
        String server = response.header("Server");
        System.out.println("server = " + server);
        assertEquals("Cowboy", server);
        //2.way
        assertEquals("Cowboy", response.header("Server"));
    }
}
