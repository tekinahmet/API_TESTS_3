package put_requests;

import base_urls.DummyRestApiBaseURL;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.DummyRestApiDataPojo;
import pojos.DummyRestApiResponsePojo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Put02 extends DummyRestApiBaseURL {
    /*
        url: https://dummy.restapiexample.com/api/v1/update/21
        HTTP request method: PUT request
        Request Body :
                {
                    "employee_name": "Ahmet",
                    "employee_salary": 123,
                    "employee_age": 23,
                    "profile_image": "perfect image"
                  }
         test case: type by using Gherkin Language
         assert:
            1. status code is 200
            2. response body should be like this
                {
                    "status": "success",
                    "data": {
                            "employee_name": "Ahmet",
                            "employee_salary": 123,
                            "employee_age": 23,
                            "profile_image": "perfect image"
                    },
                    "message": "Successfully! record has been updated."
                }

     */

    /*
    Given
        https://dummy.restapiexample.com/api/v1/update/21
    And
        Request Body :
                {
                    "employee_name": "Ahmet",
                    "employee_salary": 123,
                    "employee_age": 23,
                    "profile_image": "perfect image"
                  }
    When
        Send a request
    Then
        Status code is 200
    And
        response body should be like this
                {
                    "status": "success",
                    "data": {
                            "employee_name": "Ahmet",
                            "employee_salary": 123,
                            "employee_age": 23,
                            "profile_image": "perfect image"
                    },
                    "message": "Successfully! record has been updated."
                }
     */

    @Test
    public void put02() {
//      SET THE URL
        spec.pathParams("first", "update", "second", 21);

//      SET THE EXPECTED DATA
        DummyRestApiDataPojo expectedData = new DummyRestApiDataPojo("Ahmet", 123, 23, "perfect image");
        System.out.println("expectedData = " + expectedData);

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).body(expectedData).put("{first}/{second}");
        response.prettyPrint();

//      DO ASSERTION
        DummyRestApiResponsePojo actualData = response.as(DummyRestApiResponsePojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getEmployee_name(), actualData.getData().getEmployee_name());
        assertEquals(expectedData.getProfile_image(), actualData.getData().getProfile_image());
        assertEquals(expectedData.getEmployee_age(), actualData.getData().getEmployee_age());
        assertEquals(expectedData.getEmployee_salary(), actualData.getData().getEmployee_salary());

        //hard assertion--no need to assert them, because they are not expected data
        //if you need to assert the whole body, assert like this
        assertTrue(actualData.getStatus().contains("success"));
        assertTrue(actualData.getMessage().contains("Successfully! Record has been updated"));
    }
}
