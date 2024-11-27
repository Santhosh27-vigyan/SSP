package test;

import org.testng.annotations.Test;

import Login.LoginPageTests;

public class Demo {
	@Test
	public void test()
	{
		LoginPageTests Lpo=new LoginPageTests();
		Lpo.TestLoginWithValidCredentails("test", "test");
	}

}
