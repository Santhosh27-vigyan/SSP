package create;

import java.time.Duration;
import java.util.List;

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
	@FindBy(id = "vm-favorite-size-selector")
	private WebElement SavedTemplates;
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
	@FindBy(xpath = "//label[@for='auto_target_disk_size']")
	private WebElement DiskLable;
	@FindBy(xpath = "//label[@for='target_disk_size']/parent::div //span[@class='irs-handle single']")
	private WebElement DiskSizeScalingButton;
	@FindBy(xpath = "//label[@for='auto_target_disk_size']/parent::div //span[@class='irs-handle single']")
	private WebElement VerticalScalingDiskSizeScalingButton;
	@FindBy(xpath = "//label[@for='target_disk_size']/parent::div //span[@class='irs-single']")
	private WebElement DiskSizeValue;
	@FindBy(xpath = "//label[@for='target_disk_size']/parent::div //span[@class='irs-min']")
	private WebElement DiskSizeValueMin;
	@FindBy(xpath = "//label[@for='target_disk_size']/parent::div //span[@class='irs-max']")
	private WebElement DiskSizeValueMax;
	@FindBy(xpath = "//label[@for='auto_target_disk_size']/parent::div //span[@class='irs-single']")
	private WebElement VerticalScalingDiskSizeValue;
	@FindBy(xpath = "//label[@for='auto_target_disk_size']/parent::div //span[@class='irs-min']")
	private WebElement VerticalScalingDiskSizeValueMin;
	@FindBy(xpath = "//label[@for='auto_target_disk_size']/parent::div //span[@class='irs-max']")
	private WebElement VerticalScalingDiskSizeValueMax;
	@FindBy(xpath = "//span[@class='availability-status-resource']")
	private WebElement InsufficienterrorMessage;
	@FindBy(id = "vmHzSwitchesLB")
	private WebElement LoadBalancerButton;
	@FindBy(xpath = "//label[@for='favorite_checkbox']")
	private WebElement TemplateCheckBox;
	@FindBy(id = "decrease1")
	private WebElement decreaseButton;
	@FindBy(id = "increase1")
	private WebElement IncreaseButton;
	@FindBy(xpath = "//h4[text()='VM Backup ']")
	private WebElement VMBackUpLabel;
	@FindBy(xpath = "//label[@for='vm_backup_interval']")
	private WebElement BackUpfrequencyLabel;
	@FindBy(id = "vmBackupSwitches")
	private WebElement VMBackupButton;
	@FindBy(id = "vm_backup_interval")
	private WebElement BackUpFrequencyOptions;
	@FindBy(id = "retention")
	private WebElement RetentionPeriodOptions;
	@FindBy(id = "SIEMcustomSwitches")
	private WebElement SIEMSwitch;
	@FindBy(className = "availability-status-siem")
	private WebElement SIEMErrorMessage;
	@FindBy(id = "siem-vm-list")
	private WebElement SIEMVmList;
	@FindBy(xpath = "//div[@class='siem-vm-list']")
	private WebElement SIEMDivClass;
	@FindBy(id = "vlanname")
	private WebElement VlanOptions;
	@FindBy(xpath = "//input[contains(@id,'new') and contains(@name,'new')]")
	private List<WebElement> VmNameTextBox;
	@FindBy(xpath = "//input[contains(@id,'new')]/following-sibling::span[contains(@class,'availability-status') and contains(@class,'available')]")
	private List<WebElement> AvailabityMessage;
	@FindBy(id = "number")
	private WebElement NumberOfVirtualMachines;
	@FindBy(xpath = "//div[@id='vmNumber'] //div[@id='decrease']")
	private WebElement VMDecreaseButton;
	@FindBy(xpath = "//div[@id='vmNumber'] //div[@id='increase']")
	private WebElement VMIncreaseButton;
	@FindBy(xpath = "//label[@class='error']")
	private List<WebElement> AllErrorMessage;
	@FindBy(id = "create_vm")
	private WebElement CreateButton;
	@FindBy(id = "assign_user")
	private WebElement AssignRemoteUserButton;
	@FindBy(id = "selectall")
	private WebElement SelectAllUsersButton;
	@FindBy(xpath = "//input[contains(@class,'singlecheck')]")
	private List<WebElement> UserNames;
	@FindBy(xpath = "//input[@type='radio' and @value='0']")
	private List<WebElement> AdminRadioButtons;
	@FindBy(xpath = "//input[@type='radio' and @value='1']")
	private List<WebElement> UserRadioButtons;
	@FindBy(className = "toast-message")
	private WebElement UserReatedErrorMessage;
	@FindBy(id = "save_roles")
	private WebElement SaverolesButton;
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
	public Select SavedTemplates()
	{
		return new Select(SavedTemplates);
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
	public WebElement DiskSizeScalingButton()
	{
		return DiskSizeScalingButton;
	}
	public WebElement DiskSizeValueMin()
	{
		return DiskSizeValueMin;
	}
	public WebElement DiskSizeValueMax()
	{
		return DiskSizeValueMax;
	}
	public WebElement DiskSizeValue()
	{
		return DiskSizeValue;
	}
	public WebElement InsufficienterrorMessage()
	{
		return InsufficienterrorMessage;
	}
	public WebElement DiskLable()
	{
		return DiskLable;
	}
	public WebElement VerticalScalingDiskSizeValueMin()
	{
		return VerticalScalingDiskSizeValueMin;
	}
	public WebElement VerticalScalingDiskSizeValueMax()
	{
		return VerticalScalingDiskSizeValueMax;
	}
	public WebElement VerticalScalingDiskSizeValue()
	{
		return VerticalScalingDiskSizeValue;
	}
	public WebElement VerticalScalingDiskSizeScalingButton()
	{
		return VerticalScalingDiskSizeScalingButton;
	}
	public WebElement LoadBalancerButton()
	{
		return LoadBalancerButton;
	}
	public WebElement TemplateCheckBox()
	{
		return TemplateCheckBox;
	}
	public WebElement decreaseButton()
	{
		return decreaseButton;
	}
	public WebElement IncreaseButton()
	{
		return IncreaseButton;
	}
	public WebElement BackUpfrequencyLabel()
	{
		return BackUpfrequencyLabel;
	}
	public WebElement VMBackupButton()
	{
		return VMBackupButton;
	}
	public Select BackUpFrequencyOptions()
	{
		return new Select(BackUpFrequencyOptions);
	}
	public WebElement VMBackUpLabel()
	{
		return VMBackUpLabel;
	}
	public Select RetentionPeriodOptions()
	{
		return new Select(RetentionPeriodOptions) ;
	}
	public WebElement SIEMDivClass()
	{
		return SIEMDivClass;
	}
	public WebElement SIEMSwitch()
	{
		return SIEMSwitch;
	}
	public WebElement SIEMErrorMessage()
	{
		return SIEMErrorMessage;
	}
	public Select SIEMVmList()
	{
		return new Select(SIEMVmList);
	}
	public Select VlanOptions()
	{
		return new Select(VlanOptions);
	}
	public WebElement NumberOfVirtualMachines()
	{
		return NumberOfVirtualMachines;
	}
	public WebElement VMDecreaseButton()
	{
		return VMDecreaseButton;
	}
	public WebElement VMIncreaseButton()
	{
		return VMIncreaseButton;
	}
	public List<WebElement> VmNameTextBox()
	{
		return VmNameTextBox;
	}
	public WebElement CreateButton()
	{
		return CreateButton;
	}
	public List<WebElement> AvailabityMessage()
	{
		return AvailabityMessage;
	}
	public WebElement AssignRemoteUserButton()
	{
		return AssignRemoteUserButton;
	}
	public WebElement SelectAllUsersButton()
	{
		return SelectAllUsersButton;
	}
	public List<WebElement> UserNames()
	{
		return UserNames;
	}
	public List<WebElement> AdminRadioButtons()
	{
		return AdminRadioButtons;
	}
	public List<WebElement> UserRadioButtons()
	{
		return UserRadioButtons;
	}
	public List<WebElement> AllErrorMessage()
	{
		return AllErrorMessage;
	}
	public WebElement UserReatedErrorMessage()
	{
		return UserReatedErrorMessage;
	}
	public WebElement SaverolesButton()
	{
		return SaverolesButton;
	}
}
