package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*
 * int id;
	String username;
	String firstname;
	String lastname;
	String email;
	String password;
	String phone;
	int userstatus=0;
 */
import com.github.javafaker.Faker;

import api.endpoints.Userendpoints;
import api.endpoints.Userendpoints2;
import api.payloads.User;
import io.restassured.response.Response;

public class Usertests2 {
	
	Faker faker;
	User userpayload;
	public Logger logger;
	@BeforeClass
	public void setupdata()
	{
		faker=new Faker();
		userpayload=new User();
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger=LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1)
	public void testpostuser()
	{
		logger.debug("debug logs");
		logger.info("*****creating user *******");
		Response res=Userendpoints2.createuser(userpayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****user is created *******");
	}
	@Test(priority=2)
	public void testgetuser()
	{
		logger.info("*****getting user *******");
		Response res=Userendpoints2.getuser(this.userpayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		logger.info("*****user is retrived *******");
	}
	@Test(priority=3)
	public void testupdateuser()
	{
		logger.info("*****updating user *******");
		Response res=Userendpoints2.updateuser(this.userpayload.getUsername(),userpayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****updated user *******");
	}
	@Test(priority=4)
	public void testdeleteuser()
	{
		logger.info("*****deleting user *******");
		Response res=Userendpoints2.deleteuser(this.userpayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****deleted user *******");
	}

}
