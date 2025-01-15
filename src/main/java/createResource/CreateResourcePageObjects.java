package createResource;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class CreateResourcePageObjects {
	public WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	public CreateResourcePageObjects(WebDriver driver)
	{
		this.driver=driver;
		js=(JavascriptExecutor)driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}
	@FindBy(id = "create_resource_menu")
	private WebElement ResourceManagement;
	@FindBy(id = "resource_list")
	private WebElement ResourceListOption;
	@FindBy(xpath = "//a[@data-widget='pushmenu']")
	private WebElement CollapseButton;
	@FindBy(linkText = "Manage")
	private WebElement ManageOption;
	@FindBy(linkText = "Dashboard")
	private WebElement  DashboardOption;
	@FindBy(partialLinkText  = "Feedback")
	private WebElement  FeedbackOption;
	@FindBy(id = "feedback_modal")
	private WebElement  FeedbackOptionModal;
	@FindBy(xpath = "//a[@title='Help']")
	private WebElement HelpButton;
	@FindBy(xpath = "//ul[contains(@class,'help-dropdown')]/a")
	private List<WebElement> HelpOptions;
	@FindBy(xpath = "//a[@title='Profile and settings']")
	private WebElement ProfileAndSettings;
	@FindBy(id = "filter-serach-bar")
	private WebElement SearchBar;
	@FindBy(id = "filter-button")
	private WebElement SearchIcon;
	@FindBy(xpath = "//div[@id='filter'] //h5[@class='mt-3']")
	private List< WebElement> FilteredResources;
	@FindBy(xpath = "//h5[@class='mt-3']")
	private List< WebElement> Resources;
	By ResourceCreateButton=By.xpath("./parent::div/parent::div/following-sibling::div/button/b[contains(text(),'Create')]");
	private WebElement CreateButton;
	By ResourceViewButton=By.xpath("./parent::div/parent::div/following-sibling::div/button/b[contains(text(),'View')]");
	private WebElement ViewButton;
	@FindBy(xpath = "//h1[@class='add-form-title']")
	private WebElement FormTitle;
	@FindBy(xpath = "//table[@id='virtual_machine_list_data_table'] //tr[@role='row']/th")
	private List<WebElement> TableHeaderNames;
	public WebElement ResourceManagementButton()
	{
		return ResourceManagement;
	}
	public WebElement ResourceListOption()
	{
		return ResourceListOption;
	}
	public WebElement CollapseButton()
	{
		return CollapseButton;
	}
	public WebElement ManageOption()
	{
		return ManageOption;
	}
	public WebElement DashboardOption()
	{
		return DashboardOption;
	}
	public WebElement FeedbackOption()
	{
		return FeedbackOption;
	}
	public WebElement FeedbackOptionModal()
	{
		return FeedbackOptionModal;
	}
	public WebElement HelpIcon()
	{
		return HelpButton;
	}
	public List<WebElement> HelpOptions()
	{
		return HelpOptions;
	}
	public WebElement ProfileAndSettingsDropdown()
	{
		return ProfileAndSettings;
	}
	public List<WebElement> ProfileAndSettingsDropdownOptions()
	{
		return ProfileAndSettingsDropdown().findElements(By.xpath("./following-sibling::ul/a"));
	}
	public WebElement SearchBox()
	{
		return SearchBar;
	}
	public WebElement SearchIcon()
	{
		return SearchIcon;
	}
	public List<WebElement> FilteredResources()
	{
		return FilteredResources;
	}
	public void ClickOnCreate(String ResourceName)
	{
		for (int i = 0; i < Resources.size(); i++) {
			if(Resources.get(i).getText().equalsIgnoreCase(ResourceName))
			{
				CreateButton=Resources.get(i).findElement(ResourceCreateButton);
				break;
			}
		}
		try {
			CreateButton.click();
		} catch (Exception e) {
			js.executeScript("window.scrollBy(0,500)");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public WebElement FormTitle()
	{
		return FormTitle;
	}
	public void ClickOnView(String ResourceName)
	{
		for (int i = 0; i < Resources.size(); i++) {
			if(Resources.get(i).getText().equalsIgnoreCase(ResourceName))
			{
				ViewButton=Resources.get(i).findElement(ResourceViewButton);
				break;
			}
		}
		try {
			ViewButton.click();
		} catch (Exception e) {
			js.executeScript("window.scrollBy(0,500)");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public List<WebElement> CheckResourceType(String HeaderName)
	{
		List<WebElement> ResourceTypes = null;
	main:	for (int i = 0; i < TableHeaderNames.size(); i++) {
			//System.out.println(TableHeaderNames.get(i).getText());
			if(TableHeaderNames.get(i).getText().equalsIgnoreCase(HeaderName))
			{
				int num=i+1;
				 ResourceTypes = driver.findElements(By.xpath("//table[@id='virtual_machine_list_data_table']/tbody/tr/td["+num+"]"));
				break main;
			}
		}
	return ResourceTypes;
	}
}
