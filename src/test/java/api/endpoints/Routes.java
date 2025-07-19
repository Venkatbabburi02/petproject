package api.endpoints;

/* 
Swagger URI --> https://petstore.swagger.io

Create user(Post) : https://petstore.swagger.io/v2/user
Get user (Get): https://petstore.swagger.io/v2/user/{username}
Update user (Put) : https://petstore.swagger.io/v2/user/{username}
Delete user (Delete) : https://petstore.swagger.io/v2/user/{username}

*/

public class Routes {
	
	public static String baseurl="https://petstore.swagger.io/v2";
	
	public static String posturl=baseurl+"/user";
	
	public static String geturl=baseurl+"/user/{username}";

	public static String puturl=baseurl+"/user/{username}";
	
	public static String deleteurl=baseurl+"/user/{username}";



}
