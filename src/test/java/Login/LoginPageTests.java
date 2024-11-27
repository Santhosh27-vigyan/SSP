package Login;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class LoginPageTests extends SetAndDown {
	
	@Test(groups = "TestValidUserName")
	public void TestValidUserName() {
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
	}

	@Test(groups = "TestEmptyUserName")
	public void TestEmptyUserName() {
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().sendKeys("");
		LPO.ClickOnLogin().click();
		assertEquals(LPO.UserNameError().getText(), "Username or Email is required!");
	}

	@Test(groups = "TestValidPassword")
	public void TestValidPassword() {
		LPO.PassPassword().clear();
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
	}

	@Test(groups = "TestEmptyPassword")
	public void TestEmptyPassword() {
		LPO.PassPassword().sendKeys("");
		LPO.ClickOnLogin().click();
		assertEquals(LPO.PasswordError().getText(), "Password is required!");
	}

	@Test(groups = "TestLoginWithValidCredentails", dataProvider = "dataProvider")
	public void TestLoginWithValidCredentails(String username, String Password) {
		
		System.out.println(driver.getTitle());
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(username);
		LPO.PassPassword().clear();
		LPO.PassPassword().sendKeys(Password);
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOf(LPO.DashboardPageWebElement()));
		} catch (Exception e) {
		}
		assertEquals(driver.getTitle(), "Services");
		// wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("IPM+ Cloud|Log
		// In")));
		// driver.close();
	}

	@Test(groups = "TestLoginWithInValidCredentails", dataProvider = "dataProvider")
	public void TestLoginWithInValidCredentails(String username, String Password) throws IOException {
		LPO.PassUsername().sendKeys(username);
		LPO.PassPassword().sendKeys(Password);
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		try {
			wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		} catch (Exception e) {
		}
		assertTrue(LPO.GetCaptchaText().isDisplayed(), "Captch Should be displayed");
		// driver.close();
	}

	@Test(groups = "TestLoginWithvalidCredentialsUsingRightCaptcha", dataProvider = "dataProvider")
	public void TestLoginWithvalidCredentialsUsingRightCaptcha(String Wrongusername, String WrongPassword,
			String Rightusername, String RightPassword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys(Wrongusername);
		LPO.PassPassword().sendKeys(WrongPassword);
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		try {
			wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		} catch (Exception e) {
		}
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(Rightusername);
		LPO.PassPassword().sendKeys(RightPassword);
		LPO.CaptchTextBox().sendKeys(LPO.GetCaptchaText().getText());
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		try {
			wait.until(ExpectedConditions.visibilityOf(LPO.DashboardPageWebElement()));
		} catch (Exception e) {
		}
		assertEquals(driver.getTitle(), "Services");
		// driver.close();
	}

	@Test(groups = "TestLoginWithInvalidCredentialsUsingRightCaptcha", dataProvider = "dataProvider")
	public void TestLoginWithInvalidCredentialsUsingRightCaptcha(String Wrongusername, String WrongPassword,
			String Rightusername, String RightPassword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys(Wrongusername);
		LPO.PassPassword().sendKeys(WrongPassword);
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		try {
			wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		} catch (Exception e) {
		}
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		String FirstCaptcha = LPO.GetCaptchaText().getText();
		 System.out.println(FirstCaptcha);
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(Rightusername);
		LPO.PassPassword().sendKeys(RightPassword);
		LPO.CaptchTextBox().sendKeys(LPO.GetCaptchaText().getText());
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		try {
			wait.until(ExpectedConditions
					.not(ExpectedConditions.textToBePresentInElement(LPO.GetCaptchaText(), FirstCaptcha)));
		} catch (Exception e) {
		}
		String SecondCaptcha = LPO.GetCaptchaText().getText();
		 System.out.println(SecondCaptcha);
		assertFalse(FirstCaptcha.equals(SecondCaptcha), "Captch should be refreshed");
		// driver.close();
	}

	@Test(groups = "TestLoginWithvalidCredentialsUsingWrongCaptcha", dataProvider = "dataProvider")
	public void TestLoginWithvalidCredentialsUsingWrongCaptcha(String Wrongusername, String WrongPassword,
			String Rightusername, String RightPassword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys(Wrongusername);
		LPO.PassPassword().sendKeys(WrongPassword);
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		try {
			wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		} catch (Exception e) {
		}
		assertTrue(LPO.GetCaptchaText().isDisplayed(), "Captch Should be displayed");
		String FirstCaptcha = LPO.GetCaptchaText().getText();
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(Rightusername);
		LPO.PassPassword().sendKeys(RightPassword);
		LPO.CaptchTextBox().sendKeys("abcde");
		if (!LPO.TermsOfServiceCheckBox().isSelected())
			LPO.TermsOfServiceCheckBox().click();
		LPO.ClickOnLogin().click();
		wait.until(ExpectedConditions.attributeContains(LPO.InvalidCaptchaErrorMessage(), "style", "display: block;"));
		assertEquals(LPO.InvalidCaptchaErrorMessage().getText().trim(), "Incorrect Captcha");
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		try {
			wait.until(ExpectedConditions
					.not(ExpectedConditions.textToBePresentInElement(LPO.GetCaptchaText(), FirstCaptcha)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		String SecondCaptcha = LPO.GetCaptchaText().getText();
		// System.out.println(SecondCaptcha);
		assertFalse(FirstCaptcha.equals(SecondCaptcha), "Captch should be refreshed");
		// driver.close();
	}

	@Test(groups = "TestTermsOfServiceCheckBox")
	public void TestTermsOfServiceCheckBox() {
		if (LPO.TermsOfServiceCheckBox().isSelected()) {
			assertTrue(LPO.ClickOnLogin().isEnabled(), "Login Button Should be enabled");
		} else {
			assertFalse(LPO.ClickOnLogin().isEnabled(), "Login Button Should be disabled");
		}
	}

	@Test(groups = "TestForgotPasswordLink")
	public void TestForgotPasswordLink() {
		LPO.ClickOnForgotPasswordLink().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud | Log In")));
		assertEquals(driver.getTitle(), "IPM+ Cloud | Password Reset");
	}

	@Test(groups = "TestPasswordResetUsingEmptyEmail")
	public void TestPasswordResetUsingEmptyEmail() {
		TestForgotPasswordLink();
		LPO.PasswordResetSendLinkButton().click();
		assertEquals(LPO.EmptyEmailAddressError().getText(), "Email address is required");
	}

	@Test(groups = "TestPasswordResetUsingWrongEmail")
	public void TestPasswordResetUsingWrongEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		TestForgotPasswordLink();
		LPO.PasswordReserEmail().sendKeys("santhosh.cs@vigyanlabs.com");
		LPO.PasswordResetSendLinkButton().click();
		wait.until(ExpectedConditions.attributeContains(LPO.InvalidEmailErrorMessage(), "style", "display: block;"));
		assertEquals(LPO.InvalidEmailErrorMessage().getText().trim(), "Invalid Email Address");
	}

	@Test(groups = "TestPasswordResetUsingRightEmail")
	public void TestPasswordResetUsingRightEmail() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		TestForgotPasswordLink();
		LPO.PasswordReserEmail().sendKeys("test1@vipl.com");
		// System.out.println(driver.getTitle());
		LPO.PasswordResetSendLinkButton().click();
		wait.until(ExpectedConditions.attributeContains(LPO.PasswordrestLinkSuccessfullySentMessage(), "style",
				"display: block;"));
		assertEquals(LPO.PasswordrestLinkSuccessfullySentMessage().getText().trim(),
				"Password reset link has been sent successfully to test1@vipl.com");
		wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud | Password Reset")));
		assertEquals(driver.getTitle(), "IPM+ Cloud | Log In");
	}

	@Test(groups = "TestPasswordResetCancelButton")
	public void TestPasswordResetCancelButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		TestForgotPasswordLink();
		LPO.PasswordResetCancelButton().click();
		wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud | Password Reset")));
		assertEquals(driver.getTitle(), "IPM+ Cloud | Log In");
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
