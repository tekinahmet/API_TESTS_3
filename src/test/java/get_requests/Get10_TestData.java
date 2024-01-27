package get_requests;

import base_urls.HerokuAppBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import test_data.HerokuAppTestData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static test_data.HerokuAppTestData.bookingdatesOuterMap;

public class Get10_TestData extends HerokuAppBaseURL {
    /*
    Given
        https://restful-booker.herokuapp.com/booking/335
    When
        user send a GEt request to the url
    Then
       Response body should be like
        {
            "firstname": "Jane",
            "lastname": "Doe",
            "totalprice": 111,
            "depositpaid": true,
            "bookingdates": {
                "checkin": "2018-01-01",
                "checkout": "2019-01-01"
            },
            "additionalneeds": "Extra pillows please"
        }
     */
    @Test
    public void get10(){
//      SET THE URL
        spec.pathParams("first", "booking" ,"second", 2228);

//      SET THE EXPECTED DATA --> first create inner map, then outer map
        //inner map
        Map<String, String> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin","2018-01-01");
        bookingDatesMap.put("checkout", "2019-01-01");

        //outer map
        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("firstname", "Jane");
        expectedData.put("lastname", "Doe");
        expectedData.put("totalprice", 111);
        expectedData.put("depositpaid", true);
        expectedData.put("additionalneeds", "Extra pillows please");
        expectedData.put("bookingdates", bookingDatesMap);

        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        //response.prettyPrint();

//      DO ASSERTION
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.get("firstname"), actualData.get("firstname"));
        assertEquals(expectedData.get("lastname"), actualData.get("lastname"));
        assertEquals(expectedData.get("totalprice"), actualData.get("totalprice"));
        assertEquals(expectedData.get("depositpaid"), actualData.get("depositpaid"));
        assertEquals(expectedData.get("additionalneeds"), actualData.get("additionalneeds"));

        //since all values are inside Object container, we need to do typecasting to use it in the original type
        assertEquals(((Map)expectedData.get("bookingdates")).get("checkin"), ((Map)actualData.get("bookingdates")).get("checkin"));
        assertEquals(((Map)expectedData.get("bookingdates")).get("checkout"), ((Map)actualData.get("bookingdates")).get("checkout"));
    }
    @Test
    //recommended way
    public void get10WithTestData(){
//      SET THE URL
        spec.pathParams("first", "booking" ,"second", 2731);

        //adjust the codes by using HerokuAppTestData class

//      SET THE EXPECTED DATA --> first create inner map, then outer map
        //inner map
        Map<String, String> bookingDatesMap = HerokuAppTestData.bookingdatesInnerMap("2018-01-01", "2019-01-01");

        //outer map
        Map<String, Object> expectedData = bookingdatesOuterMap("Jane", "Doe", 111, true, bookingDatesMap, "Extra pillows please");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
        //response.prettyPrint();

//      DO ASSERTION
        Map<String, Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.get("firstname"), actualData.get("firstname"));
        assertEquals(expectedData.get("lastname"), actualData.get("lastname"));
        assertEquals(expectedData.get("totalprice"), actualData.get("totalprice"));
        assertEquals(expectedData.get("depositpaid"), actualData.get("depositpaid"));
        assertEquals(expectedData.get("additionalneeds"), actualData.get("additionalneeds"));

        //we do not need to do typecasting and
        assertEquals(bookingDatesMap.get("checkin"), ((Map)actualData.get("bookingdates")).get("checkin"));
        assertEquals(bookingDatesMap.get("checkout"), ((Map)actualData.get("bookingdates")).get("checkout"));
    }
}
