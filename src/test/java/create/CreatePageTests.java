package create;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import javax.naming.InsufficientResourcesException;
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
		assertTrue(
				CPO.SelectLocation().getOptions().stream().anyMatch(option -> option.getText().contains(LocationName)),
				"Given Value " + LocationName + " is not available in the dropdown");
		CPO.SelectLocation().selectByVisibleText(LocationName);
		assertEquals(CPO.SelectLocation().getFirstSelectedOption().getText(), LocationName);
	}
	@Test(groups = "TestProjectNameDropdown", priority = 4, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestProjectNameDropdown(String ProjectName) {
		// System.out.println(CPO.SelectProjectName().getOptions().size());
		CustomeWait();
		// System.out.println(CPO.SelectProjectName().getOptions().size());
		assertTrue(
				CPO.SelectProjectName().getOptions().stream()
						.anyMatch(option -> option.getText().contains(ProjectName)),
				"Given Value " + ProjectName + " is not available in the dropdown");
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
		assertTrue(
				CPO.SelectStoragePath().getOptions().stream()
						.anyMatch(option -> option.getText().contains(StoragePathName)),
				"Given Value " + StoragePathName + " is not available in the dropdown");
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
		assertTrue(CPO.SelectOS().getOptions().stream().anyMatch(option -> option.getText().contains(OSName)),
				"Given Value " + OSName + " is not available in the dropdown");
		CPO.SelectOS().selectByVisibleText(OSName);
		assertEquals(CPO.SelectOS().getFirstSelectedOption().getText(), OSName);
	}
	@Test(groups = "TestIOPSDropdown", priority = 7, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestIOPSDropdown(String IOPSValue) {
		assertTrue(CPO.SelectIops().getOptions().stream().anyMatch(option -> option.getText().contains(IOPSValue)),
				"Given Value " + IOPSValue + " is not available in the dropdown");
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
	@Test(groups = "TestLowerThreshold", priority = 14, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLowerThreshold(String LowerThresholdValue) {
		// TestHorizontalSwitchOn();
		assertTrue(
				CPO.LowerThreshold().getOptions().stream()
						.anyMatch(option -> option.getText().contains(LowerThresholdValue)),
				"Given Value " + LowerThresholdValue + " is not available in the dropdown");
		CPO.LowerThreshold().selectByValue(LowerThresholdValue);
		assertEquals(CPO.LowerThreshold().getFirstSelectedOption().getAttribute("value"), LowerThresholdValue);
	}
	@Test(groups = "TestUpperThreshold", priority = 15, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestUpperThreshold(String UpperThresholdValue) {
		// TestHorizontalSwitchOn();
		assertTrue(
				CPO.UpperThreshold().getOptions().stream()
						.anyMatch(option -> option.getText().contains(UpperThresholdValue)),
				"Given Value " + UpperThresholdValue + " is not available in the dropdown");
		CPO.UpperThreshold().selectByValue(UpperThresholdValue);
		assertEquals(CPO.UpperThreshold().getFirstSelectedOption().getAttribute("value"), UpperThresholdValue);
	}
	@Test(groups = "TestVcpuForHorizontalScaling", priority = 16, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVcpuForHorizontalScaling(String VcpuvalueForHorizontalScaling)
			throws InsufficientResourcesException {
		Actions a = new Actions(driver);
		String PreviousValue;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		ConditionCheck(VcpuvalueForHorizontalScaling, CPO.VcpuValueMin(), "Vertical");
		assertTrue(Integer.parseInt(VcpuvalueForHorizontalScaling) <= Integer.parseInt(CPO.VcpuValueMax().getText()),
				"Given Value(" + VcpuvalueForHorizontalScaling + ") Should be less than "
						+ CPO.VcpuValueMax().getText());
		CPO.VcpuScalingButton().click();
		mainloop: while (true) {
			// System.out.println(CPO.VcpuValue().getText() + " " +
			// VcpuvalueForHorizontalScaling);
			if (CPO.VcpuValue().getText().equals(VcpuvalueForHorizontalScaling))
				break mainloop;
			else {
				PreviousValue = CPO.VcpuValue().getText();
				a.moveToElement(CPO.VcpuScalingButton()).sendKeys(Keys.ARROW_RIGHT).build().perform();
				wait.until(ExpectedConditions
						.not(ExpectedConditions.textToBePresentInElement(CPO.VcpuValue(), PreviousValue)));
			}
			if (CPO.VcpuValue().getText() != "") {
				assertFalse(
						Integer.parseInt(CPO.VcpuValue().getText()) > Integer.parseInt(VcpuvalueForHorizontalScaling),
						"Script error");
			}
		}
//			if (InsufficienterrorMessage.getAttribute("style").contains("inline"))
//				throw new InsufficientResourcesException(InsufficienterrorMessage.getText());
	}
	@Test(groups = "TestmaxVcpuOfVerticalScaling", priority = 17, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestmaxVcpuOfVerticalScaling(String MAxValue, String MinValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		ConditionCheck(MAxValue, CPO.VerticalScalingVcpuToValue(), "Vertical Max");
		CPO.VerticalVcpuScalingToButton().click();
		assertTrue(Integer.parseInt(MAxValue) <= Integer.parseInt(CPO.VerticalVcpuScalingMax().getText()),
				"Given Value(" + MAxValue + ") Should be less than " + CPO.VerticalVcpuScalingMax().getText());
		mainloop: while (true) { // System.out.println(VerticalScalingVcpuToValue.getText()+" "+FromAndTo[1]);
			String[] VerticalScalingVcpuToValueGettext = ((String) js.executeScript("return arguments[0].textContent;",
					CPO.VerticalScalingVcpuToValue())).split("\\s+G");
			if (VerticalScalingVcpuToValueGettext[0].equals(MAxValue) || MAxValue.equals("4"))
				break mainloop;
			else {
				a.moveToElement(CPO.VerticalVcpuScalingToButton()).sendKeys(Keys.ARROW_RIGHT).build().perform();
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
			if (VerticalScalingVcpuToValueGettext[0] != "") {
				assertFalse(Integer.parseInt(String.valueOf(VerticalScalingVcpuToValueGettext[0])) > Integer
						.parseInt(MAxValue), "Script error");
			}
		}
	}
	@Test(groups = "TestMinVcpuOfVerticalScaling", priority = 18, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestMinVcpuOfVerticalScaling(String MAxValue, String MinValue) {
		String Message;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		String min = ConditionCheck(MinValue, CPO.VerticalVcpuScalingMin(), "Vertical Min");
		assertTrue(Integer.parseInt(MinValue) >= Integer.valueOf(min),
				"Given Value(" + MinValue + ") Should be greater than " + min);
		CPO.VerticalVcpuScalingFromButton().click();
		mainloop1: while (true) {
			String[] VerticalScalingVcpuFromValueGettext = ((String) js
					.executeScript("return arguments[0].textContent;", CPO.VerticalScalingVcpuFromValue()))
					.split("\\s+G");
			if (!(Integer.parseInt(VerticalScalingVcpuFromValueGettext[0].replace(" ", "").trim()) < Integer
					.valueOf(MAxValue))) {
				try {
					wait.until(ExpectedConditions.visibilityOf(CPO.Alert()));
					// System.out.println(Alert.isDisplayed());
					if (CPO.Alert().isDisplayed()) {
						wait.until(ExpectedConditions.elementToBeClickable(CPO.AlertOkButton()));
						Message = CPO.AlertMessage().getText();
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
						}
						CPO.AlertOkButton().click();
						assertEquals(Message, "Invalid vCPU vlaues.");
						break mainloop1;
					}
				} catch (NoSuchElementException e) {
				}
			}
			if (VerticalScalingVcpuFromValueGettext[0].equals(MinValue))
				break mainloop1;
			else
				a.moveToElement(CPO.VerticalVcpuScalingFromButton()).click().sendKeys(Keys.ARROW_RIGHT).build()
						.perform();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
	}
	@Test(groups = "TestErrorAlertBox", priority = 19, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestErrorAlertBox(String MAxValue, String MinValue) {
		TestmaxVcpuOfVerticalScaling(MAxValue, MinValue);
		TestMinVcpuOfVerticalScaling(MAxValue, MinValue);
	}
	@Test(groups = "TestRAMForhorizontalScaling", priority = 20, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestRAMForhorizontalScaling(String RAMValueForHorizontalScaling) throws InsufficientResourcesException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.RAMLable());
		ConditionCheck(RAMValueForHorizontalScaling, CPO.RamValueMin(), "Horizontal");
		String[] Max = CPO.RamValueMax().getText().split("\\s+G");
		assertTrue(Integer.parseInt(RAMValueForHorizontalScaling) <= Integer.parseInt(Max[0].replace(" ", "")),
				"Given Value(" + RAMValueForHorizontalScaling + ") Should be less than " + Max[0]);
		mainloop: while (true) {
			// System.out.println(RamValue.getText() + " " + value + " " + "GB");
			if (CPO.RamValue().getText().equals(RAMValueForHorizontalScaling + " " + "GB"))
				break mainloop;
			else
				a.moveToElement(CPO.RamScalingButton()).click().sendKeys(Keys.ARROW_RIGHT).build().perform();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] RamValue2 = CPO.RamValue().getText().split("\\s+G");
			if (CPO.RamValue().getText() != "")
				assertFalse(Integer.parseInt(String.valueOf(RamValue2[0].replace(" ", "").trim())) > Integer
						.parseInt(RAMValueForHorizontalScaling), "Script error");
		}
//			if (InsufficienterrorMessage.getAttribute("style").contains("inline"))
//				throw new InsufficientResourcesException(InsufficienterrorMessage.getText());
	}
	@Test(groups = "TestmaxRAMOfVerticalScaling", priority = 21, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestmaxRAMOfVerticalScaling(String MAxValue, String MinValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		String[] Max = CPO.VerticalScalingRamValueMax().getText().split("\\s+G");
		ConditionCheck(MAxValue, CPO.VerticalScalingRamToValue(), "Vertical Max");
		CPO.VerticalRamScalingToButton().click();
		assertTrue(Integer.parseInt(MAxValue) <= Integer.parseInt(Max[0].replace(" ", "")),
				"Given Value(" + MAxValue + ") Should be less than " + Max[0]);
		mainloop: while (true) {
			String[] RamToValueGettext = ((String) js.executeScript("return arguments[0].textContent;",
					CPO.VerticalScalingRamToValue())).split("\\s+G");
			if (RamToValueGettext[0].replace(" ", "").trim().equals(MAxValue) || MAxValue.equals("4"))
				break mainloop;
			else
				a.moveToElement(CPO.VerticalRamScalingToButton()).sendKeys(Keys.ARROW_RIGHT).build().perform();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (RamToValueGettext[0].replace(" ", "").trim() != "")
				assertFalse(Integer.parseInt(String.valueOf(RamToValueGettext[0].replace(" ", "").trim())) > Integer
						.parseInt(MAxValue), "Script error");
		}
	}
	@Test(groups = "TestMinRAMOfVerticalScaling", priority = 22, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestMinRAMOfVerticalScaling(String MAxValue, String MinValue) {
		String Message;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		String min = ConditionCheck(MinValue, CPO.VerticalScalingRamValueMin(), "Vertical Min");
		assertTrue(Integer.parseInt(MinValue) >= Integer.valueOf(min),
				"Given Value(" + MinValue + ") Should be greater than " + min);
		CPO.VerticalRamScalingFromButton().click();
		assertTrue(Integer.parseInt(MinValue) >= Integer.parseInt(min.replace(" ", "")),"Given Value(" + MinValue + ") Should be greater than " + min);
		
			mainloop1: while (true) {

				String[] RamFromValueGettext = ((String) js.executeScript("return arguments[0].textContent;",
						CPO.VerticalScalingRamFromValue())).split("\\s+G");

				if (!(Integer.parseInt(RamFromValueGettext[0].replace(" ", "").trim()) < Integer
						.valueOf(MAxValue))) {
					try {
						wait.until(ExpectedConditions.visibilityOf(CPO.Alert()));
						if (CPO.Alert().isDisplayed()) {
							wait.until(ExpectedConditions.elementToBeClickable(CPO.AlertOkButton()));
							Message = CPO.AlertMessage().getText();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// js.executeScript("arguments[0].click();", AlertOkButton);
							CPO.AlertOkButton().click();
							assertEquals(Message, "test");
							break mainloop1;
						}
					} catch (NoSuchElementException e) {
						
					} 
				}
				if (RamFromValueGettext[0].replace(" ", "").trim().equals(MinValue)
						|| MinValue.equals(min.replace(" ", "")))
					break mainloop1;
				else
					a.moveToElement(CPO.VerticalRamScalingFromButton()).click().sendKeys(Keys.ARROW_RIGHT).build().perform();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			if (InsufficienterrorMessage.getAttribute("style").contains("inline"))
//				throw new InsufficientResourcesException(InsufficienterrorMessage.getText());
		
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
	public String ConditionCheck(String value, WebElement MinElement, String Type) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String[] min = ((String) js.executeScript("return arguments[0].textContent;", MinElement)).split("\\s+G");
		// System.out.println(FromAndTo.length);
		if (Type.equalsIgnoreCase("Vertical Max")) {
			assertTrue((Integer.parseInt(value) % 2 == 0), "Given Value " + value + " is Not a even Number");
			assertTrue(Integer.parseInt(value) >= Integer.parseInt(min[0]),
					"Given Value " + value + " Should be greater than " + min[0]);
		} else if (Type.equalsIgnoreCase("Vertical Min")) {
			assertFalse(!(Integer.parseInt(value) % 2 == 0), "Given Value " + value + " is Not a even Number");
			assertTrue(Integer.parseInt(value) >= Integer.parseInt(min[0]),
					"Given Value " + value + " Should be greater than " + min[0]);
		} else {
			assertFalse(
					!(Integer.parseInt(value) >= Integer.parseInt(min[0].replace(" ", ""))
							&& Integer.parseInt(value) % 2 == 0),
					"Given Value(" + value + ") is either less than minimun value " + min[0] + " or Not a even Number");
		}
		return min[0];
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
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"LowerThresholdValue");
		}
		if (method.getName().equals("TestUpperThreshold")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"UpperThresholdValue");
		}
		if (method.getName().equals("TestVcpuForHorizontalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"VcpuValueForHScaling");
		}
		if (method.getName().equals("TestmaxVcpuOfVerticalScaling")
				|| method.getName().equals("TestMinVcpuOfVerticalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"VcpuvalueForVScaling");
		}
		if (method.getName().equals("TestErrorAlertBox")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "ToCheckAlert");
		}
		if (method.getName().equals("TestRAMForhorizontalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"RAMValueForHScaling");
		}
		if (method.getName().equals("TestmaxRAMOfVerticalScaling") || method.getName().equals("TestMinRAMOfVerticalScaling") ) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"RAMValueForVScaling");
		}
		return null;
	}
}
