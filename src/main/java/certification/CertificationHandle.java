package certification;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CertificationHandle {
	public WebDriver driver;
	private WebDriverWait wait;
	public CertificationHandle(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
		
	}
	@FindBy(xpath = "//*[@id='showDetails']")
	private WebElement certification;
	@FindBy(css = "#spoiler > p.url-section-falsepositive > a")
	private WebElement certification2;
	public void cert() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		long startTime = System.currentTimeMillis();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(certification));
			clicks();
		} catch (TimeoutException e) {
			long endTime = System.currentTimeMillis();
			//System.out.println("Exception caught after: " + (endTime - startTime) + " ms");
		}
		}
	
	public void clicks()
	{
		certification.click();
		certification2.click();
	}
}
