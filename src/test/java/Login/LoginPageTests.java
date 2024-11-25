package Login;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Resources.base;
import setUpAndTearDown.SetAndDown;

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

	@Test(groups = "Valid", priority = 3)
	public void LoginWithValidCredentails() {
		System.out.println(driver.getTitle());
		assertTrue(LPO.UsernameDisplayed().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
		LPO.PassPassword().clear();
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
		LPO.ClickOnLogin().click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(LPO.DashboardPageWebElement()));
		assertEquals(driver.getTitle(), "Services");
		driver.close();

	}

	@Test(groups = "Invalid", priority = 3)
	public void LoginWithInValidCredentails() {
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
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

	@Test(groups = "Captcha1", priority = 3)
	public void LoginWithvalidCredentialsUsingRightCaptcha() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys("abc");
		LPO.PassPassword().sendKeys("abc");
		LPO.ClickOnLogin().click();

		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
		LPO.CaptchTextBox().sendKeys(LPO.GetCaptchaText().getText());
		LPO.ClickOnLogin().click();

		wait.until(ExpectedConditions.visibilityOf(LPO.DashboardPageWebElement()));
		assertEquals(driver.getTitle(), "Services");
		// driver.close();

	}

	@Test(groups = "Captcha2", priority = 3)
	public void LoginWithInvalidCredentialsUsingRightCaptcha() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys("abc");
		LPO.PassPassword().sendKeys("abc");
		LPO.ClickOnLogin().click();

		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		assertTrue(LPO.GetCaptchaText().isDisplayed());

		String FirstCaptcha = LPO.GetCaptchaText().getText();
		// System.out.println(FirstCaptcha);
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
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

	@Test(groups = "Captcha3", priority = 3)
	public void LoginWithvalidCredentialsUsingWrongCaptcha() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LPO.PassUsername().sendKeys("abc");
		LPO.PassPassword().sendKeys("abc");
		LPO.ClickOnLogin().click();
		System.out.println("test");

		wait.until(
				ExpectedConditions.attributeContains(LPO.InvalidCredentialsErrorMessage(), "style", "display: block;"));
		// assertEquals(LPO.InvalidCredentialsErrorMessage().getAttribute("style"),
		// "display: block;");
		assertEquals(LPO.InvalidCredentialsErrorMessage().getText().trim(), "Invalid Username or Password!");
		assertTrue(LPO.GetCaptchaText().isDisplayed());
		String FirstCaptcha = LPO.GetCaptchaText().getText();
		LPO.PassUsername().clear();
		LPO.PassUsername().sendKeys(prop.getProperty("username"));
		LPO.PassPassword().sendKeys(prop.getProperty("password"));
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
}
