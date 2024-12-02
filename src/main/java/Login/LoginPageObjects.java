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

	public LoginPageObjects(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);

	}

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
	@FindBy(id = "logincheck")
	private WebElement TermsOfService;
	@FindBy(linkText = "Forgot Your Password ?")
	private WebElement ForgotPasswordLink;
	@FindBy(id = "email")
	private WebElement PasswordResetEmailTextBox;
	@FindBy(xpath = "//button[text()='Send Link']")
	private WebElement PasswordResetSendLinkButton;
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement PasswordResetCancelButton;
	@FindBy(id = "email-error")
	private WebElement EmptyEmailIdError;
	@FindBy(xpath = "//div[contains(@class,'invalid-email-message')]")
	private WebElement InvalidEmailerror;
	@FindBy(xpath = "//div[contains(@class,'bg-success')]")
	private WebElement SuccessfullMessage;

	public WebElement UsernameDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(UsernameDisplay));
		return UsernameDisplay;
	}

	public WebElement PassUsername() {
		return Username;
	}

	public WebElement UserNameError() {
		return UserNameerror;
	}

	public WebElement PassPassword() {
		return Password;
	}

	public WebElement PasswordError() {
		return Passworderror;
	}

	public WebElement ClickOnLogin() {
		return Login;
	}

	public WebElement InvalidCredentialsErrorMessage() {
		return InvalidCredentialserror;
	}

	public WebElement DashboardPageWebElement() {
		return DashboardPageElement;
	}

	public WebElement GetCaptchaText() {
		return CaptchaText;
	}

	public WebElement CaptchTextBox() {
		return CaptchaTextbox;
	}

	public WebElement InvalidCaptchaErrorMessage() {
		return InvalidCaptchaerror;
	}

	public WebElement TermsOfServiceCheckBox() {
		return TermsOfService;
	}

	public WebElement ClickOnForgotPasswordLink() {
		return ForgotPasswordLink;
	}

	public WebElement PasswordReserEmail() {
		return PasswordResetEmailTextBox;
	}

	public WebElement PasswordResetSendLinkButton() {
		return PasswordResetSendLinkButton;
	}

	public WebElement PasswordResetCancelButton() {
		return PasswordResetCancelButton;
	}

	public WebElement EmptyEmailAddressError() {
		return EmptyEmailIdError;
	}

	public WebElement InvalidEmailErrorMessage() {
		return InvalidEmailerror;
	}

	public WebElement PasswordrestLinkSuccessfullySentMessage() {
		return SuccessfullMessage;
	}
}
