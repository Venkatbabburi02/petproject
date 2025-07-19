package api.endpoints;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class Userendpoints {
	
	public static Response createuser(User payload)
	{
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(payload)
		.when()
		.post(Routes.posturl);
		return res;
		
	}
	public static Response getuser(String username)
	{
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username", username)
		.when()
		.get(Routes.geturl);
		return res;
		
	}
	public static Response updateuser(String username,User payload)
	{
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username",username)
		.body(payload)
		.when()
		.put(Routes.puturl);
		return res;
		
	}
	public static Response deleteuser(String username)
	{
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username",username)
		.when()
		.delete(Routes.deleteurl);
		return res;
		
	}

}
