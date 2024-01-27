package get_requests;

import base_urls.HerokuAppBaseURL;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class Get06_JsonPath extends HerokuAppBaseURL {
    /*
    Given
        https://restful-booker.herokuapp.com/booking/2243
    When
        user send a GEt request to the url
    Then
        HTTP Status Code should be 200
    And
        Response content type is "application/json"
    And
        Response body should be like
            {
                "firstname": "John",
                "lastname": "Smith",
                "totalprice": 111,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2018-01-01",
                    "checkout": "2019-01-01"
            },
                "additionalneeds": "Breakfast"
            }
     */
    @Test
    public void get06(){
//      SET THE URL
        spec.pathParams("first" , "booking", "second", 835);

//      SET THE EXPECTED DATA

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
//        response.prettyPrint();

//      DO ASSERTION

        //1 way
        response
                .then()
                .statusCode(200)
                .body("firstname", equalTo("John"),
                        "lastname", equalTo("Smith"),
                        "totalprice", equalTo(111),
                        "depositpaid", equalTo(true),
                        "bookingdates.checkin", equalTo("2018-01-01"),
                        "bookingdates.checkout", equalTo("2019-01-01"),
                        "additionalneeds", equalTo("Breakfast"))
//                .contentType("application/json")
                .contentType(ContentType.JSON);

        //2 way : we will use JsonPath class to extract the data outside the body
        JsonPath jsonPath = response.jsonPath();

        //get the data and assert it
//        System.out.println(jsonPath.getInt("totalprice")+9);//120
//        System.out.println(jsonPath.getString("totalprice")+9);//1119 (concatenation)

        //hard assertion
        assertEquals("John", jsonPath.getString("firstname"));
        assertEquals("Smith", jsonPath.getString("lastname"));
        assertEquals(111, jsonPath.getInt("totalprice"));
        assertTrue(jsonPath.getBoolean("depositpaid"));
        assertEquals("2018-01-01", jsonPath.getString("bookingdates.checkin"));
        assertEquals("2019-01-01", jsonPath.getString("bookingdates.checkout"));

        //soft assertion: testNG soft assertion
        //follow these three steps:
        //1st step: create soft assert object
        SoftAssert softAssert = new SoftAssert();

        //2nd step: use assertAll() method
        softAssert.assertEquals(jsonPath.getString("firstname"), "John", "there is no one called john");
        softAssert.assertEquals(jsonPath.getString("lastname"), "Smith", "there is no one called Smith");
        softAssert.assertEquals(jsonPath.getInt("totalprice"), 111);
        softAssert.assertEquals(jsonPath.getBoolean("depositpaid"), true);
//        softAssert.assertTrue(jsonPath.getBoolean("depositpaid"));
        softAssert.assertEquals(jsonPath.getString("bookingdates.checkin"), "2018-01-01" );
        softAssert.assertEquals(jsonPath.getString("bookingdates.checkout"), "2019-01-01" );


        //3rd step: use assertAll() method (DON'T FORGET TO TYPE THIS METHOD!!!)
        softAssert.assertAll();


    }
}
