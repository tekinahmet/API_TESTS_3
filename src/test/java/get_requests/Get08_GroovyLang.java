package get_requests;

import base_urls.JSonPLaceHolderBaseURL;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Get08_GroovyLang extends JSonPLaceHolderBaseURL {
    /*
    Given
        https://jsonplaceholder.typicode.com/todos
    When
        User send a GEt request to the url
    Then
        1.HTTP Status Code should be 200
        2.Print all ids greater than 190 on the console
            Assert that there are 10 ids greater than 190
        3.Print all userIds whose ids are less than 5 on the console
            Assert that number of userIds whose ids are less than 5 is 4
        4.Print all titles whose ids are less than 5
            Assert that "delectus aut autem" is one of the titles whose ids is less than 5
     */
    @Test
    public void get08(){
//      SET THE URL
        spec.pathParam("first", "todos");

//      SET THE EXPECTED DATA

//      SEND REQUEST AND GET RESPONSE
        Response response = given(spec).get("{first}");
//        response.prettyPrint();//in list[]

//      DO ASSERTION

//2.Print all ids greater than 190 on the console

        //1st way to assert statusCode()
        response
                .then()
                .statusCode(200);
        //2nd way to assert statusCode()
        assertEquals(200, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        //1st way : by using for each loop

//        Print all ids greater than 190 on the console
        List<Integer> idList= jsonPath.getList("id");//to get ids in list
        //System.out.println("idList = " + idList);
        List<Integer> idsGreaterThan190 = new ArrayList<>();//empty list
        for (int w:idList) {
            if (w > 190) {
                //System.out.println(w+" ");
                idsGreaterThan190.add(w);
            }
        }
        System.out.println("idsGreaterThan190 = " + idsGreaterThan190);//idsGreaterThan190 = [191, 192, 193, 194, 195, 196, 197, 198, 199, 200]
//Assert that there are 10 ids greater than 190
        assertEquals(10, idsGreaterThan190.size());

        //2nd way: by using GROOVY LANGUAGE (recommended) to filter the data if we have a list with json

        System.out.println("jsonPath.getList(\"id\") = " + jsonPath.getList("id"));
        System.out.println("jsonPath.getList(\"title\") = " + jsonPath.getList("title"));

        //findAll{it.id>190} to get the list by filtering with id
//        System.out.println("\ngroovy language to get the list whose ids are greater than 190\n " + jsonPath.getList("findAll{it.id>190}"));
        List<Object> getListByUsingGroovy = jsonPath.getList("findAll{it.id>190}");
        System.out.println("getListByUsingGroovy = " + getListByUsingGroovy);

//Assert that there are 10 ids greater than 190
        assertEquals(10, getListByUsingGroovy.size());

        //findAll{it.id>190}.id-->just add .id to get just the ids
//        System.out.println("\ngroovy language to get the ids greater than 190\n " + jsonPath.getList("findAll{it.id>190}.id"));
        List<Integer> getIdsByUsingGroovy= jsonPath.getList("findAll{it.id>190}.id");
        System.out.println("getIdsByUsingGroovy = " + getIdsByUsingGroovy);
        assertEquals(10, getIdsByUsingGroovy.size());

//3.Print all userIds whose ids are less than 5 on the console

        //findAll{it.id>190}.userId-->just add .userId to get just the userIds
//        System.out.println("\ngroovy language to get the userIds in the list whose the ids greater than 190\n " + jsonPath.getList("findAll{it.id>190}.userId"));
        List<String> getUserIdUsingGroovy = jsonPath.getList("findAll{it.id<5}.userId");
        System.out.println("getUserIdUsingGroovy = " + getUserIdUsingGroovy);

//Assert that number of userIds whose ids are less than 5 is 4
        assertEquals(4, getUserIdUsingGroovy.size());

        //findAll{it.id>190}.userId-->just add .completed to get just the completeds
//        System.out.println("\ngroovy language to get the completeds in the list whose the ids greater than 190\n " + jsonPath.getList("findAll{it.id>190}.completed"));
        List<String> getCompletedUsingGroovy = jsonPath.getList("findAll{it.id>190}.completed");
        System.out.println("getCompletedUsingGroovy = " + getCompletedUsingGroovy);
        assertEquals(10, getCompletedUsingGroovy.size());

//4.Print all titles whose ids are less than 5
        //findAll{it.id<5}.title-->just add .title to get just the titles
//        System.out.println("\ngroovy language to get title in the list whose the ids greater than 190\n " + jsonPath.getList("findAll{it.id>190}.title"));
        List<String> getTitleByUsingGroovy =jsonPath.getList("findAll{it.id<5}.title");
        System.out.println("getTitleByUsingGroovy = " + getTitleByUsingGroovy);

//Assert that "delectus aut autem" is one of the titles whose ids is less than 5
        assertTrue(getTitleByUsingGroovy.contains("delectus aut autem"));

    }
}
