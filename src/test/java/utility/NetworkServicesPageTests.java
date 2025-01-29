package utility;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
	Map<String, String> Ipmap = new HashMap<>();

	@Test(groups = "CertAndLogin", priority = 1)
	public void CertAndLogin() throws IOException {
		LoginPageTests LPT = new LoginPageTests();
		LPT.TestLoginWithValidCredentails(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 2, dependsOnMethods = "CertAndLogin", alwaysRun = false, groups = { "TestPublicIpAddRequest" })
	public void GetVMNamesIpAndStatusFromResourcePage() {
		String FirstName = null;
		CreatePageTests CPT = new CreatePageTests();
		CRPO.ResourceManagementButton().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(CRPO.ResourceListOption()));
		CRPO.ResourceListOption().click();
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud")));
		} catch (Exception e) {
		}
		while (true) {
			assertEquals(driver.getTitle(), "Virtual Machines");
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return CPO.TableHeaderNames().size() > 0;
				}
			});
			Pair<Integer, List<WebElement>> VMNamesList = CPT.ListOfVmNames(CPO.TableHeaderNames(), CPO.TableId());
			System.out.println(VMNamesList.getRight().size() + "FN");
			for (int j = 0; j < VMNamesList.getRight().size(); j++) {
				FirstName = VMNamesList.getRight().get(0).getText();
				// System.out.println(VMNamesList.getRight().get(j).getText()+" from network");
				String IP = CPT.GetData(VMNamesList.getRight(), VMNamesList.getLeft(),
						VMNamesList.getRight().get(j).getText(), CPO.TableHeaderNames(), "IP Address");
				// System.out.println(IP +"From Network");
				map.put(VMNamesList.getRight().get(j).getText(), IP);
			}
			// System.out.println(CPO.NextButton().getAttribute("class"));
			// System.out.println(!CPO.NextButton().getAttribute("class").contains("disabled"));
			if (!CPO.NextButton().getAttribute("class").contains("disabled")) {
				CPO.NextButton().click();
				// System.out.println(FirstName);
				// System.out.println(VMNamesList.getRight().get(0).getText());
				wait.until(ExpectedConditions
						.not(ExpectedConditions.textToBePresentInElement(VMNamesList.getRight().get(0), FirstName)));
			} else
				break;
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
			"TestPublicIpAddRequest" }, dataProvider = "dataProvider")
	public void TestPublicIpAddRequest(String VMName, String duration, String CancelOrSave) {
		CreatePageTests CPT = new CreatePageTests();
		boolean Status = false;
		String FirstName = null;
		// System.out.println(VMName+" check");
		System.out.println(map.get(VMName) + " check1");
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String IP = (String) js.executeScript("return arguments[0].value;", NSPO.IPaddress());
		System.out.println(IP + " check");
		assertEquals(map.get(VMName), IP);
		assertTrue(
				NSPO.DurationDropdown().getOptions().stream().anyMatch(option -> option.getText().contains(duration)),
				"Given Value " + duration + " is not available in the dropdown");
		NSPO.DurationDropdown().selectByVisibleText(duration);
		assertEquals(NSPO.DurationDropdown().getFirstSelectedOption().getText(), duration);
		if (CancelOrSave.equalsIgnoreCase("Save")) {
			NSPO.PublicIpSubmitButton().click();
			try {
				wait.until(ExpectedConditions.visibilityOf(CPO.UserReatedErrorMessage()));
			} catch (Exception e) {
			}
			// System.out.println(CPO.UserReatedErrorMessage().getText()+" message");
			assertEquals(CPO.UserReatedErrorMessage().getText(), "Public IP request has been sent successfully");
			wait.until(ExpectedConditions.invisibilityOf(CPO.UserReatedErrorMessage()));
			NSPO.PublicIp().click();
			main: while (true) {
				Pair<Integer, List<WebElement>> VMNamesList = CPT.ListOfVmNames(NSPO.TableHeaderNames(),
						NSPO.TableId());
				// System.out.println(VMNamesList.getRight().size()+" Test 1");
				for (int i = 0; i < VMNamesList.getRight().size(); i++) {
					FirstName = String.valueOf(VMNamesList.getRight().get(i).getText());
					// System.out.println(String.valueOf(VMNamesList.getRight().get(i).getText()) +"
					// test 2");
					if (String.valueOf(VMNamesList.getRight().get(i).getText()).equalsIgnoreCase(VMName)) {
						// System.out.println(CPT.GetData(VMNamesList.getRight(), VMNamesList.getLeft(),
						// VMName, NSPO.TableHeaderNames(),"IP Address") +" test 3");
						assertEquals(CPT.GetData(VMNamesList.getRight(), VMNamesList.getLeft(), VMName,
								NSPO.TableHeaderNames(), "IP Address"), IP);
						Status = true;
						break main;
					}
				}
				if (!NSPO.PublicIpNextButton().getAttribute("class").contains("disabled")) {
					NSPO.PublicIpNextButton().click();
					wait.until(ExpectedConditions.not(
							ExpectedConditions.textToBePresentInElement(VMNamesList.getRight().get(0), FirstName)));
				} else
					break;
			}
			assertTrue(Status, "COULD find the created IP in the Table");
		} else
			NSPO.PublicIpCancelButton().click();
	}

	@Test(priority = 7, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = {
			"TestOpenPortAddRequest" }, dataProvider = "dataProvider")
	public void TestOpenPortAddRequest(String VMName,String portNumber,String Reason,String Type,String duration,String CancelOrSave) {
		CreatePageTests CPT = new CreatePageTests();
		String Status = null;
		String FirstName = null;
		String PublicIp = null;
		String error = null;
		int count = 0;
		JavascriptExecutor js=(JavascriptExecutor)driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.OpenPort()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.PublicIp()));
		NSPO.PublicIp().click();
		wait.until(ExpectedConditions.visibilityOf(NSPO.OpenPort()));
		main: while (true) {
			Pair<Integer, List<WebElement>> VMNamesList = CPT.ListOfVmNames(NSPO.TableHeaderNames(), NSPO.TableId());
			forloop: for (int j = 0; j < NSPO.TableHeaderNames().size(); j++) {
				// System.out.println(tableHeaderNames.get(j).getText().trim()+" header Names");
				if (NSPO.TableHeaderNames().get(j).getText().trim().equalsIgnoreCase("Ticket Status")) {
					// System.out.println(VMNames.size() + " VMNames.size()2");
					int num1 = (j + 1) - VMNamesList.getLeft();
					for (int j2 = 0; j2 < VMNamesList.getRight().size(); j2++) {
						// System.out.println(VMNames.get(j2).getText().trim()+", "+VMName);
						FirstName = VMNamesList.getRight().get(j2).getText();
						Status = VMNamesList.getRight().get(j2)
								.findElement(By.xpath("./following-sibling::td[" + num1 + "]")).getText();
						// System.out.println(Status+
						// j2+String.valueOf(VMNamesList.getRight().get(j2).getText()));
						if (Status.equalsIgnoreCase("Completed")) {
							map.put(String.valueOf(VMNamesList.getRight().get(j2).getText()), Status);
							for (int j1 = 0; j1 < NSPO.TableHeaderNames().size(); j1++) {
								if (NSPO.TableHeaderNames().get(j1).getText().trim()
										.equalsIgnoreCase("Public IP Address")) {
									// System.out.println(VMNames.size() + " VMNames.size()2");
									int num2 = (j1 + 1) - VMNamesList.getLeft();
									// System.out.println(num2);
									PublicIp = VMNamesList.getRight().get(j2)
											.findElement(By.xpath("./following-sibling::td[" + num2 + "]")).getText();
									// System.out.println(PublicIp+
									// j2+String.valueOf(VMNamesList.getRight().get(j2).getText()));
									Ipmap.put(String.valueOf(VMNamesList.getRight().get(j2).getText()), PublicIp);
									// System.out.println(Ipmap);
								}
							}
						}
					}
					break forloop;
				}
			}
			if (!NSPO.PublicIpNextButton().getAttribute("class").contains("disabled")) {
				NSPO.PublicIpNextButton().click();
				wait.until(ExpectedConditions
						.not(ExpectedConditions.textToBePresentInElement(VMNamesList.getRight().get(0), FirstName)));
			} else
				break main;
		}
		// System.out.println(map);
		NSPO.OpenPort().click();
		wait.until(ExpectedConditions.elementToBeClickable(NSPO.OpenPortAddRequestButton()));
		wait.until(ExpectedConditions.visibilityOf(NSPO.OpenPortAddRequestButton()));
		NSPO.OpenPortAddRequestButton().click();
		assertTrue(NSPO.OpenPortTitle().getText().equalsIgnoreCase("Open Port"), "Wrong Page opened");
		assertTrue(map.get(VMName).equalsIgnoreCase("Completed"),
				"Status is not completed in the public IP for the given VM " + VMName);
		assertTrue(
				NSPO.OpenPortVmNameDropdown().getOptions().stream()
						.anyMatch(option -> option.getText().contains(VMName)),
				"Given Value " + VMName + " is not available in the dropdown");
		NSPO.OpenPortVmNameDropdown().selectByVisibleText(VMName);
		assertEquals(NSPO.OpenPortVmNameDropdown().getFirstSelectedOption().getText(), VMName);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//select[@id='ip_address']/option[1]")));
		assertTrue(Ipmap.get(VMName).equalsIgnoreCase(NSPO.OpenPortPublicIpAddressDropdown().getFirstSelectedOption().getText()),
				"Ip is not matching with the IP presene in the public Ip Table");
		NSPO.OpenPortTextBox().sendKeys(portNumber);
		NSPO.Portlabel().click();
		if(NSPO.OpenPortTextBoxError().size()>0)
			error=NSPO.OpenPortTextBoxError().get(0).getText();
		assertFalse(NSPO.OpenPortTextBoxError().size()>0,error);
		NSPO.OpenPortreason().sendKeys(Reason);
		NSPO.TypeLabel().click();
		if(NSPO.ReasonTextAreaError().size()>0)
			error=NSPO.ReasonTextAreaError().get(0).getText();
		assertFalse(NSPO.ReasonTextAreaError().size()>0,error);
		if(Type.equalsIgnoreCase("TCP"))
			js.executeScript("arguments[0].click();", NSPO.TCPtype());
			
		else if(Type.equalsIgnoreCase("UDP"))
			js.executeScript("arguments[0].click();", NSPO.UDptype());
			
		else
			assertTrue(false,"Given Type is not there to select");
		assertTrue(
				NSPO.OpenPortDurationDropDown().getOptions().stream()
						.anyMatch(option -> option.getText().contains(duration)),
				"Given Value " + duration + " is not available in the dropdown");
		NSPO.OpenPortDurationDropDown().selectByVisibleText(duration);
		assertEquals(NSPO.OpenPortDurationDropDown().getFirstSelectedOption().getText(), duration);
		if(CancelOrSave.equalsIgnoreCase("Save"))
		{
			NSPO.OpenPortSubmitButton().click();
		}
		else if(CancelOrSave.equalsIgnoreCase("Cancel"))
			NSPO.OpenPortCancelButton().click();
		else
			assertTrue(false,"Given Option is not there to click");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 8, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = {
			"TestClosePortAddRequest" })
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

	@Test(priority = 9, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = {
			"TestVlanCommunicationAddRequest" })
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

	@Test(priority = 10, dependsOnMethods = "TestNetworkServicesOption", alwaysRun = false, groups = {
			"TestBandwidthAddRequest" })
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
		if (method.getName().equals("TestOpenPortAddRequest")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\Networkservices.xlsx",
					"OpenPortPage");
		}
		return null;
	}
}
