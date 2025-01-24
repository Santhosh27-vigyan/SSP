package storage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Login.LoginPageTests;
import create.CreatePageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class StoragePageTests extends SetAndDown {
	@Test(groups = "CertAndLogin", priority = 1)
	public void CertAndLogin() throws IOException {
		LoginPageTests LPT = new LoginPageTests();
		LPT.TestLoginWithValidCredentails(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 2, dependsOnMethods = "CertAndLogin", alwaysRun = false, groups = { "Storage" })
	public void TestResourceManagementOption() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(SPO.ResourceManagementOption()));
		SPO.ResourceManagementOption().click();
	}

	@Test(priority = 3, dependsOnMethods = "TestResourceManagementOption", alwaysRun = false, groups = { "Storage" })
	public void TestStorageOtion() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(SPO.StorageOptionButton()));
		SPO.StorageOptionButton().click();
		wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Services")));
		assertEquals(driver.getTitle(), "Storage");
	}

	@Test(priority = 4, groups = { "Storage" }, dependsOnMethods = "TestStorageOtion", alwaysRun = false)
	public void TestAddNewStorageButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

	@Test(priority = 5, groups = {
			"Positive" }, dependsOnMethods = "TestAddNewStorageButton", alwaysRun = false, dataProvider = "dataProvider")
	public void TestZoneDropdown(String ZoneName) {
		assertTrue(SPO.SelectZone().getOptions().stream().anyMatch(option -> option.getText().contains(ZoneName)),
				"Given Value " + ZoneName + " is not available in the dropdown");
		SPO.SelectZone().selectByVisibleText(ZoneName);
		assertEquals(SPO.SelectZone().getFirstSelectedOption().getText(), ZoneName);
	}

	@Test(priority = 6, groups = {
			"Positive" }, dependsOnMethods = "TestZoneDropdown", alwaysRun = false, dataProvider = "dataProvider")
	public void TestLinuxOrWindowsRadioButton(String OSType) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (OSType.equalsIgnoreCase("Linux"))
			js.executeScript("arguments[0].click();", SPO.SelectLinuxRadioButton());
		if (OSType.equalsIgnoreCase("Windows"))
			js.executeScript("arguments[0].click();", SPO.SelectWindowsRadioButton());
		// SPO.SelectLinuxRadioButton().click();
	}

	@Test(priority = 7, groups = {
			"Positive" }, dependsOnMethods = "TestZoneDropdown", alwaysRun = false, dataProvider = "dataProvider")
	public void TestTypeDropdown(String Type) {
		assertTrue(SPO.SelectType().getOptions().stream().anyMatch(option -> option.getText().contains(Type)),
				"Given Value " + Type + " is not available in the dropdown");
		SPO.SelectType().selectByVisibleText(Type);
		assertEquals(SPO.SelectType().getFirstSelectedOption().getText(), Type);
	}

	@Test(priority = 8, groups = {
			"Positive" }, dependsOnMethods = "TestZoneDropdown", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStoragePathTextBoxWithValidInputs(String PathName) {
		SPO.StorageName().sendKeys(PathName);
		Actions a = new Actions(driver);
		a.moveToElement(SPO.TotalSizeSlider()).click().build().perform();
		String error = null;
		String error1 = null;
		if (SPO.ErrorInStorageName().size() > 0)
			error = SPO.ErrorInStorageName().get(0).getText();
		assertTrue(SPO.ErrorInStorageName().size() == 0, error);
		if (SPO.NotAvailableMessage().size() > 0)
			error1 = SPO.NotAvailableMessage().get(0).getText();
		if (SPO.AvailableMessage().size() > 0)
			assertTrue(SPO.AvailableMessage().get(0).getText().equals("Available"));
		else
			assertTrue(SPO.AvailableMessage().size() > 0, error1);
	}

	@Test(priority = 9, groups = {
			"Negative" }, dependsOnMethods = "TestAddNewStorageButton", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStoragePathTextBoxWithEmptyInput(String PathName) {
		SPO.StorageName().clear();
		SPO.StorageName().sendKeys(PathName);
		Actions a = new Actions(driver);
		a.moveToElement(SPO.TotalSizeSlider()).click().build().perform();
		assertEquals(SPO.ErrorInStorageName().get(0).getText(), "Storage Display Name is required!");
	}

	@Test(priority = 10, groups = {
			"Negative" }, dependsOnMethods = "TestAddNewStorageButton", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStoragePathTextBoxWithlessThan5Characters(String PathName) {
		SPO.StorageName().clear();
		SPO.StorageName().sendKeys(PathName);
		Actions a = new Actions(driver);
		a.moveToElement(SPO.TotalSizeSlider()).click().build().perform();
		assertEquals(SPO.ErrorInStorageName().get(0).getText(), "Please enter at least 5 characters.");
	}

	@Test(priority = 11, groups = {
			"Negative" }, dependsOnMethods = "TestAddNewStorageButton", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStoragePathTextBoxWithMoreThanThan30Characters(String PathName) {
		SPO.StorageName().clear();
		SPO.StorageName().sendKeys(PathName);
		Actions a = new Actions(driver);
		a.moveToElement(SPO.TotalSizeSlider()).click().build().perform();
		assertEquals(SPO.ErrorInStorageName().get(0).getText(), "Please enter no more than 30 characters.");
	}

	@Test(priority = 12, groups = {
			"Negative" }, dependsOnMethods = "TestAddNewStorageButton", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStoragePathTextBoxWithExistingName(String PathName) {
		SPO.StorageName().clear();
		SPO.StorageName().sendKeys(PathName);
		Actions a = new Actions(driver);
		a.moveToElement(SPO.TotalSizeSlider()).click().build().perform();
		assertEquals(SPO.ErrorInStorageName().get(0).getText(), "Already Exists! Try with a different name!");
	}

	@Test(priority = 13, groups = {
			"Positive" }, dependsOnMethods = "TestZoneDropdown", alwaysRun = false, dataProvider = "dataProvider")
	public void TestTotalSize(String SizeValue) {
		CreatePageTests CPT = new CreatePageTests();
		Actions a = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		String PreviousValue;
		a.moveToElement(SPO.TotalSizeSlider()).click().build().perform();
		CPT.ConditionCheck(SizeValue, SPO.SizeValueMin(), "Horizontal");
		assertTrue(Integer.parseInt(SizeValue) % 100 == 0, "Given number should be divisible by 100");
		String[] Max = SPO.SizeValueMax().getText().split("\\s+G");
		assertTrue(Integer.parseInt(SizeValue) <= Integer.parseInt(Max[0].replace(" ", "")),
				"Given Value(" + SizeValue + ") Should be less than " + Max[0]);
		mainloop: while (true) {
			// System.out.println(SPO.SizeValue().getText()+" "+SizeValue+" "+"GB");
			if (SPO.SizeValue().getText().replaceAll("\\s+", "").equals(SizeValue + "GB"))
				break mainloop;
			else {
				PreviousValue = SPO.SizeValue().getText();
				a.moveToElement(SPO.TotalSizeSlider()).sendKeys(Keys.ARROW_RIGHT).build().perform();
				wait.until(ExpectedConditions
						.not(ExpectedConditions.textToBePresentInElement(SPO.SizeValue(), PreviousValue)));
			}
			String[] DiskValue = SPO.SizeValue().getText().split("\\s+G");
			assertFalse(Integer.parseInt(String.valueOf(DiskValue[0].replace(" ", "").trim())) > Integer
					.parseInt(SizeValue), "Script error");
		}
//			if (InsufficienterrorMessage.getAttribute("style").contains("inline"))
//				throw new InsufficientResourcesException(InsufficienterrorMessage.getText());
	}

	@Test(priority = 14, groups = { "Positive" }, dependsOnMethods = "TestTotalSize", alwaysRun = false)
	public void TestCancelButton() {
		SPO.CancelButton().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		wait.until(ExpectedConditions.invisibilityOf(SPO.AddStorageHeading()));
		assertTrue(SPO.AddnewStorageButton().isDisplayed());
	}

	@Test(priority = 15, groups = {
			"Positive" }, dependsOnMethods = "TestTotalSize", alwaysRun = false, dataProvider = "dataProvider")
	public void TestAddButton(String StorageName) {

		SPO.AddButton().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			wait.until(ExpectedConditions.visibilityOf(CPO.UserReatedErrorMessage()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(CPO.UserReatedErrorMessage().getText());
		assertEquals(CPO.UserReatedErrorMessage().getText(), "New Storage has been successfully added.");
		wait.until(ExpectedConditions.visibilityOf(SPO.AddnewStorageButton()));
		assertTrue(SPO.AddnewStorageButton().isDisplayed());
		assertTrue(CommonMethodForTableValues(StorageName, "TestAddButton").getLeft(),
				"Could Not find the Name in the table");
	}

	@Test(priority = 16, groups = {
			"Positive" }, dependsOnMethods = "TestAddButton", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStorageStatusAfterAdding(String StorageName) {
		int timeout1 = 180; // 20 minutes timeout in seconds
		int pollingInterval = 30; // 30 seconds polling interval
		Wait<WebDriver> wait3 = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeout1))
				.pollingEvery(Duration.ofSeconds(pollingInterval)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			wait3.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					try {
						if (CommonMethodForTableValues(StorageName, "TestStorageStatusAfterAdding").getLeft())
							return true;
						else
							driver.navigate().refresh();
					} catch (StaleElementReferenceException | NoSuchElementException e) {
						System.out.println(e);
					}
					return null;
				}
			});
		} catch (TimeoutException e) {
			// TODO: handle exception
		}
		assertTrue(CommonMethodForTableValues(StorageName, "TestStorageStatusAfterAdding").getLeft(),
				CommonMethodForTableValues(StorageName, "TestStorageStatusAfterAdding").getRight());
	}

	@Test(priority = 17, groups = {
			"View" }, dependsOnMethods = "TestStorageOtion", alwaysRun = false, dataProvider = "dataProvider")
	public void TestStorageViewOption(String StorageName) {
	assertTrue(CommonMethodForTableValues(StorageName, "TestStorageViewOption").getLeft(),"Given Storage Name "+StorageName+" is not present");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOf(SPO.STorageViewPage()));
		} catch (Exception e) {
		}
		assertTrue(SPO.STorageViewPage().isDisplayed(), "Page is not Opened");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Pair<Boolean, String> CommonMethodForTableValues(String StorageName, String methodName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		while(true)
		{
		for (int i = 0; i < SPO.TableHeaderNames().size(); i++) {
			// System.out.println(SPO.TableHeaderNames().get(i).getText());
			if (SPO.TableHeaderNames().get(i).getText().equalsIgnoreCase("Storage")) {
				int k = i + 1;
				wait.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return driver
								.findElements(By.xpath("//table[@id='storage_list_data_table']/tbody/tr/td[" + k + "]"))
								.size() > 0; // Check if the size is 7
					}
				});
				List<WebElement> StorageNames = driver
						.findElements(By.xpath("//table[@id='storage_list_data_table']/tbody/tr/td[" + (i + 1) + "]"));
				// System.out.println(StorageNames.size());
				for (int j = 0; j < StorageNames.size(); j++) {
					// System.out.println(StorageNames.get(j).getText());
					// System.out.println(StorageName);

					if (methodName.equals("TestAddButton")) {
						if (StorageNames.get(j).getText().equalsIgnoreCase(StorageName))
							return Pair.of(true, null);
						
					} else {
						if (StorageNames.get(j).getText().equalsIgnoreCase(StorageName)) {
							for (int l = 0; l < SPO.TableHeaderNames().size(); l++) {
								if (methodName.equals("TestStorageStatusAfterAdding")) {
									if (SPO.TableHeaderNames().get(l).getText().equalsIgnoreCase("Status")) {
										int index = l - i;
										System.out.println(StorageNames.get(j)
												.findElement(By.xpath("./following-sibling::td[" + index + "]/small"))
												.getText() + "is the status");
										return Pair.of(
												StorageNames.get(j)
														.findElement(By
																.xpath("./following-sibling::td[" + index + "]/small"))
														.getText().equalsIgnoreCase("ATTACHED"),
												StorageNames.get(j)
														.findElement(By
																.xpath("./following-sibling::td[" + index + "]/small"))
														.getText());
									}
								} else if (methodName.equals("TestStorageViewOption")) {
									if (SPO.TableHeaderNames().get(l).getText().equalsIgnoreCase("Controls")) {
										int index = l - i;
										StorageNames.get(j)
												.findElement(By.xpath("./following-sibling::td[" + index + "]/button"))
												.click();
										return Pair.of(true,"");
									}
								}

							}
						}
					}
				}
			}
		}
		if(SPO.NextButton().getAttribute("class").contains("disabled"))
		break;
		else
			SPO.NextButton().click();
		}
		return Pair.of(false, null);
	}

	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("TestZoneDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx", "Zone");
		}
		if (method.getName().equals("TestLinuxOrWindowsRadioButton")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx", "OSType");
		}
		if (method.getName().equals("TestTypeDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx", "Types");
		}
		if (method.getName().equals("TestStoragePathTextBoxWithValidInputs") || method.getName().equals("TestAddButton")
				|| method.getName().equals("TestStorageStatusAfterAdding")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx", "StoragePath");
		}
		if (method.getName().equals("TestStoragePathTextBoxWithEmptyInput")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"emptyStoragePathName");
		}
		if (method.getName().equals("TestStoragePathTextBoxWithlessThan5Characters")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"LessThan5CharStorageName");
		}
		if (method.getName().equals("TestStoragePathTextBoxWithMoreThanThan30Characters")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"MoreThan30CharStorageName");
		}
		if (method.getName().equals("TestStoragePathTextBoxWithExistingName")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx",
					"ExistingStorageName");
		}
		if (method.getName().equals("TestTotalSize")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx", "TotalSize");
		}
		if (method.getName().equals("TestStorageViewOption")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Storage.xlsx", "StorageNameToView");
		}
		return null;
	}
}
