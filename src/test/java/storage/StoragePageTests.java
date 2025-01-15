package storage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Login.LoginPageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class StoragePageTests extends SetAndDown {
	@Test(groups = "CertAndLogin", priority = 1)
	public void CertAndLogin() throws IOException {
		LoginPageTests LPT = new LoginPageTests();
		LPT.TestLoginWithValidCredentails(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	@Test(priority = 2,dependsOnMethods = "CertAndLogin",alwaysRun = false,groups = "Storage")
	public void TestResourceManagementOption()
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(SPO.ResourceManagementOption()));
		SPO.ResourceManagementOption().click();
		
	}
	@Test(priority = 3,dependsOnMethods = "TestResourceManagementOption",alwaysRun = false,groups = "Storage")
	public void TestStorageOtion()
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(SPO.StorageOptionButton()));
		SPO.StorageOptionButton().click();
		wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Services")));
		assertEquals(driver.getTitle(), "Storage");
	}
	@Test(priority = 4,groups = "Storage",dependsOnMethods = "TestStorageOtion",alwaysRun = false)
	public void TestAddNewStorageButton()
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(SPO.AddnewStorageButton()));
		wait.until(ExpectedConditions.elementToBeClickable(SPO.AddnewStorageButton()));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SPO.AddnewStorageButton().click();
		wait.until(ExpectedConditions.visibilityOf(SPO.AddStorageHeading()));
	}
	@Test(priority = 5,groups = "Storage",dependsOnMethods = "TestAddNewStorageButton",alwaysRun = false,dataProvider = "dataProvider")
	public void TestZoneDropdown(String ZoneName)
	{
		assertTrue(
				SPO.SelectZone().getOptions().stream().anyMatch(option -> option.getText().contains(ZoneName)),
				"Given Value "+ZoneName+" is not available in the dropdown");
		SPO.SelectZone().selectByVisibleText(ZoneName);
		assertEquals(SPO.SelectZone().getFirstSelectedOption().getText(), ZoneName);
		
	}
	@Test(priority = 6,groups = "Storage",dependsOnMethods = "TestZoneDropdown",alwaysRun = false,dataProvider = "dataProvider")
	public void TestLinuxOrWindowsRadioButton(String OSType)
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JavascriptExecutor js=(JavascriptExecutor)driver;
		if(OSType.equalsIgnoreCase("Linux"))
		js.executeScript("arguments[0].click();", SPO.SelectLinuxRadioButton());
		if(OSType.equalsIgnoreCase("Windows"))
			js.executeScript("arguments[0].click();", SPO.SelectWindowsRadioButton());
		//SPO.SelectLinuxRadioButton().click();
		
	}
	@Test(priority = 7,groups = "Storage",dependsOnMethods = "TestZoneDropdown",alwaysRun = false,dataProvider = "dataProvider")
	public void TestTypeDropdown(String Type)
	{
		assertTrue(
				SPO.SelectType().getOptions().stream().anyMatch(option -> option.getText().contains(Type)),
				"Given Value "+Type+" is not available in the dropdown");
		SPO.SelectType().selectByVisibleText(Type);
		assertEquals(SPO.SelectType().getFirstSelectedOption().getText(), Type);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("TestZoneDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"Zone");
		}
		if (method.getName().equals("TestLinuxOrWindowsRadioButton")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"OSType");
		}
		if (method.getName().equals("TestTypeDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"Types");
		}
		return null;
		}
}
