package storage;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class storagePageObjects {
	public WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	public storagePageObjects(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "create_resource_menu")
	private WebElement ResourceManagement;
	@FindBy(id = "add_storage_menu_item")
	private WebElement StorageOption;
	@FindBy(xpath = "//button[contains(@class,'create-new-button')]")
	private WebElement AddNewStorage;
	@FindBy(xpath = "//h1[text()='Add New Storage']")
	private WebElement AddStorageHeading;
	@FindBy(id = "dc_location_zone_new")
	private WebElement ZoneDropdown;
	@FindBy(id = "Linux_new")
	private WebElement LinuxRadioButton;
	@FindBy(id = "Windows_new")
	private WebElement WindowsRadioButton;
	@FindBy(id = "pool_new")
	private WebElement TypeDropdown;

	public WebElement ResourceManagementOption() {
		return ResourceManagement;
	}

	public WebElement StorageOptionButton() {
		return StorageOption;
	}

	public WebElement AddnewStorageButton() {
		return AddNewStorage;
	}

	public WebElement AddStorageHeading() {
		return AddStorageHeading;
	}

	public Select SelectZone() {
		Select Zones = new Select(ZoneDropdown);
		return Zones;
	}

	public WebElement SelectLinuxRadioButton() {
		return LinuxRadioButton;
	}

	public WebElement SelectWindowsRadioButton() {
		return WindowsRadioButton;
	}

	public Select SelectType() {
		Select Types = new Select(TypeDropdown);
		return Types;
	}
}
