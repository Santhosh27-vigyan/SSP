package create;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.Duration;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.*;
import javax.naming.InsufficientResourcesException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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
import createResource.CreateResourcePageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class CreatePageTests extends SetAndDown {
	// if you want to test vm IP's other than excel value please define it below
	// ex:String[] vmnames={"VMName"};
	String[] vmnames;

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

	@Test(groups = { "TestLocationDropdown", "Vertical", "Horizontal",
			"None" }, priority = 3, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLocationDropdown(String LocationName) {
		assertTrue(
				CPO.SelectLocation().getOptions().stream().anyMatch(option -> option.getText().contains(LocationName)),
				"Given Value " + LocationName + " is not available in the dropdown");
		CPO.SelectLocation().selectByVisibleText(LocationName);
		assertEquals(CPO.SelectLocation().getFirstSelectedOption().getText(), LocationName);
	}

	@Test(groups = { "TestProjectNameDropdown", "Vertical", "Horizontal",
			"None" }, priority = 4, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestStoragePathDropdown", "Vertical", "Horizontal",
			"None" }, priority = 5, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestStoragePathDropdown(String StoragePathName) {
		// System.out.println(CPO.SelectStoragePath().getOptions().size());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return CPO.SelectStoragePath().getOptions().stream()
							.anyMatch(option -> option.getText().contains(StoragePathName)); // Check if the size is 7
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
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

	@Test(groups = { "TestOSDropdown", "Vertical", "Horizontal",
			"None" }, priority = 6, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestOSDropdown(String OSName, String StoragePathName) {
		CustomeWait();
		// System.out.println(CPO.SelectStoragePath().getOptions().size());
		TestStoragePathDropdown(StoragePathName);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return CPO.SelectOS().getOptions().stream().anyMatch(option -> option.getText().contains(OSName));
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertTrue(CPO.SelectOS().getOptions().stream().anyMatch(option -> option.getText().contains(OSName)),
				"Given Value " + OSName + " is not available in the dropdown");
		CPO.SelectOS().selectByVisibleText(OSName);
		assertEquals(CPO.SelectOS().getFirstSelectedOption().getText(), OSName);
	}

	@Test(groups = { "TestIOPSDropdown", "Vertical", "Horizontal",
			"None" }, priority = 7, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestIOPSDropdown(String IOPSValue) {
		assertTrue(CPO.SelectIops().getOptions().stream().anyMatch(option -> option.getText().contains(IOPSValue)),
				"Given Value " + IOPSValue + " is not available in the dropdown");
		CPO.SelectIops().selectByVisibleText(IOPSValue);
		assertEquals(CPO.SelectIops().getFirstSelectedOption().getText(), IOPSValue);
	}

	@Test(groups = { "TestHorizontalSwitchOn",
			"Horizontal" }, priority = 8, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestHorizontalSwitchOff",
			"Horizontal" }, priority = 9, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestVerticalSwitchOn",
			"Vertical" }, priority = 10, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestVerticalSwitchOff",
			"Vertical" }, priority = 11, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestSavedTemplatesRadioButton",
			"None" }, priority = 12, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestSavedTemplatesDropDown",
			"None" }, priority = 13, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestSavedTemplatesDropDown(String SavedTemplateName) {
		assertTrue(
				CPO.SavedTemplates().getOptions().stream()
						.anyMatch(option -> option.getText().contains(SavedTemplateName)),
				"Given Value " + SavedTemplateName + " is not available in the dropdown");
		CPO.SavedTemplates().selectByVisibleText(SavedTemplateName);
		assertEquals(CPO.SavedTemplates().getFirstSelectedOption().getAttribute("value"), SavedTemplateName);
	}

	@Test(groups = { "TestCustomRadioButton",
			"None" }, priority = 14, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestLowerThreshold", "Vertical",
			"Horizontal" }, priority = 15, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLowerThreshold(String LowerThresholdValue) {
		// TestHorizontalSwitchOn();
		assertTrue(
				CPO.LowerThreshold().getOptions().stream()
						.anyMatch(option -> option.getText().contains(LowerThresholdValue)),
				"Given Value " + LowerThresholdValue + " is not available in the dropdown");
		CPO.LowerThreshold().selectByValue(LowerThresholdValue);
		assertEquals(CPO.LowerThreshold().getFirstSelectedOption().getAttribute("value"), LowerThresholdValue);
	}

	@Test(groups = { "TestUpperThreshold", "Vertical",
			"Horizontal" }, priority = 16, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestUpperThreshold(String UpperThresholdValue) {
		// TestHorizontalSwitchOn();
		assertTrue(
				CPO.UpperThreshold().getOptions().stream()
						.anyMatch(option -> option.getText().contains(UpperThresholdValue)),
				"Given Value " + UpperThresholdValue + " is not available in the dropdown");
		CPO.UpperThreshold().selectByValue(UpperThresholdValue);
		assertEquals(CPO.UpperThreshold().getFirstSelectedOption().getAttribute("value"), UpperThresholdValue);
	}

	@Test(groups = { "TestVcpuForHorizontalScaling", "Horizontal",
			"None" }, priority = 17, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVcpuForHorizontalScaling(String VcpuvalueForHorizontalScaling)
			throws InsufficientResourcesException {
		Actions a = new Actions(driver);
		String PreviousValue;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		ConditionCheck(VcpuvalueForHorizontalScaling, CPO.VcpuValueMin(), "Horizontal");
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

	@Test(groups = { "TestmaxVcpuOfVerticalScaling",
			"Vertical" }, priority = 18, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestMinVcpuOfVerticalScaling",
			"Vertical" }, priority = 19, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestMinVcpuOfVerticalScaling(String MAxValue, String MinValue) {
		String Message;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		String min = ConditionCheck(MinValue, CPO.VerticalVcpuScalingMin(), "Vertical Min");
		assertTrue(Integer.parseInt(MinValue) >= Integer.valueOf(min),
				"Given Value(" + MinValue + ") Should be greater than " + min);
		if (Integer.parseInt(MinValue) != Integer.valueOf(min))
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

	@Test(groups = { "TestErrorAlertBoxOfVcpu",
			"Vertical" }, priority = 20, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestErrorAlertBoxOfVcpu(String MAxValue, String MinValue) {
		TestmaxVcpuOfVerticalScaling(MAxValue, MinValue);
		TestMinVcpuOfVerticalScaling(MAxValue, MinValue);
	}

	@Test(groups = { "TestRAMForhorizontalScaling", "Horizontal",
			"None" }, priority = 21, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestmaxRAMOfVerticalScaling",
			"Vertical" }, priority = 22, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
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

	@Test(groups = { "TestMinRAMOfVerticalScaling",
			"Vertical" }, priority = 23, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestMinRAMOfVerticalScaling(String MAxValue, String MinValue) {
		String Message;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions a = new Actions(driver);
		String min = ConditionCheck(MinValue, CPO.VerticalScalingRamValueMin(), "Vertical Min");
		assertTrue(Integer.parseInt(MinValue) >= Integer.valueOf(min),
				"Given Value(" + MinValue + ") Should be greater than " + min);
		if (Integer.parseInt(MinValue) != Integer.valueOf(min))
			CPO.VerticalRamScalingFromButton().click();
		assertTrue(Integer.parseInt(MinValue) >= Integer.parseInt(min.replace(" ", "")),
				"Given Value(" + MinValue + ") Should be greater than " + min);
		mainloop1: while (true) {
			String[] RamFromValueGettext = ((String) js.executeScript("return arguments[0].textContent;",
					CPO.VerticalScalingRamFromValue())).split("\\s+G");
			if (!(Integer.parseInt(RamFromValueGettext[0].replace(" ", "").trim()) < Integer.valueOf(MAxValue))) {
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
						assertEquals(Message, "Invalid RAM values.");
						break mainloop1;
					}
				} catch (NoSuchElementException e) {
				}
			}
			if (RamFromValueGettext[0].replace(" ", "").trim().equals(MinValue)
					|| MinValue.equals(min.replace(" ", "")))
				break mainloop1;
			else
				a.moveToElement(CPO.VerticalRamScalingFromButton()).click().sendKeys(Keys.ARROW_RIGHT).build()
						.perform();
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

	@Test(groups = { "TestErrorAlertBoxOfRAM",
			"Vertical" }, priority = 24, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestErrorAlertBoxOfRAM(String MAxValue, String MinValue) {
		TestmaxRAMOfVerticalScaling(MAxValue, MinValue);
		TestMinRAMOfVerticalScaling(MAxValue, MinValue);
	}

	@Test(groups = { "TestDiskForHorizontalScaling", "Horizontal",
			"None" }, priority = 25, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestDiskForHorizontalScaling(String DiskSizeForHorizontalScaling)
			throws InsufficientResourcesException {
		Actions a = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		String PreviousValue;
		a.moveToElement(CPO.DiskSizeScalingButton()).click().build().perform();
		ConditionCheck(DiskSizeForHorizontalScaling, CPO.DiskSizeValueMin(), "Horizontal");
		String[] Max = CPO.DiskSizeValueMax().getText().split("\\s+G");
		assertTrue(Integer.parseInt(DiskSizeForHorizontalScaling) <= Integer.parseInt(Max[0].replace(" ", "")),
				"Given Value(" + DiskSizeForHorizontalScaling + ") Should be less than " + Max[0]);
		mainloop: while (true) {
			//System.out.println(DiskSizeValue.getText()+" "+value+" "+"GB");
			if (CPO.DiskSizeValue().getText().equals(DiskSizeForHorizontalScaling + " " + "GB"))
				break mainloop;
			else {
				PreviousValue = CPO.DiskSizeValue().getText();
				a.moveToElement(CPO.DiskSizeScalingButton()).sendKeys(Keys.ARROW_RIGHT).build().perform();
				wait.until(ExpectedConditions
						.not(ExpectedConditions.textToBePresentInElement(CPO.DiskSizeValue(), PreviousValue)));
			}
			String[] DiskValue = CPO.DiskSizeValue().getText().split("\\s+G");
			assertFalse(Integer.parseInt(String.valueOf(DiskValue[0].replace(" ", "").trim())) > Integer
					.parseInt(DiskSizeForHorizontalScaling), "Script error");
		}
//			if (InsufficienterrorMessage.getAttribute("style").contains("inline"))
//				throw new InsufficientResourcesException(InsufficienterrorMessage.getText());
	}

	@Test(groups = { "TestDiskForVerticalScaling",
			"Vertical" }, priority = 26, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestDiskForVerticalScaling(String DiskSizeForVerticalScaling) throws InsufficientResourcesException {
		Actions a = new Actions(driver);
		String PreviousValue;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.DiskLable());
		// a.moveToElement(VerticalScalingDiskSizeScalingButton).click().build().perform();
		ConditionCheck(DiskSizeForVerticalScaling, CPO.VerticalScalingDiskSizeValueMin(), "vertical Min");
		String[] Max = CPO.VerticalScalingDiskSizeValueMax().getText().split("\\s+G");
		assertTrue(Integer.parseInt(DiskSizeForVerticalScaling) <= Integer.parseInt(Max[0].replace(" ", "")),
				"Given Value(" + DiskSizeForVerticalScaling + ") Should be less than " + Max[0]);
		mainloop: while (true) {
			// System.out.println(DiskSizeValue.getText()+" "+value+" "+"GB");
			if (CPO.VerticalScalingDiskSizeValue().getText().equals(DiskSizeForVerticalScaling + " " + "GB"))
				break mainloop;
			else {
				PreviousValue = CPO.VerticalScalingDiskSizeValue().getText();
				a.moveToElement(CPO.VerticalScalingDiskSizeScalingButton()).click().sendKeys(Keys.ARROW_RIGHT).build()
						.perform();
				wait.until(ExpectedConditions.not(ExpectedConditions
						.textToBePresentInElement(CPO.VerticalScalingDiskSizeValue(), PreviousValue)));
			}
			String[] DiskValue = CPO.VerticalScalingDiskSizeValue().getText().split("\\s+G");
			assertFalse(Integer.parseInt(String.valueOf(DiskValue[0].replace(" ", "").trim())) > Integer
					.parseInt(DiskSizeForVerticalScaling), "Script error");
		}
//			if (InsufficienterrorMessage.getAttribute("style").contains("inline"))
//				throw new InsufficientResourcesException(InsufficienterrorMessage.getText());
	}

	@Test(groups = { "TestLoadBalancerOn",
			"Horizontal" }, priority = 27, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLoadBalancerOn() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.LoadBalancerButton());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestLoadBalancerOff",
			"Horizontal" }, priority = 28, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestLoadBalancerOff() {
		TestLoadBalancerOn();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.LoadBalancerButton());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestTemplateCheckBoxChecked", "Horizontal",
			"None" }, priority = 29, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestTemplateCheckBoxChecked() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.TemplateCheckBox());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestTemplateCheckBoxUnChecked", "Horizontal",
			"None" }, priority = 30, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestTemplateCheckBoxUnChecked() {
		TestTemplateCheckBoxChecked();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.TemplateCheckBox());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestMaxGroupCount",
			"Horizontal" }, priority = 31, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestMaxGroupCount(String MaxGroupCount) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while (true) {
			String CurrentmaxCount1 = (String) js.executeScript("return document.getElementById('number1').value;");
			if (Integer.parseInt(CurrentmaxCount1) > Integer.parseInt(MaxGroupCount)) {
				CPO.decreaseButton().click();
			} else if (Integer.parseInt(CurrentmaxCount1) < Integer.parseInt(MaxGroupCount)) {
				CPO.IncreaseButton().click();
			} else if (Integer.parseInt(CurrentmaxCount1) == Integer.parseInt(MaxGroupCount)) {
				break;
			}
			String CurrentmaxCount2 = (String) js.executeScript("return document.getElementById('number1').value;");
			assertFalse(CurrentmaxCount1.equals(CurrentmaxCount2),
					"Max group count value can be " + Integer.parseInt(CurrentmaxCount1));
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestVMBackupSwitchOff", "Vertical", "Horizontal",
			"None" }, priority = 32, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVMBackupSwitchOff() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.VMBackUpLabel());
		if (!CPO.BackUpfrequencyLabel().isDisplayed())
			System.out.println("VM Backup Switch is Already Turned Off");
		else
			js.executeScript("arguments[0].click();", CPO.VMBackupButton());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestVMBackupSwitchOn", "Vertical", "Horizontal",
			"None" }, priority = 33, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVMBackupSwitchOn() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.VMBackUpLabel());
		if (CPO.BackUpfrequencyLabel().isDisplayed())
			System.out.println("VM Backup Switch is Already Turned On");
		else
			js.executeScript("arguments[0].click();", CPO.VMBackupButton());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestBackUpFrequencyDropdown", "Vertical", "Horizontal",
			"None" }, priority = 34, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestBackUpFrequencyDropdown(String BackUpFrequencyValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.VMBackUpLabel());
		if (!CPO.BackUpfrequencyLabel().isDisplayed())
			js.executeScript("arguments[0].click();", CPO.VMBackupButton());
		assertTrue(
				CPO.BackUpFrequencyOptions().getOptions().stream()
						.anyMatch(option -> option.getText().contains(BackUpFrequencyValue)),
				"Given Value " + BackUpFrequencyValue + " is not available in the dropdown");
		CPO.BackUpFrequencyOptions().selectByVisibleText(BackUpFrequencyValue);
		assertEquals(CPO.BackUpFrequencyOptions().getFirstSelectedOption().getText(), BackUpFrequencyValue);
	}

	@Test(groups = { "TestRetentionPeriodDropdown", "Vertical", "Horizontal",
			"None" }, priority = 35, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestRetentionPeriodDropdown(String RetentionPeriodValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.VMBackUpLabel());
		if (!CPO.BackUpfrequencyLabel().isDisplayed())
			js.executeScript("arguments[0].click();", CPO.VMBackupButton());
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return CPO.RetentionPeriodOptions().getOptions().stream()
							.anyMatch(option -> option.getText().contains(RetentionPeriodValue));
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertTrue(
				CPO.RetentionPeriodOptions().getOptions().stream()
						.anyMatch(option -> option.getText().contains(RetentionPeriodValue)),
				"Given Value " + RetentionPeriodValue + " is not available in the dropdown");
		CPO.RetentionPeriodOptions().selectByVisibleText(RetentionPeriodValue);
		assertEquals(CPO.RetentionPeriodOptions().getFirstSelectedOption().getText(), RetentionPeriodValue);
	}

	@Test(groups = { "TestSIEMSwitchOn", "Vertical", "Horizontal",
			"None" }, priority = 36, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestSIEMSwitchOn() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (CPO.SIEMDivClass().getAttribute("style").contains("none")) {
			js.executeScript("arguments[0].click();", CPO.SIEMSwitch());
			if (CPO.SIEMErrorMessage().getAttribute("style").contains("inline"))
				throw new Error(CPO.SIEMErrorMessage().getText());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("SIEM Option is already turned on");
	}

	@Test(groups = { "TestSIEMSwitchOff", "Vertical", "Horizontal",
			"None" }, priority = 37, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestSIEMSwitchOff() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (!CPO.SIEMDivClass().getAttribute("style").contains("none")) {
			js.executeScript("arguments[0].click();", CPO.SIEMSwitch());
			if (CPO.SIEMErrorMessage().getAttribute("style").contains("inline"))
				throw new Error(CPO.SIEMErrorMessage().getText());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("SIEM Option is already turned off");
	}

	@Test(groups = { "TestSIEMDropdown", "Vertical", "Horizontal",
			"None" }, priority = 38, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestSIEMDropdown(String SIEMName) {
		TestSIEMSwitchOn();
		assertTrue(CPO.SIEMVmList().getOptions().stream().anyMatch(option -> option.getText().contains(SIEMName)),
				"Given Value " + SIEMName + " is not available in the dropdown");
		CPO.SIEMVmList().selectByVisibleText(SIEMName);
		assertEquals(CPO.SIEMVmList().getFirstSelectedOption().getText(), SIEMName);
	}

	@Test(groups = { "TestVLANDropdown", "Vertical", "Horizontal",
			"None" }, priority = 39, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVLANDropdown(String VLANName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		String Message;
		assertTrue(CPO.VlanOptions().getOptions().stream().anyMatch(option -> option.getText().contains(VLANName)),
				"Given Value " + VLANName + " is not available in the dropdown");
		CPO.VlanOptions().selectByVisibleText(VLANName);
		try {
			wait.until(ExpectedConditions.visibilityOf(CPO.Alert()));
			Message = CPO.AlertMessage().getText();
			CPO.AlertOkButton().click();
			assertEquals(Message,
					"Note: This VLAN is fully utilized. Select a different VLAN or contact Admin for additional VLAN requirement.");
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertEquals(CPO.VlanOptions().getFirstSelectedOption().getText(), VLANName);
	}

	@Test(groups = { "TestNumberOfVM",
			"Vertical" }, priority = 40, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestNumberOfVM(String MaxVMCount) {
		Actions a = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a.moveToElement(CPO.NumberOfVirtualMachines()).build().perform();
		// js.executeScript("arguments[0].scrollIntoView(true);",
		// CPO.NumberOfVirtualMachines());
		while (true) {
			String CurrentmaxCount1 = (String) js.executeScript("return document.getElementById('number').value;");
			if (Integer.parseInt(CurrentmaxCount1) > Integer.parseInt(MaxVMCount)) {
				CPO.VMDecreaseButton().click();
			} else if (Integer.parseInt(CurrentmaxCount1) < Integer.parseInt(MaxVMCount)) {
				CPO.VMIncreaseButton().click();
			} else if (Integer.parseInt(CurrentmaxCount1) == Integer.parseInt(MaxVMCount)) {
				break;
			}
			String CurrentmaxCount2 = (String) js.executeScript("return document.getElementById('number').value;");
			assertFalse(CurrentmaxCount1.equals(CurrentmaxCount2),
					"Max group count value can be " + Integer.parseInt(CurrentmaxCount1));
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(groups = { "TestVMNameTextBoxForHorizontal",
			"Horizontal" }, priority = 41, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVMNameTextBoxForHorizontal(String VMNamesForHorizontal) {
		vmnames = VMNamesForHorizontal.split(",");
		VMName(VMNamesForHorizontal);
	}

	@Test(groups = { "TestVMNameTextBoxForVertical", "Vertical",
			"None" }, priority = 42, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestVMNameTextBoxForVertical(String VMNamesForVertical, String NumberOfVms) {
		vmnames = VMNamesForVertical.split(",");
		TestNumberOfVM(NumberOfVms);
		VMName(VMNamesForVertical);
	}

	@Test(groups = { "TestAssignRemoteUserButton", "Vertical", "Horizontal",
			"None" }, priority = 43, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestAssignRemoteUserButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.AssignRemoteUserButton());
		js.executeScript("$('#display_modal').modal('show');");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("display_modal")));
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertTrue(!CPO.SelectAllUsersButton().isSelected(), "Modal didn't Open");
	}

	@Test(groups = { "TestAssignRemoteUserModal", "Vertical", "Horizontal",
			"None" }, priority = 44, dataProvider = "dataProvider", dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestAssignRemoteUserModal(String SelectAllUserCondition, String username, String Role) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("display_modal")));
		if (SelectAllUserCondition.equals("yes")) {
			if (!CPO.SelectAllUsersButton().isSelected()) {
				js.executeScript("arguments[0].click();", CPO.SelectAllUsersButton());
				if (Role.equalsIgnoreCase("Admin"))
					TestAdminRadioButton(username);
				if (Role.equalsIgnoreCase("User"))
					TestUserRadioButton(username);
			}
		} else {
			TestUserNamePresentOrNot(username);
			if (Role.equalsIgnoreCase("Admin"))
				TestAdminRadioButton(username);
			if (Role.equalsIgnoreCase("User"))
				TestUserRadioButton(username);
		}
	}

	@Test(groups = { "TestSaveRolesButtonInAssignRolesModal", "Vertical", "Horizontal",
			"None" }, priority = 45, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestSaveRolesButtonInAssignRolesModal() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.SaverolesButton());
		try {
			assertFalse(CPO.UserReatedErrorMessage().isDisplayed(), CPO.UserReatedErrorMessage().getText());
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
	}

	@Test(groups = { "TestCreateButtontocheckErrorsInTheForm", "Vertical", "Horizontal",
			"None" }, priority = 46, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestCreateButtontocheckErrorsInTheForm() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.CreateButton());
		String Error = "";
		for (int i = 0; i < CPO.AllErrorMessage().size(); i++) {
			Error += CPO.AllErrorMessage().get(i).getText();
		}
//		for (int i = 0; i < CPO.AvailabityMessage().size(); i++) {
//			Error += CPO.AvailabityMessage().get(i).getText();
//		}
		// System.out.println(CPO.AllErrorMessage().size());
		// if(CPO.AllErrorMessage().size() > 0 || CPO.AvailabityMessage().size()>0)
		if (CPO.AllErrorMessage().size() > 0) {
			System.out.println("Errors are " + Error);
		} else {
			assertTrue(CPO.AllErrorMessage().size() > 0, "No Errors Found");
			// assertTrue(CPO.AvailabityMessage().size()>0,"No Errors Found");
		}
	}

	@Test(groups = { "TestCreateButtontocheckErrorsAfterSubmit", "Vertical", "Horizontal",
			"None" }, priority = 47, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestCreateButtontocheckErrorsAfterSubmit() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.CreateButton());
		try {
			wait.until(ExpectedConditions.visibilityOf(CPO.UserReatedErrorMessage()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertTrue(CPO.UserReatedErrorMessage().getText().contains("Error"));
		assertEquals(CPO.UserReatedErrorMessage().getText(), "Error while creating new Virtual Machine!");
		System.out
				.println("error in TestCreateButtontocheckErrorsAfterSubmit " + CPO.UserReatedErrorMessage().getText());
	}

	@Test(groups = { "TestCreateButtontocheckSuccessMessageAfterSubmit", "Vertical", "Horizontal",
			"None" }, priority = 48, dependsOnMethods = "ClickOnCreateReource", alwaysRun = false)
	public void TestCreateButtontocheckSuccessMessageAfterSubmit() {
		Logger log = LogManager.getLogger(CreatePageTests.class.getName());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", CPO.CreateButton());
		try {
			wait.until(ExpectedConditions.visibilityOf(CPO.UserReatedErrorMessage()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		log.info(CPO.UserReatedErrorMessage().getText());
		assertEquals(CPO.UserReatedErrorMessage().getText(),
				"New Virtual Machine Request has been successfully created.");
	}

	@Test(groups = { "TestIPAddress", "Vertical", "Horizontal", "None" }, priority = 49, dependsOnMethods = {
			"ClickOnCreateReource", "TestCreateButtontocheckSuccessMessageAfterSubmit" }, alwaysRun = false)
	public void TestIPAddress() {
		CRPO.ResourceListOption().click();
		for (int m = 0; m < vmnames.length; m++) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String IpAddress = null;
			try {
				wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud")));
			} catch (Exception e) {
			}
			assertEquals(driver.getTitle(), "Virtual Machines");
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return CPO.TableHeaderNames().size() > 0;
				}
			});
			// FluentWait with polling interval and timeout
			int timeout1 = 1200; // 20 minutes timeout in seconds
			int pollingInterval = 30; // 30 seconds polling interval
			// System.out.println(CPO.TableHeaderNames().size() +
			// "CPO.TableHeaderNames().size()");
			// Create FluentWait instance
			Wait<WebDriver> wait3 = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeout1)) // Total
																										// wait
																										// time
					.pollingEvery(Duration.ofSeconds(pollingInterval)) // Poll every 30 seconds
					.ignoring(NoSuchElementException.class) // Ignore NoSuchElementException during the
															// wait
					.ignoring(StaleElementReferenceException.class); // Ignore
																		// StaleElementReferenceException
																		// during the wait
			Pair<Integer, List<WebElement>> VMNamesList = ListOfVmNames(m);
			IpAddress = IPAddress(VMNamesList.getRight(), VMNamesList.getLeft(), m);
			try {
				int index = m;
				// Wait until the desired status is present in the status element
				wait3.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						try {
							if (GetStatus(ListOfVmNames(index).getRight(), ListOfVmNames(index).getLeft(), index)
									.equals("Running"))
								return true;
							driver.navigate().refresh();
						} catch (NoSuchElementException | StaleElementReferenceException e) {
							System.out.println(e);
						}
						return null;
					}
				});
				System.out.println("VM Status is "
						+ GetStatus(ListOfVmNames(index).getRight(), ListOfVmNames(index).getLeft(), index));
			} catch (Exception e) {
				System.out.println("VM Status is "
						+ GetStatus(ListOfVmNames(m).getRight(), ListOfVmNames(m).getLeft(), m) + " After 15 mins");
			}
			assertTrue(TestIPPinging(IpAddress), "IP is not pinging for the vm ");
		}
	}

	public Pair<Integer, List<WebElement>> ListOfVmNames(int ResourceIndex) {
		int num = 0;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		List<WebElement> VMNames = null;
		for (int i = 0; i < CPO.TableHeaderNames().size(); i++) {
			if (CPO.TableHeaderNames().get(i).getText().trim().equalsIgnoreCase("VM Name")) {
				num = i + 1;
				int index = num;
				wait.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return driver
								.findElements(By.xpath(
										"//table[@id='virtual_machine_list_data_table']/tbody/tr/td[" + index + "]"))
								.size() > 0;
					}
				});
				VMNames = driver.findElements(
						By.xpath("//table[@id='virtual_machine_list_data_table']/tbody/tr/td[" + num + "]"));
			}
		}
		return Pair.of(num, VMNames);
	}

	public String IPAddress(List<WebElement> VMNames, int num, int ResourceIndex) {
		String IpAddress = null;
		for (int j = 0; j < CPO.TableHeaderNames().size(); j++) {
			if (CPO.TableHeaderNames().get(j).getText().trim().equalsIgnoreCase("IP Address")) {
				// System.out.println(VMNames.size() + " VMNames.size()2");
				int num1 = (j + 1) - num;
				for (int j2 = 0; j2 < VMNames.size(); j2++) {
					// System.out.println(VMNames.get(j2).getText().trim());
					if (VMNames.get(j2).getText().trim().equalsIgnoreCase(vmnames[ResourceIndex])) {
						// System.out.println(VMNames.size() + " VMNames.size()3");
						IpAddress = VMNames.get(j2).findElement(By.xpath("./following-sibling::td[" + num1 + "]"))
								.getText();
						// System.out.println(IpAddress + " IpAddress");
					}
				}
			}
		}
		return IpAddress;
	}

	public String GetStatus(List<WebElement> VMNames, int num, int ResourceIndex) {
		String Status = null;
		for (int j = 0; j < CPO.TableHeaderNames().size(); j++) {
			if (CPO.TableHeaderNames().get(j).getText().trim().equalsIgnoreCase("Status")) {
				int num1 = (j + 1) - num;
				for (int j2 = 0; j2 < VMNames.size(); j2++) {
					if (VMNames.get(j2).getText().trim().equalsIgnoreCase(vmnames[ResourceIndex])) {
						Status = VMNames.get(j2).findElement(By.xpath("./following-sibling::td[" + num1 + "]"))
								.getText();
						break;
					}
				}
			}
		}
		return Status;
	}

	public boolean TestIPPinging(String IpAddress) {
		Logger log = LogManager.getLogger(CreatePageTests.class.getName());
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int timeout = 30000; // Timeout in milliseconds
		boolean status = false;
		try {
			InetAddress inet = InetAddress.getByName(IpAddress);
			if (inet.isReachable(timeout)) {
				status = true;
				System.out.println(IpAddress + " is reachable");
				log.info(IpAddress + " is reachable");
			} else {
				System.out.println(IpAddress + " is not reachable");
				status = false;
				log.info(IpAddress + " is not reachable");
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return status;
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

	public void VMName(String VMNames) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String[] Names = VMNames.split(",");
		// Below line is not working in firefox
		// a.moveToElement(CreateButton).build().perform();
		js.executeScript("arguments[0].scrollIntoView(true);", CPO.CreateButton());
		for (int i = 0; i < CPO.VmNameTextBox().size(); i++) {
			// System.out.println(Names.length + Names[i]);
			// System.out.println(VmNameTextBox.size() + "VmNameTextBox");
			CPO.VmNameTextBox().get(i).sendKeys(Names[i]);
			assertFalse(CPO.AvailabityMessage().get(i).getText().contains("Taken"),
					"Given VM Name " + Names[i] + " is " + CPO.AvailabityMessage().get(i).getText());
			if (i == Names.length - 1)
				break;
		}
		assertTrue(Names.length == CPO.VmNameTextBox().size(),
				"Need " + CPO.VmNameTextBox().size() + " Names but we have " + Names.length);
	}

	public void TestUserNamePresentOrNot(String UserName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean CustomerNamePresent = false;
		// System.out.println( UserNames.size());
		for (int k = 0; k < CPO.UserNames().size(); k++) {
			if (CPO.UserNames().get(k).findElement(By.xpath("./following-sibling::label")).getText()
					.contains(UserName)) {
				CustomerNamePresent = true;
				js.executeScript("arguments[0].scrollIntoView(true)", CPO.UserNames().get(k));
				js.executeScript("arguments[0].click();", CPO.UserNames().get(k));
				break;
			}
		}
		if (!CustomerNamePresent)
			throw new Error(UserName + " is not in the list");
	}

	public void TestAdminRadioButton(String UserName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int j = 0; j < CPO.AdminRadioButtons().size(); j++) {
			if (CPO.UserNames().get(j).findElement(By.xpath("./following-sibling::label")).getText()
					.contains(UserName)) {
				js.executeScript("arguments[0].scrollIntoView(true)", CPO.AdminRadioButtons().get(j));
				js.executeScript("arguments[0].click();", CPO.AdminRadioButtons().get(j));
				break;
			}
		}
	}

	public void TestUserRadioButton(String UserName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int j = 0; j < CPO.UserRadioButtons().size(); j++) {
			if (CPO.UserNames().get(j).findElement(By.xpath("./following-sibling::label")).getText()
					.contains(UserName)) {
				js.executeScript("arguments[0].scrollIntoView(true)", CPO.UserRadioButtons().get(j));
				js.executeScript("arguments[0].click();", CPO.UserRadioButtons().get(j));
				break;
			}
		}
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
		if (method.getName().equals("TestSavedTemplatesDropDown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "SavedTemplates");
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
		if (method.getName().equals("TestErrorAlertBoxOfVcpu")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"ToCheckAlertOfVcpu");
		}
		if (method.getName().equals("TestRAMForhorizontalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"RAMValueForHScaling");
		}
		if (method.getName().equals("TestmaxRAMOfVerticalScaling")
				|| method.getName().equals("TestMinRAMOfVerticalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"RAMValueForVScaling");
		}
		if (method.getName().equals("TestDiskForHorizontalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"DiskSizeForHScaling");
		}
		if (method.getName().equals("TestDiskForVerticalScaling")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"DisSizeForVScaling");
		}
		if (method.getName().equals("TestErrorAlertBoxOfRAM")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"ToCheckAlertOfRam");
		}
		if (method.getName().equals("TestMaxGroupCount")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "MaxGroupCount");
		}
		if (method.getName().equals("TestBackUpFrequencyDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"BackUpfrequencyValue");
		}
		if (method.getName().equals("TestRetentionPeriodDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx",
					"RetentionPeriodValue");
		}
		if (method.getName().equals("TestSIEMDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "SIEMName");
		}
		if (method.getName().equals("TestVLANDropdown")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "VLANName");
		}
		if (method.getName().equals("TestNumberOfVM")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "NumberOfVms");
		}
		if (method.getName().equals("TestVMNameTextBoxForHorizontal")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "VMNamesForH");
		}
		if (method.getName().equals("TestVMNameTextBoxForVertical")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "VMNamesForV");
		}
		if (method.getName().equals("TestAssignRemoteUserModal")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreatePage.xlsx", "UserInfo");
		}
		return null;
	}
}
