package get_requests;

import base_urls.PetStoreBaseURL;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class Get07_SoftAssertion_testNG extends PetStoreBaseURL {
     /*
    Given
        https://petstore.swagger.io/v2/pet/1986
    When
        user send a GEt request to the url
    Then
        HTTP Status Code should be 200
    And
        Response content type is "application/json"
    And
        Response body should be like
        {
                "id": 1986,
                "category": {
                "id": 0,
                "name": "Bird"
        },
                "name": "Tweety",
                "photoUrls": [
                         "string"
                        ],
                 "tags": [
        {
                        "id": 0,
                        "name": "string"
        }
                         ],
                "status": "available"
        }
      */

    @Test   //import from junit
    public void get07() {
//      SET THE URL
        spec.pathParams("first", "pet", "second", 1986);

//      SET THE EXPECTED DATA

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}/{second}");
//        response.prettyPrint();

//      DO ASSERTION
        response
                .then()
                .statusCode(200)
                .body("category.name", equalTo("Bird"),
                       "name", equalTo("Tweety"),
                        "status", equalTo("available"))
//                .contentType(ContentType.JSON)
                .contentType("application/json");

        //Jsonpath
        JsonPath jsonPath = response.jsonPath();
        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON +"", response.contentType());//+"" to convert it to string, concatenate it, enum
        assertEquals("Bird", jsonPath.getString("category.name"));
        assertEquals("Tweety", jsonPath.getString("name"));
        assertEquals("available", jsonPath.getString("status"));

        //softAssert object -- testng
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(jsonPath.getString("category.name"), "Bird");
        softAssert.assertEquals(jsonPath.getString("name"), "Tweety");
        softAssert.assertEquals(jsonPath.getString("status"), "available");

        //do not forget assertAll()
        softAssert.assertAll();

    }
}
