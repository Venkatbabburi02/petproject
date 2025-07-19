package api.endpoints;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class Userendpoints2 {
	static ResourceBundle geturl()
	{
		ResourceBundle routes=ResourceBundle.getBundle("routes");
		return routes;
	}
	
	
	public static Response createuser(User payload)
	{
		String post_url=geturl().getString("posturl");
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(payload)
		.when()
		.post(post_url);
		return res;
		
	}
	public static Response getuser(String username)
	{
		String get_url=geturl().getString("geturl");
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username", username)
		.when()
		.get(get_url);
		return res;
		
	}
	public static Response updateuser(String username,User payload)
	{
		String update_url=geturl().getString("updateurl");
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username",username)
		.body(payload)
		.when()
		.put(update_url);
		return res;
		
	}
	public static Response deleteuser(String username)
	{
		String delete_url=geturl().getString("deleteurl");
		 Response res=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.pathParam("username",username)
		.when()
		.delete(delete_url);
		return res;
		
	}

}
