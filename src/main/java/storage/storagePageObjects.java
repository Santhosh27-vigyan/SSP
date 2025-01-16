package storage;

import java.time.Duration;
import java.util.List;

import javax.xml.xpath.XPath;

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
	@FindBy(id = "storage_name")
	private WebElement StorageNameTextBox;
	@FindBy(xpath = "//label[@class='error']")
	private List<WebElement> StoragePageErrors;
	@FindBy(xpath = "//input[@id='storage_name']/following-sibling::label[@class='error']")
	private List<WebElement> ErrorsinStorageNameTextBox;
	@FindBy(xpath = "//span[@class='availability-status available']")
	private List<WebElement> AvailableMessage;
	@FindBy(xpath = "//span[@class='availability-status not-available']")
	private List<WebElement> NotAvailableMessage;
	@FindBy(xpath = "//span[@class='irs-handle single']")
	private WebElement TotalSizeButton;
	@FindBy(xpath = "//label[@for='total_storage_size_new']/parent::div //span[@class='irs-single']")
	private WebElement SizeValue;
	@FindBy(xpath = "//label[@for='total_storage_size_new']/parent::div //span[@class='irs-min']")
	private WebElement SizeValueMin;
	@FindBy(xpath = "//label[@for='total_storage_size_new']/parent::div //span[@class='irs-max']")
	private WebElement SizeValueMax;
	@FindBy(id = "createStoragebuttom")
	private WebElement AddButton;
	@FindBy(xpath = "//button[contains(@onclick,'cancel_add_operation')]")
	private WebElement CancelButton;
	@FindBy(xpath = "//table[@id='storage_list_data_table']/thead/tr/th")
	private List<WebElement> TableHeaderNames;
	@FindBy(xpath = "//div[contains(@class,'storage_list_view')]/h1[text()='VM List']")
	private WebElement StorageViewPage;

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

	public WebElement StorageName() {
		return StorageNameTextBox;
	}

	public List<WebElement> ErrorsOnStoragePage() {
		return StoragePageErrors;
	}

	public List<WebElement> ErrorInStorageName() {
		return ErrorsinStorageNameTextBox;
	}

	public List<WebElement> AvailableMessage() {
		return AvailableMessage;
	}

	public List<WebElement> NotAvailableMessage() {
		return NotAvailableMessage;
	}

	public WebElement TotalSizeSlider() {
		return TotalSizeButton;
	}

	public WebElement SizeValue() {
		return SizeValue;
	}

	public WebElement SizeValueMin() {
		return SizeValueMin;
	}

	public WebElement SizeValueMax() {
		return SizeValueMax;
	}

	public WebElement CancelButton() {
		return CancelButton;
	}

	public WebElement AddButton() {
		return AddButton;
	}

	public List<WebElement> TableHeaderNames() {
		return TableHeaderNames;
	}
	public WebElement STorageViewPage()
	{
		return StorageViewPage;
	}
}
