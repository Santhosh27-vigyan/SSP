package Login;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObjects {
	public WebDriver driver;
	private WebDriverWait wait;
	public LoginPageObjects(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
		
	}
	@FindBy(xpath = "//*[@id='showDetails']")
	private WebElement certification;
	@FindBy(css = "#spoiler > p.url-section-falsepositive > a")
	private WebElement certification2;
	@FindBy(xpath = "//label[@for='username']")
	private WebElement UsernameDisplay;
	@FindBy(id = "username")
	private WebElement Username;
	@FindBy(id = "username-error")
	private WebElement UserNameerror;
	@FindBy(id = "password-error")
	private WebElement Passworderror;
	@FindBy(id = "password")
	private WebElement Password;
	@FindBy(id = "loginBtn")
	private WebElement Login;
	@FindBy(xpath = "//div[contains(@class,'invalid-credentials')]")
	private WebElement InvalidCredentialserror;
	@FindBy(id = "home_menu_item")
	private WebElement DashboardPageElement;
	@FindBy(id = "captcha_text")
	private WebElement CaptchaText;
	@FindBy(id = "captcha")
	private WebElement CaptchaTextbox;
	@FindBy(xpath = "//div[contains(@class,'invalid-captcha')]")
	private WebElement InvalidCaptchaerror;
	public void cert() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		long startTime = System.currentTimeMillis();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(certification));
			clicks();
		} catch (TimeoutException e) {
			long endTime = System.currentTimeMillis();
			System.out.println("Exception caught after: " + (endTime - startTime) + " ms");
		}
		}
	
	public void clicks()
	{
		certification.click();
		certification2.click();
	}
	public WebElement UsernameDisplayed()
	{
		wait.until(ExpectedConditions.visibilityOf(UsernameDisplay));
		return UsernameDisplay;
	}
	public WebElement PassUsername() {
		return Username;
	}
	public WebElement UserNameError()
	{
		return UserNameerror;
	}
	public WebElement PassPassword()
	{
		return Password;
	}
	public WebElement PasswordError()
	{
		return Passworderror;
	}
	public WebElement ClickOnLogin()
	{
		return Login;
	}
	public WebElement InvalidCredentialsErrorMessage()
	{
		return InvalidCredentialserror;
	}
	public WebElement DashboardPageWebElement()
	{
		return DashboardPageElement;
	}
	public WebElement GetCaptchaText()
	{
		return CaptchaText;
	}
	public WebElement CaptchTextBox()
	{
		return CaptchaTextbox;
	}
	public WebElement InvalidCaptchaErrorMessage()
	{
		return InvalidCaptchaerror;
	}
}
