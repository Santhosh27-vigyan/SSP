package utility;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Login.LoginPageTests;
import create.CreatePageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class NetworkServicesPageTests extends SetAndDown {
	Map<String, String> map = new HashMap<>();

	@Test(groups = "CertAndLogin", priority = 1)
	public void CertAndLogin() throws IOException {
		LoginPageTests LPT = new LoginPageTests();
		LPT.TestLoginWithValidCredentails(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 2, dependsOnMethods = "CertAndLogin", alwaysRun = false, groups = { "Utility" })
	public void GetVMNamesIpAndStatusFromResourcePage() {
		CreatePageTests CPT = new CreatePageTests();
		CRPO.ResourceManagementButton().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(CRPO.ResourceListOption()));
		CRPO.ResourceListOption().click();
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
		Pair<Integer, List<WebElement>> VMNamesList = CPT.ListOfVmNames(CPO.TableHeaderNames(),CPO.TableId());
		for (int j = 0; j < VMNamesList.getRight().size(); j++) {
			IPAddress(VMNamesList.getRight(), VMNamesList.getLeft(), VMNamesList.getRight().get(j).getText(),
					CPO.TableHeaderNames());
			map.put(VMNamesList.getRight().get(j).getText(), IPAddress(VMNamesList.getRight(), VMNamesList.getLeft(),
					VMNamesList.getRight().get(j).getText(), CPO.TableHeaderNames()));
		}
	}

	@Test(priority = 3, dependsOnMethods = "CertAndLogin", alwaysRun = false, groups = { "Utility" })
	public void TestUtilityOption() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.UtilityOption()));
		NSPO.UtilityOption().click();
	}

	@Test(priority = 4, dependsOnMethods = "TestUtilityOption", alwaysRun = false, groups = { "Utility" })
	public void TestNetworkServicesOption() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.NetworkServicesOption()));
		NSPO.NetworkServicesOption().click();
		wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Services")));
		assertEquals(driver.getTitle(), "Network Services");
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.OpenPort()));
	}

	@Test(priority = 5, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = {
			"Utility" }, dataProvider = "dataProvider", enabled = false)
	public void TestNetworkServicesOptions(String OptionName) {
		if (OptionName.equalsIgnoreCase("Create Vpn User")) {
			NSPO.CreateVPNUser().click();
		} else if (OptionName.equalsIgnoreCase("Open Port")) {
			NSPO.OpenPort().click();
		} else if (OptionName.equalsIgnoreCase("Close Port")) {
			NSPO.ClosePort().click();
		} else if (OptionName.equalsIgnoreCase("VLAN Communication")) {
			NSPO.VlanCommunication().click();
		} else if (OptionName.equalsIgnoreCase("Public IP")) {
			NSPO.PublicIp().click();
		} else if (OptionName.equalsIgnoreCase("Bandwidth")) {
			NSPO.Bandwidth().click();
		} else {
			assertTrue(false, "Given Option not Available");
		}
	}

	@Test(priority = 6, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = {
			"Utility" }, dataProvider = "dataProvider")
	public void TestPublicIpAddRequest(String VMName, String duration, String CancelOrSave) {
		
		CreatePageTests CPT = new CreatePageTests();
		boolean Status = false;
		// System.out.println(VMName+" check");
		 System.out.println(map.get(VMName)+" check1");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.PublicIp()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.PublicIp()));
		NSPO.PublicIp().click();
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.PublicIPAddRequestButton()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.PublicIPAddRequestButton()));
		NSPO.PublicIPAddRequestButton().click();
		assertTrue(NSPO.PublicIPTitle().getText().equalsIgnoreCase("Public Ip"), "Wrong Page opened");
		assertTrue(NSPO.VMNameDropdown().getOptions().stream().anyMatch(option -> option.getText().contains(VMName)),
				"Given Value " + VMName + " is not available in the dropdown");
		NSPO.VMNameDropdown().selectByVisibleText(VMName);
		assertEquals(NSPO.VMNameDropdown().getFirstSelectedOption().getText(), VMName);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String IP = (String) js.executeScript("return arguments[0].value;", NSPO.IPaddress());
		 System.out.println(IP+" check");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(map.get(VMName), IP);
		assertTrue(
				NSPO.DurationDropdown().getOptions().stream().anyMatch(option -> option.getText().contains(duration)),
				"Given Value " + duration + " is not available in the dropdown");
		NSPO.DurationDropdown().selectByVisibleText(duration);
		assertEquals(NSPO.DurationDropdown().getFirstSelectedOption().getText(), duration);
		if (CancelOrSave.equalsIgnoreCase("Save"))
		{
			NSPO.PublicIpSubmitButton().click();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(CPO.UserReatedErrorMessage()));
		} catch (Exception e) {
			
		}
		// System.out.println(CPO.UserReatedErrorMessage().getText()+" message");
		assertEquals(CPO.UserReatedErrorMessage().getText(), "Public IP request has been sent successfully");
		wait.until(ExpectedConditions.invisibilityOf(CPO.UserReatedErrorMessage()));
		NSPO.PublicIp().click();
		Pair<Integer, List<WebElement>> VMNamesList = CPT.ListOfVmNames(NSPO.TableHeaderNames(),NSPO.TableId());
		//System.out.println(VMNamesList.getRight().size()+" Test 1");
		for (int i = 0; i < VMNamesList.getRight().size(); i++) {
			//System.out.println(String.valueOf(VMNamesList.getRight().get(i).getText()) +" test 2");
			if( String.valueOf(VMNamesList.getRight().get(i).getText()).equalsIgnoreCase(VMName))
			{
				//System.out.println(String.valueOf(IPAddress(VMNamesList.getRight(), VMNamesList.getLeft(), VMName, NSPO.TableHeaderNames())) +" test 3");
				assertEquals(IPAddress(VMNamesList.getRight(), VMNamesList.getLeft(), VMName, NSPO.TableHeaderNames()), IP);  
				Status=true;
				break;
			}
		}
		assertTrue(Status,"COULD find the created IP in the Table");
		}
		else
			NSPO.PublicIpCancelButton().click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}

	@Test(priority = 7, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = { "Utility" })
	public void TestOpenPortAddRequest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.OpenPort()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.OpenPort()));
		NSPO.OpenPort().click();
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.OpenPortAddRequestButton()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.OpenPortAddRequestButton()));
		NSPO.OpenPortAddRequestButton().click();
		assertTrue(NSPO.OpenPortTitle().getText().equalsIgnoreCase("Open Port"), "Wrong Page opened");
	}

	@Test(priority = 8, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = { "Utility" })
	public void TestClosePortAddRequest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.ClosePort()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.ClosePort()));
		NSPO.ClosePort().click();
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.ClosePortAddRequestButton()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.ClosePortAddRequestButton()));
		NSPO.ClosePortAddRequestButton().click();
		assertTrue(NSPO.ClosePortTitle().getText().equalsIgnoreCase("Close Port"), "Wrong Page opened");
	}

	@Test(priority = 9, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = { "Utility" })
	public void TestVlanCommunicationAddRequest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.VlanCommunication()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.VlanCommunication()));
		NSPO.VlanCommunication().click();
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.VlanCommunicationAddRequestButton()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.VlanCommunicationAddRequestButton()));
		NSPO.VlanCommunicationAddRequestButton().click();
		assertTrue(NSPO.VlanCommunicationsTitle().getText().equalsIgnoreCase("Vlan Communication"),
				"Wrong Page opened");
	}

	@Test(priority = 10, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = { "Utility" })
	public void TestBandwidthAddRequest() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.Bandwidth()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.Bandwidth()));
		NSPO.Bandwidth().click();
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.BandwidthAddRequestButton()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.BandwidthAddRequestButton()));
		NSPO.BandwidthAddRequestButton().click();
		assertTrue(NSPO.BandwidthTitle().getText().equalsIgnoreCase("Bandwidth"), "Wrong Page opened");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String IPAddress(List<WebElement> VMNames, int num, String VMName, List<WebElement> TableHeaderNames) {
		String IpAddress = null;
		for (int j = 0; j < TableHeaderNames.size(); j++) {
			if (TableHeaderNames.get(j).getText().trim().equalsIgnoreCase("IP Address")) {
				// System.out.println(VMNames.size() + " VMNames.size()2");
				int num1 = (j + 1) - num;
				for (int j2 = 0; j2 < VMNames.size(); j2++) {
					// System.out.println(VMNames.get(j2).getText().trim());
					if (VMNames.get(j2).getText().trim().equalsIgnoreCase(VMName)) {
						// System.out.println(VMNames.size() + " VMNames.size()3");
						IpAddress = VMNames.get(j2).findElement(By.xpath("./following-sibling::td[" + num1 + "]"))
								.getText();
					//	 System.out.println(IpAddress + " IpAddress");
					}
				}
			}
		}
		return IpAddress;
	}

	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("TestNetworkServicesOptions")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Networkservices.xlsx",
					"NetworkservicesOption");
		}
		if (method.getName().equals("TestPublicIpAddRequest")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Networkservices.xlsx",
					"PublicIPPage");
		}
		return null;
	}
}
