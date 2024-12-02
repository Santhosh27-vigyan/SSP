package setUpAndTearDown;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Login.LoginPageObjects;
import Resources.Base1;
import certification.CertificationHandle;
import create.CreatePageObject;
import createResource.CreateResourcePageObjects;

public class SetAndDown extends Base1 {
	public static WebDriver driver;
	public static Properties prop;
	public static FileInputStream fis;
	public static LoginPageObjects LPO;
	public static CertificationHandle Ch;
	public static CreateResourcePageObjects CRPO;
	public static CreatePageObject CPO;

	@Parameters("ClassName")
	@BeforeSuite
	public void DriverIntilization(String ClassName) throws IOException {
		prop = new Properties();
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\data.properties");
		prop.load(fis);
		driver = InitializeDriver();
		// System.out.println(ClassName);
		LPO = new LoginPageObjects(driver);
		CRPO = new CreateResourcePageObjects(driver);
		if (ClassName.equalsIgnoreCase("CreatePageObjects"))
			CPO = new CreatePageObject(driver);
		Ch = new CertificationHandle(driver);
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void TestOpenLoginPage() {
		driver.get(prop.getProperty("url"));
		// LPO.cert();
		Ch.cert();
	}

	@AfterSuite
	public void TearDown() {
		if (driver != null) {
			driver.quit(); // Cleanly close the session
		}
	}
}
