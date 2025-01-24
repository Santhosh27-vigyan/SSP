package utility;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NetworkServicesPageObjects {
	public WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	public NetworkServicesPageObjects(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "support_menu")
	private WebElement UtilityOption;
	@FindBy(id = "network_services_menu_item")
	private WebElement NetworkServicesOption;
	@FindBy(id = "custom-tabs-four-createVPN-tab")
	private WebElement CreateVPNUser;
	@FindBy(id = "custom-tabs-four-home-tab")
	private WebElement OpenPort;
	@FindBy(id = "custom-tabs-four-profile-tab")
	private WebElement ClosePort;
	@FindBy(id = "custom-tabs-four-vlan-tab")
	private WebElement VlanCommunication;
	@FindBy(id = "custom-tabs-four-publicIP-tab")
	private WebElement PublicIp;
	@FindBy(id = "custom-tabs-four-bandwidth-tab")
	private WebElement Bandwidth;
	@FindBy(xpath = "//div[@id='custom-tabs-four-createVPN'] //button[contains(@class,'create-new-button')]")
	private WebElement createVPNAddRequestButton;
	@FindBy(xpath = "//div[@id='custom-tabs-four-home'] //button[contains(@class,'create-new-button')]")
	private WebElement OpenPortAddRequestButton;
	@FindBy(xpath = "//div[@id='custom-tabs-four-profile'] //button[contains(@class,'create-new-button')]")
	private WebElement ClosePortAddRequestButton;
	@FindBy(xpath = "//div[@id='custom-tabs-four-vlan'] //button[contains(@class,'create-new-button')]")
	private WebElement VlanCommunicationAddRequestButton;
	@FindBy(xpath = "//div[@id='custom-tabs-four-publicIP'] //button[contains(@class,'create-new-button')]")
	private WebElement PublicIPAddRequestButton;
	@FindBy(xpath = "//div[@id='custom-tabs-four-bandwidth'] //button[contains(@class,'create-new-button')]")
	private WebElement BandwidthAddRequestButton;
	@FindBy(xpath = "//form[@class='form-horizontal'] //h3[@id='form-title']")
	private WebElement OpenPortTitle;
	@FindBy(xpath = "//form[@class='form-horizontal-close-port'] //h3[@id='form-title']")
	private WebElement ClosePortTitle;
	@FindBy(xpath = "//form[@class='form-horizontal-vlan'] //h3[@id='form-title']")
	private WebElement VlanCommunicationsTitle;
	@FindBy(xpath = "//form[@class='form-horizontal-publicIP'] //h3[@id='form-title']")
	private WebElement PublicIPTitle;
	@FindBy(xpath = "//form[@class='form-horizontal-bandwidth'] //h3[@id='form-title']")
	private WebElement BandwidthTitle;
	@FindBy(id = "vm_name2")
	private WebElement VMNameDropdown;
	@FindBy(id = "ip_address2")
	private WebElement IPaddress;
	@FindBy(id = "duration3")
	private WebElement DurationDropdown;
	@FindBy(xpath = "//button[@onclick='addNetwork_Service('open_port')']")
	private WebElement OpenPortSubmitButton;
	@FindBy(xpath = "//form[@class='form-horizontal'] //button[contains(@onclick,'cancel_edit_operation')]")
	private WebElement OpenPortCancelButton;
	@FindBy(xpath = "//button[@onclick='addNetwork_Service('close_port')']")
	private WebElement ClosePortSubmitButton;
	@FindBy(xpath = "//form[@class='form-horizontal-close-port'] //button[contains(@onclick,'cancel_edit_operation')]")
	private WebElement ClosePortCancelButton;
	@FindBy(xpath = "//button[@onclick='addVlan()']")
	private WebElement VlanCommunicationSubmitButton;
	@FindBy(xpath = "//form[@class='form-horizontal-vlan'] //button[contains(@onclick,'cancel_edit_operation')]")
	private WebElement VlanCommunicationCancelButton;
	@FindBy(xpath = "//button[@onclick='addPublicIP()']")
	private WebElement PublicIpSubmitButton;
	@FindBy(xpath = "//form[@class='form-horizontal-publicIP'] //button[contains(@onclick,'cancel_edit_operation')]")
	private WebElement PublicIpCancelButton;
	@FindBy(xpath = "//button[@onclick='changeBandwidth()']")
	private WebElement BandwidthSubmitButton;
	@FindBy(xpath = "//form[@class='form-horizontal-bandwidth'] //button[contains(@onclick,'cancel_edit_operation')]")
	private WebElement BandwidthCancelButton;
	@FindBy(xpath = "//table[@id='custom-tabs-four-publicIP-table'] //tr[@role='row']/th")
	private List<WebElement> TableHeaderNames;
	private String TableId="custom-tabs-four-publicIP-table"; //from TableHeaderNames Xpath
	

	public WebElement UtilityOption() {
		return UtilityOption;
	}

	public WebElement NetworkServicesOption() {
		return NetworkServicesOption;
	}

	public WebElement CreateVPNUser() {
		return CreateVPNUser;
	}

	public WebElement OpenPort() {
		return OpenPort;
	}

	public WebElement ClosePort() {
		return ClosePort;
	}

	public WebElement VlanCommunication() {
		return VlanCommunication;
	}

	public WebElement PublicIp() {
		return PublicIp;
	}

	public WebElement Bandwidth() {
		return Bandwidth;
	}

	public WebElement createVPNAddRequestButton() {
		return createVPNAddRequestButton;
	}

	public WebElement OpenPortAddRequestButton() {
		return OpenPortAddRequestButton;
	}

	public WebElement ClosePortAddRequestButton() {
		return ClosePortAddRequestButton;
	}

	public WebElement VlanCommunicationAddRequestButton() {
		return VlanCommunicationAddRequestButton;
	}

	public WebElement PublicIPAddRequestButton() {
		return PublicIPAddRequestButton;
	}

	public WebElement BandwidthAddRequestButton() {
		return BandwidthAddRequestButton;
	}

	public WebElement OpenPortTitle() {
		return OpenPortTitle;
	}

	public WebElement ClosePortTitle() {
		return ClosePortTitle;
	}

	public WebElement VlanCommunicationsTitle() {
		return VlanCommunicationsTitle;
	}

	public WebElement PublicIPTitle() {
		return PublicIPTitle;
	}

	public WebElement BandwidthTitle() {
		return BandwidthTitle;
	}

	public Select VMNameDropdown() {
		return new Select(VMNameDropdown);
	}
	public WebElement IPaddress()
	{
		return IPaddress;
	}
	public Select  DurationDropdown()
	{
		return new Select(DurationDropdown) ;
	}
	public WebElement OpenPortSubmitButton()
	{
		return OpenPortSubmitButton;
	}
	public WebElement OpenPortCancelButton()
	{
		return OpenPortCancelButton;
	}
	public WebElement ClosePortSubmitButton()
	{
		return ClosePortSubmitButton;
	}
	public WebElement ClosePortCancelButton()
	{
		return ClosePortCancelButton;
	}
	public WebElement VlanCommunicationSubmitButton()
	{
		return VlanCommunicationSubmitButton;
	}
	public WebElement VlanCommunicationCancelButton()
	{
		return VlanCommunicationCancelButton;
	}
	public WebElement PublicIpSubmitButton()
	{
		return PublicIpSubmitButton;
	}
	public WebElement PublicIpCancelButton()
	{
		return PublicIpCancelButton;
	}
	public WebElement BandwidthSubmitButton()
	{
		return BandwidthSubmitButton;
	}
	public WebElement BandwidthCancelButton()
	{
		return BandwidthCancelButton;
	}
	public List<WebElement> TableHeaderNames()
	{
		return TableHeaderNames;
	}
	public String TableId()
	{
		return TableId;
	}

}
