package setUpAndTearDown;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import Login.LoginPageObjects;
import Resources.Base1;

public class SetAndDown extends Base1 {
	public static WebDriver driver;
	public static Properties prop;
	public static FileInputStream fis;
	public static LoginPageObjects LPO;

	@BeforeSuite
	public void DriverIntilization() throws IOException {
		prop = new Properties();
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\data.properties");
		prop.load(fis);
		driver = InitializeDriver();
		LPO = new LoginPageObjects(driver);
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void TestOpenLoginPage() {
		driver.get(prop.getProperty("url"));
		LPO.cert();
	}
}
