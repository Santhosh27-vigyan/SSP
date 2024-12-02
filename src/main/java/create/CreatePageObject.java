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

	public CreatePageObject(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
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
	@FindBy(xpath = "//label[@for='vcpu']/parent::div //span[@class='irs-handle single']")
	private WebElement VcpuScalingButton;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[contains(@class,'irs-handle from')]")
	private WebElement VerticalVcpuScalingFromButton;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[contains(@class,'irs-handle to')]")
	private WebElement VerticalVcpuScalingToButton;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[contains(@class,'irs-min')]")
	private WebElement VerticalVcpuScalingMin;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[contains(@class,'irs-max')]")
	private WebElement VerticalVcpuScalingMax;
	@FindBy(xpath = "//label[@for='vcpu']/parent::div //span[@class='irs-single']")
	private WebElement VcpuValue;
	@FindBy(xpath = "//label[@for='vcpu']/parent::div //span[@class='irs-min']")
	private WebElement VcpuValueMin;
	@FindBy(xpath = "//label[@for='vcpu']/parent::div //span[@class='irs-max']")
	private WebElement VcpuValueMax;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[@class='irs-from']")
	private WebElement VerticalScalingVcpuFromValue;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[@class='irs-to']")
	private WebElement VerticalScalingVcpuToValue;
	@FindBy(xpath = "//label[@for='vcpu_range_1']/parent::div //span[@class='irs-single']")
	private WebElement VerticalScalingVcpuFromToValue;
	@FindBy(xpath = "//div[@role='dialog']")
	private WebElement Alert;
	@FindBy(xpath = "//button[contains(@class,'bootbox-accept')]")
	private WebElement AlertOkButton;
	@FindBy(className = "bootbox-body")
	private WebElement AlertMessage;
	@FindBy(xpath = "//label[@for='ram']")
	private WebElement RAMLable;
	@FindBy(xpath = "//label[@for='ram']/parent::div //span[@class='irs-handle single']")
	private WebElement RamScalingButton;
	@FindBy(xpath = "//label[@for='ram']/parent::div //span[@class='irs-single']")
	private WebElement RamValue;
	@FindBy(xpath = "//label[@for='ram']/parent::div //span[@class='irs-min']")
	private WebElement RamValueMin;
	@FindBy(xpath = "//label[@for='ram']/parent::div //span[@class='irs-max']")
	private WebElement RamValueMax;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[contains(@class,'irs-handle from')]")
	private WebElement VerticalRamScalingFromButton;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[contains(@class,'irs-handle to')]")
	private WebElement VerticalRamScalingToButton;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[@class='irs-from']")
	private WebElement VerticalScalingRamFromValue;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[@class='irs-to']")
	private WebElement VerticalScalingRamToValue;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[@class='irs-single']")
	private WebElement VerticalScalingRamFromToValue;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[@class='irs-min']")
	private WebElement VerticalScalingRamValueMin;
	@FindBy(xpath = "//label[@for='ram_range_1']/parent::div //span[@class='irs-max']")
	private WebElement VerticalScalingRamValueMax;
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

	public WebElement HorizonantalSwitch() {
		return HorizontalScalingButton;
	}

	public WebElement HorizonantalSwitchDiv() {
		return HorizontalScalingDiv;
	}

	public WebElement VerticalSwitch() {
		return VerticalScalingButton;
	}

	public WebElement VerticalSwitchDiv() {
		return VerticalScalingDiv;
	}

	public WebElement CustomRadioButton() {
		return CustomRadioButton;
	}

	public WebElement SavedTemplatesRadioButton() {
		return SavedTemplatesRadioButton;
	}

	public WebElement CustomSelectorDiv() {
		return CustomSizeSelectorDiv;
	}

	public WebElement SelectTemplateDropdownDiv() {
		return SelecttemplateDropdownDiv;
	}

	public Select UpperThreshold() {
		return new Select(UpperThreshold);
	}

	public Select LowerThreshold() {
		return new Select(LowerThreshold);
	}
	public WebElement VcpuValueMin()
	{
		return VcpuValueMin;
	}
	public WebElement VcpuValueMax()
	{
		return VcpuValueMax;
	}
	public WebElement VcpuScalingButton()
	{
		return VcpuScalingButton;
	}
	public WebElement VcpuValue()
	{
		return VcpuValue;
	}
	public WebElement VerticalVcpuScalingMin()
	{
		return VerticalVcpuScalingMin;
	}
	public WebElement VerticalVcpuScalingToButton()
	{
		return VerticalVcpuScalingToButton;
	}
	public WebElement VerticalVcpuScalingMax()
	{
		return VerticalVcpuScalingMax;
	}
	public WebElement VerticalScalingVcpuToValue()
	{
		return VerticalScalingVcpuToValue;
	}
	public WebElement VerticalScalingVcpuFromValue()
	{
		return VerticalScalingVcpuFromValue;
	}
	public WebElement AlertOkButton()
	{
		return AlertOkButton;
	}
	public WebElement AlertMessage()
	{
		return AlertMessage;
	}
	public WebElement VerticalVcpuScalingFromButton()
	{
		return VerticalVcpuScalingFromButton;
	}
	public WebElement Alert()
	{
		return Alert;
	}
	public WebElement RAMLable()
	{
		return RAMLable;
	}
	public WebElement RamValueMin()
	{
		return RamValueMin;
	}
	public WebElement RamValueMax()
	{
		return RamValueMax;
	}
	public WebElement RamValue()
	{
		return RamValue;
	}
	public WebElement RamScalingButton()
	{
		return RamScalingButton;
	}
	public WebElement VerticalScalingRamValueMax()
	{
		return VerticalScalingRamValueMax;
	}
	public WebElement VerticalRamScalingToButton()
	{
		return VerticalRamScalingToButton;
	}
	public WebElement VerticalScalingRamToValue()
	{
		return VerticalScalingRamToValue;
	}
	public WebElement VerticalScalingRamValueMin()
	{
		return VerticalScalingRamValueMin;
	}
	public WebElement VerticalRamScalingFromButton()
	{
		return VerticalRamScalingFromButton;
	}
	public WebElement VerticalScalingRamFromValue()
	{
		return VerticalScalingRamFromValue;
	}
	
}
