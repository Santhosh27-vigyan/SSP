package allLoginTests;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Login.LoginPageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class ExecuteAllLoginTestAtOnce {
	SetAndDown SAD = new SetAndDown();
	LoginPageTests LPT = new LoginPageTests();
	String ClassName;
	@Parameters("ClassName")
	@Test(priority = 1)
	public void TestValidUserName(String ClassName) throws IOException {
		this.ClassName=ClassName;
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestValidUserName();
		SAD.TearDown();
	}
	
	@Test(priority = 2)
	public void TestEmptyUserName() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestEmptyUserName();
		SAD.TearDown();
	}
	
	@Test(priority = 3)
	public void TestValidPassword() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestValidPassword();
		SAD.TearDown();
	}
	
	@Test(priority = 4)
	public void TestEmptyPassword() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestEmptyPassword();
		SAD.TearDown();
	}
	
	@Test(priority = 5,dataProvider = "dataProvider")
	public void TestLoginWithInValidCredentails(String UserName,String Password) throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestLoginWithInValidCredentails(UserName, Password);
		SAD.TearDown();
	}
	
	@Test(priority = 6,dataProvider = "dataProvider")
	public void TestLoginWithValidCredentails(String UserName,String Password) throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestLoginWithValidCredentails(UserName, Password);
		SAD.TearDown();
	}
	
	@Test(priority = 7,dataProvider = "dataProvider")
	public void TestLoginWithInvalidCredentialsUsingRightCaptcha(String WrongUserName,String WrongPassword,String RightUserName,String RightPassword) throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestLoginWithInvalidCredentialsUsingRightCaptcha(WrongUserName,WrongPassword,RightUserName,RightPassword);
		SAD.TearDown();
	}
	
	@Test(priority = 8,dataProvider = "dataProvider")
	public void TestLoginWithvalidCredentialsUsingRightCaptcha(String WrongUserName,String WrongPassword,String RightUserName,String RightPassword) throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestLoginWithvalidCredentialsUsingRightCaptcha(WrongUserName,WrongPassword,RightUserName,RightPassword);
		SAD.TearDown();
	}
	
	@Test(priority = 9,dataProvider = "dataProvider")
	public void TestLoginWithvalidCredentialsUsingWrongCaptcha(String WrongUserName,String WrongPassword,String RightUserName,String RightPassword) throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestLoginWithvalidCredentialsUsingWrongCaptcha(WrongUserName,WrongPassword,RightUserName,RightPassword);
		SAD.TearDown();
	}
	
	@Test(priority = 10)
	public void TestTermsOfServiceCheckBox() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestTermsOfServiceCheckBox();
		SAD.TearDown();
	}
	
	@Test(priority = 11)
	public void TestForgotPasswordLink() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestForgotPasswordLink();
		SAD.TearDown();
	}
	
	@Test(priority = 12)
	public void TestPasswordResetUsingEmptyEmail() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestPasswordResetUsingEmptyEmail();
		SAD.TearDown();
	}
	
	@Test(priority = 13)
	public void TestPasswordResetUsingWrongEmail() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestPasswordResetUsingWrongEmail();
		SAD.TearDown();
	}
	
	@Test(priority = 14)
	public void TestPasswordResetUsingRightEmail() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestPasswordResetUsingRightEmail();
		SAD.TearDown();
	}
	
	@Test(priority = 15)
	public void TestPasswordResetCancelButton() throws IOException {
		SAD.DriverIntilization(ClassName);
		SAD.TestOpenLoginPage();
		LPT.TestPasswordResetCancelButton();
		SAD.TearDown();
	}
	
	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("TestLoginWithValidCredentails")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"Valid");
		}
		if (method.getName().equals("TestLoginWithInValidCredentails")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"Invalid");
		}
		if (method.getName().equals("TestLoginWithvalidCredentialsUsingRightCaptcha")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"ValidCredAndCaptcha");
		}
		if (method.getName().equals("TestLoginWithInvalidCredentialsUsingRightCaptcha")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"InvalidCredAndValidCaptcha");
		}
		if (method.getName().equals("TestLoginWithvalidCredentialsUsingWrongCaptcha")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"ValidCredAndInValidCaptcha");
		}
		return null;
	}
}
