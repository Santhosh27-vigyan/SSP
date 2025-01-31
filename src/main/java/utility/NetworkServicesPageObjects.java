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
	@FindBy(xpath = "//form[@class='form-horizontal'] //button[contains(@onclick,'open_port')]")
	private WebElement OpenPortSubmitButton;
	@FindBy(xpath = "//form[@class='form-horizontal'] //button[contains(@onclick,'cancel_edit_operation')]")
	private WebElement OpenPortCancelButton;
	@FindBy(xpath = "//form[@class='form-horizontal-close-port'] //button[contains(@onclick,'close_port')]")
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
	@FindBy(xpath = "//li[@id='custom-tabs-four-publicIP-table_next']")
	private WebElement PublicIpNextButton;
	@FindBy(id = "vm_name")
	public WebElement OpenPortVMName;
	@FindBy(id = "ip_address")
	private WebElement OpenPortPublicIpAddress;
	@FindBy(id = "open_port")
	private WebElement OpenPortTextBox;
	@FindBy(xpath = "//label[@for='open_port']")
	private WebElement Portlabel;
	@FindBy(xpath = "//input[@id='open_port']/following-sibling::label")
	private List<WebElement> OpenPortTextBoxError;
	@FindBy(xpath = "//input[@id='open_port']/following-sibling::small")
	private WebElement OpenPortTextBoxError2;
	@FindBy(id = "reason")
	private WebElement OpenPortreason;
	@FindBy(xpath = "//label[@for='vm-size-selector']")
	private WebElement TypeLabel;
	@FindBy(xpath = "//textarea[@id='reason']/following-sibling::label")
	private List<WebElement> ReasonTextAreaError;
	@FindBy(id = "tcp")
	private WebElement TCPtype;
	@FindBy(id = "udp")
	private WebElement UDPtype;
	@FindBy(id = "duration")
	private WebElement OpenPortDuration;
	@FindBy(id = "vm_name1")
	private WebElement ClosePortVMName;
	@FindBy(xpath = "//form[@class='form-horizontal-close-port'] //small[@id='portError1']")
	private WebElement ClosePortPortError;
	@FindBy(id = "reason1")
	private WebElement ClosePortReasonTextArea;
	@FindBy(xpath = "//textarea[@id='reason1']/following-sibling::label")
	private List<WebElement> ClosePortReasonTextAreaError;
	@FindBy(xpath = "//form[@class='form-horizontal-close-port'] //label[@for='reason1']")
	private WebElement ClosePortReasonLabel;
	@FindBy(id = "tcp1")
	private WebElement ClosePortTCPtype;
	@FindBy(id = "udp1")
	private WebElement ClosePortUDPtype;
	@FindBy(id = "duration4")
	private WebElement ClosePortDuration;
	@FindBy(id = "from_vlan")
	private WebElement FromVlan;
	@FindBy(id = "to_vlan")
	private WebElement ToVlan;
	@FindBy(id = "unidirectional")
	private WebElement UniDirectionalType;
	@FindBy(id  = "bidirectional")
	private WebElement BiDirectionalType;
	@FindBy(id = "duration2")
	private WebElement VlanCommunicationDuration;
	@FindBy(xpath = "//form[@class='form-horizontal-vlan'] //label[@class='error']")
	private List<WebElement> VlanCommunicationErrors;
	
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
	public WebElement PublicIpNextButton()
	{
		return PublicIpNextButton;
	}
	public Select OpenPortVmNameDropdown()
	{
		return new Select(OpenPortVMName);
	}
	public Select OpenPortPublicIpAddressDropdown()
	{
		return new Select(OpenPortPublicIpAddress);
	}
	public WebElement OpenPortTextBox()
	{
		return OpenPortTextBox;
	}
	public WebElement Portlabel()
	{
		return Portlabel;
	}
	public List<WebElement> OpenPortTextBoxError() {
		return OpenPortTextBoxError;
	}
	public WebElement OpenPortTextBoxError2() {
		return OpenPortTextBoxError2;
	}
	public WebElement OpenPortreason()
	{
		return OpenPortreason;
	}
	public WebElement TypeLabel()
	{
		return TypeLabel;
	}
	public List<WebElement> ReasonTextAreaError()
	{
		return ReasonTextAreaError;
	}
	public WebElement TCPtype()
	{
		return TCPtype;
	}
	public WebElement UDptype()
	{
		return UDPtype;
	}
	public Select OpenPortDurationDropDown()
	{
		return new Select(OpenPortDuration);
	}
	public Select ClosePortVMNameDropDown()
	{
		return new Select(ClosePortVMName);
	}
	public WebElement ClosePortPortError()
	{
		return ClosePortPortError;
	}
	public WebElement ClosePortReasonTextArea()
	{
		return ClosePortReasonTextArea;
	}
	public List<WebElement> ClosePortReasonTextAreaError()
	{
		return ClosePortReasonTextAreaError;
	}
	public WebElement ClosePortReasonLabel()
	{
		return ClosePortReasonLabel;
	}
	public WebElement ClosePortTCPtype()
	{
		return ClosePortTCPtype;
	}
	public WebElement ClosePortUDPtype()
	{
		return ClosePortUDPtype;
	}
	public Select ClosePortDurationDropDown()
	{
		return new Select(ClosePortDuration);
	}
	public Select FromVlanDropDown()
	{
		return new Select(FromVlan);
	}
	public Select ToVlanDropDown()
	{
		return new Select(ToVlan);
	}
	public WebElement UniDirectionalType()
	{
		return UniDirectionalType;
	}
	public WebElement BiDirectionalType()
	{
		return BiDirectionalType;
	}
	public Select VlanCommunicationDurationDropdown()
	{
		return new Select(VlanCommunicationDuration);
	}
	public List<WebElement> VlanCommunicationErrors()
	{
		return VlanCommunicationErrors;
	}
	 

}
