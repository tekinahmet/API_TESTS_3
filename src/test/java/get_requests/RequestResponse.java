package get_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class RequestResponse {
    /*
    1. postman is used for  manuel testing
    2. we use rest assured library for api automation testing
    3. to type automation script, follow these steps
        a. understand the requirements
        b. type the test case
            -->to type test cases, we use gherkin languages
            -->the keywords in gherkin language:
                given: used for pre-conditions(endpoint)
                when: used for actions
                then: used for output(assertion)
                and: used for multiple usage of keywords
        c. start to type automation script
//      SET THE URL
//      SET THE EXPECTED DATA
//      SEND REQUEST AND GET RESPONSE
//      DO ASSERTION
     */


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
 */

    public static void main(String[] args) {
//        set the url
        String url = "https://restful-booker.herokuapp.com/booking/10";
//        set expected data
        //we will this step later

//        send request and get response
        Response response = given().when().get(url);
        //to see the response on the console, use prettyprint() method with the response object
//        response.prettyPrint();

//        do assertion
        //to do assertion we need testing framework like JUnit, Test NG, Cucumber etc.

        //HTTP Status Code should be 200
        System.out.println("\nstatusCode() = " + response.statusCode());
        System.out.println("getStatusCode() = " + response.getStatusCode());


        //Content Type should be application/json
        System.out.println("\ncontentType() = " + response.contentType());
        System.out.println("response.getContentType() = " + response.getContentType());


        //Status Line should be HTTP/1.1 200 OK
        System.out.println("\nstatusLine() = " + response.statusLine());
        System.out.println("getStatusLine() = " + response.getStatusLine());


        //how to get header
        System.out.println("\nServer= " + response.header("Server"));
        System.out.println("Content-Type = " + response.header("Content-Type"));
        System.out.println("Date = " + response.header("Date"));//GMT


        //how to get headers
        System.out.println("\nHeaders():\n" + response.headers());

        //how to get time
        System.out.println("\ntime() = " + response.time());
    }
}


































