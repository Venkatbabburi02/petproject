package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.Userendpoints;
import api.payloads.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class TTDtests {
	
	
	@Test(priority=1, dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testpostuser(String userid,String username, String fname,String lname,String email, String pwd, String ph)
	{
		User userpayload=new User();
		userpayload.setId(Integer.parseInt(userid));
		userpayload.setUsername(username);
		userpayload.setFirstname(fname);
		userpayload.setLastname(lname);
		userpayload.setEmail(email);
		userpayload.setPassword(pwd);
		userpayload.setPhone(ph);
		
		Response res=Userendpoints.createuser(userpayload);
		Assert.assertEquals(res.getStatusCode(),200);
		
	}
	@Test(priority=2, dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testgetuser(String username)
	{
		Response res=Userendpoints.deleteuser(username);
		Assert.assertEquals(res.getStatusCode(),200);
	}

}
