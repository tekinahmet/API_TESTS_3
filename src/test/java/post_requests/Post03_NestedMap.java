package post_requests;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static test_data.HerokuAppTestData.*;

public class Post03_NestedMap extends HerokuAppBaseURL {
     /*
    Given
       1. https://restful-booker.herokuapp.com/booking/1175
       2.
          {
                "firstname": "John",
                "lastname": "Doe",
                "totalprice": 11111,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2021-09-09",
                    "checkout": "2021-09-21"
                },
                "additionalneeds": "Extra pillows please"
            }
        When
           I send POST request to the url
        Then
            Status code is 200
        And
            response body should be like
            {
                "bookingid": 4260,
                "booking": {
                "firstname": "John",
                "lastname": "Doe",
                "totalprice": 11111,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2021-09-09",
                    "checkout": "2021-09-21"
                    },
            "additionalneeds": "Extra pillows please"
            }
     */

    @Test
    public void post03() {
//      SET THE URL
        spec
                //.contentType(ContentType.JSON)//we can declare this method here as well
                .pathParam("first", "booking");

//      SET THE EXPECTED DATA
        Map<String, String> bookingdates = bookingdatesInnerMap("2021-09-09", "2021-09-21");
        Map<String, Object> expectedData = bookingdatesOuterMap("John", "Doe", 11111, true, bookingdates, "Extra pillows please");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        //do not forget to add setContentType(ContentType.JSON) in the base url; without it, we get "Internal Server Error"
        //without body() method, we get "Internal Server Error"
        Response response = given(spec).body(expectedData).post("{first}");
        response.prettyPrint();

//      DO ASSERTION IN NESTED MAP
        //1st Way
        //de-serialization json to java (map)
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("\nactData = " + actualData);

        assertEquals(200, response.statusCode());

        //do typecasting to assert the data in nested map
        assertEquals(expectedData.get("firstname"), ((Map)actualData.get("booking")).get("firstname"));
        assertEquals(expectedData.get("lastname"), ((Map)actualData.get("booking")).get("lastname"));
        assertEquals(expectedData.get("totalprice"), ((Map)actualData.get("booking")).get("totalprice"));
        assertEquals(expectedData.get("depositpaid"), ((Map)actualData.get("booking")).get("depositpaid"));
        assertEquals(expectedData.get("additionalneeds"), ((Map) actualData.get("booking")).get("additionalneeds"));

        //double typecasting to get data in nested map
        assertEquals(bookingdates.get("checkin"),((Map)((Map) actualData.get("booking")).get("bookingdates")).get("checkin"));
        assertEquals(bookingdates.get("checkout"),((Map)((Map) actualData.get("booking")).get("bookingdates")).get("checkout"));

//      DO ASSERTION IN NESTED MAP
        //2nd Way
        //de-serialization json to java (map)
        //change Object to Map<String, Object>
        Map<String, Map<String, Object>> actData = response.as(HashMap.class);
        System.out.println("\nactData = " + actData);

        assertEquals(200, response.statusCode());

        //do typecasting to assert the data in nested map
        assertEquals(expectedData.get("firstname"), ((Map)actData.get("booking")).get("firstname"));
        assertEquals(expectedData.get("lastname"), ((Map)actData.get("booking")).get("lastname"));
        assertEquals(expectedData.get("totalprice"), ((Map)actData.get("booking")).get("totalprice"));
        assertEquals(expectedData.get("depositpaid"), ((Map)actData.get("booking")).get("depositpaid"));
        assertEquals(expectedData.get("additionalneeds"), ((Map) actData.get("booking")).get("additionalneeds"));

        //double typecasting to get data in nested map
        assertEquals(bookingdates.get("checkin"),((Map)((Map) actData.get("booking")).get("bookingdates")).get("checkin"));
        assertEquals(bookingdates.get("checkout"),((Map)((Map) actData.get("booking")).get("bookingdates")).get("checkout"));



    }
}
