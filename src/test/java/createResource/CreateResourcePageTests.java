package createResource;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Login.LoginPageTests;
import setUpAndTearDown.SetAndDown;
import utilities.ExcelUtils;

public class CreateResourcePageTests extends SetAndDown {
	@Test(groups = "CertAndLogin", priority = 1)
	public void CertAndLogin() throws IOException {
		LoginPageTests LPT = new LoginPageTests();
		LPT.TestLoginWithValidCredentails(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(groups = "TestCollapseButton", priority = 2)
	public void TestCollapseButton() {
		assertTrue(CRPO.ResourceManagementButton().isDisplayed());
		CRPO.CollapseButton().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(CRPO.ResourceManagementButton())));
		} catch (Exception e) {
		}
		assertTrue(!CRPO.ResourceManagementButton().isDisplayed(), "Options not collapsed");
	}

	@Test(groups = "TestManageOption", priority = 2)
	public void TestManageOption() {
		CRPO.ManageOption().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("Services")));
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud")));
		} catch (Exception e) {
		}
		assertEquals(driver.getTitle(), "Services - IPM+ Cloud - Dashboards - Grafana");
	}

	@Test(groups = "TestDashboardOption", priority = 2)
	public void TestDashboardOption() {
		CRPO.DashboardOption().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("Services")));
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("IPM+ Cloud")));
		} catch (Exception e) {
		}
		assertEquals(driver.getTitle(), "Dashboard");
	}

	@Test(groups = "TestFeedbackOption", priority = 2)
	public void TestFeedbackOption() {
		CRPO.FeedbackOption().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
			wait.until(ExpectedConditions.visibilityOf(CRPO.FeedbackOptionModal()));
		} catch (Exception e) {
		}
		assertTrue(CRPO.FeedbackOptionModal().isDisplayed(), "Feedback page didn't appear");
	}

	@Test(groups = "TestHelpIcon", priority = 2, dataProvider = "dataProvider")
	public void TestHelpIcon(String Option1, String Option2, String Option3) {
		CRPO.HelpIcon().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
			wait.until(ExpectedConditions.attributeToBe(CRPO.HelpIcon(), "aria-expanded", "true"));
		} catch (Exception e) {
		}
		assertEquals(CRPO.HelpOptions().get(0).getText().trim(), Option1);
		assertEquals(CRPO.HelpOptions().get(1).getText().trim(), Option2);
		assertEquals(CRPO.HelpOptions().get(2).getText().trim(), Option3);
	}

	@Test(groups = "TestprofileAndSettingsOption", priority = 2, dataProvider = "dataProvider")
	public void TestprofileAndSettingsOption(String Option1, String Option2) {
		CRPO.ProfileAndSettingsDropdown().click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
			wait.until(ExpectedConditions.attributeToBe(CRPO.ProfileAndSettingsDropdown(), "aria-expanded", "true"));
		} catch (Exception e) {
		}
		assertEquals(CRPO.ProfileAndSettingsDropdownOptions().get(0).getText().trim(), Option1);
		assertEquals(CRPO.ProfileAndSettingsDropdownOptions().get(1).getText().trim(), Option2);
	}

	@Test(groups = "TestSearchBox", priority = 2, dataProvider = "dataProvider")
	public void TestSearchBox(String SearchText) {
		CRPO.SearchBox().sendKeys(SearchText);
	}

	@Test(groups = "TestSearchIconWithResourceNotInTheList", priority = 2, dataProvider = "dataProvider")
	public void TestSearchIconWithResourceNotInTheList(String TextToSearch) {
		// Minimum 3 characters to be sent for search to work and it is case sensitive.
		TestSearchBox(TextToSearch);
		CRPO.SearchIcon().click();
		assertFalse(CRPO.FilteredResources().size() != 0,
				"Search is showing results even for the resource not in the list");
	}

	@Test(groups = "TestSearchIconWithResourceInTheList", priority = 2, dataProvider = "dataProvider")
	public void TestSearchIconWithResourceInTheList(String TextToSearch) {
		// Minimum 3 characters to be sent for search to work and it is case sensitive.
		TestSearchBox(TextToSearch);
		CRPO.SearchIcon().click();
		assertTrue(CRPO.FilteredResources().size() == 1,
				"Search is Either not showing results even for the resource Present in the list or showing more than 1 Result");
		assertEquals(CRPO.FilteredResources().get(0).getText(), TextToSearch);
	}

	@Test(groups = "TestSearchIconWithPartialResourceNameInTheList", priority = 2, dataProvider = "dataProvider")
	public void TestSearchIconWithPartialResourceNameInTheList(String TextToSearch) {
		// Minimum 3 characters to be sent for search to work and it is case sensitive.
		TestSearchBox(TextToSearch);
		CRPO.SearchIcon().click();
		assertTrue(CRPO.FilteredResources().size() >= 1,
				"Search is not showing results even for the resource Present in the list");
		for (int i = 0; i < CRPO.FilteredResources().size(); i++) {
			assertTrue(CRPO.FilteredResources().get(i).getText().contains(TextToSearch),
					"Resource name does not contain the Partial String");
		}
	}

	@Test(groups = "TestCreateReource", priority = 2, dataProvider = "dataProvider")
	public void TestCreateReource(String ResourceName) {
		CRPO.ClickOnCreate(ResourceName);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("Services")));
		} catch (Exception e) {
		}
		assertEquals(driver.getTitle(), "IPM+ Cloud");
		assertTrue(CRPO.FormTitle().getText().contains(ResourceName),
				"Form is not opened for Given Resourcename " + ResourceName);
	}

	@Test(groups = "TestViewReource", priority = 2, dataProvider = "dataProvider")
	public void TestViewReource(String ResourceName) {
		CRPO.ClickOnView(ResourceName);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.titleContains("Services")));
		} catch (Exception e) {
		}
		assertEquals(driver.getTitle(), "Virtual Machines");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < CRPO.CheckResourceType().size(); i++) {
			assertTrue(CRPO.CheckResourceType().get(i).getText().equals(ResourceName),
					"Table Showing the Details of the resources Types other the " + ResourceName);
		}
	}

	@DataProvider
	public Object[][] dataProvider(Method method) throws IOException {
		if (method.getName().equals("TestHelpIcon")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"HelpOptions");
		}
		if (method.getName().equals("TestprofileAndSettingsOption")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"SettingOptions");
		}
		if (method.getName().equals("TestSearchBox")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"SearchBoxText");
		}
		if (method.getName().equals("TestSearchIconWithResourceNotInTheList")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"ResNameToSearchInvalid");
		}
		if (method.getName().equals("TestSearchIconWithResourceInTheList")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"ResNameToSearchValid");
		}
		if (method.getName().equals("TestSearchIconWithPartialResourceNameInTheList")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"PartialResNameToSearch");
		}
		if (method.getName().equals("TestCreateReource")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"ResourceNameToCreate");
		}
		if (method.getName().equals("TestViewReource")) {
			return ExcelUtils.GetExcelData(
					System.getProperty("user.dir") + "\\src\\main\\java\\utilities\\CreateResourcePage.xlsx",
					"ResourceNameToView");
		}
		return null;
	}
}
