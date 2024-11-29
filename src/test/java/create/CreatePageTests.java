package create;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Login.LoginPageTests;
import createResource.CreateResourcePageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class CreatePageTests extends SetAndDown {
	@Test(groups = "CertAndLogin", priority = 1)
	public void CertAndLogin() throws IOException {
		LoginPageTests LPT = new LoginPageTests();
		LPT.TestLoginWithValidCredentails(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(groups = "ClickOnCreateReource", priority = 2, dataProvider = "dataProvider", dependsOnMethods = "CertAndLogin", alwaysRun = false)
	public void ClickOnCreateReource(String ResourceName) {
		CreateResourcePageTests CRPT = new CreateResourcePageTests();
		CRPT.TestCreateReource(ResourceName);
	}

	@Test(groups = "TestLocationDropdown", priority = 3, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLocationDropdown(String LocationName) {
		CPO.SelectLocation().selectByVisibleText(LocationName);
		assertEquals(CPO.SelectLocation().getFirstSelectedOption().getText(), LocationName);
	}

	@Test(groups = "TestProjectNameDropdown", priority = 4, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestProjectNameDropdown(String ProjectName) {
		// System.out.println(CPO.SelectProjectName().getOptions().size());
		CustomeWait();
		// System.out.println(CPO.SelectProjectName().getOptions().size());
		CPO.SelectProjectName().selectByVisibleText(ProjectName);
		assertEquals(CPO.SelectProjectName().getFirstSelectedOption().getText(), ProjectName);
	}

	@Test(groups = "TestStoragePathDropdown", priority = 5, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestStoragePathDropdown(String StoragePathName) {
		// System.out.println(CPO.SelectStoragePath().getOptions().size());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				return CPO.SelectStoragePath().getOptions().stream()
						.anyMatch(option -> option.getText().contains(StoragePathName)); // Check if the size is 7
			}
		});
		try {
			CPO.SelectStoragePath().selectByVisibleText(StoragePathName);
		} catch (StaleElementReferenceException e) {
			CPO.SelectStoragePath().selectByVisibleText(StoragePathName);
		}

		assertEquals(CPO.SelectStoragePath().getFirstSelectedOption().getText(), StoragePathName);
	}

	@Test(groups = "TestOSDropdown", priority = 6, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestOSDropdown(String OSName, String StoragePathName) {
		CustomeWait();
		// System.out.println(CPO.SelectStoragePath().getOptions().size());
		TestStoragePathDropdown(StoragePathName);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {

				return CPO.SelectOS().getOptions().stream().anyMatch(option -> option.getText().contains(OSName));
			}
		});
		CPO.SelectOS().selectByVisibleText(OSName);
		assertEquals(CPO.SelectOS().getFirstSelectedOption().getText(), OSName);
	}

	@Test(groups = "TestIOPSDropdown", priority = 7, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestIOPSDropdown(String IOPSValue) {
		CPO.SelectIops().selectByVisibleText(IOPSValue);
		assertEquals(CPO.SelectIops().getFirstSelectedOption().getText(), IOPSValue);
	}

	@Test(groups = "TestHorizontalSwitchOn", priority = 8, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestHorizontalSwitchOn() {
		if (!CPO.VerticalSwitchDiv().getAttribute("style").equals("display: none;")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", CPO.HorizonantalSwitch());
			assertFalse(!CPO.VerticalSwitchDiv().getAttribute("style").equals("display: none;"),
					"Vertical Scaling Switch option is still available");
		} else {
			System.out.println("Horizontal Switch is already turned on");
		}
	}

	@Test(groups = "TestHorizontalSwitchOff", priority = 9, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestHorizontalSwitchOff() {

		if (CPO.VerticalSwitchDiv().getAttribute("style").equals("display: none;")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", CPO.HorizonantalSwitch());
			assertTrue(!CPO.VerticalSwitchDiv().getAttribute("style").equals("display: none;"),
					"Vertical Scaling Switch option is not available");
		} else {
			System.out.println("Horizontal Switch is already turned off");
		}
	}

	@Test(groups = "TestVerticalSwitchOn", priority = 10, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVerticalSwitchOn() {
		if (!CPO.HorizonantalSwitchDiv().getAttribute("style").equals("display: none;")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", CPO.VerticalSwitch());
			assertFalse(!CPO.HorizonantalSwitchDiv().getAttribute("style").equals("display: none;"),
					"Horizontal Scaling Switch option is still available");
		} else {
			System.out.println("Vertical Switch is already turned on");
		}
	}

	@Test(groups = "TestVerticalSwitchOff", priority = 11, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVerticalSwitchOff() {
		if (CPO.HorizonantalSwitchDiv().getAttribute("style").equals("display: none;")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", CPO.VerticalSwitch());
			assertTrue(!CPO.HorizonantalSwitchDiv().getAttribute("style").equals("display: none;"),
					"Horizontal Scaling Switch option is not available");
		} else {
			System.out.println("Vertical Switch is already turned off");
		}
	}

	@Test(groups = "TestSavedTemplatesRadioButton", priority = 12, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestSavedTemplatesRadioButton() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		assertTrue(!CPO.HorizonantalSwitchDiv().getAttribute("style").equals("display: none;"),
				"Horizontal Scaling is selected So Custom Radio button cannot be used");
		assertTrue(!CPO.VerticalSwitchDiv().getAttribute("style").equals("display: none;"),
				"Vertical Scaling is selected So Custom Radio button cannot be used");
		js.executeScript("arguments[0].click();", CPO.SavedTemplatesRadioButton());
		assertTrue(!CPO.SelectTemplateDropdownDiv().getAttribute("style").equals("display: none;"),
				"Saved Templates Option is not selected");
	}

	@Test(groups = "TestCustomRadioButton", priority = 13, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestCustomRadioButton() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		assertTrue(!CPO.HorizonantalSwitchDiv().getAttribute("style").equals("display: none;"),
				"Horizontal Scaling is selected So Custom Radio button cannot be used");
		assertTrue(!CPO.VerticalSwitchDiv().getAttribute("style").equals("display: none;"),
				"Vertical Scaling is selected So Custom Radio button cannot be used");
		js.executeScript("arguments[0].click();", CPO.CustomRadioButton());
		assertTrue(CPO.CustomSelectorDiv().getAttribute("style").equals("display: block;"),
				"Custom Option is not selected");
	}
	@Test(groups = "TestLowerThreshold", priority = 14, dataProvider = "dataProvider",  dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLowerThreshold(String LowerThresholdValue) {
		TestHorizontalSwitchOn();
		assertTrue(CPO.SelectOS().getOptions().stream().anyMatch(option -> option.getText().contains(LowerThresholdValue)),"Given Value is not available in the dropdown");
		CPO.LowerThreshold().selectByValue(LowerThresholdValue);
		assertEquals(CPO.LowerThreshold().getFirstSelectedOption().getText(),LowerThresholdValue);
	}

	public void CustomeWait() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				List<WebElement> options = CPO.SelectProjectName().getOptions(); // Get all options in the dropdown
				return !options.get(0).getText().contains("Select"); // Check if the size is 7
			}
		});
	}

	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("ClickOnCreateReource")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"ResourceNameToCreate");
		}
		if (method.getName().equals("TestLocationDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "Location");
		}
		if (method.getName().equals("TestProjectNameDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "ProjectName");
		}
		if (method.getName().equals("TestStoragePathDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"StoragePathName");
		}
		if (method.getName().equals("TestOSDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "OSName");
		}
		if (method.getName().equals("TestIOPSDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "IOPSValue");
		}
		if (method.getName().equals("TestLowerThreshold")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "IOPSValue");
		}
		return null;
	}
}
