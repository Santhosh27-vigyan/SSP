package Login;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class LoginPageTests extends SetAndDown {
	@Test(groups = "ValidUsername", priority = 1)
	public void TestValidUserName() {
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
	}

	@Test(groups = "empty", priority = 1)
	public void TestEmptyUserName() {
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
		LPO.ClickOnLogin().click();
		assertEquals(LPO.UserNameError().getText(), "Username or Email is required!");
	}

	@Test(groups = "ValidPasssword", priority = 2)
	public void TestValidPassword() {
		LPO.PassPassword().clear();
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
	}

	@Test(groups = "empty", priority = 2)
	public void TestEmptyPassword() {
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
		LPO.ClickOnLogin().click();
		assertEquals(LPO.PasswordError().getText(), "Password is required!");
	}

	@Test(groups = "LoginWithValidCredentails", priority = 3, dataProvider = "dataProvider")
	public void LoginWithValidCredentails(String username, String Password) {
		System.out.println(driver.getTitle());
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(username);
		LPO.PassPassword().clear();
		LPO.PassPassword().sendKeys(Password);
		LPO.ClickOnLogin().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(LPO.DashboardPageWebElement()));
		// wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("IPM+ Cloud|Log
		// In")));
		assertEquals(driver.getTitle(), "Services");
		// driver.close();
	}

	@Test(groups = "LoginWithInValidCredentails", priority = 3, dataProvider = "dataProvider")
	public void LoginWithInValidCredentails(String username, String Password) {
		LPO.PassUsername().sendKeys(username);
		LPO.PassPassword().sendKeys(Password);
		LPO.ClickOnLogin().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		// driver.close();
	}

	@Test(groups = "LoginWithvalidCredentialsUsingRightCaptcha", priority = 3, dataProvider = "dataProvider")
	public void LoginWithvalidCredentialsUsingRightCaptcha(String Wrongusername, String WrongPassword,
			String Rightusername, String RightPassword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys(Wrongusername);
		LPO.PassPassword().sendKeys(WrongPassword);
		LPO.ClickOnLogin().click();
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(Rightusername);
		LPO.PassPassword().sendKeys(RightPassword);
		LPO.CaptchTextBox().sendKeys(LPO.GetCaptchaText().getText());
		LPO.ClickOnLogin().click();
		wait.until(ExpectedConditions.visibilityOf(LPO.DashboardPageWebElement()));
		assertEquals(driver.getTitle(), "Services");
		// driver.close();
	}

	@Test(groups = "LoginWithInvalidCredentialsUsingRightCaptcha", priority = 3, dataProvider = "dataProvider")
	public void LoginWithInvalidCredentialsUsingRightCaptcha(String Wrongusername, String WrongPassword,
			String Rightusername, String RightPassword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys(Wrongusername);
		LPO.PassPassword().sendKeys(WrongPassword);
		LPO.ClickOnLogin().click();
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		String FirstCaptcha = LPO.GetCaptchaText().getText();
		// System.out.println(FirstCaptcha);
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(Rightusername);
		LPO.PassPassword().sendKeys(RightPassword);
		LPO.CaptchTextBox().sendKeys(LPO.GetCaptchaText().getText());
		LPO.ClickOnLogin().click();
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		wait.until(ExpectedConditions
				.not(ExpectedConditions.textToBePresentInElement(LPO.GetCaptchaText(), FirstCaptcha)));
		String SecondCaptcha = LPO.GetCaptchaText().getText();
		// System.out.println(SecondCaptcha);
		assertFalse(FirstCaptcha.equals(SecondCaptcha));
		// driver.close();
	}

	@Test(groups = "LoginWithvalidCredentialsUsingWrongCaptcha", priority = 3, dataProvider = "dataProvider")
	public void LoginWithvalidCredentialsUsingWrongCaptcha(String Wrongusername, String WrongPassword,
			String Rightusername, String RightPassword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys(Wrongusername);
		LPO.PassPassword().sendKeys(WrongPassword);
		LPO.ClickOnLogin().click();
		System.out.println("test");
		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		wait.until(ExpectedConditions.visibilityOf(LPO.GetCaptchaText()));
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		String FirstCaptcha = LPO.GetCaptchaText().getText();
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(Rightusername);
		LPO.PassPassword().sendKeys(RightPassword);
		LPO.CaptchTextBox().sendKeys("abcde");
		LPO.ClickOnLogin().click();
		wait.until(ExpectedConditions.attributeContains(LPO.InvalidCaptchaErrorMessage(), "style", "display: block;"));
		assertEquals(LPO.InvalidCaptchaErrorMessage().getText().trim(), "Incorrect Captcha");
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		wait.until(ExpectedConditions
				.not(ExpectedConditions.textToBePresentInElement(LPO.GetCaptchaText(), FirstCaptcha)));
		String SecondCaptcha = LPO.GetCaptchaText().getText();
		// System.out.println(SecondCaptcha);
		assertFalse(FirstCaptcha.equals(SecondCaptcha));
		// driver.close();
	}

	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("LoginWithValidCredentails")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"Valid");
		}
		if (method.getName().equals("LoginWithInValidCredentails")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"Invalid");
		}
		if (method.getName().equals("LoginWithvalidCredentialsUsingRightCaptcha")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"ValidCredAndCaptcha");
		}
		if (method.getName().equals("LoginWithInvalidCredentialsUsingRightCaptcha")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"InvalidCredAndValidCaptcha");
		}
		if (method.getName().equals("LoginWithvalidCredentialsUsingWrongCaptcha")) {
			return ExcelUtils.GetExcelData(System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Data.xlsx",
					"ValidCredAndInValidCaptcha");
		}
		return null;
	}
}
