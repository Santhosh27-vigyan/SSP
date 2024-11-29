package create;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.asm.Advice.Return;

public class CreatePageObject {
	public WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	public CreatePageObject (WebDriver driver)
	{
		this.driver=driver;
		js=(JavascriptExecutor)driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}
	@FindBy(id = "location")
	private WebElement LocationOptions;
	@FindBy(id = "vmprojectname")
	private WebElement ProjectNames;
	@FindBy(id = "targetstoragepath")
	private WebElement StoragePathOptions;
	@FindBy(id = "vmos")
	private WebElement OSOptions;
	@FindBy(id = "iops")
	private WebElement IopsOptions;
	@FindBy(id = "customSwitches1")
	private WebElement HorizontalScalingButton;
	@FindBy(id = "horizontal-button-outer-div")
	private WebElement HorizontalScalingDiv;
	@FindBy(id = "customSwitches")
	private WebElement VerticalScalingButton;
	@FindBy(id = "vertical-button-outer-div")
	private WebElement VerticalScalingDiv;
	@FindBy(id = "vm_size_type_custom")
	private WebElement CustomRadioButton;
	@FindBy(id = "vm_size_type_favorite_template")
	private WebElement SavedTemplatesRadioButton;
	@FindBy(className = "vm-custom-size-selector-outer-div")
	private WebElement CustomSizeSelectorDiv;
	@FindBy(xpath = "//div[@class='form-group row vm-favorite-size-selector-outer-div']")
	private WebElement SelecttemplateDropdownDiv;
	@FindBy(id = "lower_threshold_selector")
	private WebElement LowerThreshold;
	@FindBy(id = "upper_threshold_selector")
	private WebElement UpperThreshold;
	public Select SelectLocation() {
		wait.until(ExpectedConditions.elementToBeClickable(LocationOptions));
		return new Select(LocationOptions);
		
	}

	public Select SelectProjectName() {
		wait.until(ExpectedConditions.elementToBeClickable(ProjectNames));
		return new Select(ProjectNames);
	}

	public Select SelectStoragePath() {
		wait.until(ExpectedConditions.elementToBeClickable(StoragePathOptions));
		return new Select(StoragePathOptions);	
	}
	public Select SelectOS() {
		js.executeScript("arguments[0].scrollIntoView(true);", OSOptions);
		return new Select(OSOptions);
	}

	public Select SelectIops() {
		js.executeScript("arguments[0].scrollIntoView(true);", IopsOptions);
		return new Select(IopsOptions);
	}
	public WebElement HorizonantalSwitch()
	{
		return HorizontalScalingButton;
	}
	public WebElement HorizonantalSwitchDiv()
	{
		return HorizontalScalingDiv;
	}
	public WebElement VerticalSwitch()
	{
		return VerticalScalingButton;
	}
	public WebElement VerticalSwitchDiv()
	{
		return VerticalScalingDiv;
	}
	public WebElement CustomRadioButton()
	{
		return CustomRadioButton;
	}
	public WebElement SavedTemplatesRadioButton()
	{
		return SavedTemplatesRadioButton;
	}
	public WebElement CustomSelectorDiv()
	{
		return CustomSizeSelectorDiv;
	}
	public WebElement SelectTemplateDropdownDiv()
	{
		return SelecttemplateDropdownDiv;
	}
	public WebElement UpperThreshold()
	{
		return UpperThreshold;
	}
	public Select LowerThreshold()
	{
		return new Select(LowerThreshold);
	}
}
